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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * <p>NtmlFilter class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class NtmlFilter implements Filter {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	//
	FilterConfig config = null;
	ServletContext servletContext = null;

	/**
	 * <p>Constructor for NtmlFilter.</p>
	 */
	public NtmlFilter() {
	}

	/** {@inheritDoc} */
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
		servletContext = config.getServletContext();
	}

	/** {@inheritDoc} */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		logger.debug("Filtro Ntlm");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();

		String requestPath = httpRequest.getServletPath();
		logger.debug("requestPath " + requestPath);

		String user_agent = null;
		String auth = null;

		String workstation = null;
		String domain = null;
		String username = null;

		try {
			/*
			 * Operatore loggato? SI: prosegue con il flusso normalmente; NO:
			 * tentativo di accesso con utente Windows e poi, se fallisce,
			 * redirect alla pagina di autenticazione;
			 */
			if ((requestPath != null && requestPath.endsWith("index.jspx")) || (requestPath != null && requestPath.endsWith("status.jspx")) || requestPath != null && requestPath.endsWith("logout.jspx") || requestPath != null && requestPath.endsWith("authenticate.jspx")) {
				logger.debug("Richiesta una pagina fra quelle speciali, esco dal filtro");
				// chain.doFilter(request, response);
				// return;
			} else {

				/* COMMENTATO per problemi POST JSF */
				// // <editor-fold defaultstate="collapsed"
				// desc="DETERMINAZIONE UTENTE WINDOWS">

				/*
				 * La tecnica utilizzata (settaggio header) per la
				 * determinazione dell'utente Windows e' funzionante
				 * esclusivamente con browser Microsoft Internet Explorer. Se
				 * altro browser salta questo step
				 */
				user_agent = httpRequest.getHeader("user-agent");
				// if ((user_agent != null) && (user_agent.indexOf("MSIE") >
				// -1)) {
				if ((user_agent != null)) {
					logger.debug("USER-AGENT: " + user_agent);

					auth = httpRequest.getHeader("Authorization");
					if (auth == null) {
						logger.debug("STEP1: SC_UNAUTHORIZED");

						httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						httpResponse.setHeader("WWW-Authenticate", "NTLM");
						httpResponse.flushBuffer();
						return;
					}

					if (auth.startsWith("NTLM ")) {
						logger.debug("STEP2: NTLM");

						byte[] msg = org.apache.commons.codec.binary.Base64.decodeBase64(auth.substring(5));
						// byte[] msg = new
						// sun.misc.BASE64Decoder().decodeBuffer(auth.substring(5));
						int off = 0, length, offset;
						if (msg[8] == 1) {
							logger.debug("STEP2a: NTLM");

							byte z = 0;
							byte[] msg1 = { (byte) 'N', (byte) 'T', (byte) 'L', (byte) 'M', (byte) 'S', (byte) 'S', (byte) 'P', z, (byte) 2, z, z, z, z, z, z, z, (byte) 40, z, z, z, (byte) 1, (byte) 130, z, z, z, (byte) 2, (byte) 2, (byte) 2, z, z, z, z, z, z, z, z, z, z, z, z };
							// httpResponse.setHeader("WWW-Authenticate",
							// "NTLM " + new
							// sun.misc.BASE64Encoder().encodeBuffer(msg1));
							httpResponse.setHeader("WWW-Authenticate", "NTLM " + Base64.encodeBase64String(msg1));
							httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
							return;

						} else if (msg[8] == 3) {
							logger.debug("STEP2b: read data");

							off = 30;
							length = msg[off + 17] * 256 + msg[off + 16];
							offset = msg[off + 19] * 256 + msg[off + 18];
							workstation = new String(msg, offset, length);

							length = msg[off + 1] * 256 + msg[off];
							offset = msg[off + 3] * 256 + msg[off + 2];
							domain = new String(msg, offset, length);

							length = msg[off + 9] * 256 + msg[off + 8];
							offset = msg[off + 11] * 256 + msg[off + 10];
							username = new String(msg, offset, length);

							char a = 0;
							char b = 32;
							// logger.debug("Username:" +
							// username.trim().replace(a, b).replaceAll("",
							// ""));
							username = username.trim().replace(a, b).replaceAll(" ", "");
							workstation = workstation.trim().replace(a, b).replaceAll(" ", "");
							domain = domain.trim().replace(a, b).replaceAll(" ", "");

							logger.debug("Username: " + username);
							logger.debug("RemoteHost: " + workstation);
							logger.debug("Domain: " + domain);

							chain.doFilter(request, response);
						}
					}
				} // if IE
					// </editor-fold>

				/*
				 * Mirco 30/09/10: Autentico solo con il nome utente senza il
				 * prefisso DOMINIO Questo mi e' utile per i nomi importati da
				 * LDAP che hanno specificato solo il nome dell'account
				 */
				String winUser = username; // domain + "\\" + username;
				try {
					if (winUser != null) {
						// utenteDaLoggare =
						// UtentiDAO.getUtenteByWindowsLogin(winUser);
					}
				} catch (Exception e) {
				}

			}
		} catch (RuntimeException e) {
			logger.error("Errore nel Filtro Autenticazione");
			logger.error(e);
			chain.doFilter(request, response);
		} finally {

		}

		logger.debug("Fine Filtro Ntlm");
	}

	/**
	 * <p>destroy.</p>
	 */
	public void destroy() {

	}

}
