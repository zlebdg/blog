package com.github.xuqplus2.blog.service.impl;

import com.github.xuqplus2.blog.domain.Document;
import com.github.xuqplus2.blog.domain.OssFile;
import com.github.xuqplus2.blog.domain.Seal;
import com.github.xuqplus2.blog.repository.DocumentRepository;
import com.github.xuqplus2.blog.repository.OssFileRepository;
import com.github.xuqplus2.blog.repository.SealRepository;
import com.github.xuqplus2.blog.repository.UserRepository;
import com.github.xuqplus2.blog.service.QiniuService;
import com.github.xuqplus2.blog.service.SignService;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.vo.req.SignReq;
import com.github.xuqplus2.blog.vo.req.Stamper;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SignServiceImpl implements SignService {

    @Autowired
    SealRepository sealRepository;
    @Autowired
    OssFileRepository ossFileRepository;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DocumentRepository documentRepository;

    // 签名要素
    private static String ksFile = "ks";
    private static char[] password = "123123".toCharArray();
    private static String contact = "445172495@qq.com";
    private static String reason = "-_-.测试签名不需要理由.-_-";
    private static String location = "闵行浦江镇";
    private static String digestAlgorithm = DigestAlgorithms.SHA256;
    private static MakeSignature.CryptoStandard subfilter = MakeSignature.CryptoStandard.CADES;

    @Override
    public void sign(SignReq signReq) throws Exception {
        List<Document> documents = signReq.getDocuments();
        List<Stamper> stampers = signReq.getStampers();
        Map<Integer, List<Stamper>> docStamperMap = new HashMap<>();
        for (Stamper stamper : stampers) {
            Integer docIndex = stamper.getDocIndex();
            if (!docStamperMap.containsKey(docIndex)) {
                docStamperMap.put(docIndex, new ArrayList<>());
            }
            docStamperMap.get(docIndex).add(stamper);
        }
        for (int i = 0; i < documents.size(); i++) {
            Document pdfDoc = documents.get(i);
            OssFile file = ossFileRepository.getOne(pdfDoc.getId());
            String download = qiniuService.download(file.getFileKey());
            InputStream is = new UrlResource(download).getInputStream();
            ByteArrayOutputStream os = signADocument(is, docStamperMap.get(i));
            if (null == os) {
                continue;
            }
            ByteArrayInputStream signedIs = new ByteArrayInputStream(os.toByteArray());
            String signedFileKey = qiniuService.upload(signedIs);
            OssFile signedFile = new OssFile();
            signedFile.setUploadBy(CurrentUserUtil.currentUser(userRepository));
            signedFile.setFileKey(signedFileKey);
            signedFile.setFilename(file.getFilename());
            ossFileRepository.save(signedFile);
            Document signedDoc = new Document();
            signedDoc.setFile(signedFile);
            signedDoc.setUser(CurrentUserUtil.currentUser(userRepository));
            signedDoc.setType(Document.Type.SIGNED);
            documentRepository.save(signedDoc);
        }
    }

    public ByteArrayOutputStream signADocument(InputStream is, List<Stamper> stampers) throws Exception {
        if (CollectionUtils.isEmpty(stampers)) {
            return null;
        }
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream stream = new ClassPathResource(ksFile).getInputStream()) {
            ks.load(stream, password);
        }
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, password);
        Certificate[] chain = ks.getCertificateChain(alias);
        PdfReader reader = new PdfReader(is);
        for (int i = 0, stampersSize = stampers.size(); i < stampersSize; i++) {
            Stamper stamperDesc = stampers.get(i);
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                // 计算签名矩形
                Rectangle pageSize = reader.getPageSize(stamperDesc.getPageIndex() + 1);
                float height = pageSize.getHeight();
                float ury = height - stamperDesc.getTop();
                float llx = stamperDesc.getLeft();
                float lly = ury - stamperDesc.getHeight();
                float urx = llx + stamperDesc.getWidth();
                Rectangle rectangle = new Rectangle(llx, lly, urx, ury);
                rectangle = handleRotation(rectangle, stamperDesc.getRotateAngle());

                // 可追加签名
                PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
                PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
                appearance.setReason(reason);
                appearance.setLocation(location);
                appearance.setContact(contact);
                appearance.setSignatureCreator(contact);
                appearance.setVisibleSignature(rectangle, stamperDesc.getPageIndex() + 1, "sig-" + System.currentTimeMillis());
                appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC); // 显示图片不显示描述
                appearance.setSignDate(GregorianCalendar.getInstance());
                appearance.setLayer2Text("haha");
                appearance.setLayer4Text("haha");

                Seal seal = sealRepository.getOne(stamperDesc.getSealId());
                String filenameExt = StringUtils.getFilenameExtension(seal.getFile().getFilename());
                try (InputStream sealImage = new UrlResource(qiniuService.download(seal.getFile().getFileKey())).getInputStream()) {
                    ByteArrayOutputStream image = new ByteArrayOutputStream();
                    Thumbnails.of(sealImage)
                            .forceSize(stamperDesc.getWidth() * 10, stamperDesc.getHeight() * 10)
                            .rotate(stamperDesc.getRotateAngle())
                            .outputFormat(filenameExt)
                            .toOutputStream(image);
                    appearance.setSignatureGraphic(Image.getInstance(image.toByteArray()));
                }

                // 摘要
                ExternalDigest digest = new BouncyCastleDigest();
                // 签名
                ExternalSignature signature = new PrivateKeySignature(pk, digestAlgorithm, provider.getName());
                MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, subfilter);
                boolean isLast = i >= stampersSize - 1;
                if (isLast) {
                    // 返回
                    return os;
                } else {
                    // 重读
                    reader = new PdfReader(new ByteArrayInputStream(os.toByteArray()));
                }
            }
        }
        return null;
    }

    // 旋转之后签名框变大
    private static final float ADJUSTMENT = 0;

    public static Rectangle handleRotation(Rectangle rectangle, float rotationDegrees) {
        if (rotationDegrees % 180 == 0) {
            return rectangle;
        }
        float llx = rectangle.getLeft() + ADJUSTMENT;
        float lly = rectangle.getBottom() + ADJUSTMENT;
        float urx = rectangle.getRight() - ADJUSTMENT;
        float ury = rectangle.getTop() - ADJUSTMENT;

        float width = urx - llx;
        float height = ury - lly;
        float adjustX = getAdjustX(width, height, rotationDegrees);
        float adjustY = getAdjustY(width, height, rotationDegrees);

        return new Rectangle(llx - adjustX - ADJUSTMENT, lly - adjustY - ADJUSTMENT, urx + adjustX + ADJUSTMENT, ury + adjustY + ADJUSTMENT, rectangle.getRotation());
    }

    private static float getAdjustX(float width, float height, float rotationDegrees) {
        double degrees = getDegrees(rotationDegrees);
        double cos = Math.cos(degrees);
        double sin = Math.sin(degrees);
        return (float) ((cos * width + sin * height - width) / 2);
    }

    private static float getAdjustY(float width, float height, float rotationDegrees) {
        double degrees = getDegrees(rotationDegrees);
        double cos = Math.cos(degrees);
        double sin = Math.sin(degrees);
        return (float) (sin * width + cos * height - height) / 2;
    }

    private static double getDegrees(float rotationDegrees) {
        float degrees = rotationDegrees;
        degrees = degrees % 360;
        if (degrees < 0) {
            degrees = degrees + 360;
        }
        if (degrees > 180) {
            degrees = degrees - 180;
        }
        if (degrees > 90) {
            degrees = 180 - degrees;
        }
        return Math.PI / 180 * degrees;
    }
}
