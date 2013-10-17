/*
    Copyright (c) 2012 Mirco Attocchi
	
    This file is part of WebAppCommon.

    WebAppCommon is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WebAppCommon is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with WebAppCommon.  If not, see <http://www.gnu.org/licenses/>.
*/

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
 <filter-class>it.attocchi.rest.JSONPRequestFilter</filter-class>
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
		
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
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
		
	}

	private String getCallbackMethod(HttpServletRequest httpRequest) {
		return httpRequest.getParameter("callback");
	}

	private boolean isJSONPRequest(HttpServletRequest httpRequest) {
		String callbackMethod = getCallbackMethod(httpRequest);
		return (callbackMethod != null && callbackMethod.length() > 0);
	}

}
