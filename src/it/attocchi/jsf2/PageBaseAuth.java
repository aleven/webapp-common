package it.attocchi.jsf2;

import it.attocchi.jsf2.exceptions.PageAuthException;
import it.attocchi.web.filters.AuthFilter;

/**
 * Pagina Gestione Utenti Autenticati (Richiede filtro @AuthFilter)
 * 
 * @author Mirco
 * 
 */
public abstract class PageBaseAuth extends PageBaseNoAuth {

	private boolean debug = false;

	// @PostConstruct
	// private void postConstruct() {
	// try {
	//
	// if (getIdUtenteLoggato() > 0) {
	// init();
	// } else {
	//
	// addErrorMessage("Necessaria Autenticazione");
	// }
	//
	// } catch (Exception ex) {
	// // logger.error("postConstruct", ex);
	// addErrorMessage("postConstruct", ex);
	// }
	// }

	// protected abstract void inizializeMembers() throws Exception;

	@Override
	protected void preInit() throws PageAuthException {

		// inizializeMembers();

		if (getIdUtenteLoggato() > 0) {

			// Login e' stato fatto

		} else {
			logger.error("Necessaria Autenticazione");
			// addErrorMessage("Necessaria Autenticazione");
			// throw new Exception("Necessaria Autenticazione");
			throw new PageAuthException();
		}
	}

	protected abstract void init() throws Exception;

	/*
	 * Gestione Autorizzazione Utente in Sessione
	 */
	private int idUtenteLoggato;

	public int getIdUtenteLoggato() {

		idUtenteLoggato = getSessionObjectAsInt(AuthFilter.PARAM_AUTH);

		int param = getParamObjectAsInt(AuthFilter.PARAM_AUTH);

		if (idUtenteLoggato <= 0 || param != idUtenteLoggato) {

			if (param == 0 && debug) {
				logger.warn("auth debug mode");
				param = 1;
				addWarnMessage("auth debug mode", "");
			}

			if (param > 0) {
				idUtenteLoggato = param;
				setSessionObject(AuthFilter.PARAM_AUTH, idUtenteLoggato);
			}
		}

		return idUtenteLoggato;
	}

	public boolean isUtenteLoggato() {
		return getIdUtenteLoggato() > 0;
	}
}
