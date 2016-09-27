/*
    Copyright (c) Twig (http://twigstechtips.blogspot.it/2010/03/css-ie8-meta-tag-to-disable.html)
	
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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/*
 * Usage on web.xml:
 * 
 * 	
 <!-- IE7VersionFilter -->
 <filter>
 <display-name>IE7VersionFilter</display-name>
 <filter-name>IE7VersionFilter</filter-name>
 <filter-class>it.attocchi.web.filters.IE7VersionFilter</filter-class>
 </filter>
 <filter-mapping>
 <filter-name>IE7VersionFilter</filter-name>
 <servlet-name>Faces Servlet</servlet-name>
 </filter-mapping>
 <!-- IE7VersionFilter -->

 */
public class IE7VersionFilter implements Filter {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	String pathToBeIgnored;
	String userAgentMatch;
	String xuaCompatible;
	String xuaDefault;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		logger.debug("IE7VersionFilter check");

		String path = httpRequest.getRequestURI();
		// if (path.startsWith("/specialpath")) {
		if (pathToBeIgnored != null && wildCardMatch(path, pathToBeIgnored)) {
			logger.debug("IE7VersionFilter not applied for: " + path);
			
			if (StringUtils.isNotBlank(xuaDefault)) {
				httpResponse.setHeader("x-ua-compatible", xuaDefault);
				logger.debug("appling the default x-ua-compatible: " + xuaDefault);
			}
			
			chain.doFilter(request, response); // Just continue chain.
		} else {
			// Do your business stuff here for all paths other than
			// /specialpath.
			/*
			 * http://twigstechtips.blogspot.com/2010/03/css-ie8-meta-tag-to-disable
			 * .html
			 */
			
			/* 
			 * https://msdn.microsoft.com/it-it/library/hh869301(v=vs.85).aspx
			 * https://msdn.microsoft.com/it-it/library/hh920767(v=vs.85).aspx
			 * Specifying legacy document modes
			 * https://msdn.microsoft.com/en-us/library/jj676915(v=vs.85).aspx
			 */

			String userAgent = httpRequest.getHeader("User-Agent");
			if (StringUtils.isBlank(userAgentMatch) || userAgent.contains(userAgentMatch)) {
				logger.debug("IE7VersionFilter applied agent: " + userAgent);
				// "IE=EmulateIE7"
				httpResponse.setHeader("x-ua-compatible", xuaCompatible);
				logger.debug("x-ua-compatible: " + xuaCompatible);
			} else {
				logger.debug("IE7VersionFilter not applied for agent " + userAgent);
				/* per tutti i Browser che non matchano potrebbe essere IE11 ad esempio, quindi per quello specifico EDGE */
				/* ho notato che in alcuni casi non specificando niente con IE11 per i siti intranet va in automatico in COMPATIBILITA e proviamo a bypassare così */ 
				// <meta http-equiv="x-ua-compatible" content="IE=edge" />
				if (StringUtils.isNotBlank(xuaDefault)) {
					httpResponse.setHeader("x-ua-compatible", xuaDefault);
					logger.debug("appling the default x-ua-compatible: " + xuaDefault);
				}
			}

			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		pathToBeIgnored = config.getInitParameter("pathToBeIgnored");
		userAgentMatch = config.getInitParameter("userAgentMatch");
		xuaCompatible = config.getInitParameter("X-UA-Compatible");
		xuaDefault = config.getInitParameter("X-UA-Compatible-DEFAULT");
	}

	/**
	 * Performs a wildcard matching for the text and pattern provided.
	 * 
	 * @param text
	 *            the text to be tested for matches.
	 * 
	 * @param pattern
	 *            the pattern to be matched for. This can contain the wildcard
	 *            character '*' (asterisk).
	 * 
	 * @return <tt>true</tt> if a match is found, <tt>false</tt> otherwise.
	 */

	public static boolean wildCardMatch(String text, String pattern) {
		// Create the cards by splitting using a RegEx. If more speed
		// is desired, a simpler character based splitting can be done.
		String[] cards = pattern.split("\\*");

		// Iterate over the cards.
		for (String card : cards) {
			int idx = text.indexOf(card);

			// Card not detected in the text.
			if (idx == -1) {
				return false;
			}

			// Move ahead, towards the right of the text.
			text = text.substring(idx + card.length());
		}

		return true;
	}

}
