package com.project.movie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.password:}")
	private String password;

	@Value("${spring.data.redis.ssl.enabled:false}")
	private boolean sslEnabled;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
		if (!password.isEmpty()) {
			config.setPassword(password);
		}

		LettuceClientConfiguration clientConfig = sslEnabled
				? LettuceClientConfiguration.builder().useSsl().build()
				: LettuceClientConfiguration.defaultConfiguration();

		return new LettuceConnectionFactory(config, clientConfig);
	}
}
