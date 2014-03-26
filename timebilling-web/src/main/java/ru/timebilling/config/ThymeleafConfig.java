package ru.timebilling.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.github.dandelion.datatables.thymeleaf.dialect.DataTablesDialect;

@Configuration
@PropertySource("classpath:thymeleaf.properties")
public class ThymeleafConfig {


    @Bean
    public TemplateResolver templateResolver(){
    	
    	TemplateResolver resolver = new TemplateResolver();
    	resolver.setResourceResolver(thymeleafResourceResolver());
    	resolver.setPrefix("classpath:/templates/");
    	resolver.setSuffix(".html");
    	resolver.setTemplateMode("LEGACYHTML5");
    	resolver.setCharacterEncoding("UTF-8");
    	resolver.setCacheable(true);
    	return resolver;    	
    }
    
	@Bean
	protected SpringResourceResourceResolver thymeleafResourceResolver() {
		return new SpringResourceResourceResolver();
	}

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());

        // we add some dialects to extend the functionalities of Thymeleaf
        Set<IDialect> dialects = new HashSet<>();

        // we add the Spring Security dialect to thymeleaf
        dialects.add(new SpringSecurityDialect());

        // we add the Dandelion DataTable dialect to thymeleaf
        dialects.add(new DataTablesDialect());

        templateEngine.setAdditionalDialects(dialects);
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver() ;
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setViewNames(new String[]{"*"});
        viewResolver.setCache(false);
        viewResolver.setCharacterEncoding("UTF-8");

        return viewResolver;
    }
}