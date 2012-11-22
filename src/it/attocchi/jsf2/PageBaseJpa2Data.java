package it.attocchi.jsf2;

import it.attocchi.jpa2.IJpaListernes;

import javax.persistence.EntityManagerFactory;

/**
 * Gestione EntityManagerFactory in Sessione o Context (richiede uno dei filtri
 * 
 * @IJpaListernes)
 * 
 * @author Mirco
 * 
 */
abstract class PageBaseJpa2Data extends PageBaseForceInitSession {
	/**
	 * Use With @JPASessionListener
	 * 
	 * @return
	 */
	private EntityManagerFactory getEmfSession() {
		EntityManagerFactory emf = (EntityManagerFactory) getSession().getAttribute(IJpaListernes.SESSION_EMF);
		if (emf == null) {
			// logger.error("getEmfSession: EntityManagerFactory is not in Session");
		}
		return emf;
	}

	/**
	 * Use With @JPAListener
	 * 
	 * @return
	 */
	private EntityManagerFactory getEmfContext() {
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute(IJpaListernes.SESSION_EMF);
		if (emf == null) {
			// logger.error("getEmfContext: EntityManagerFactory is not in Context");
		}
		return emf;
	}

	protected abstract void init() throws Exception;

	protected EntityManagerFactory getEmfShared() {
		EntityManagerFactory emf = null;

		EntityManagerFactory emfSession = getEmfSession();
		EntityManagerFactory emfContext = getEmfContext();

		if (emfSession != null) {
			emf = emfSession;
		} else if (emfContext != null) {
			emf = emfContext;
		} else {
			logger.error("getEmfShared: EntityManagerFactory is not in Session or in Context ");
		}

		return emf;
	}
}
