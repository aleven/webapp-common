package it.attocchi.jsf2;


import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

/**
 * Pagina che Forza l'inizializzazione della Sessione
 * 
 * @author Mirco
 * 
 */
abstract class PageBaseForceInitSession extends PageBase {
	/**
	 * http://stackoverflow.com/questions/7433575/cannot-create-a-session-after-
	 * the-response-has-been-committed
	 */
	// @PostConstruct
	public void initialiseSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	protected abstract void init() throws Exception;
}
