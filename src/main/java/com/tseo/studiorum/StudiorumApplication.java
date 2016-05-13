package com.tseo.studiorum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
public class StudiorumApplication {
	
	public static void main( String[] args){
		
		final String[] CLASSPATH_RESOURCE_LOCATIONS = {
				"classpath:/META-INF/resources/", "classpath:/webapp/resources/",
				"classpath:/static/" };
		
		
		SpringApplication.run(StudiorumApplication.class, args);
	}
}
