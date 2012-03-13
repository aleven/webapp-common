package it.espressoft.jsf2;

import it.espressoft.jpa2.IJpaListernes;

import javax.persistence.EntityManagerFactory;

public class PageBaseJpa2Data extends PageBase {
	/**
	 * Use With @JPASessionListener
	 * 
	 * @return
	 */
	protected EntityManagerFactory getEmfSession() {
		return (EntityManagerFactory) getSession().getAttribute(IJpaListernes.SESSION_EMF);
	}

	/**
	 * Use With @JPAListener
	 * 
	 * @return
	 */
	protected EntityManagerFactory getEmfContext() {
		return (EntityManagerFactory) getServletContext().getAttribute(IJpaListernes.SESSION_EMF);
	}

}
