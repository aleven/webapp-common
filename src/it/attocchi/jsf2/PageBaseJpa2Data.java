package it.attocchi.jsf2;

import it.attocchi.jpa2.IJpaListernes;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
		EntityManagerFactory emf = null;

		HttpSession session = getSession();
		if (session != null) {
			emf = (EntityManagerFactory) session.getAttribute(IJpaListernes.SESSION_EMF);
		} else {
			logger.error("Session is null ");
		}

		return emf;
	}

	/**
	 * Use With @JPAListener
	 * 
	 * @return
	 */
	private EntityManagerFactory getEmfContext() {
		EntityManagerFactory emf = null;

		ServletContext context = getServletContext();
		if (context != null) {
			emf = (EntityManagerFactory) context.getAttribute(IJpaListernes.SESSION_EMF);
		} else {
			logger.error("Context is null ");
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
