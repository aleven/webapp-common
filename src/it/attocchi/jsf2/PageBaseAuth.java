/*
    Copyright (c) 2012,2013 Mirco Attocchi
	
    This file is part of WebAppCommon.

    WebAppCommon is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WebAppCommon is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with WebAppCommon.  If not, see <http://www.gnu.org/licenses/>.
 */

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

		// if (getIdUtenteLoggato() > 0) {
		if (StringUtils.isNotBlank(getAuthKey())) {

			// Login e' stato fatto\passato

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
	private String idUtenteLoggato;

	// private String authKey;

	// public long getIdUtenteLoggato() {
	public String getAuthKey() {

		// idUtenteLoggato = getSessionObjectAsInt(AuthFilter.PARAM_AUTH);
		idUtenteLoggato = getSessionObjectAsString(AuthFilter.PARAM_AUTH);

		// int param = getParamObjectAsInt(AuthFilter.PARAM_AUTH);
		String param = getParamObject(AuthFilter.PARAM_AUTH);

		// if (idUtenteLoggato <= 0 || param != idUtenteLoggato) {
		if (idUtenteLoggato == null || (param != null && !param.equals(idUtenteLoggato))) {

			// if (param == 0 && debug) {
			if (param == null && debug) {
				logger.warn("auth debug mode");
				param = "1";
				addWarnMessage("auth debug mode", "");
			}

			// if (param > 0) {
			if (StringUtils.isNotBlank(param)) {
				idUtenteLoggato = param;
				setSessionObject(AuthFilter.PARAM_AUTH, idUtenteLoggato);
			}
		}

		return idUtenteLoggato;
	}

	// public String getAuthKey() {
	// authKey = getSessionObjectAsString(AuthFilter.PARAM_AUTH);
	// String param = getParamObjectAsString(AuthFilter.PARAM_AUTH);
	// }

	public boolean isUtenteLoggato() {
		// return getIdUtenteLoggato() > 0 ||
		// StringUtils.isNotBlank(getAuthKey());
		// return getIdUtenteLoggato() > 0;
		return StringUtils.isNotBlank(getAuthKey());
	}
}
