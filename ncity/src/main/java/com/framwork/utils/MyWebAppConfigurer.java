package com.framwork.utils;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Value("${uploadDir}")
	private String uploadDir;
	/**
	 * 新增静态资源访问路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (uploadDir.equals("") || uploadDir.equals("${uploadDir}")) {
			String imagesPath = MyWebAppConfigurer.class.getClassLoader()
					.getResource("").getPath();
			if (imagesPath.indexOf(".jar") > 0) {
				imagesPath = imagesPath
						.substring(0, imagesPath.indexOf(".jar"));
			} else if (imagesPath.indexOf("classes") > 0) {
				imagesPath = "file:"+ imagesPath.substring(0, imagesPath.indexOf("classes"));
			}
			imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))
					+ "/files/";
			uploadDir = imagesPath;
		}
		registry.addResourceHandler("/files/**").addResourceLocations(
				uploadDir);
		super.addResourceHandlers(registry);
	}

	/**
	 * 配置文件的大小
	 * @return
	 */
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(10*1024L * 1024L);
        return factory.createMultipartConfig();
    }
}
