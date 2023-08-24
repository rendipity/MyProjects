package com.publicapi.apimanage.web.config;

import com.publicapi.apimanage.web.base.interceptor.JwtAuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class interceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/authCode","/user/register");
    }
}
