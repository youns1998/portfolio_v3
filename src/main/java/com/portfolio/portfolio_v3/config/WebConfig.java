package com.portfolio.portfolio_v3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ✅ WebConfig 클래스는 CORS(Cross-Origin Resource Sharing) 설정을 관리하는 Spring Boot 설정 클래스이다.
 * ✅ CORS는 다른 출처(Origin)에서 리소스를 요청할 수 있도록 허용하는 기능으로, 프론트엔드(React 등)와 백엔드(Spring Boot)를 분리해서 개발할 때 필수적이다.
 */
@Configuration  // ✅ Spring의 설정 클래스임을 명시
public class WebConfig implements WebMvcConfigurer {

    /**
     * ✅ CORS 정책을 설정.
     * ✅ 모든 엔드포인트(/**)에 대해 CORS 허용.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // ✅ 모든 엔드포인트(/**)에 대해 CORS 허용
                .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173") // ✅ 개발 환경에서 프론트엔드 도메인 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ 허용할 HTTP 메서드 지정
                .allowedHeaders("*") // ✅ 모든 헤더 허용
                .allowCredentials(true); // ✅ 쿠키 및 인증 정보 포함 허용
    }

    /**
     * ✅ UTF-8 인코딩을 설정하여 한글이 깨지지 않도록 처리.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converters.add(converter);
    }
}