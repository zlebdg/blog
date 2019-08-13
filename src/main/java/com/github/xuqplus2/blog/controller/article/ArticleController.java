package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.AppText;
import com.github.xuqplus2.blog.domain.Article;
import com.github.xuqplus2.blog.domain.ArticleInfo;
import com.github.xuqplus2.blog.domain.User;
import com.github.xuqplus2.blog.repository.AppTextRepository;
import com.github.xuqplus2.blog.repository.ArticleInfoRepository;
import com.github.xuqplus2.blog.repository.ArticleRepository;
import com.github.xuqplus2.blog.repository.UserRepository;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.vo.req.ArticleReq;
import com.github.xuqplus2.blog.vo.resp.ArticleResp;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RequestMapping("article")
@RestController
@Transactional
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    AppTextRepository appTextRepository;
    @Autowired
    ArticleInfoRepository articleInfoRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity query(Long id) {
        Article article = articleRepository.getById(id);
        if (null != article) {
            if (null == article.getArticleInfo()) {
                ArticleInfo info = new ArticleInfo();
                info.setId(article.getId());
                articleInfoRepository.save(info);
                article.setArticleInfo(info);
                articleRepository.save(article);
            }
            return BasicResp.ok(new ArticleResp(article));
        }
        throw new RuntimeException("文章不存在");
    }

    @PostMapping
    @Transactional
    public ResponseEntity post(@Valid ArticleReq article, BindingResult bindingResult) {
        if (null == article.getId()) {
            /* hash 防重复提交, todo, hash碰撞 */
            if (!articleRepository.existsByHash(article.getHash())) {
                // 文章作者
                User user = CurrentUserUtil.currentUser(userRepository);
                // 文章内容
                AppText appText = new AppText(article.getText());
                appTextRepository.save(appText);
                // 文章
                Article newArticle = new Article(article);
                newArticle.setAuthor(user);
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
