package ru.timebilling.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import ru.timebilling.service.UserDetailsServiceImpl;
import ru.timebilling.web.component.AppId;
import ru.timebilling.web.filter.AppIdFilter;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AppId appId;
	
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	AppIdFilter appIdFilter = new AppIdFilter();
    	appIdFilter.setAppId(appId);
    	
    	http.addFilterBefore(appIdFilter,  UsernamePasswordAuthenticationFilter.class);
    	
        http
            .authorizeRequests()
                .antMatchers("/login", "/app/*/login", "/resources/**").permitAll()
                .anyRequest().authenticated();
        http
            .formLogin()
//                .loginPage("/app/*/login")                
                .successHandler(getSuccessHandler())
                .failureHandler(getAuthenticationFailureHandler())                
//                .and().exceptionHandling().authenticationEntryPoint(getAuthEntryPointWithUseForward("/login"))
                .and().exceptionHandling()
                .authenticationEntryPoint(getAuthEntryPointWithRedirect("/app$/login"))
                
                .and()
            .logout().logoutSuccessHandler(getLogoutSuccessHandler())
                .permitAll();
        
    }


	private AuthenticationFailureHandler getAuthenticationFailureHandler() {
		SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
		handler.setDefaultFailureUrl("/app$/login?error");
		handler.setRedirectStrategy(new AppIdRedirectionStrategy(appId));
		return handler;
	}


	private LogoutSuccessHandler getLogoutSuccessHandler() {		
		SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
		handler.setRedirectStrategy(new ReffererRedirectionStrategy());

//		handler.setDefaultTargetUrl("/app$/login?logout");
//		handler.setRedirectStrategy(new AppIdRedirectionStrategy(appId));
		return handler;
	}


	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService);
    }
    
    protected AuthenticationSuccessHandler getSuccessHandler() {
    	SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
		handler.setDefaultTargetUrl("/app$/");
		handler.setRedirectStrategy(new AppIdRedirectionStrategy(appId));
		return handler;
	}

	protected AuthenticationEntryPoint getAuthEntryPointWithUseForward(String loginFormUrl){
    	LoginUrlAuthenticationEntryPoint l = new LoginUrlAuthenticationEntryPoint(loginFormUrl);    	
    	l.setUseForward(true);
    	return l;
    }
	
	protected AuthenticationEntryPoint getAuthEntryPointWithRedirect(String loginFormUrl){
		LoginUrlAuthenticationEntryPointWithRedirectStrategy l = new 
				LoginUrlAuthenticationEntryPointWithRedirectStrategy(loginFormUrl,
						new AppIdRedirectionStrategy(appId));
		return l;
    }

}

/**
 * стратегия редиректа при которой в строке учитывается appId 
 * @author vshmelev
 *
 */
class AppIdRedirectionStrategy extends DefaultRedirectStrategy{
	
	AppId appId;
		

	public AppIdRedirectionStrategy(AppId appId) {
		super();
		this.appId = appId;
	}


	@Override
	public void sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		
		String url0 = url;
		
		if(url0.contains("app$")){
			url0 = url.replace("app$", "app/" + appId.getId());
		}
		
		System.out.println("redirecting to [" + url0 + "][" + appId.getId() + "]");
		super.sendRedirect(request, response, url0);
	}	
}

/**
 * стратегия редиректа на url из заголовка запроса referer. 
 * Используется в LogoutSuccessHandler, для того чтобы после выхода перейти на страницу логина с правильным appId
 * @author vshmelev
 *
 */
class ReffererRedirectionStrategy extends DefaultRedirectStrategy{
	@Override
	public void sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		
		String url0 = url;
//		Enumeration<String> headerNames = request.getHeaderNames();
//		while(headerNames.hasMoreElements()){
//			String hn = headerNames.nextElement();
//			System.out.println("header[" + hn + "][" + request.getHeader(hn) + "]");
//		}
		
		String referrer = request.getHeader("referer");
		if(referrer!=null){
			url0 = referrer;
		}
		System.out.println("redirecting to [" + url0 + "]");
		super.sendRedirect(request, response, url0);
	}	
	
}

/**
 * Переписан метод {@link LoginUrlAuthenticationEntryPoint#commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
 * так как стандартная реализация не позволяет задать {@link RedirectStrategy}
 * @author vshmelev
 *
 */
class LoginUrlAuthenticationEntryPointWithRedirectStrategy extends LoginUrlAuthenticationEntryPoint{

	private RedirectStrategy strategy;
	
	public LoginUrlAuthenticationEntryPointWithRedirectStrategy(String loginFormUrl, RedirectStrategy strategy) {
		super(loginFormUrl);	
		this.strategy = strategy;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
		strategy.sendRedirect(request, response, redirectUrl);
	}		
}
