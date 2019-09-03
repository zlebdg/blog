package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.ArticleInfo;
import com.github.xuqplus2.blog.repository.*;
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

    @Transactional
    @PostMapping("readPlus")
    public ResponseEntity readPlus(Long id) {
        ArticleInfo info = articleInfoRepository.getById(id);
        info.readPlus();
        articleInfoRepository.save(info);
        return BasicResp.ok(info.getRead());
    }
}
