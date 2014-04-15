/*
    Copyright (c) 2012,2013 Mirco Attocchi
	
    This file is part of WebAppCommon.

    WebAppCommon is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WebAppCommon is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with WebAppCommon.  If not, see <http://www.gnu.org/licenses/>.
*/

package it.attocchi.jpa2;

import it.attocchi.web.config.SoftwareProperties;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class JPAListener
 * 
 * @author Mirco Attocchi
 */
public class JPAContextListener implements ServletContextListener {

	protected static final Logger logger = Logger.getLogger(JPAContextListener.class.getName());

	// private JpaController chachedController;

	/**
	 * Default constructor.
	 */
	public JPAContextListener() {
		
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent e) {

		/*
		 * Aggiunta del Supporto alla Configurazione
		 */
		// SoftwareConfig.init(e.getSession().getServletContext());
		SoftwareProperties.init(e.getServletContext());
		Map<String, String> dbProps = SoftwareProperties.getJpaDbProps();

		// com.objectdb.Enhancer.enhance("it.caderplink.entities.*");

		EntityManagerFactory emf = null;
		if (dbProps != null) {
			emf = Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU, dbProps);
		} else {
			emf = Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU);
		}

		// com.objectdb.Enhancer.enhance("it.caderplink.entities.*");
		// EntityManagerFactory emf =
		// Persistence.createEntityManagerFactory("$objectdb/db/caderplink.odb");
		// e.getServletContext().setAttribute("emf", emf);

		// com.objectdb.Enhancer.enhance("it.caderplink.entities.*");

		// EntityManagerFactory emf =
		// Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU);
		e.getServletContext().setAttribute(IJpaListernes.SESSION_EMF, emf);
		logger.info(IJpaListernes.SESSION_EMF + " start");

		// chachedController = new JpaController(IJpaListernes.DEFAULT_PU);
		// chachedController.test();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent e) {
		// EntityManagerFactory emf = (EntityManagerFactory)
		// e.getServletContext().getAttribute("emf");
		// emf.close();

		EntityManagerFactory emf = (EntityManagerFactory) e.getServletContext().getAttribute(IJpaListernes.SESSION_EMF);
		if (emf != null)
			emf.close();
		logger.info(IJpaListernes.SESSION_EMF + " close");

		// chachedController.closeEmf();
	}

}