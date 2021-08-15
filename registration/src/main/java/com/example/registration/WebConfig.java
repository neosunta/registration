package com.example.registration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
@Autowired
private JwtAuthorizeInterceptor jwtRequestInterceptor;

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addInterceptors(InterceptorRegistry registry) {
	            registry.addInterceptor(jwtRequestInterceptor); //add jwtRequestInterceptor
	        	
	        }
	        
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**");
	        }
	    };
	}
}