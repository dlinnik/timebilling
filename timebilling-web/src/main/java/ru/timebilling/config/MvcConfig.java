package ru.timebilling.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ru.timebilling.web.interceptor.AppInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("login");
        registry.addViewController("/app/*/login").setViewName("/login");

    }
    
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//    	registry.addInterceptor(new AppInterceptor());
//    }

}
