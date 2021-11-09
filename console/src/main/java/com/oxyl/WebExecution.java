package com.oxyl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan({"com.oxyl.execution",
				"com.oxyl.controller",
	            "com.oxyl.dao",
	            "com.oxyl.mapper",
	            "com.oxyl.service",
	            "com.oxyl.validator",
	            "com.oxyl.ui"})
public class WebExecution extends AbstractContextLoaderInitializer {
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebExecution.class);
		return context;
	}
}
