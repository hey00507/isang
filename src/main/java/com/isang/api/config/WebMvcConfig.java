package com.isang.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // ??
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns("/foo"); // 인터셉터 실행에서 제외 -> AuthInterceptor 가 아에 실행되지 않아 로그가 걸리지 않음
        ;
    }
}
