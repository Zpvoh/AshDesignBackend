package com.web.design;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class CorsConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //允许跨域访问的链接 "/**" 表示允许所有链接
                .allowedMethods("*");           //允许的http方法(GET,PUT,POST,DELETE...),"*"表示允许所有方法
    }
}
