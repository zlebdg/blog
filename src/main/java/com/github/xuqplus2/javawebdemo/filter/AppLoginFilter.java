package com.github.xuqplus2.javawebdemo.filter;

import com.github.xuqplus2.javawebdemo.domain.oauth.OAuthCallbackAddress;
import com.github.xuqplus2.javawebdemo.repository.OAuthCallbackAddressRepository;
import com.github.xuqplus2.javawebdemo.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拿到并记录<code>referer</code>
 * 在callback时重定向到<code>referer</code>
 * <p>
 * 具体实现方法:
 * --自定义filter类, 注册到目标filter前
 * --catch到<code>UserRedirectRequiredException</code> 记录state和referer
 * --callback时根据state拿到referer
 * --重定向
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
                String state = request.getParameter("state");
                if (StringUtils.isEmpty(state)) {
                    try {
                        chain.doFilter(request, response);
                    } catch (UserRedirectRequiredException e) {
                        String key = e.getStateKey();
                        OAuthCallbackAddress callbackAddress = new OAuthCallbackAddress(key, referer);
                        callbackAddressRepository.saveAndFlush(callbackAddress);
                        log.info("key=>{}, key=>{}, referer=>{}", key, key, referer);
                        throw e;
                    }
                    return;
                } else {
                    if (callbackAddressRepository.existsByIdAndIsDeletedFalse(state)) {
                        OAuthCallbackAddress address = callbackAddressRepository.getByIdAndIsDeletedFalse(state);
                        log.info("address=>{}", address);
                        if (null != address && !StringUtils.isEmpty(address.getReferer()) && !address.getReferer().equals(referer)) {
                            if (response instanceof HttpServletResponse) {
                                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                                httpServletResponse
                                        .sendRedirect(String.format("%s#/oauth/callbackPage?%s",
                                                address.getReferer(), httpServletRequest.getQueryString()));
                                return;
                            }
                        }
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
