package it.webappcommon.lib.jpa.scooped;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>PersistenceAppListener class.</p>
 *
 * @author puche
 *
 *         Web application lifecycle listener.
 * @version $Id: $Id
 */
public class PersistenceAppListener implements ServletContextListener {

	private static final String PU_PARAMETER_NAME = "PersistenceAppListener.PERSISTENCE_UNIT";

	/** {@inheritDoc} */
	public void contextInitialized(ServletContextEvent evt) {
		PersistenceManager pm = PersistenceManager.getInstance();
		if (pm instanceof ScopedPersistenceManager) {
			// If the name of the persistence unit has not been set yet
			if (PersistenceManager.getPersistenceUnit() == null) {
				String pu = evt.getServletContext().getInitParameter(PU_PARAMETER_NAME);
				PersistenceManager.setPersistenceUnit((pu != null) ? pu : PersistenceManager.DEFAULT_PERSISTENCE_UNIT);
			}
		}
	}

	/** {@inheritDoc} */
	public void contextDestroyed(ServletContextEvent evt) {
		PersistenceManager.getInstance().closeEntityManagerFactory();
	}
}
