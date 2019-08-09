package com.github.xuqplus2.blog.domain.oauth;

import com.github.xuqplus2.blog.domain.BasicDomain;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class OAuthCallbackAddress extends BasicDomain {
    @Id
    private String id;
    private String referer;

    public OAuthCallbackAddress() {
    }

    public OAuthCallbackAddress(String id, String referer) {
        this.id = id;
        this.referer = referer;
    }
}
