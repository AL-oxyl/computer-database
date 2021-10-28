package com.oxyl.execution;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@ComponentScan({"com.oxyl.validator",
	            "com.oxyl.dao",
	            "com.oxyl.controller",
	            "com.oxyl.dao.bddmapper",
	            "com.oxyl.mapper",
	            "com.oxyl.service"})
public class WebConfig extends DelegatingWebMvcConfiguration {
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
		resourceViewResolver.setPrefix("/WEB-INF/views/");
		resourceViewResolver.setSuffix(".jsp");
		return resourceViewResolver;
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
}
