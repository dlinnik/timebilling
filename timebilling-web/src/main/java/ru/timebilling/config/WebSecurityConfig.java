package ru.timebilling.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import ru.timebilling.service.AppService;
import ru.timebilling.service.UserDetailsServiceImpl;
import ru.timebilling.web.filter.AppIdFromSubdomainFilter;
import ru.timebilling.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
//	AppIdFromSubdomainFilter appIdFilter = new AppIdFromSubdomainFilter();
//	appIdFilter.setAppService(appService);
//	http.addFilterBefore(appIdFilter,  UsernamePasswordAuthenticationFilter.class);
//    	http.addFilterBefore(new CharacterEncodingFilter(), SecurityContextPersistenceFilter.class);
    	
    	http.csrf().disable();
    	
        http
            .authorizeRequests()
                .antMatchers("/login", "/403",
                		//"/app/*/login", 
                		"/resources/**").permitAll()
                .anyRequest().authenticated();
        http
            .formLogin()
                .loginPage("/login").permitAll()   
                .successHandler(getSuccessHandler())
                //.and().exceptionHandling().accessDeniedPage("/403").and()
                ;
//                .failureHandler(getAuthenticationFailureHandler())                
//                .and().exceptionHandling().authenticationEntryPoint(getAuthEntryPointWithUseForward("/login"))
//                .and().exceptionHandling()
//                .authenticationEntryPoint(getAuthEntryPointWithRedirect("/app$/login"))
                
//                .and()
//            .logout().logoutSuccessHandler(getLogoutSuccessHandler())
//                .permitAll();
        
    }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService);
    }
    

//
//	private AuthenticationFailureHandler getAuthenticationFailureHandler() {
//		SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
//		handler.setDefaultFailureUrl("/app$/login?error");
//		handler.setRedirectStrategy(new AppIdRedirectionStrategy(appId));
//		return handler;
//	}
//
//
//	private LogoutSuccessHandler getLogoutSuccessHandler() {		
//		SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
//		handler.setRedirectStrategy(new ReffererRedirectionStrategy());
//
////		handler.setDefaultTargetUrl("/app$/login?logout");
////		handler.setRedirectStrategy(new AppIdRedirectionStrategy(appId));
//		return handler;
//	}
//
//
//    
    protected AuthenticationSuccessHandler getSuccessHandler() {
    	SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
		handler.setDefaultTargetUrl("/projects");
//		handler.setRedirectStrategy(new AppIdRedirectionStrategy(appId));
		return handler;
	}
//
//	protected AuthenticationEntryPoint getAuthEntryPointWithUseForward(String loginFormUrl){
//    	LoginUrlAuthenticationEntryPoint l = new LoginUrlAuthenticationEntryPoint(loginFormUrl);    	
//    	l.setUseForward(true);
//    	return l;
//    }
//	
//	protected AuthenticationEntryPoint getAuthEntryPointWithRedirect(String loginFormUrl){
//		LoginUrlAuthenticationEntryPointWithRedirectStrategy l = new 
//				LoginUrlAuthenticationEntryPointWithRedirectStrategy(loginFormUrl,
//						new AppIdRedirectionStrategy(appId));
//		return l;
//    }
//
    
}
//
///**
// * стратегия редиректа при которой в строке учитывается appId 
// * @author vshmelev
// *
// */
//class AppIdRedirectionStrategy extends DefaultRedirectStrategy{
//	
//	AppContext appId;
//		
//
//	public AppIdRedirectionStrategy(AppContext appId) {
//		super();
//		this.appId = appId;
//	}
//
//
//	@Override
//	public void sendRedirect(HttpServletRequest request,
//			HttpServletResponse response, String url) throws IOException {
//		
//		String url0 = url;
//		
//		if(url0.contains("app$")){
//			url0 = url.replace("app$", "app/" + appId.getId());
//		}
//		
//		System.out.println("redirecting to [" + url0 + "][" + appId.getId() + "]");
//		super.sendRedirect(request, response, url0);
//	}	
//}
//
///**
// * стратегия редиректа на url из заголовка запроса referer. 
// * Используется в LogoutSuccessHandler, для того чтобы после выхода перейти на страницу логина с правильным appId
// * @author vshmelev
// *
// */
//class ReffererRedirectionStrategy extends DefaultRedirectStrategy{
//	@Override
//	public void sendRedirect(HttpServletRequest request,
//			HttpServletResponse response, String url) throws IOException {
//		
//		String url0 = url;
////		Enumeration<String> headerNames = request.getHeaderNames();
////		while(headerNames.hasMoreElements()){
////			String hn = headerNames.nextElement();
////			System.out.println("header[" + hn + "][" + request.getHeader(hn) + "]");
////		}
//		
//		String referrer = request.getHeader("referer");
//		if(referrer!=null){
//			url0 = referrer;
//		}
//		System.out.println("redirecting to [" + url0 + "]");
//		super.sendRedirect(request, response, url0);
//	}	
//	
//}
//
///**
// * Переписан метод {@link LoginUrlAuthenticationEntryPoint#commence(HttpServletRequest, HttpServletResponse, AuthenticationException)}
// * так как стандартная реализация не позволяет задать {@link RedirectStrategy}
// * @author vshmelev
// *
// */
//class LoginUrlAuthenticationEntryPointWithRedirectStrategy extends LoginUrlAuthenticationEntryPoint{
//
//	private RedirectStrategy strategy;
//	
//	public LoginUrlAuthenticationEntryPointWithRedirectStrategy(String loginFormUrl, RedirectStrategy strategy) {
//		super(loginFormUrl);	
//		this.strategy = strategy;
//	}
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException) throws IOException, ServletException {
//		String redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
//		strategy.sendRedirect(request, response, redirectUrl);
//	}		
//}
