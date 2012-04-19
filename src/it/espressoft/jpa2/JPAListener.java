package it.espressoft.jpa2;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class JPAListener
 * 
 */
public class JPAListener implements ServletContextListener {

	protected static final Logger logger = Logger.getLogger(JPAListener.class.getName());

	private JpaController chachedController;

	/**
	 * Default constructor.
	 */
	public JPAListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent e) {
		// com.objectdb.Enhancer.enhance("it.caderplink.entities.*");
		// EntityManagerFactory emf =
		// Persistence.createEntityManagerFactory("$objectdb/db/caderplink.odb");
		// e.getServletContext().setAttribute("emf", emf);

		// com.objectdb.Enhancer.enhance("it.caderplink.entities.*");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU);
		e.getServletContext().setAttribute(IJpaListernes.SESSION_EMF, emf);
		logger.info(IJpaListernes.SESSION_EMF + " start");

		chachedController = new JpaController(IJpaListernes.DEFAULT_PU);
		chachedController.test();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent e) {
		// EntityManagerFactory emf = (EntityManagerFactory)
		// e.getServletContext().getAttribute("emf");
		// emf.close();

		EntityManagerFactory emf = (EntityManagerFactory) e.getServletContext().getAttribute(IJpaListernes.SESSION_EMF);
		emf.close();
		logger.info(IJpaListernes.SESSION_EMF + " close");

		chachedController.close();
	}

}
