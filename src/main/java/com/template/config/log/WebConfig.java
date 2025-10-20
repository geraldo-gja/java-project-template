package com.template.config.log;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Application configuration to add interceptors to Spring MVC.
 *
 * This class uses the {@link LoggingInterceptor} to
 * intercept and log all HTTP requests.
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private LoggingInterceptor loggingInterceptor;

    /**
     * Adds the {@link LoggingInterceptor} to the Spring MVC interceptor registry.
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }

}
