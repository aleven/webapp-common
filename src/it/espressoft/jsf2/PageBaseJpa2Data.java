package it.espressoft.jsf2;

import it.espressoft.jpa2.IJpaListernes;

import javax.persistence.EntityManagerFactory;

/**
 * Gestione EntityManagerFactory in Sessione o Context (richiede uno dei filtri
 * @IJpaListernes)
 * 
 * @author Mirco
 * 
 */
public abstract class PageBaseJpa2Data extends PageBaseForceInitSession {
	/**
	 * Use With @JPASessionListener
	 * 
	 * @return
	 */
	protected EntityManagerFactory getEmfSession() {
		EntityManagerFactory emf = (EntityManagerFactory) getSession().getAttribute(IJpaListernes.SESSION_EMF);
		if (emf == null) {
			logger.error("getEmfSession: EntityManagerFactory is not in Session");
		}
		return emf;
	}

	/**
	 * Use With @JPAListener
	 * 
	 * @return
	 */
	protected EntityManagerFactory getEmfContext() {
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute(IJpaListernes.SESSION_EMF);
		if (emf == null) {
			logger.error("getEmfContext: EntityManagerFactory is not in Context");
		}
		return emf;
	}

	protected abstract void init() throws Exception;

}
