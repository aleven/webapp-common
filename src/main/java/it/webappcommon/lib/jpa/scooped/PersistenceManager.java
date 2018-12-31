package it.webappcommon.lib.jpa.scooped;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;

/**
 * A scooped persistence manager for JSF
 *
 * @author puche
 * @version $Id: $Id
 */
public class PersistenceManager {

	private static final Logger log = Logger.getLogger(PersistenceManager.class.getName());

	/** Constant <code>DEFAULT_PERSISTENCE_UNIT="DefaultPU"</code> */
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

	/**
	 * <p>getInstance.</p>
	 *
	 * @return a {@link it.webappcommon.lib.jpa.scooped.PersistenceManager} object.
	 */
	public static PersistenceManager getInstance() {

		return singleton;
	}

	/**
	 * <p>Constructor for PersistenceManager.</p>
	 */
	protected PersistenceManager() {
	}

	/**
	 * <p>getEntityManagerFactory.</p>
	 *
	 * @return a {@link javax.persistence.EntityManagerFactory} object.
	 */
	public synchronized EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			emf = createEntityManagerFactory();
			log.info("Persistence Manager has been initialized");
		}
		return emf;
	}

	/**
	 * <p>closeEntityManagerFactory.</p>
	 */
	public synchronized void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
			emf = null;
			log.info("Persistence Manager has been closed");
		}
	}

	/**
	 * <p>createEntityManagerFactory.</p>
	 *
	 * @return a {@link javax.persistence.EntityManagerFactory} object.
	 */
	protected EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(persistenceUnit);
	}

	/**
	 * <p>Getter for the field <code>persistenceUnit</code>.</p>
	 *
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
