package com.github.xuqplus2.blog.config;

import com.github.xuqplus2.blog.config.kz.AppDefaultUserAuthenticationConverter;
import com.github.xuqplus2.blog.filter.AppLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AppLoginFilter appLoginFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**
                 * target, before <code>${@link OAuth2ClientAuthenticationProcessingFilter}</code>
                 */
                .addFilterBefore(appLoginFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/blog/article**/**").hasAuthority("article")
                .antMatchers("/",
                        "/blog/auth**/**",
                        "/blog/test**/**",
                        "/blog/public**/**",
                        "/blog/login**/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginProcessingUrl("/blog/login").and()
                // there's an endpoint
                // .logout().logoutUrl("/blog/auth/logout").permitAll().and()
                .csrf().disable();
    }

    @Autowired
    public void CheckTokenEndpoint(RemoteTokenServices services) {
        DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
        converter.setUserTokenConverter(new AppDefaultUserAuthenticationConverter());
        services.setAccessTokenConverter(converter);
    }
}
