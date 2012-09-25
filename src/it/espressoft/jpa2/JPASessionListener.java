package it.espressoft.jpa2;

import it.espressoft.web.config.SoftwareConfig;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * Session Lifecycle Listener implementation class JPASessionListener
 * 
 * @author Mirco Attocchi
 */
public class JPASessionListener implements HttpSessionListener {

	protected static final Logger logger = Logger.getLogger(JPASessionListener.class.getName());

	// private Controller chachedController;

	/**
	 * Default constructor.
	 */
	public JPASessionListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent e) {

		/*
		 * Aggiunta del Supporto alla Configurazione
		 */
		SoftwareConfig.init(e.getSession().getServletContext());
		Map<String, String> dbProps = SoftwareConfig.getJpaDbProps();

		// com.objectdb.Enhancer.enhance("it.caderplink.entities.*");

		EntityManagerFactory emf = null;
		if (dbProps != null) {
			emf = Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU, dbProps);
		} else {
			emf = Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU);
		}

		e.getSession().setAttribute(IJpaListernes.SESSION_EMF, emf);
		logger.info(IJpaListernes.SESSION_EMF + " start");
		//
		// chachedController = new Controller(Controller.DEFAULT_PU);
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent e) {

		EntityManagerFactory emf = (EntityManagerFactory) e.getSession().getAttribute(IJpaListernes.SESSION_EMF);
		emf.close();
		logger.info(IJpaListernes.SESSION_EMF + " close");
		//
		// chachedController.close();
	}

}
