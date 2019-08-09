package com.github.xuqplus2.blog.repository;

import com.github.xuqplus2.blog.domain.oauth.OAuthCallbackAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface OAuthCallbackAddressRepository extends JpaRepository<OAuthCallbackAddress, String> {

    boolean existsByIdAndIsDeletedFalse(String id);

    OAuthCallbackAddress getByIdAndIsDeletedFalse(String id);

    @Transactional
    @Modifying
    @Query("update OAuthCallbackAddress a set a.referer = ?1, a.updateAt = ?2 where a.id = ?3")
    void updateRefererById(String referer, long l, String id);
}
