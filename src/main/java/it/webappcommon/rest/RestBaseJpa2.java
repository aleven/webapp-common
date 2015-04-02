package it.webappcommon.rest;

import it.attocchi.jpa2.IJpaListernes;
import it.attocchi.web.config.SoftwareProperties;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestBaseJpa2 {

	// protected final Logger logger =
	// Logger.getLogger(this.getClass().getName());
	// slf4j
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/* GESTIONE ACCESSO DATI PER I TEST */
	protected EntityManagerFactory emf;

	// @Resource
	// protected WebServiceContext context;
	@Context
	protected ServletContext restServletContext;

	// @WebMethod(exclude = true)
	protected void initContextEmf(EntityManagerFactory emf) {

		if (emf != null)
			this.emf = emf;
		else {

		}
	}

	// @WebMethod(exclude = true)
	// protected ServletContext getServletContext() {
	// return (ServletContext)
	// restServletContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
	// }

	protected EntityManagerFactory getContextEmf() {
		if (this.emf == null) {
			/*
			 * Aggiunta del Supporto alla Configurazione
			 */
			// SoftwareConfig.init(e.getSession().getServletContext());
			// if (context != null)
			// SoftwareProperties.init(context);
			// else
			SoftwareProperties.init(restServletContext);

			Map<String, String> dbProps = SoftwareProperties.getJpaDbProps();

			// ServletContext c = restServletContext;
			// DataSource source = (DataSource)
			// (c.lookup("java:comp/env/jdbc/MySource"));

			// com.objectdb.Enhancer.enhance("it.caderplink.entities.*");

			// EntityManagerFactory emf = null;
			if (dbProps != null) {
				this.emf = Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU, dbProps);
			} else {
				this.emf = Persistence.createEntityManagerFactory(IJpaListernes.DEFAULT_PU);
			}
		}

		return this.emf;
	}

	/**
	 * 
	 */
	protected void closeContextEmf() {
		if (this.emf != null) {
			emf.close();
		}
	}
	
//	@Override
//	protected void finalize() throws Throwable {
//		closeContextEmf();
//		super.finalize();
//	}
}
