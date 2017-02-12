package com.hamesc.opl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hamesc.opl.service.GithubService;

@Configuration
@ComponentScan("com.hamesc.service")
public class ServiceConfiguration {

	@Bean
	public GithubService githubService() {
		return new GithubService();
	}
}