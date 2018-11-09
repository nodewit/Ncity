package com.ncity.app.uitls;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 * 
 * @author 艾克 2018-10-04 13:52:22
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

	@Value("${online}")
	private int online;

	@Bean
	public Docket createRestApi() {
		if(online == 1) {    		
			return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
					//如果是线上环境，添加路径过滤，设置为全部都不符合
					.apis(RequestHandlerSelectors.basePackage("com.ncity.app.action")).paths(PathSelectors.none())
					.build();    	
		}else {
			return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
					.apis(RequestHandlerSelectors.basePackage("com.ncity.app.action")).paths(PathSelectors.any()).build();
		}
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("诺城 RESTful APIs").description("诺城项目后台api接口文档").version("1.0").build();
	}
}
