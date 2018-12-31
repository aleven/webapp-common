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

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class LoginFilter
 * <pre>
 * {@code
 * <filter> <display-name>AuthFilter</display-name>
 * <filter-name>AuthFilter</filter-name>
 * <filter-class>it.attocchi.web.filters.LoginFilter</filter-class> <init-param>
 * <param-name>enabled</param-name> <param-value>false</param-value>
 * </init-param> <init-param> <param-name>allowedUrls</param-name>
 * <param-value>index.html</param-value> </init-param> <init-param>
 * <param-name>allowedServerName</param-name>
 * <param-value>localhost</param-value> </init-param> <init-param>
 * <param-name>loginPage</param-name> <param-value>login.html</param-value>
 * </init-param> </filter> <filter-mapping>
 * <filter-name>AuthFilter</filter-name> <url-pattern>/api/*</url-pattern>
 * <url-pattern>*.html</url-pattern> </filter-mapping>
 * }
 * </pre>
 * @author mirco
 * @version $Id: $Id
 */
public class LoginFilter implements Filter {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	/** Constant <code>CURRENT_USER="CURRENT_USER"</code> */
	public static final String CURRENT_USER = "CURRENT_USER";

	String enabled;
	String allowedUrls;
	String allowedServerName;
	String loginPage;

	/**
	 * Default constructor.
	 */
	public LoginFilter() {

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

		HttpServletRequest httprequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;

		logger.debug(httprequest.getRequestURI());

		if (Boolean.parseBoolean(enabled)) {

			String url = httprequest.getServletPath();
			boolean allowedRequest = false;

			/* AGGIUNTO PER DEBUG IN SVILUPPO */
			if (allowedServerName != null && !allowedServerName.isEmpty()) {
				logger.debug(String.format("allowedServerName=%s", allowedServerName));
				allowedRequest = httprequest.getServerName().equals(allowedServerName);
			}
			if (!allowedRequest) {
				if (loginPage != null && !loginPage.isEmpty()) {
					if (allowedRequest || url.endsWith(loginPage) || url.indexOf(loginPage) >= 0) {
						logger.debug(String.format("loginPage=%s", loginPage));
						allowedRequest = true;
					}
				}
			}
			if (!allowedRequest) {
				if (allowedUrls != null && !allowedUrls.isEmpty()) {
					logger.debug("allowedUrls=" + allowedUrls);
					for (String allowedUrl : allowedUrls.split(",")) {
						logger.debug(String.format("allowedUrl=%s", allowedUrl));
						if (allowedRequest || url.endsWith(allowedUrl) || url.indexOf(allowedUrl) >= 0) {
							allowedRequest = true;
						}
					}
				}
			}
			if (!allowedRequest) {
				HttpSession session = httprequest.getSession(false);
				if (null == session) {
					String to = httprequest.getContextPath() + "/" + loginPage;
					logger.debug("sendRedirect to " + to);
					httpresponse.sendRedirect(to);
				} else {
					Object currentUser = session.getAttribute(LoginFilter.CURRENT_USER);
					logger.debug(String.format("currentUser=%s", currentUser));
					if (null == currentUser) {
						String to = httprequest.getContextPath() + "/" + loginPage;
						logger.debug("sendRedirect to " + to);
						httpresponse.sendRedirect(to);
					}
				}
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/** {@inheritDoc} */
	public void init(FilterConfig fConfig) throws ServletException {
		enabled = fConfig.getInitParameter("enabled");
		allowedUrls = fConfig.getInitParameter("allowedUrls");
		allowedServerName = fConfig.getInitParameter("allowedServerName");
		loginPage = fConfig.getInitParameter("loginPage");
	}
}
