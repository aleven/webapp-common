/*
    Copyright (c) 2007,2014 Mirco Attocchi
	
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

package it.webappcommon.lib.jpa.scooped;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * <p>PersistenceManagerUtil class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class PersistenceManagerUtil {

	private static final Logger logger = Logger.getLogger(PersistenceManagerUtil.class.getName());

	/**
	 * Chiude l'entity manager passato come parametro se non e' null ed e'
	 * aperto
	 *
	 * @param em a {@link javax.persistence.EntityManager} object.
	 */
	public static void close(EntityManager em) {
		if (em != null && em.isOpen()) {
			em.close();
			logger.debug("em close() called");
		}
	}
}
