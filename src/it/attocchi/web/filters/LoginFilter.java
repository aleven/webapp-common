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

package it.attocchi.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

	public static final String CURRENT_USER = "CURRENT_USER";

	/**
	 * Default constructor.
	 */
	public LoginFilter() {

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

		// place your code here

		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		String url = httprequest.getServletPath();
		boolean allowedRequest = false;

		/* AGGIUNTO PER DEBUG IN SVILUPPO */
		allowedRequest = httprequest.getServerName().equals("localhost");

		if (allowedRequest || url.endsWith("index.xhtml") || url.endsWith("login.xhtml")) {
			allowedRequest = true;
		}

		if (!allowedRequest) {
			HttpSession session = httprequest.getSession(false);
			if (null == session) {

				httpresponse.sendRedirect(httprequest.getContextPath() + "/login.xhtml");
			} else {
				Object currentUser = session.getAttribute(LoginFilter.CURRENT_USER);
				if (null == currentUser) {
					httpresponse.sendRedirect(httprequest.getContextPath() + "/login.xhtml");
				}
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// String filename = fConfig.getInitParameter("enabled");
	}

}
