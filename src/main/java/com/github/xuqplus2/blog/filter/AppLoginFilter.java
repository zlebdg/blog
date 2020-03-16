package com.github.xuqplus2.blog.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拿到并记录<code>referer</code>
 * 在callback时重定向到<code>referer</code>
 * <p>
 * 具体实现方法:
 * --自定义filter类, 注册到目标filter前
 * --catch到<code>UserRedirectRequiredException</code> 记录referer
 * --重定向
 */
@Slf4j
@Component
public class AppLoginFilter implements Filter {

    private static final String CALLBACK_URL = AppLoginFilter.class.getName();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            if ("/login".equalsIgnoreCase(((HttpServletRequest) request).getRequestURI())) {
                String referer = httpServletRequest.getHeader("referer");
                String state = request.getParameter("state");
                if (StringUtils.isEmpty(state)) {
                    try {
                        chain.doFilter(request, response);
                    } catch (UserRedirectRequiredException e) {
                        httpServletRequest.getSession().setAttribute(CALLBACK_URL, referer);
                        String key = e.getStateKey();
                        log.info("key=>{}, referer=>{}", key, referer);
                        throw e;
                    }
                } else {
                    Object o = httpServletRequest.getSession().getAttribute(CALLBACK_URL);
                    if (null != o && o instanceof String && !o.equals(referer)) {
                        httpServletResponse.sendRedirect(String.format("%s#/oauth/callbackPage?%s",
                                o, httpServletRequest.getQueryString()));
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
