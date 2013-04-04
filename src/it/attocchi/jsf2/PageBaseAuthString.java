package it.attocchi.jsf2;

import it.attocchi.jsf2.exceptions.PageAuthException;
import it.attocchi.web.filters.AuthFilter;

import org.apache.commons.lang3.StringUtils;

/**
 * Pagina Gestione Utenti Autenticati (Richiede filtro @AuthFilter)
 * 
 * @author Mirco
 * 
 */
public abstract class PageBaseAuthString extends PageBaseNoAuth {

	@Override
	protected void preInit() throws PageAuthException {
		if (StringUtils.isNotEmpty(getIdUtenteLoggato())) {

			// Login e' stato fatto

		} else {
			logger.error("Necessaria Autenticazione");
			// addErrorMessage("Necessaria Autenticazione");
			// throw new Exception("Necessaria Autenticazione");
		}
	}

	protected abstract void init() throws Exception;

	/*
	 * Gestione Autorizzazione Utente in Sessione
	 */
	private String idUtenteLoggato;

	public String getIdUtenteLoggato() {

		if (StringUtils.isEmpty(idUtenteLoggato)) {
			idUtenteLoggato = getSessionObjectAsString(AuthFilter.PARAM_AUTH);

			if (StringUtils.isEmpty(idUtenteLoggato)) {
				String param = getParamObject(AuthFilter.PARAM_AUTH);
				if (StringUtils.isNotEmpty(param)) {
					idUtenteLoggato = param;
					setSessionObject(AuthFilter.PARAM_AUTH, idUtenteLoggato);
				}
			}
		}

		return idUtenteLoggato;
	}

	public boolean isUtenteLoggato() {
		return StringUtils.isNotEmpty(getIdUtenteLoggato());
	}
}
