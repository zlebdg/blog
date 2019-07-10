package com.github.xuqplus2.javawebdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/",
                    "/index.html",
                    "/oauth/**",
                    "/login**").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().permitAll()
            .and().logout().permitAll()
            .and().csrf().disable();
  }
}
