package ru.timebilling.web.filter;

import java.io.IOException;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ru.timebilling.model.RequestURLSupplier;

/**
 * saves request url to the thread local context for a later usage
 * @author vshmelev
 *
 */
@Component
public class RequestURLFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		RequestURLSupplier.setURL(new URL(request.getRequestURL().toString()));
		filterChain.doFilter(request, response);
	}

}
