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

public class PersistenceManagerUtil {

	// public static final boolean DEBUG = true;
	// public static final String PERSISTENCE_UNIT = "DefaultPU";
	// //new PersistenceManager(); // For not scoped em
	// private static final PersistenceManager singleton = new
	// PersistenceManager(); // for request scope (lazy close) (requiere
	// declarar request listener)
	// //new PersistenceManager(); // For not scoped em
	// private EntityManagerFactory emf;
	//
	// public static PersistenceManager getInstance() {
	//
	// return singleton;
	// }
	//
	// protected PersistenceManager() {
	// }
	//
	// public synchronized EntityManagerFactory getEntityManagerFactory() {
	//
	// if (emf == null) {
	// emf = createEntityManagerFactory();
	// if (DEBUG) {
	// System.out.println(
	// "\n*** Persistence enabled " + new java.util.Date());
	// }
	// }
	// return emf;
	// }
	//
	// public synchronized void closeEntityManagerFactory() {
	//
	// if (emf != null) {
	// emf.close();
	// emf = null;
	// if (DEBUG) {
	// System.out.println(
	// "\n*** Persistence destroyed " + new java.util.Date());
	// }
	// }
	//
	// }
	//
	// protected EntityManagerFactory createEntityManagerFactory() {
	// return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	// }

	/**
	 * Chiude l'entity manager passato come parametro se non e' null ed e'
	 * aperto
	 * 
	 * @param em
	 */
	public static void close(EntityManager em) {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}
}
