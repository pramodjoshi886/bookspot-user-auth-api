package com.allstate.BookSpotApp;

import javax.servlet.Filter;

import com.allstate.BookSpotApp.jwtfilter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:dbconfig.properties")
public class BookSpotAppApplication {
	@Bean
	public FilterRegistrationBean jwtFilter() {
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>();
		bean.setFilter(new AuthFilter());
		bean.addUrlPatterns("/api/book-spot/*");
		return bean;
	}
	public static void main(String[] args) {
		SpringApplication.run(BookSpotAppApplication.class, args);
	}

}
