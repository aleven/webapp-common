package it.webappcommon.lib.jpa.scooped;

import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author puche
 */
public class PersistenceManager {

	private static final Logger log = Logger.getLogger(PersistenceManager.class.getName());

	protected static final String DEFAULT_PERSISTENCE_UNIT = "DefaultPU";
	private static String persistenceUnit = null;

	private static final PersistenceManager singleton = new ScopedPersistenceManager(); // for
																						// request
																						// scope
																						// (lazy
																						// close)
																						// (requiere
																						// declarar
																						// request
																						// listener)
	// new PersistenceManager(); // For not scoped em

	private EntityManagerFactory emf;

	public static PersistenceManager getInstance() {

		return singleton;
	}

	protected PersistenceManager() {
	}

	public synchronized EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			emf = createEntityManagerFactory();
			log.info("Persistence Manager has been initialized");
		}
		return emf;
	}

	public synchronized void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
			emf = null;
			log.info("Persistence Manager has been closed");
		}
	}

	protected EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(persistenceUnit);
	}

	/**
	 * @return The name of the persistence unit that will be used when the
	 *         entity manager factory is created.
	 */
	public static String getPersistenceUnit() {
		return persistenceUnit;
	}

	/**
	 * Sets the name of the persistence unit that will be used when the entity
	 * manager factory is created.
	 * 
	 * @param persistenceUnit
	 *            Name of one persistence unit defined in persistence.xml
	 */
	public static void setPersistenceUnit(String persistenceUnit) {
		PersistenceManager.persistenceUnit = persistenceUnit;
	}
}