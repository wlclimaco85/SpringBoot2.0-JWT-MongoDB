package com.freebies.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
@EnableMongoRepositories(mongoTemplateRef = "mongoTemplate",basePackages ="com.freebies.dao")
public class PrimaryMongoConfig extends AbstractMongoConfig {

	@Primary
	@Override
	@Bean(name = "mongoTemplate")
	public MongoTemplate getMongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}
}
