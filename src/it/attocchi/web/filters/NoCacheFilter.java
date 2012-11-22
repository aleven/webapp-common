package it.attocchi.web.filters;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Usage:
 * 
 * 	<!-- NoCacheFilter -->
	<filter>
		<display-name>NoCacheFilter</display-name>
		<filter-name>NoCacheFilter</filter-name>
		<filter-class>com.hydromec.performance.filters.NoCacheFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>NoCacheFilter</filter-name>
		<servlet-name>Faces Servlet</servlet-name>
	</filter-mapping>
	<!-- NoCacheFilter -->
	
 */
public class NoCacheFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		/*
		 * Skip JSF resources (CSS/JS/Images/etc)
		 */
		if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {

			/* HTTP 1.1. */
			res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			/* HTTP 1.0. */
			res.setHeader("Pragma", "no-cache");
			/* Proxies. */
			res.setDateHeader("Expires", 0);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

}
