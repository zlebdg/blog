package com.github.xuqplus2.javawebdemo.filter;

import com.github.xuqplus2.javawebdemo.domain.oauth.OAuthCallbackAddress;
import com.github.xuqplus2.javawebdemo.repository.OAuthCallbackAddressRepository;
import com.github.xuqplus2.javawebdemo.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 拿到并记录<code>referer</code>
 * 在callback时重定向到<code>referer</code>
 */
@Slf4j
@Component
public class AppLoginFilter implements Filter {

    @Autowired
    OAuthCallbackAddressRepository callbackAddressRepository;
    @Autowired
    EncryptService encryptService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if ("/login".equalsIgnoreCase(((HttpServletRequest) request).getRequestURI())) {
                String referer = httpServletRequest.getHeader("Referer");
                String sessionId = httpServletRequest.getSession().getId();
                String encryptSessionId = encryptService.sha256Md5(sessionId);
                if (!callbackAddressRepository.existsByEncryptSessionIdAndIsDeletedFalse(encryptSessionId)) {
                    OAuthCallbackAddress callbackAddress = new OAuthCallbackAddress(encryptSessionId, referer);
                    callbackAddressRepository.save(callbackAddress);
                    log.info("sessionId=>{}, encryptSessionId=>{}, referer=>{}", sessionId, encryptSessionId, referer);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
