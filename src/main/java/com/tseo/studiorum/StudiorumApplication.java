package com.tseo.studiorum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tseo.studiorum.filters.JwtFilter;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@ComponentScan
@Configuration
public class StudiorumApplication {

    @Bean
    public FilterRegistrationBean student() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

	public static void main( String[] args){
		SpringApplication.run(StudiorumApplication.class, args);
	}
}
