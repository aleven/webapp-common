package it.attocchi.rest;

import it.attocchi.utils.Crono;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/*
 * USE ON WEB.XML
 * 
 * 
 * 	
 * 
 * 	<!-- JSONP FILTER -->
 <filter>
 <display-name>JSONPRequestFilter</display-name>
 <filter-name>JSONPRequestFilter</filter-name>
 <filter-class>it.atreeblu.atreeflow.rapportoserver.filter.JSONPRequestFilter</filter-class>
 </filter>
 <filter-mapping>
 <filter-name>JSONPRequestFilter</filter-name>
 <!-- <url-pattern>/JSONPFilter</url-pattern> -->
 <servlet-name>JAX-RS Servlet</servlet-name>
 </filter-mapping>

 */

/**
 * Servlet Filter implementation class JSONPFilter
 */
public class JSONPRequestFilter implements Filter {

	protected static final Logger logger = Logger.getLogger(JSONPRequestFilter.class.getName());

	/**
	 * Default constructor.
	 */
	public JSONPRequestFilter() {
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

		if (!(request instanceof HttpServletRequest)) {
			throw new ServletException("This filter can " + " only process HttpServletRequest requests");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (isJSONPRequest(httpRequest)) {
			ServletOutputStream out = response.getOutputStream();

			Crono.start("JSONPRequestFilter");

			out.println(getCallbackMethod(httpRequest) + "(");
			chain.doFilter(request, response);
			out.println(");");

			response.setContentType("text/javascript");

			Crono.stopAndLogDebug("JSONPRequestFilter", logger);

		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	private String getCallbackMethod(HttpServletRequest httpRequest) {
		return httpRequest.getParameter("callback");
	}

	private boolean isJSONPRequest(HttpServletRequest httpRequest) {
		String callbackMethod = getCallbackMethod(httpRequest);
		return (callbackMethod != null && callbackMethod.length() > 0);
	}

}
