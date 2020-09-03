package com.github.xuqplus2.blog.controller.sign;

import com.github.xuqplus2.blog.domain.OssFile;
import com.github.xuqplus2.blog.repository.OssFileRepository;
import com.github.xuqplus2.blog.repository.UserRepository;
import com.github.xuqplus2.blog.service.QiniuService;
import com.github.xuqplus2.blog.util.AppNotLoginException;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping({"file"})
public class FileController {

    @Autowired
    QiniuService qiniuService;
    @Autowired
    OssFileRepository ossFileRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("upload")
    public ResponseEntity upload(MultipartFile[] files, MultipartFile file) throws IOException, AppNotLoginException {
        if (null != files && files.length > 0) {
            List<OssFile> ossFiles = new ArrayList<>(files.length);
            for (MultipartFile f : files) {
                OssFile ossFile = upload(f);
                ossFiles.add(ossFile);
            }
            return ResponseEntity.ok().body(ossFiles);
        }
        if (null != file) {
            OssFile ossFile = upload(file);
            return ResponseEntity.ok().body(ossFile);
        }
        return ResponseEntity.ok().build();
    }

    private OssFile upload(MultipartFile file) throws IOException, AppNotLoginException {
        String filename = file.getOriginalFilename();
        String key = qiniuService.upload(file.getInputStream());
        OssFile ossFile = new OssFile();
        ossFile.setFileKey(key);
        ossFile.setFilename(filename);
        ossFile.setUploadBy(CurrentUserUtil.currentUser(userRepository));
        ossFileRepository.save(ossFile);
        return ossFile;
    }

    @GetMapping("download")
    public ResponseEntity download(Long id) throws IOException {
        OssFile ossFile = ossFileRepository.getOne(id);
        String fileKey = ossFile.getFileKey();
        String filename = URLEncoder.encode(ossFile.getFilename(), "utf8");
        String url = qiniuService.download(fileKey);
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(filename);
        return ResponseEntity.ok()
                .contentType(mediaType.orElse(MediaType.APPLICATION_OCTET_STREAM))
                .header("Content-Disposition", "attachment;fileName=" + filename)
                .body(new UrlResource(url));
    }
}
