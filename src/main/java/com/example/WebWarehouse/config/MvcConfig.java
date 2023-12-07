package com.example.WebWarehouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/css")
                .addResourceLocations("classpath:/static/img")
                .addResourceLocations("classpath:/static/js")
                .addResourceLocations("classpath:/static/vendor");
    }*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/static/**",
                        "/warehouse/static/**",
                        "/cell/static/**",
                        "/product/static/**"
                )
                .addResourceLocations(
                        "classpath:/static/css",
                        "classpath:/static/img",
                        "classpath:/static/js",
                        "classpath:/static/vendor"
                );
    }
}
