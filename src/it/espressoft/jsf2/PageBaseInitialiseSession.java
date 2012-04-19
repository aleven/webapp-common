package it.espressoft.jsf2;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

public class PageBaseInitialiseSession extends PageBase {
	/**
	 * http://stackoverflow.com/questions/7433575/cannot-create-a-session-after-
	 * the-response-has-been-committed
	 */
	@PostConstruct
	void initialiseSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
}
