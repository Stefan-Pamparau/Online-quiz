package com.iquest.webapp.configuration;

import com.iquest.webapp.filters.PermissionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public Filter permissionFilter() {
        return new PermissionFilter();
    }
}
