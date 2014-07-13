package ru.timebilling.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ru.timebilling.model.UserPassCredentials;
import ru.timebilling.model.service.UtUtils;
import static ru.timebilling.model.RequestURLSupplier.URL_LOGIN;
import static ru.timebilling.model.RequestURLSupplier.URL_LOGIN_UT;


@Component
public class LoginPreAuthFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationManager am;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {

		String ut = req.getParameter(URL_LOGIN_UT);
		UserPassCredentials up = UtUtils.decodeUserPassCredentials(ut);
		
		String user = up.getUserName();		
		String pass = up.getPassword();
		

		UserDetails uDetails = userDetailsService.loadUserByUsername(user);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(uDetails, pass);
        token.setDetails(new WebAuthenticationDetails(req));
		
//		Authentication request = new UsernamePasswordAuthenticationToken(user,
//				pass);
//		DaoAuthenticationProvider authenticator = new DaoAuthenticationProvider();
		
		Authentication result = am.authenticate(token);
		// information that user is authenticated
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    securityContext.setAuthentication(result);
		
	    // Create a new session and add the security context.
	    HttpSession session = req.getSession(true);
	    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		
		//filterChain.doFilter(req, resp);
	    //TODO: to the done page!
		resp.sendRedirect("/");

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		return !request.getRequestURI().startsWith(URL_LOGIN) ||
				request.getParameter(URL_LOGIN_UT) == null;
	}
	
	

}
