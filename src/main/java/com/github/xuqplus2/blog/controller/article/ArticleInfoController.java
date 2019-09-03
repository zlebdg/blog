package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.ArticleInfo;
import com.github.xuqplus2.blog.domain.User;
import com.github.xuqplus2.blog.domain.UserLiked;
import com.github.xuqplus2.blog.repository.*;
import com.github.xuqplus2.blog.util.AppNotLoginException;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.vo.resp.ArticleInfoResp;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Slf4j
@RestController
@RequestMapping({"articleInfo", "public/articleInfo"})
public class ArticleInfoController {

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
    @Autowired
    UserLikedRepository userLikedRepository;

    @Transactional
    @PostMapping("readPlus")
    public ResponseEntity readPlus(Long id) {
        ArticleInfo info = articleInfoRepository.getById(id);
        info.readPlus();
        articleInfoRepository.save(info);
        return BasicResp.ok(info.getRead());
    }

    @Transactional
    @PostMapping("like")
    public ResponseEntity like(Long id) throws AppNotLoginException {
        ArticleInfo info = articleInfoRepository.getById(id);
        User user = CurrentUserUtil.currentUser(userRepository);
        UserLiked userLiked = userLikedRepository.getByArticleInfoIdAndUserId(id, user.getId());
        if (null == userLiked) {
            userLiked = new UserLiked(info, user);
            info.likePlus();
            userLikedRepository.save(userLiked);
            articleInfoRepository.save(info);
        }
        return BasicResp.ok(new ArticleInfoResp(info, user.getId(), true, null, null));
    }
}
