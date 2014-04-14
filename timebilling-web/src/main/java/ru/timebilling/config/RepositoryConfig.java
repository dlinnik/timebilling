package ru.timebilling.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import ru.timebilling.persistance.domain.BaseEntity;
import ru.timebilling.persistance.domain.Project;
import ru.timebilling.web.interceptor.AppInterceptor;
import ru.timebilling.web.interceptor.JPAFilterInterceptor;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

@Configuration
public class RepositoryConfig extends
        RepositoryRestMvcConfiguration {

	@Autowired
	AppInterceptor appInterceptor;
	
	@Autowired
	JPAFilterInterceptor filterInterceptor;

	
	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub
		super.configureRepositoryRestConfiguration(config);
		config.exposeIdsFor(Project.class);
	}


	@Bean
	public Filter openEntityManagerInViewFilter() {
	    return new OpenEntityManagerInViewFilter();
	}

	
    @Override
    public RequestMappingHandlerMapping repositoryExporterHandlerMapping() {
        RequestMappingHandlerMapping mapping = super
                .repositoryExporterHandlerMapping();

        mapping.setInterceptors(new Object[] { appInterceptor, filterInterceptor });
        return mapping;
    }
}