package ru.timebilling.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import ru.timebilling.model.service.UserDetailsServiceImpl;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();
    	
        http
            .authorizeRequests()
                .antMatchers("/login.html", 
                		"/js/**",
                		"/css/**",
                		"/fonts/**",
                		"/images/**",
                		"/resources/**",
                		
                		/*public api */"/public/**").permitAll()
                .anyRequest().authenticated();
        http
            .formLogin()
                .loginPage("/login.html").permitAll()   
                .successHandler(getSuccessHandler());
        
    }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService);
    }
    

    protected AuthenticationSuccessHandler getSuccessHandler() {
    	SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
		handler.setDefaultTargetUrl("/index.html");
		return handler;
	}
}
