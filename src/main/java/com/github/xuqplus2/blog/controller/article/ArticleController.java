package com.github.xuqplus2.blog.controller.article;

import com.github.xuqplus2.blog.domain.AppText;
import com.github.xuqplus2.blog.domain.Article;
import com.github.xuqplus2.blog.domain.ArticleInfo;
import com.github.xuqplus2.blog.domain.User;
import com.github.xuqplus2.blog.repository.AppTextRepository;
import com.github.xuqplus2.blog.repository.ArticleInfoRepository;
import com.github.xuqplus2.blog.repository.ArticleRepository;
import com.github.xuqplus2.blog.repository.UserRepository;
import com.github.xuqplus2.blog.util.AppNotLoginException;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.vo.req.ArticleReq;
import com.github.xuqplus2.blog.vo.resp.ArticleResp;
import com.github.xuqplus2.blog.vo.resp.BasicResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping({"blog/article", "blog/public/article"})
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
    public ResponseEntity post(@Valid ArticleReq article, BindingResult bindingResult) throws AppNotLoginException {
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

    /**
     * 文章分页查询接口,
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("query")
    public ResponseEntity query(String userId, Integer page, Integer size) {
        if (!StringUtils.isEmpty(userId)) {
            Sort sort = JpaSort.by(Sort.Direction.DESC, "createAt");
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            Page<Article> articles = articleRepository.findAllByAuthorId(userId, pageRequest);
            List<ArticleResp> collect = articles.stream().map(a -> {
                return new ArticleResp(a, true);
            }).collect(Collectors.toList());
            return BasicResp.ok(new PageImpl(collect, pageRequest, articles.getTotalElements()));
        } else {
            Sort sort = JpaSort.by(Sort.Direction.DESC, "createAt");
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            Page<Article> articles = articleRepository.findAll(pageRequest);
            List<ArticleResp> collect = articles.stream().map(a -> {
                return new ArticleResp(a, true);
            }).collect(Collectors.toList());
            return BasicResp.ok(new PageImpl(collect, pageRequest, articles.getTotalElements()));
        }
    }
}
