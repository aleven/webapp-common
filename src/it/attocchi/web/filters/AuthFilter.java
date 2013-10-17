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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class AuthFilter implements Filter {

	protected static final Logger logger = Logger.getLogger(AuthFilter.class.getName());

	public static final String PARAM_AUTH = "auth";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		logger.debug(req.getRequestURI());

		// Get the value of a request parameter; the name is case-sensitive
		String name = PARAM_AUTH;
		String value = request.getParameter(name);
		// if (value != null && !"".equals(value)) {
		if (StringUtils.isNotBlank(value)) {
			// The request parameter 'param' was not present in the query string
			// e.g. http://hostname.com?a=b

			// The request parameter 'param' was present in the query string but
			// has no value
			// e.g. http://hostname.com?param=&a=b

			// int idUtente = Integer.parseInt(value);

			HttpSession session = req.getSession();
			if (session != null) {
				// session.setAttribute(PARAM_AUTH, idUtente);
				session.setAttribute(PARAM_AUTH, value);
			}

			logger.info("AuthFilter: " + value);
			chain.doFilter(request, response);
		}
		// } else {
		// String page = "login.xhtml";
		// if (req.getRequestURI().endsWith(page)) {
		// logger.info("AuthFilter: " + page);
		// chain.doFilter(request, response);
		// } else {
		// logger.error("AuthFilter: No Login");
		// res.sendRedirect(req.getContextPath() + "/" + page);
		// }
		// }

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
