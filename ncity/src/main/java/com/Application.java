package com;


import javax.servlet.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import com.framwork.filter.CustomFilter;

/**
 * 此类必须在所有需要注入类的顶层，不然不能注入。
 * @author YXD110
 *
 */
@Controller
@EnableScheduling
@SpringBootApplication
public class Application {

	/**
	 * 启动main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 配置过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(customFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("customFilter");
		return registration;
	}

	/**
	 * 创建一个bean
	 * 
	 * @return
	 */
	@Bean(name = "customFilter")
	public Filter customFilter() {
		return new CustomFilter();
	}
}
