package it.webappcommon.lib.jpa.scooped;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * <p>PersistenceAppRequestListener class.</p>
 *
 * @author puche
 *
 *         Web application lifecycle listener.
 * @version $Id: $Id
 */
public class PersistenceAppRequestListener extends PersistenceAppListener implements ServletRequestListener {

	/**
	 * {@inheritDoc}
	 *
	 * ### Method from ServletRequestListener ###
	 *
	 * The request is about to come into scope of the web application.
	 */
	public void requestInitialized(ServletRequestEvent evt) {

	}

	/**
	 * {@inheritDoc}
	 *
	 * ### Method from ServletRequestListener ###
	 *
	 * The request is about to go out of scope of the web application.
	 */
	public void requestDestroyed(ServletRequestEvent evt) {

		PersistenceManager pm = PersistenceManager.getInstance();

		if (pm instanceof ScopedPersistenceManager) {
			LazyCloseEntityManager em = ((ScopedEntityManagerFactory) pm.getEntityManagerFactory()).getEntityManager();

			// solo si ya exista se cierra, pero es posible que no exista
			if (em != null)
				em.lazyClose();
		}
	}
}
