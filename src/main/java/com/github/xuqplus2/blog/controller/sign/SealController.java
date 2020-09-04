package com.github.xuqplus2.blog.controller.sign;

import com.github.xuqplus2.blog.domain.Seal;
import com.github.xuqplus2.blog.repository.OssFileRepository;
import com.github.xuqplus2.blog.repository.SealRepository;
import com.github.xuqplus2.blog.repository.UserRepository;
import com.github.xuqplus2.blog.util.AppNotLoginException;
import com.github.xuqplus2.blog.util.CurrentUserUtil;
import com.github.xuqplus2.blog.vo.resp.CurrentUser;
import org.springframework.beans.BeanUtils;
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
@RequestMapping({"seal"})
public class SealController {

    @Autowired
    SealRepository sealRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OssFileRepository ossFileRepository;

    @GetMapping("list")
    public ResponseEntity list(Pageable pageable) throws AppNotLoginException {
        Page<Seal> seals = sealRepository.findAllByUserOrderByCreateAtDesc(CurrentUserUtil.currentUser(userRepository), pageable);
        return ResponseEntity.ok().body(seals);
    }

    @Transactional
    @PostMapping("create")
    public ResponseEntity create(@RequestBody Seal seal) throws AppNotLoginException {
        seal.setUser(CurrentUserUtil.currentUser(userRepository));
        if (null != seal.getFile() && null != seal.getFile().getId()) {
            seal.setFile(ossFileRepository.getOne(seal.getFile().getId()));
        }
        sealRepository.save(seal);
        return ResponseEntity.ok().body(seal);
    }
}
