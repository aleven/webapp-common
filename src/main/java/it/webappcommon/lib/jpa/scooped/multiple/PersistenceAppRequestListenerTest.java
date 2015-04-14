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

package it.webappcommon.lib.jpa.scooped.multiple;

import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.log4j.Logger;

/**
 * 
 * @author Mirco Attocchi
 * 
 *         Web application lifecycle listener.
 */

public class PersistenceAppRequestListenerTest implements ServletRequestListener {

	private static final Logger logger = Logger.getLogger(PersistenceAppRequestListenerTest.class.getName());

	/**
	 * ### Method from ServletRequestListener ###
	 * 
	 * The request is about to come into scope of the web application.
	 */
	public void requestInitialized(ServletRequestEvent evt) {
		// TODO add your code here:
	}

	/**
	 * ### Method from ServletRequestListener ###
	 * 
	 * The request is about to go out of scope of the web application.
	 */
	public void requestDestroyed(ServletRequestEvent evt) {

		MultiplePersistenceManagerTest pm = MultiplePersistenceManagerTest.getInstance();

		if (pm instanceof ScopedMultiplePersistenceManagerTest) {

			Set<Entry<String, EntityManagerFactory>> listaAperti = pm.getEntityManagerFactoryList();
			if (!listaAperti.isEmpty()) {
				for (Entry<String, EntityManagerFactory> emfEntry : listaAperti) {

					// LazyCloseEntityManagerTest em =
					// ((ScopedEntityManagerFactoryTest) pm
					// .getEntityManagerFactory(pm.PERSISTENCE_UNIT))
					// .getEntityManager();

					LazyCloseEntityManagerTest lem = ((ScopedEntityManagerFactoryTest) emfEntry.getValue()).getEntityManager();

					// only if exist
					if (lem != null) {
						lem.lazyClose();

						logger.debug("*** LazyClose EntityManager " + emfEntry.getKey() + " close " + new java.util.Date());
					}

				}
			}
		}
		// else {
		//
		// // Usando sempre lo SCOPED meglio chiudere tutto
		//
		// pm.closeEntityManagerFactory();
		// }

	}
}