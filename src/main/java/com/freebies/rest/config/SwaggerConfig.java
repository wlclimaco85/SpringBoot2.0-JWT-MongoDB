package com.freebies.rest.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	

	/**
	 * @return
	 */
	@Bean
	public Docket postsApi() {
		Parameter aParameterBuilder = new ParameterBuilder().name("Authorization").modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();
		List<Parameter> aParameters = new ArrayList<>();
		aParameters.add(aParameterBuilder);
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.freebies")).paths(postPaths()).build().pathMapping("")
				.globalOperationParameters(aParameters);
	}

	private Predicate<String> postPaths() {
		return or(regex("/api/*"), regex("/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Rest API")
				.description("Rest API reference for developers")
				.termsOfServiceUrl("https://github.com/sbaala")
				.contact(new Contact("Rest API", "https://github.com/sbaala",
						"balachandar.bdu@gmail.com"))
				.license("Rest API License").licenseUrl("balachandar.bdu@gmail.com").version("1.0")
				.build();
	}

}