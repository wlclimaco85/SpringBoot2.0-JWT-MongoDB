package com.freebies.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan("com.freebies")
@EnableWebMvc
@Import(DispatcherInitializer.class)
public class FreebiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreebiesApplication.class, args);
	}
	
	
}
