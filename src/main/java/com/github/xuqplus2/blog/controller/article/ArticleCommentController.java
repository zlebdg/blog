package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.AnonymousUser;
import com.github.xuqplus2.blog.domain.ArticleComment;
import com.github.xuqplus2.blog.domain.User;
import com.github.xuqplus2.blog.repository.*;
import com.github.xuqplus2.blog.util.AppNotLoginException;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.vo.req.ArticleCommentReq;
import com.github.xuqplus2.blog.vo.resp.ArticleCommentResp;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("articleComment")
public class ArticleCommentController {

    @Autowired
    ArticleCommentRepository articleCommentRepository;
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
        ArticleComment articleComment = new ArticleComment(req);
        if (null != articleComment.getText()) {
            appTextRepository.save(articleComment.getText());
        }
        try {
            User user = CurrentUserUtil.currentUser(userRepository);
            articleComment.setAuthor(user);
        } catch (AppNotLoginException e) {
            AnonymousUser anonymousUser = new AnonymousUser();
            anonymousUser.setId(UUID.randomUUID().toString());
            anonymousUser.setIp("xxxx");
            anonymousUser.setUsername("一位不愿透露姓名的网友");
            anonymousUserRepository.save(anonymousUser);
            articleComment.setAnonymousAuthor(anonymousUser);
        }
        articleCommentRepository.save(articleComment);
        articleCommentRepository.updateArticleIdAndReplyToId(req.getArticleId(), req.getReplyToId(), articleComment.getId());
        ArticleCommentResp resp = new ArticleCommentResp(articleComment);
        return BasicResp.ok();
    }
}
