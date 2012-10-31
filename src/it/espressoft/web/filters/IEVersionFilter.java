package it.espressoft.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Usage on web.xml:
 * 
 * 	
 <!-- IEVersionFilter -->
 <filter>
 <display-name>IEVersionFilter</display-name>
 <filter-name>IEVersionFilter</filter-name>
 <filter-class>it.espressoft.web.filters.IEVersionFilter</filter-class>
 </filter>
 <filter-mapping>
 <filter-name>IEVersionFilter</filter-name>
 <servlet-name>Faces Servlet</servlet-name>
 </filter-mapping>
 <!-- IEVersionFilter -->

 */
public class IEVersionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		/*
		 * http://twigstechtips.blogspot.com/2010/03/css-ie8-meta-tag-to-disable.
		 * html
		 */
		res.setHeader("X-UA-Compatible", "IE=EDGE");

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

}
