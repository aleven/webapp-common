/*
    Copyright (c) 2012,2013 Mirco Attocchi
	
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
 *
 * @author mirco
 * @version $Id: $Id
 */
public class RestAccessControlFilter implements Filter {

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(RestAccessControlFilter.class.getName());

	/**
	 * Default constructor.
	 */
	public RestAccessControlFilter() {
		
	}

	/**
	 * <p>destroy.</p>
	 *
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
