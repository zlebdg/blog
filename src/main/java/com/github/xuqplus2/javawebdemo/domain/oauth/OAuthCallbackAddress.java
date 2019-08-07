package com.github.xuqplus2.javawebdemo.domain.oauth;

import com.github.xuqplus2.javawebdemo.domain.BasicDomain;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class OAuthCallbackAddress extends BasicDomain {
    @Id
    private String encryptSessionId; // sessionId散列
    private String referer;

    public OAuthCallbackAddress() {
    }

    public OAuthCallbackAddress(String sessionId, String referer) {
        this.encryptSessionId = sessionId;
        this.referer = referer;
    }
}
