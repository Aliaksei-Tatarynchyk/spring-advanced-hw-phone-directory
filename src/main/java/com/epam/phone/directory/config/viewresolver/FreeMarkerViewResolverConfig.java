package com.epam.phone.directory.config.viewresolver;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

// commented because configuring freemarker view resolver via application.properties is more convenient
//@Configuration
public class FreeMarkerViewResolverConfig {

//    @Bean
    public ViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(true);
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".ftl");
        viewResolver.setOrder(100);
        return viewResolver;
    }

//    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        config.setTemplateLoaderPath("classpath:/templates");
        return config;
    }

}