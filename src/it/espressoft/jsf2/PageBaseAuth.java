package it.espressoft.jsf2;

import it.espressoft.web.filters.AuthFilter;

import javax.annotation.PostConstruct;

/**
 * Pagina Gestione Utenti Autenticati (Richiede filtro @AuthFilter)
 * 
 * @author Mirco
 * 
 */
public abstract class PageBaseAuth extends PageBaseNoAuth {

	@PostConstruct
	private void postConstruct() {
		try {

			if (getIdUtenteLoggato() > 0) {
				init();
			} else {

				setErrorMessage("Necessaria Autenticazione");
			}

		} catch (Exception ex) {
			// logger.error("postConstruct", ex);
			setErrorMessage("postConstruct", ex);
		}
	}

	protected abstract void init() throws Exception;

	/*
	 * Gestione Autorizzazione Utente in Sessione
	 */
	private int idUtenteLoggato;

	public int getIdUtenteLoggato() {

		idUtenteLoggato = getSessionObjectAsInt(AuthFilter.PARAM_AUTH);

		if (idUtenteLoggato <= 0) {
			int param = getParamObjectAsInt(AuthFilter.PARAM_AUTH);
			if (param > 0) {
				idUtenteLoggato = param;
				setSessionObject(AuthFilter.PARAM_AUTH, idUtenteLoggato);
			}
		}

		return idUtenteLoggato;
	}
}
