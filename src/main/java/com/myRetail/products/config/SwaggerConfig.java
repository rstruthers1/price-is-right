package com.myRetail.products.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Value("${spring.application.title}")
    private String appTitle;

    @Value("${spring.application.description}")
    private String appDesc;

    @Value("${spring.application.version}")
    private String appVersion;

    @Value("${spring.application.serviceurl}")
    private String appServiceUrl;

    @Value("${spring.application.contact.name}")
    private String appContactName;

    @Value("${spring.application.contact.url}")
    private String appContactUrl;

    @Value("${spring.application.contact.email}")
    private String appContactEmail;

    @Bean
    public Docket api() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex(".*products.*"))
                .build()
                .useDefaultResponseMessages(false);

        return docket;
    }

    private ApiInfo getApiInfo () {
        return new ApiInfoBuilder()
                .title(appTitle)
                .description(appDesc)
                .termsOfServiceUrl(appServiceUrl)
                .version(appVersion)
                .contact(new Contact(appContactName, appContactUrl, appContactEmail))
                .build();
    }
}
