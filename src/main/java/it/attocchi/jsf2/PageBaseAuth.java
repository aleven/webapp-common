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

import it.attocchi.web.filters.AuthFilter;

import org.apache.commons.lang3.StringUtils;

/**
 * Pagina Gestione Utenti Autenticati (Richiede filtro @AuthFilter)
 * 
 * @author Mirco
 * 
 */
public abstract class PageBaseAuth extends PageBaseNoAuth {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean debug = false;

	@Override
	protected final void init() throws Exception {

		if (isUtenteLoggato()) {
			initLogged();
		} else {
			logger.warn("Necessaria Autenticazione");
			initNonLogged();
			throw new Exception("Necessaria Autenticazione");
		}
	}

	protected abstract void initLogged() throws Exception;

	protected abstract void initNonLogged() throws Exception;

	/*
	 * Gestione Autorizzazione Utente in Sessione
	 */
	private String idUtenteLoggato;

	public String getAuthKey() {

		idUtenteLoggato = getSessionObjectAsString(AuthFilter.PARAM_AUTH);

		String param = getParamObject(AuthFilter.PARAM_AUTH);

		/* l'opzione debug si puo' inizializzare da web.xml */
		debug = getInitParam("debug") != null && getInitParam("debug").equalsIgnoreCase("true");
			
		if (idUtenteLoggato == null || (param != null && !param.equals(idUtenteLoggato))) {

			if (param == null && debug) {
				logger.warn("auth debug mode");
				param = "1";
				addWarnMessage("auth debug mode", "");
			}

			if (StringUtils.isNotBlank(param)) {
				idUtenteLoggato = param;
				setSessionObject(AuthFilter.PARAM_AUTH, idUtenteLoggato);
			}
		}

		return idUtenteLoggato;
	}

	protected boolean isUtenteLoggato() {
		return StringUtils.isNotBlank(getAuthKey());
	}

}
