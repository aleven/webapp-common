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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Pagina Base con Metodi di Gestione JSF
 * 
 * @author Mirco
 * 
 */
abstract class PageBase implements Serializable {

	protected static final Logger logger = Logger.getLogger(PageBase.class.getName());

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

	protected void addInfoMessage(String summary) {
		addInfoMessage(summary, "");
	}

	protected void addInfoMessage(String summary, String detail) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
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

		addErrorMessage("An error ocurred.", ex);
	}

	protected void addErrorMessage(String summary) {
		addErrorMessage(summary, "");
	}

	protected void addErrorMessage(String summary, Throwable ex) {

		if (ex != null && ex.getMessage() != null && summary != null) {

			if (ex.getCause() == null) {
				logger.error(summary, ex);
				getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ex.getMessage()));
			} else {
				logger.error(summary, ex.getCause());
				getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ex.getCause().getMessage()));
			}

		} else {
			
			logger.error("NullPointerException", ex);
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NullPointerException", "java.lang.NullPointerException"));
		}

	}

	protected void addErrorMessage(String summary, String detail) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
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
		String res = "";

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

	protected void redirect(String url) {
		try {
			getFacesContext().getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.error(e);
		}
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
				downloadFileName = rename + fileName.substring(fileName.lastIndexOf("."));
			}

			// Init servlet response.
			response.reset();
			response.setHeader("Content-Type", "application/octet-stream");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + downloadFileName + "\"");
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
				logger.error("close", e);
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

	public String getJsfRedirect(String outcome) {
		return outcome + "?faces-redirect=true";
	}

}
