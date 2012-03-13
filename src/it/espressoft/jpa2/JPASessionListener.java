package it.espressoft.jpa2;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * Session Lifecycle Listener implementation class JPASessionListener
 * 
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
		// // com.objectdb.Enhancer.enhance("it.caderplink.entities.*");
		// EntityManagerFactory emf =
		// Persistence.createEntityManagerFactory(Controller.DEFAULT_PU);
		// e.getSession().setAttribute(SESSION_EMF, emf);
		// logger.debug(SESSION_EMF + " start");
		//
		// chachedController = new Controller(Controller.DEFAULT_PU);
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent e) {
		// EntityManagerFactory emf = (EntityManagerFactory)
		// e.getSession().getAttribute(SESSION_EMF);
		// emf.close();
		// logger.debug(SESSION_EMF + " close");
		//
		// chachedController.close();
	}

}
