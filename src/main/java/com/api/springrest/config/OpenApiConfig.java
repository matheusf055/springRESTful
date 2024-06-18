package com.api.springrest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API RESTful")
                        .version("v1")
                        .description("RESTful complete")
                        .termsOfService("")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.linkedin.com/in/matheus-fernandes-765516284/")));
    }
}
