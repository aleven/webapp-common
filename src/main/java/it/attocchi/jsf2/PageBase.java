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

package it.attocchi.jsf2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Pagina Base con Metodi di Gestione JSF
 * 
 * @author Mirco
 * 
 */
abstract class PageBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Logger logger = Logger.getLogger(this.getClass().getName());
	protected static final Logger loggerStatic = Logger.getLogger(PageBase.class.getName());

	protected ServletContext getServletContext() {
		return (ServletContext) getExternalContext().getContext();
	}

	protected HttpSession getSession() {
		HttpSession res = (HttpSession) getExternalContext().getSession(false);
		if (res == null) {
			logger.warn("HttpSession is null");
		}
		return res;
	}

	protected void addFatalMessage(String summary) {
		addInfoMessage(summary, "");
	}

	protected void addFatalMessage(String summary, String detail) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
	}

	protected void addInfoMessage(String summary) {
		addInfoMessage(summary, "");
	}

	protected void addInfoMessage(String summary, String detail) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	protected void addWarnMessage(String summary) {
		addWarnMessage(summary, "");
	}

	protected void addWarnMessage(String summary, String detail) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}

	protected void addErrorMessage(Throwable ex) {

		// if (ex != null && ex.getMessage() != null) {
		// getFacesContext().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
		// } else {
		// getFacesContext().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore Generico", null));
		// }

		// logger.error(ex);
		//
		// if (ex != null && ex.getMessage() != null) {
		// addErrorMessage(ex.getMessage());
		// } else {
		// addErrorMessage("NullPointerException", ex);
		// }

		if (ex != null)
			addErrorMessage(ex.getMessage(), ex);
		else
			addErrorMessage("Error", ex);

	}

	protected void addErrorMessage(String summary) {
		addErrorMessage(summary, "");
	}

	protected void addErrorMessage(String summary, Throwable ex) {

		if (ex != null && ex.getMessage() != null) {

			if (summary != null) {
				if (ex.getCause() == null) {
					logger.error(summary, ex);
					getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ex.getMessage()));
				} else {
					logger.error(summary, ex.getCause());
					getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ex.getCause().getMessage()));
				}
			} else {
				if (ex.getCause() == null) {
					logger.error(ex);
					summary = ex.getMessage();
					getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ""));
				} else {
					logger.error(ex.getCause());
					summary = ex.getCause().getMessage();
					getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ""));
				}
			}

		} else {

			logger.error("Error", ex);
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Null"));
		}

	}

	protected void addErrorMessage(String summary, String detail) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	protected void addValidationMessage(String componentId, String summary) {

		getFacesContext().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));

		/*
		 * UIComponent comp =
		 * FacesContext.getCurrentInstance().getViewRoot().findComponent
		 * (componentId); if ((comp != null) && (comp instanceof UIInput)) {
		 * ((UIInput) comp).setValid(false); }
		 */

	}

	protected String getInitParam(String name) {
		return getExternalContext().getInitParameter(name);
	}

	protected FacesContext getFacesContext() {
		FacesContext res = FacesContext.getCurrentInstance();
		if (res == null) {
			logger.warn("FacesContext is null");
		}
		return res;
	}

	protected ExternalContext getExternalContext() {
		ExternalContext res = getFacesContext().getExternalContext();
		if (res == null) {
			logger.warn("ExternalContext is null");
		}
		return res;
	}

	/*
	 * SCREEN SIZE
	 */

	private Integer viewportWidth;
	private Integer viewportHeight;

	public int getViewportWidth() {
		if (viewportWidth == null) {
			viewportWidth = getParamObjectAsInt("viewportWidth");
		}
		return viewportWidth;
	}

	public int getViewportHeight() {
		if (viewportHeight == null) {
			viewportHeight = getParamObjectAsInt("viewportHeight");
		}
		return viewportHeight;
	}

	/*
	 * END SCREEN SIZE
	 */

	/**
	 * Verify if current page has certain name passed parameter
	 * 
	 * @param paramName
	 * @return
	 */
	protected boolean hasParam(String paramName) {
		return getParamObject(paramName) != null;
	}

	protected String getParamObject(String paramName) {
		String res = null;

		try {
			Object o = getExternalContext().getRequestParameterMap().get(paramName);
			if (o != null) {
				res = o.toString();
			}
		} catch (Exception ex) {
			logger.error("getParamObject", ex);
		}

		return res;
	}

	protected int getParamObjectAsInt(String paramName) {
		int res = 0;
		try {
			Object o = getParamObject(paramName);
			if (o != null) {
				res = Integer.parseInt(o.toString());
			}
		} catch (Exception ex) {
			logger.error("getParamObjectAsInt", ex);
		}
		return res;
	}

	protected Long getParamObjectAsLong(String paramName) {
		Long res = 0l;
		try {
			Object o = getParamObject(paramName);
			if (o != null) {
				res = Long.parseLong(o.toString());
			}
		} catch (Exception ex) {
			logger.error("getParamObjectAsLong", ex);
		}
		return res;
	}

	protected <T> T getParamObject(String paramName, Class<T> clazz) {
		T res = null;

		try {
			Object o = getExternalContext().getRequestParameterMap().get(paramName);
			if (o != null) {
				res = clazz.cast(o);
			}
		} catch (Exception ex) {
			logger.error("getParamObject", ex);
		}

		return res;
	}

	protected String getResourceBundle(String resourceName, String key) {
		ResourceBundle bundle = getFacesContext().getApplication().getResourceBundle(getFacesContext(), resourceName);
		return bundle.getString(key);
	}

	/*
	 * SESSION
	 */

	public Object getSessionObject(String object_name) {
		Object returnValue = null;

		// FacesContext facesContext = null;
		// Map sessionObjects = null;
		try {
			// facesContext = getFacesContext();
			// sessionObjects =
			// facesContext.getExternalContext().getSessionMap();
			// returnValue = sessionObjects.get(object_name);

			HttpSession session = getSession();
			if (session != null) {
				returnValue = session.getAttribute(object_name);
			}

		} catch (Exception e) {

		} finally {
			// facesContext = null;
			// sessionObjects = null;
		}

		return returnValue;
	}

	protected int getSessionObjectAsInt(String paramName) {
		int res = 0;

		try {
			Object o = getSessionObject(paramName);
			if (o != null) {
				res = Integer.parseInt(o.toString());
			}
		} catch (Exception ex) {
			logger.error("getSessionObjectAsInt", ex);
		}
		return res;
	}

	protected String getSessionObjectAsString(String paramName) {
		String res = null;

		try {
			Object o = getSessionObject(paramName);
			if (o != null) {
				res = String.valueOf(o.toString());
			}
		} catch (Exception ex) {
			logger.error("getSessionObjectAsString", ex);
		}
		return res;
	}

	public Object setSessionObject(String object_name, Object object) {
		Object returnValue = null;

		FacesContext facesContext = null;
		Map sessionObjects = null;
		try {
			facesContext = getFacesContext();
			sessionObjects = facesContext.getExternalContext().getSessionMap();
			sessionObjects.remove(object_name);
			returnValue = sessionObjects.put(object_name, object);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			sessionObjects = null;
		}

		return returnValue;
	}

	/*
	 * END SESSION
	 */

	protected void redirect(String relativeUrl) {
		try {
			getFacesContext().getExternalContext().redirect(relativeUrl);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	protected void redirectContext(String relativeUrl) {
		if (!relativeUrl.startsWith("/"))
			relativeUrl = "/" + relativeUrl;

		redirect(getRequest().getContextPath() + relativeUrl);
	}

	protected void redirectContextCall(String relativeUrl) {

		String uri = getRequest().getRequestURI();
		/* rimuoviamo il context dal parametro che chiamiamo */
		uri = uri.replace(getRequest().getContextPath(), "");

		String newUrl = String.format("%s?call=%s", relativeUrl, uri);

		logger.warn(newUrl);

		redirectContext(newUrl);
	}

	/*
	 * DOWNLOAD
	 * 
	 * thanks to: http://balusc.blogspot.it/
	 */

	// Constants
	// ----------------------------------------------------------------------------------

	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	// Actions
	// ------------------------------------------------------------------------------------

	protected void downloadPDF(String fileName, String rename) throws IOException {

		// Prepare.
		FacesContext facesContext = getFacesContext();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		File file = new File(fileName);
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

			String downloadFileName = file.getName();
			if (rename != null && !rename.equals("")) {
				/*
				 * controllo se rename specifica l'estensione, se non la
				 * specifica la impostiamo dal nome originale
				 */
				String extNuova = FilenameUtils.getExtension(rename);
				String extOrig = FilenameUtils.getExtension(fileName);
				if (StringUtils.isBlank(extNuova))
					if (StringUtils.isNotBlank(extNuova))
						rename = rename + "." + extOrig;
				downloadFileName = rename;
				// fileName.substring(fileName.lastIndexOf("."));
			}

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			// response.setHeader("Content-Disposition", "inline; filename=\"" +
			// downloadFileName + "\"");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		// Inform JSF that it doesn't need to handle response.
		// This is very important, otherwise you will get the following
		// exception in the logs:
		// java.lang.IllegalStateException: Cannot forward after response has
		// been committed.
		facesContext.responseComplete();
	}

	// Helpers (can be refactored to public utility class)
	// ----------------------------------------

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail
				// it. It may be useful to
				// know that this will generally only be thrown when the client
				// aborted the download.
				// e.printStackTrace();
				loggerStatic.error("close", e);
			}
		}
	}

	/*
	 * 
	 */

	public Locale getCurrentLocale() {
		return getFacesContext().getViewRoot().getLocale();
	}

	public String getCurrentLocaleDatePattern() {
		SimpleDateFormat format = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, getCurrentLocale());
		return format.toLocalizedPattern();
	}

	public String getRealPath() {
		return getServletContext().getRealPath("/");
	}

	public String getRealPath(String relativePath) {
		return getServletContext().getRealPath(relativePath);
	}

	/*
	 * 
	 */
	public String getJsfRedirect(String outcome) {
		return outcome + "?faces-redirect=true";
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	protected String encodeParam(String value) {
		return Base64.encodeBase64URLSafeString(value.getBytes());
	}

	protected String decodeParam(String value) {
		return new String(Base64.decodeBase64(value));
	}

	public String truncate50(String aString) {
		String res = aString;
		if (res != null && !res.isEmpty()) {
			if (res.length() > 50) {
				res = res.substring(0, 50) + "...";
			}
		}
		return res;
	}

	protected void sessionInvalidate() {
		// HttpSession session = request.getSession(false);
		if (getSession() != null)
			getSession().invalidate();
	}
}
