package ru.timebilling.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ru.timebilling.service.conversion.DateFormatter;
import ru.timebilling.web.interceptor.AppInterceptor;
import ru.timebilling.web.interceptor.JPAFilterInterceptor;


@Configuration
//@EnableWebMvc
//@EnableSpringDataWebSupport
public class MvcConfig extends WebMvcConfigurerAdapter {

	
	@Autowired
	AppInterceptor appInterceptor;
	
	@Autowired
	JPAFilterInterceptor filterInterceptor;
	
	@Autowired
	DateFormatter dateFormatter;
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("login");
//        registry.addViewController("/app/*/login").setViewName("/login");
        
    	registry.addViewController("/403").setViewName("/denied");
        registry.addViewController("/login").setViewName("/login");
    	registry.addViewController("/templates").setViewName("/templates");
       


    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(appInterceptor).addPathPatterns("/**");//.excludePathPatterns("/403");
    	registry.addInterceptor(filterInterceptor).addPathPatterns("/**");
    }
        
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(dateFormatter);
	}
	
	
    @Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		super.configureMessageConverters(converters);
		converters.add(converter());
	}

	@Bean
    MappingJackson2HttpMessageConverter converter() {
    	MappingJackson2HttpMessageConverter  converter = new MappingJackson2HttpMessageConverter ();
        //do your customizations here...
        return converter;
    }

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
//		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());		
	}
	
//	@Bean
//	PageableHandlerMethodArgumentResolver getPageableHandlerMethodArgumentResolver(){
//		return new PageableHandlerMethodArgumentResolver();
//	}
	
	
    
    

}
