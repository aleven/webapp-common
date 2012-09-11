package it.espressoft.web.filters;

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

public class AuthFilter implements Filter {

	protected static final Logger logger = Logger.getLogger(AuthFilter.class.getName());

	public static final String PARAM_AUTH = "auth";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		logger.debug(req.getRequestURI());

		// Get the value of a request parameter; the name is case-sensitive
		String name = PARAM_AUTH;
		String value = request.getParameter(name);
		if (value != null && !"".equals(value)) {
			// The request parameter 'param' was not present in the query string
			// e.g. http://hostname.com?a=b

			// The request parameter 'param' was present in the query string but
			// has no value
			// e.g. http://hostname.com?param=&a=b
			int idUtente = Integer.parseInt(value);

			HttpSession session = req.getSession();
			if (session != null)
				session.setAttribute(PARAM_AUTH, idUtente);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

}
