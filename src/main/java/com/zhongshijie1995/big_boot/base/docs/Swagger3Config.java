package com.zhongshijie1995.big_boot.base.docs;

import com.zhongshijie1995.big_boot.info.dao.InfoBuild;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;

@Configuration
public class Swagger3Config {

    @Value("${docs.title}")
    private String docsTitle;

    @Value("${docs.description}")
    private String description;

    @Resource
    private InfoBuild infoBuildProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(docsTitle)
                .description(description)
                .version(infoBuildProperties.getVersion())
                .build();
    }
}
