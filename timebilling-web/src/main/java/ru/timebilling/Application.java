package ru.timebilling;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.github.dandelion.datatables.core.web.servlet.DatatablesServlet;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
    	ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
    	
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
    
    /**
     * регистрируем dandelion controller
     * @return
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration()
    {
    	return new ServletRegistrationBean(new DatatablesServlet(), "/datatablesController/*");    
    }

}
