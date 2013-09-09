package it.attocchi.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

/**
 * <!-- <filter-class>ch.bedag.gba.capweb.util.IE9Filter</filter-class> -->
 */
public class IE9TextFilter implements Filter {
	protected static final Logger logger = Logger.getLogger(IE9TextFilter.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		// noop

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// Hier zwingen wir den IE9 in den Kombatibilitaetsmodus
		// ((HttpServletResponse) response).setHeader("X-UA-Compatible",
		// "IE=EmulateIE8");

		// Der IE9 sendet fuer css neu nur noch "text/css" als Accept header.
		// Dies hat zur Folge, dass die Klasse HtmlRenderUtils folgende
		// Exception wirft.
		// "ContentTypeList does not contain a supported content type: text/css"
		// Um dies zu verhindern kann man entweder das Myfaces Patchen oder eben
		// den Request korrigierren.
		// Wir haben uns für die zweite Lösung enschieden und verändern den
		// Accept header. Neu wird es "text/css, */*" anstatt nur "text/css"

		// String accept = ((HttpServletRequest) request).getHeader("Accept");
		//
		// if ("text/css".equals(accept)) {
		// chain.doFilter(new IE9HttpServletRequestWrapper((HttpServletRequest)
		// request), response);
		// } else {
		// chain.doFilter(request, response);
		// }

		// PrintWriter out = response.getWriter();
		// CharResponseWrapper wrapper = new
		// CharResponseWrapper((HttpServletResponse) response);
		// chain.doFilter(request, wrapper);
		//
		// // Extract the text from the completed servlet and apply the regexes
		// String modifiedHtml = wrapper.toString();
		// // for (int i = 0; i < this.searchPatterns.size(); i++) {
		// // modifiedHtml =
		// //
		// this.searchPatterns.get(i).matcher(modifiedHtml).replaceAll(this.replaceStrings.get(i));
		// // }
		// // modifiedHtml = modifiedHtml.replaceAll("text/css", "text/html");
		// modifiedHtml = modifiedHtml.replaceAll("text/css", "text/html");
		//
		// // Write our modified text to the real response
		// response.setContentLength(modifiedHtml.getBytes().length);
		// out.write(modifiedHtml);
		// out.close();

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		logger.debug(httpRequest.getRequestURI());
		
		if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
			
			logger.debug(httpRequest.getContentType());
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// noop
	}

	// Request Wrapper. Catches the getHeader for Accept. When it is text/css we

	// will return simply "text/css, */*"

	private class IE9HttpServletRequestWrapper extends HttpServletRequestWrapper {

		public IE9HttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getHeader(String name) {
			String header = super.getHeader(name);
			if ("text/css".equalsIgnoreCase(header)) {
				header = "text/css, */*";
			}
			return header;
		}

	}

}
