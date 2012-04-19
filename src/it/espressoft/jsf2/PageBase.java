package it.espressoft.jsf2;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class PageBase implements Serializable {

	protected static final Logger logger = Logger.getLogger(PageBase.class.getName());

	protected ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	protected HttpSession getSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (HttpSession) facesContext.getExternalContext().getSession(false);
	}

	protected void setInfoMessage(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	protected void setWarnMessage(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}

	protected void setErrorMessage(Throwable ex) {

		// if (ex != null && ex.getMessage() != null) {
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
		// } else {
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore Generico", null));
		// }

		setErrorMessage("An error ocurred.", ex);
	}

	protected void setErrorMessage(String summary) {
		setErrorMessage(summary, "");
	}

	protected void setErrorMessage(String summary, Throwable ex) {

		if (ex != null && ex.getMessage() != null) {

			if (ex.getCause() == null) {
				logger.error(summary, ex);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ex.getMessage()));
			} else {
				logger.error(summary, ex.getCause());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, ex.getCause().getMessage()));
			}

		} else {
			logger.error(summary, ex);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, "Errore Generico"));
		}

	}

	protected void setErrorMessage(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	protected String getInitParam(String name) {
		return getExternalContext().getInitParameter(name);
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
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

	protected String getParamObject(String paramName) {
		String res = null;

		try {
			Object p = getExternalContext().getRequestParameterMap().get(paramName);
			res = p.toString();
		} catch (Exception ex) {
			// logger.error("getParamObject", ex);
		}

		return res;
	}

	protected int getParamObjectAsInt(String paramName) {
		int res = 0;
		try {
			res = Integer.parseInt(getParamObject(paramName));
		} catch (Exception ex) {
			// logger.error("getParamObjectAsInt", ex);
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

		FacesContext facesContext = null;
		Map sessionObjects = null;
		try {
			facesContext = FacesContext.getCurrentInstance();
			sessionObjects = facesContext.getExternalContext().getSessionMap();
			returnValue = sessionObjects.get(object_name);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			sessionObjects = null;
		}

		return returnValue;
	}

	public Object setSessionObject(String object_name, Object object) {
		Object returnValue = null;

		FacesContext facesContext = null;
		Map sessionObjects = null;
		try {
			facesContext = FacesContext.getCurrentInstance();
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
}
