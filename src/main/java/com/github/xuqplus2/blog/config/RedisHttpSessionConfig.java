package com.github.xuqplus2.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
public class RedisHttpSessionConfig {
}
