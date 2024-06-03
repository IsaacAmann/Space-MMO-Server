package com.SpaceMMO;

import com.SpaceMMO.Services.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpaceMmoApplication {
	@Autowired
	RateLimitService rateLimitService;

	public static void main(String[] args)
	{
		SpringApplication application = new SpringApplication(SpaceMmoApplication.class);
		application.setAdditionalProfiles("dev");
		application.run(args);
	}
	@Configuration
	@EnableWebMvc
	public class Config implements WebMvcConfigurer
	{
		//Allows cross origin resource sharing (CORS)
		@Override
		public void addCorsMappings(CorsRegistry registry)
		{
			registry.addMapping("/**");
		}
		@Override
		public void addInterceptors(InterceptorRegistry registry)
		{
			RateLimitService.RateLimitInterceptor interceptor = rateLimitService.getInterceptor();

			registry.addInterceptor(interceptor).addPathPatterns("/*");
		}

	}
}
