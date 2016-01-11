package it.webappcommon.rest;

import it.attocchi.jpa2.IJpaListernes;
import it.attocchi.web.config.SoftwareProperties;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mirco
 *
 */
public class RestBaseJpa2 {

	// protected final Logger logger =
	// Logger.getLogger(this.getClass().getName());
	// slf4j
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/* GESTIONE ACCESSO DATI PER I TEST */
	/*
	 * da specifiche JPA l'istanza dovrebbe essere unica per tutta l'app, quindi
	 * il contesto dovrebbe averne una
	 */
	protected EntityManagerFactory emf;
	private boolean emfFromContext = false;

	// @Resource
	// protected WebServiceContext context;
	@Context
	protected ServletContext restServletContext;

	@Context
	protected UriInfo uriInfo;
	
	// @WebMethod(exclude = true)
	// protected void initContextEmf(EntityManagerFactory emf) {
	// if (emf != null)
	// this.emf = emf;
	// else {
	//
	// }
	// }

	// @WebMethod(exclude = true)
	// protected ServletContext getServletContext() {
	// return (ServletContext)
	// restServletContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
	// }

	/**
	 * Verifica innanzitutto se esiste un EMF di contesto (inizializzato con il
	 * listener. Nel caso non esiste ne crea uno nuovo (sconsigliato per
	 * lentezza)
	 * 
	 * @return
	 */
	protected EntityManagerFactory getContextEmf() {
		if (this.emf == null) {
			/*
			 * verifico se nel context o nella sessione già c'e' un emf
			 * inizializzato
			 */
			if (restServletContext != null && restServletContext.getAttribute(IJpaListernes.APPLICATION_EMF) != null) {
				emf = (EntityManagerFactory) restServletContext.getAttribute(IJpaListernes.APPLICATION_EMF);
				emfFromContext = true;
			} else {
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

				// Mirco: nel caso in cui ho specificato un nome di PU diverso
				// dal default leggo dal web.xml
				String persistenceUnitName = IJpaListernes.DEFAULT_PU;
				if (restServletContext != null) {
					persistenceUnitName = restServletContext.getInitParameter(IJpaListernes.WEB_XML_INITPARAMETER_NAME);
					if (persistenceUnitName == null || persistenceUnitName.isEmpty()) {
						persistenceUnitName = IJpaListernes.DEFAULT_PU;
					}
				}

				// EntityManagerFactory emf = null;
				if (dbProps != null) {
					this.emf = Persistence.createEntityManagerFactory(persistenceUnitName, dbProps);
				} else {
					this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
				}
			}
		}

		return this.emf;
	}

	/**
	 * Si occupa di chiudere EMF nel caso in precedenza non sia stato recuperato
	 * dal context
	 */
	protected void closeContextEmf() {
		if (!emfFromContext && this.emf != null) {
			emf.close();
			emf = null;
		}
	}

	// @Override
	// protected void finalize() throws Throwable {
	// closeContextEmf();
	// super.finalize();
	// }
}
