package com.github.xuqplus2.blog.controller.sign;

import com.github.xuqplus2.blog.domain.Document;
import com.github.xuqplus2.blog.repository.DocumentRepository;
import com.github.xuqplus2.blog.repository.UserRepository;
import com.github.xuqplus2.blog.service.SignService;
import com.github.xuqplus2.blog.util.AppNotLoginException;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.vo.req.SignReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping({"sign"})
public class SignController {

    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SignService signService;

    @GetMapping("signed/list")
    public ResponseEntity signedList(Pageable pageable) throws AppNotLoginException {
        Page<Document> documents = documentRepository.findAllByUserOrderByCreateAtDesc(CurrentUserUtil.currentUser(userRepository), pageable);
        return ResponseEntity.ok().body(documents);
    }

    @Transactional
    @PostMapping
    public ResponseEntity sign(@RequestBody SignReq signReq) throws Exception {
        signService.sign(signReq);
        return ResponseEntity.ok().body("签署成功");
    }
}
