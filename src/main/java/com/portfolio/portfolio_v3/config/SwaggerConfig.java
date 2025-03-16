package com.portfolio.portfolio_v3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI portfolioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Portfolio API Documentation")
                        .description("포트폴리오 백엔드 API 명세서")
                        .version("v1.0.0"));
    }
}