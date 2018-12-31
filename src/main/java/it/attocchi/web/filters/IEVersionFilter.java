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

import org.apache.log4j.Logger;

/**
 * Usage on web.xml:
 *
 * <pre>
 * {@code
 * <!-- IEVersionFilter -->
 * <filter>
 * <display-name>IEVersionFilter</display-name>
 * <filter-name>IEVersionFilter</filter-name>
 * <filter-class>it.attocchi.web.filters.IEVersionFilter</filter-class>
 * </filter>
 * <filter-mapping>
 * <filter-name>IEVersionFilter</filter-name>
 * <servlet-name>Faces Servlet</servlet-name>
 * </filter-mapping>
 * <!-- IEVersionFilter -->
 * }
 * </pre>
 * <p>IEVersionFilter class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class IEVersionFilter implements Filter {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	/** {@inheritDoc} */
	@Override
	public void destroy() {

	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
