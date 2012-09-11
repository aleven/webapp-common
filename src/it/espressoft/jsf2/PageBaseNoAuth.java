package it.espressoft.jsf2;

import javax.annotation.PostConstruct;

/**
 * Pagina Gestione Utenti Autenticati (Richiede filtro @AuthFilter)
 * 
 * @author Mirco
 * 
 */
public abstract class PageBaseNoAuth extends PageBaseJpa2Data {

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
