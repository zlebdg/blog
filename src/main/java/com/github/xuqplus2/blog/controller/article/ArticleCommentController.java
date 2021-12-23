package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.AnonymousUser;
import com.github.xuqplus2.blog.domain.ArticleComment;
import com.github.xuqplus2.blog.domain.ArticleInfo;
import com.github.xuqplus2.blog.domain.User;
import com.github.xuqplus2.blog.repository.*;
import com.github.xuqplus2.blog.util.AppNotLoginException;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.util.RequestUtil;
import com.github.xuqplus2.blog.vo.req.ArticleCommentReq;
import com.github.xuqplus2.blog.vo.resp.ArticleCommentResp;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping({"blog/articleComment", "blog/public/articleComment"})
public class ArticleCommentController {

    @Autowired
    ArticleCommentRepository articleCommentRepository;
    @Autowired
    ArticleInfoRepository articleInfoRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    AppTextRepository appTextRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnonymousUserRepository anonymousUserRepository;

    @Transactional
    @PostMapping
    public ResponseEntity post(@Valid ArticleCommentReq req, BindingResult bindingResult) {
        if (!articleRepository.existsById(req.getArticleId())) {
            throw new RuntimeException("文章不存在, id=" + req.getArticleId());
        }
        if (null != req.getReplyToId() && !articleCommentRepository.existsById(req.getReplyToId())) {
            throw new RuntimeException("回复的评论不存在, id=" + req.getReplyToId());
        }
        // 评论数加
        ArticleInfo articleInfo = articleInfoRepository.getById(req.getArticleId());
        articleInfo.commentPlus();
        // 保存评论数据
        ArticleComment articleComment = new ArticleComment(req);
        if (null != articleComment.getAppText()) {
            appTextRepository.save(articleComment.getAppText());
        }
        try {
            User user = CurrentUserUtil.currentUser(userRepository);
            articleComment.setAuthor(user);
        } catch (AppNotLoginException e) {
            String remoteIp = RequestUtil.getRemoteIp();
            AnonymousUser anonymousUser = new AnonymousUser();
            anonymousUser.setId(UUID.randomUUID().toString());
            // todo, xxf 获取请求ip
            anonymousUser.setIp(remoteIp);
            anonymousUser.setUsername("不愿透露姓名的网友");
            anonymousUserRepository.save(anonymousUser);
            articleComment.setAnonymousAuthor(anonymousUser);
        }
        articleCommentRepository.save(articleComment);
        articleInfoRepository.save(articleInfo);
        articleCommentRepository.updateArticleIdAndReplyToId(req.getArticleId(), req.getReplyToId(), articleComment.getId());
        ArticleCommentResp resp = new ArticleCommentResp(articleComment, articleCommentRepository);
        return BasicResp.ok(resp);
    }

    @GetMapping
    public ResponseEntity comments(Long id, Integer page, Integer size) {
        Sort sort = JpaSort.by(Sort.Direction.DESC, "createAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<ArticleComment> r = articleCommentRepository.findAllByArticleIdAndReplyToNull(id, pageRequest);
        List<ArticleCommentResp> collect = r.stream().map(c -> {
            return new ArticleCommentResp(c, articleCommentRepository);
        }).collect(Collectors.toList());
        return BasicResp.ok(new PageImpl(collect, pageRequest, r.getTotalElements()));
    }
}
