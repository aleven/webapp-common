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
		
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent e) {

		/*
		 * Aggiunta del Supporto alla Configurazione
		 */
		SoftwareProperties.init(e.getSession().getServletContext());
		Map<String, String> dbProps = SoftwareProperties.getJpaDbProps();

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
		if (emf != null)
			emf.close();
		logger.info(IJpaListernes.SESSION_EMF + " close");
		//
		// chachedController.close();
	}

}
