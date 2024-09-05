package edu.pnu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// http://localhost:8080/images/이미지파일명
		registry.addResourceHandler("/images/**")
			.addResourceLocations("classpath:static/images/","file:c:/Temp/upload/");

		// http://localhost:8080/images1/이미지파일명
		registry.addResourceHandler("/images1/**")
			.addResourceLocations("file:c:/Temp/upload1/");
	}
}
