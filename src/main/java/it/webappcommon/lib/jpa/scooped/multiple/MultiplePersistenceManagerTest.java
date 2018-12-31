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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

/**
 * <p>MultiplePersistenceManagerTest class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class MultiplePersistenceManagerTest {

	private static final Logger logger = Logger.getLogger(MultiplePersistenceManagerTest.class.getName());

	// public static final boolean DEBUG = true;
	/** Constant <code>PERSISTENCE_UNIT="DefaultPU"</code> */
	public static final String PERSISTENCE_UNIT = "DefaultPU";

	private static final MultiplePersistenceManagerTest singletonScooped = new ScopedMultiplePersistenceManagerTest();
	/*
	 * for request scope ( lazy close ) ( requiere declarar request listener )
	 */

	// private static final MultiplePersistenceManagerTest singleton = new
	// MultiplePersistenceManagerTest();
	// /*
	// * For not scoped em
	// */

	// private EntityManagerFactory emfToClose;
	private Map<String, EntityManagerFactory> emfMap;

	/**
	 * <p>getInstance.</p>
	 *
	 * @return a {@link it.webappcommon.lib.jpa.scooped.multiple.MultiplePersistenceManagerTest} object.
	 */
	public static MultiplePersistenceManagerTest getInstance() {

		// if (singletonScooped == null) {
		// singletonScooped = new ScopedMultiplePersistenceManagerTest();
		// }
		// if (scooped) {
		// singleton = new ScopedPersistenceManagerTest();
		// } else {
		// singleton = new MultiplePersistenceManagerTest();
		// }
		// }

		return singletonScooped;
	}

	// public static MultiplePersistenceManagerTest getInstanceNotScooped() {
	//
	// // if (singletonScooped == null) {
	// // singletonScooped = new ScopedMultiplePersistenceManagerTest();
	// // }
	// // if (scooped) {
	// // singleton = new ScopedPersistenceManagerTest();
	// // } else {
	// // singleton = new MultiplePersistenceManagerTest();
	// // }
	// // }
	//
	// return singleton;
	// }

	/**
	 * <p>Constructor for MultiplePersistenceManagerTest.</p>
	 */
	protected MultiplePersistenceManagerTest() {
	}

	/**
	 * <p>getEntityManagerFactory.</p>
	 *
	 * @param persisteceUnit a {@link java.lang.String} object.
	 * @return a {@link javax.persistence.EntityManagerFactory} object.
	 */
	public synchronized EntityManagerFactory getEntityManagerFactory(String persisteceUnit) {

		if (emfMap == null) {
			emfMap = new HashMap<String, EntityManagerFactory>();
		}

		if (emfMap.get(persisteceUnit) == null) {
			emfMap.put(persisteceUnit, createEntityManagerFactory(persisteceUnit));
			logger.debug("\n*** Persistence " + persisteceUnit + " enabled " + new java.util.Date());
		}
		// emfToClose = emfMap.get(persisteceUnit);

		// if (emfToClose == null) {
		// emfToClose = createEntityManagerFactory(pu);
		// if (DEBUG)
		// System.out.println("\n*** Persistence enabled "
		// + new java.util.Date());
		// }

		// return emfToClose;
		return emfMap.get(persisteceUnit);
	}

	/**
	 * <p>getEntityManagerFactoryList.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public synchronized Set<Entry<String, EntityManagerFactory>> getEntityManagerFactoryList() {
		return emfMap.entrySet();
	}

	/**
	 * <p>closeEntityManagerFactory.</p>
	 *
	 * @param persisteceUnit a {@link java.lang.String} object.
	 */
	public synchronized void closeEntityManagerFactory(String persisteceUnit) {
		if (emfMap != null) {
			if (!emfMap.isEmpty()) {

				EntityManagerFactory emf = emfMap.get(persisteceUnit);
				if (emf != null) {
					// for (Entry<String, EntityManagerFactory> emfEntry :
					// emfMap.entrySet()) {
					// EntityManagerFactory emf = emfEntry.getValue();

					emf.close();
					emf = null;

					logger.debug("*** Persistence " + persisteceUnit + " destroyed " + new java.util.Date());
				}
				// Mirco: errore pulirla qui, se non sono chiuse tutte??
				// emfMap.clear();
			}
			// Mirco: errore pulirla qui, se non sono chiuse tutte??
			// emfMap = null;
		}
	}

	/**
	 * <p>closeEntityManagerFactory.</p>
	 */
	public synchronized void closeEntityManagerFactory() {

		if (emfMap != null) {
			if (!emfMap.isEmpty()) {
//				for (Entry<String, EntityManagerFactory> emfEntry : emfMap.entrySet()) {
//					EntityManagerFactory emf = emfEntry.getValue();
//
//					emf.close();
//					emf = null;
//
//					logger.debug("\n*** Persistence " + emfEntry.getKey() + " destroyed " + new java.util.Date());
//				}
				// Mirco: ottimizzazione, chiudo usando il metodo dedicato
				for (String key : emfMap.keySet()) {
					closeEntityManagerFactory(key);
				}
				emfMap.clear();
			}
			emfMap = null;
		}

		// if (emfToClose != null) {
		// emfToClose.close();
		// emfToClose = null;
		// if (DEBUG)
		// System.out.println("\n*** Persistence destroyed "
		// + new java.util.Date());
		// }
	}

	/**
	 * <p>createEntityManagerFactory.</p>
	 *
	 * @param pu a {@link java.lang.String} object.
	 * @return a {@link javax.persistence.EntityManagerFactory} object.
	 */
	protected EntityManagerFactory createEntityManagerFactory(String pu) {
		return Persistence.createEntityManagerFactory(pu);
	}
}
