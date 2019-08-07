package com.github.xuqplus2.javawebdemo.config;

import com.github.xuqplus2.javawebdemo.filter.AppLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AppLoginFilter appLoginFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // target, before OAuth2ClientAuthenticationProcessingFilter
                .addFilterBefore(appLoginFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/",
                        "/auth**/**",
                        "/login**").permitAll()
                .anyRequest().authenticated().and()
                .csrf().disable();
    }
}
