package com.freebies.config;

import java.util.concurrent.ExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

	protected static int nbThreadPool = 5;

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
		tpte.setCorePoolSize(nbThreadPool);
		tpte.setMaxPoolSize(nbThreadPool * 2);
		tpte.setQueueCapacity(nbThreadPool * 10);
		return tpte;
	}
	
	@Bean
	public ExecutorService executorService() {
		return new ExecutorServiceAdapter(taskExecutor());
	}

}
