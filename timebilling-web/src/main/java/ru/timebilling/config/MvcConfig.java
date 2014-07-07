package ru.timebilling.config;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ru.timebilling.model.service.conversion.CustomObjectMapper;
import ru.timebilling.web.interceptor.JPAFilterInterceptor;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	JPAFilterInterceptor filterInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.html");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(filterInterceptor).addPathPatterns("/**").excludePathPatterns("/public/**");
    }


	
    @Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(converter());
	}

	@Bean
    MappingJackson2HttpMessageConverter converter() {
    	MappingJackson2HttpMessageConverter  converter = new MappingJackson2HttpMessageConverter ();
    	converter.setObjectMapper(new CustomObjectMapper());
        return converter;
    }

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());		
	}
	
	@Bean
	public Filter openEntityManagerInViewFilter() {
	    return new OpenEntityManagerInViewFilter();
	}

}
