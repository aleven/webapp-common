package it.attocchi.jsf2;

import javax.annotation.PostConstruct;

/**
 * Pagina Gestione Utenti Autenticati (Richiede filtro @AuthFilter)
 * 
 * @author Mirco
 * 
 */
public abstract class PageBaseNoAuthNoJpa2 extends PageBaseForceInitSession {

	@PostConstruct
	private void postConstruct() {
		try {

			initialiseSession();

			init();

		} catch (Exception ex) {
			// logger.error("postConstruct", ex);
			addErrorMessage("postConstruct", ex);
		}
	}

	protected abstract void init() throws Exception;

}
