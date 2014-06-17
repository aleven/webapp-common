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

package it.atreeblu.lib.jpa.scooped;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author Mirco Attocchi
 */
public class PersistenceManager {

	public static final boolean DEBUG = true;
	// public static final String PERSISTENCE_UNIT = "KatiaServerPU";

	// private static final PersistenceManager singleton =
	// new ScopedPersistenceManager(); // for request scope (lazy close)
	// (requiere declarar request listener)
	private static final PersistenceManager singleton = new PersistenceManager(); // For
																					// not
																					// scoped
																					// em

	private EntityManagerFactory emf;

	public static PersistenceManager getInstance() {

		return singleton;
	}

	protected PersistenceManager() {
	}

	public synchronized EntityManagerFactory getEntityManagerFactory(String persistentUnit) {

		if (emf == null) {
			emf = createEntityManagerFactory(persistentUnit);
			if (DEBUG)
				System.out.println("\n*** Persistence enabled "
						+ new java.util.Date());
		}
		return emf;
	}

	public synchronized void closeEntityManagerFactory() {

		if (emf != null) {
			emf.close();
			emf = null;
			if (DEBUG)
				System.out.println("\n*** Persistence destroyed "
						+ new java.util.Date());
		}
	}

	protected EntityManagerFactory createEntityManagerFactory(String persistentUnit) {

		return Persistence.createEntityManagerFactory(persistentUnit);
	}
}