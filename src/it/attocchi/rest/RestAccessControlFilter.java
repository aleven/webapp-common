package it.attocchi.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/*
 * ABOUT CORS
 * http://en.wikipedia.org/wiki/Cross-origin_resource_sharing
 * 
 * 
 * USE ON WEB.XML
 * 
 * 
 * 	
 * 
 * 	<!-- RestAccessControlFilter FILTER -->
 <filter>
 <display-name>RestAccessControlFilter</display-name>
 <filter-name>RestAccessControlFilter</filter-name>
 <filter-class>it.attocchi.rest.RestAccessControlFilter</filter-class>
 </filter>
 <filter-mapping>
 <filter-name>RestAccessControlFilter</filter-name>
 <!-- <url-pattern>/JSONPFilter</url-pattern> -->
 <servlet-name>JAX-RS Servlet</servlet-name>
 </filter-mapping>

 */

/**
 * Servlet Filter implementation class RestAccessControlFilter
 */
public class RestAccessControlFilter implements Filter {

	protected static final Logger logger = Logger.getLogger(RestAccessControlFilter.class.getName());

	/**
	 * Default constructor.
	 */
	public RestAccessControlFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse httpResp = (HttpServletResponse) response;

		httpResp.addHeader("Access-Control-Allow-Origin", "*");
		httpResp.addHeader("Access-Control-Allow-Credentials", "true");
		httpResp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		httpResp.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		
		httpResp.setHeader("Access-Control-Allow-Origin", "*");
		httpResp.setHeader("Access-Control-Allow-Credentials", "true");
		httpResp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		httpResp.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");
		
		chain.doFilter(request, response);
        
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
