package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.AppText;
import com.github.xuqplus2.blog.domain.Article;
import com.github.xuqplus2.blog.repository.AppTextRepository;
import com.github.xuqplus2.blog.repository.ArticleRepository;
import com.github.xuqplus2.blog.vo.req.ArticleReq;
import com.github.xuqplus2.blog.vo.resp.ArticleResp;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RequestMapping("article")
@RestController
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    AppTextRepository appTextRepository;

    @GetMapping
    public ResponseEntity query(Long id) {
        Article article = articleRepository.getById(id);
        if (null != article)
            return BasicResp.ok(new ArticleResp(article));
        throw new RuntimeException("文章不存在");
    }

    @PostMapping
    @Transactional
    public ResponseEntity post(ArticleReq article) {
        if (null == article.getId()) {
            /* hash 防重复提交, todo, hash碰撞 */
            if (!articleRepository.existsByHash(article.getHash())) {
                AppText appText = new AppText(article.getText());
                appTextRepository.save(appText);
                Article newArticle = new Article(article);
                newArticle.setAppText(appText);
                articleRepository.save(newArticle);
                return BasicResp.ok(newArticle.getId());
            }
            throw new RuntimeException("hash碰撞, hash=" + article.getHash());
        } else {
            Article saved = articleRepository.getById(article.getId());
            if (null != saved.getAppText()) {
                AppText appText = saved.getAppText();
                appText.setText(article.getText());
                appTextRepository.save(appText);
            }
            saved.set(article);
            articleRepository.saveAndFlush(saved);
            return BasicResp.ok();
        }
    }

    @GetMapping("query")
    public ResponseEntity query(PageRequest pageRequest) {
        return null;
    }
}
