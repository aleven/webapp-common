package it.attocchi.jsf2;

import it.attocchi.jsf2.exceptions.PageAuthException;

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

			initialiseSession();

			preInit();

			init();

		} catch (Exception ex) {
			logger.error("postConstruct", ex);
			addErrorMessage(ex.getMessage(), ex);
		}
	}

	protected abstract void preInit() throws PageAuthException;

	protected abstract void init() throws Exception;

}
