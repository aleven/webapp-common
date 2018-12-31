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


import javax.faces.context.FacesContext;

/**
 * Pagina che Forza l'inizializzazione della Sessione
 * 
 * @author Mirco
 * 
 */
abstract class PageBaseForceInitSession extends PageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * http://stackoverflow.com/questions/7433575/cannot-create-a-session-after-
	 * the-response-has-been-committed
	 */
	// @PostConstruct
	public final void initialiseSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	/**
	 * <p>init.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract void init() throws Exception;
}
