/*
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许发送Cookie
        config.addAllowedOrigin("http://127.0.0.1:5500"); // 允许的域
        config.addAllowedOrigin("http://localhost:8080"); // 添加这行
        config.addAllowedHeader("*"); // 允许任何头
        config.addAllowedMethod("*"); // 允许任何方法
        source.registerCorsConfiguration("/**", config); // 对所有API的路径应用这个配置
        return new CorsFilter(source);
    }
}*/
