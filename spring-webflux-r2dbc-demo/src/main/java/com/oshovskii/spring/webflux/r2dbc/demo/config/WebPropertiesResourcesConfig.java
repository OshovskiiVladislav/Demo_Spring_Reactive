package com.oshovskii.spring.webflux.r2dbc.demo.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebPropertiesResourcesConfig {
    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }
}
