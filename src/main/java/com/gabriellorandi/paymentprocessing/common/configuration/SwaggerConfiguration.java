package com.gabriellorandi.paymentprocessing.common.configuration;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Payment Processing")
                        .version("1.0.0")
                        .description("App that processes credit card transactions.")
                        .build())
                .enable(true)
                .select()
                .paths(PathSelectors.any())
                .build();
    }
}
