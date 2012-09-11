package it.espressoft.jsf2;

import it.espressoft.web.filters.AuthFilter;

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

			init();

		} catch (Exception ex) {
			// logger.error("postConstruct", ex);
			setErrorMessage("postConstruct", ex);
		}
	}

	protected abstract void init() throws Exception;

}
