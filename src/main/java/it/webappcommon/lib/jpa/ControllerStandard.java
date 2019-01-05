/*
    Copyright (c) 2007,2014 Mirco Attocchi
	
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

package it.webappcommon.lib.jpa;

import java.io.Closeable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import it.attocchi.utils.ListUtils;
import it.webappcommon.lib.ExceptionLogger;
import it.webappcommon.lib.jpa.scooped.PersistenceManager;
import it.webappcommon.lib.jpa.scooped.PersistenceManagerUtil;

/**
 * <p>Abstract ControllerStandard class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public abstract class ControllerStandard implements Closeable {

	/**
	 * Questa variabile serve ad indicare che tipo di EM usare (se chiusura du
	 * Listener oppure Normalissimo)
	 */
	protected boolean scoopedEM = false;

	protected EntityManager em = null;
	protected boolean passedEm = false;

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(ControllerStandard.class.getName());

	protected String persistenceUnitName;

	/**
	 * Creates a new instance of Controller
	 */
	public ControllerStandard() {
		initPersistenceUnitName();
	}

	// public abstract ControllerStandard getIstance(boolean scoopedEM);

	/**
	 * <p>Constructor for ControllerStandard.</p>
	 *
	 * @param scoopedEM
	 *            Specifica se tenere aperto EMF e EM usando lo scooped
	 */
	public ControllerStandard(boolean scoopedEM) {
		this();
		this.scoopedEM = scoopedEM;
	}

	/**
	 * <p>Constructor for ControllerStandard.</p>
	 *
	 * @param em a {@link javax.persistence.EntityManager} object.
	 */
	public ControllerStandard(EntityManager em) {
		this();
		this.em = em;
		this.passedEm = true;
	}

	/**
	 * <p>create.</p>
	 *
	 * @param entity a {@link it.webappcommon.lib.jpa.Serializable} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> void create(T entity) throws Exception {
		EntityManagerFactory emf = null;
		// // EntityManager em = null;
		boolean tAlreadyActive = false;

		if (entity == null)
			return;

		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			tAlreadyActive = em.getTransaction().isActive();
			if (!tAlreadyActive) {
				em.getTransaction().begin();
			}

			this.beforeCreate(entity);
			em.persist(entity);
			this.afterCreate(entity);

			// Commit only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().commit();
			}

			// if (lazyCloseEM) {
			// em.refresh(entity);
			// }
		} catch (Exception ex) {
			// Rollback only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().rollback();
			}
			ExceptionLogger.logExceptionWithCause(logger, ex);
			throw ex;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				emf = null;
				em = null;
			}
		}
	}

	/**
	 * <p>edit.</p>
	 *
	 * @param entity a {@link it.webappcommon.lib.jpa.Serializable} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> void edit(T entity) throws Exception {
		EntityManagerFactory emf = null;
		// // EntityManager em = null;
		boolean tAlreadyActive = false;

		if (entity == null)
			return;

		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			tAlreadyActive = em.getTransaction().isActive();
			if (!tAlreadyActive) {
				em.getTransaction().begin();
			}

			this.beforeUpdate(entity);
			em.merge(entity);
			this.afterUpdate(entity);

			// Commit only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().commit();
			}

			// if (lazyCloseEM) {
			// em.refresh(entity);
			// }
		} catch (Exception ex) {
			// Rollback only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().rollback();
			}
			ExceptionLogger.logExceptionWithCause(logger, ex);
			throw ex;
		} finally {

			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				emf = null;
				em = null;
			}
		}
	}

	/**
	 * <p>editSimple.</p>
	 *
	 * @param entity a {@link it.webappcommon.lib.jpa.Serializable} object.
	 * @throws java.lang.Exception if any.
	 */
	public void editSimple(Serializable entity) throws Exception {
		EntityManagerFactory emf = null;
		// // EntityManager em = null;
		boolean tAlreadyActive = false;

		if (entity == null)
			return;

		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			tAlreadyActive = em.getTransaction().isActive();
			if (!tAlreadyActive) {
				em.getTransaction().begin();
			}

			// entity.beforeUpdate();
			em.merge(entity);
			// entity.afterUpdate();

			// Commit only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().commit();
			}

			// if (lazyCloseEM) {
			// em.refresh(entity);
			// }
		} catch (Exception ex) {
			// Rollback only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().rollback();
			}
			ExceptionLogger.logExceptionWithCause(logger, ex);
			throw ex;
		} finally {

			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				emf = null;
				em = null;
			}
		}
	}

	/**
	 * <p>destroy.</p>
	 *
	 * @param entity a {@link it.webappcommon.lib.jpa.Serializable} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> void destroy(T entity) throws Exception {
		EntityManagerFactory emf = null;
		// // EntityManager em = null;
		boolean tAlreadyActive = false;

		if (entity == null)
			return;

		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			tAlreadyActive = em.getTransaction().isActive();
			if (!tAlreadyActive) {
				em.getTransaction().begin();
			}

			// entity = find(entity.getClass(), entity.getId());

			// if (entity != null) {
			this.beforeDelete(entity);
			em.remove(entity);
			this.afterDelete(entity);
			// } else {
			// throw new
			// Exception("Impossibile trovare la Entita' da Cancellare");
			// }

			// Commit only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().commit();
			}
		} catch (Exception ex) {
			// Rollback only if local transaction
			if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive())) {
				em.getTransaction().rollback();
			}
			// logger.error("Errore su destroy di controller : " +
			// ex.getMessage());
			ExceptionLogger.logExceptionWithCause(logger, ex);

			throw ex;
		} finally {

			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				emf = null;
				em = null;
			}
		}
	}

	// public void destroyBeta(Serializable entity, Long id) throws
	// Exception {
	// EntityManagerFactory emf = null;
	// // // EntityManager em = null;
	// boolean tAlreadyActive = false;
	//
	// if (entity == null)
	// return;
	//
	// if (id <= 0)
	// return;
	//
	// try {
	//
	// if (!passedEm) { emf = getEntityManagerFactory();
	// em = emf.createEntityManager(); }
	// }
	//
	// tAlreadyActive = em.getTransaction().isActive();
	// if (!tAlreadyActive) {
	// em.getTransaction().begin();
	// }
	//
	// entity = find(entity.getClass(), id);
	//
	// if (entity != null) {
	// entity.beforeDelete();
	// em.remove(entity);
	// entity.afterDelete();
	// } else {
	// throw new Exception("Impossibile trovare la Entita' da Cancellare");
	// }
	//
	// // Commit only if local transaction
	// if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive()))
	// {
	// em.getTransaction().commit();
	// }
	// } catch (Exception ex) {
	// // Rollback only if local transaction
	// if (!tAlreadyActive && (em != null) && (em.getTransaction().isActive()))
	// {
	// em.getTransaction().rollback();
	// }
	// // logger.error("Errore su destroy di controller : " +
	// // ex.getMessage());
	// ExceptionLogger.logExceptionWithCause(logger, ex);
	//
	// throw ex;
	// } finally {
	//
	// if (em != null) {
	// if (em!=null) { em.close(); }
	// }
	// emf = null;
	// em = null;
	// }
	// }
	// }

	/**
	 * <p>find.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param id a {@link java.lang.Object} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T find(Class<T> classObj, Object id) throws Exception {
		T returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			returnValue = (T) em.find(classObj, id);
		} catch (Exception e) {
			// logger.error("Errore su find di controller : " + e.getMessage() +
			// " " + e.getCause() != null ? e.getCause().getMessage() : "");
			ExceptionLogger.logExceptionWithCause(logger, e);
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
		}
		return returnValue;
	}

	/**
	 * Usato specialmente per aggiornare dopo un salvataggio. Nel caso delle
	 * applicazioni web usiamo em che si chiude dopo la request quindi gli
	 * oggetti non si aggiornano in automatico dopo il salvataggio.
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param id a {@link java.lang.Object} object.
	 * @param <T> an Serializable
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T findAndRefresh(Class<T> classObj, Object id) throws Exception {
		T returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			returnValue = (T) em.find(classObj, id);

			if (returnValue != null) {
				// if (lazyCloseEM) {
				em.refresh(returnValue);
				// }
			}

		} catch (Exception e) {
			// logger.error("Errore su find di controller : " + e.getMessage() +
			// " " + e.getCause() != null ? e.getCause().getMessage() : "");
			ExceptionLogger.logExceptionWithCause(logger, e);
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
		}
		return returnValue;
	}

	/**
	 * <p>findSingle.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T findSingle(Class<T> classObj, String query, Map<String, Object> map) throws Exception {
		T returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Query q = null;
		Iterator i = null;
		Map.Entry entry = null;
		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			q = em.createNamedQuery(query);
			if (map != null) {
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}
			try {
				returnValue = (T) q.getSingleResult();
			} catch (NoResultException ex) {
				returnValue = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			i = null;
			entry = null;
		}
		return returnValue;
	}

	/**
	 * <p>findList.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @param firstItem a int.
	 * @param batchSize a int.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findList(Class<T> classObj, String query, Map<String, Object> map, int firstItem, int batchSize) throws Exception {
		List<T> returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Query q = null;
		Iterator i = null;
		Map.Entry entry = null;
		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			q = em.createNamedQuery(query);
			if (batchSize > 0) {
				q.setFirstResult(firstItem);
				q.setMaxResults(batchSize);
			}
			if (map != null) {
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}
			returnValue = (List<T>) q.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			i = null;
			entry = null;
		}
		return returnValue;
	}

	// public <T extends Serializable> List<T> findAll(
	// Class<T> classObj, int firstItem, int batchSize) throws Exception {
	// List<T> returnValue = null;
	//
	// // EntityManager em = null;
	// Query q = null;
	// Iterator i = null;
	// try {
	// em =
	// PersistenceManagerTest.getInstance().getEntityManagerFactory(getPersistenceUnitName())
	// .createEntityManager();
	// q = em.createQuery("from " + classObj.getName());
	//
	// if (batchSize > 0) {
	// q.setFirstResult(firstItem);
	// q.setMaxResults(batchSize);
	// }
	// returnValue = (List<T>) q.getResultList();
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// if (em!=null) { em.close(); }
	// em = null;
	// q = null;
	// i = null;
	// }
	// return returnValue;
	// }

	/**
	 * <p>getItemCount.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public int getItemCount(Class classObj) throws Exception {
		int returnValue = 0;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Query q = null;
		StringBuffer hsqlQuery = null;
		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			hsqlQuery = new StringBuffer();
			hsqlQuery.append("select count(*) from ");
			hsqlQuery.append(classObj.getCanonicalName());
			hsqlQuery.append(" as o");
			q = em.createQuery(hsqlQuery.toString());
			returnValue = ((Long) q.getSingleResult()).intValue();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			hsqlQuery = null;
		}
		return returnValue;
	}

	/**
	 * <p>getItemCount.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public int getItemCount(Class classObj, String query, Map<String, Object> map) throws Exception {
		int returnValue = 0;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Query q = null;
		Iterator i = null;
		Map.Entry entry = null;
		try {

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			q = em.createNamedQuery(query);

			if (map != null) {
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}
			returnValue = ((Long) q.getSingleResult()).intValue();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			i = null;
			entry = null;
		}
		return returnValue;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	// private String getPersistenceUnitName() = "";

	// public ControllerStandard() {
	// this(MultiplePersistenceManagerTest.PERSISTENCE_UNIT);
	// }
	//
	// protected ControllerStandard(String getPersistenceUnitName()) {
	// this.getPersistenceUnitName() = getPersistenceUnitName();
	// }

	// public abstract String getPersistenceUnitName();
	/**
	 * <p>initPersistenceUnitName.</p>
	 */
	public abstract void initPersistenceUnitName();

	/**
	 * <p>Getter for the field <code>persistenceUnitName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	/**
	 * <p>Setter for the field <code>persistenceUnitName</code>.</p>
	 *
	 * @param persistenceUnitName a {@link java.lang.String} object.
	 */
	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	/**
	 * <p>setPersistenceUnitNameL.</p>
	 *
	 * @param persistenceUnitName a {@link java.lang.String} object.
	 * @return a {@link it.webappcommon.lib.jpa.ControllerStandard} object.
	 */
	public ControllerStandard setPersistenceUnitNameL(String persistenceUnitName) { this.setPersistenceUnitName(persistenceUnitName); return this; }

	public class StringValuesCount {

		private String itemValue;
		private Long itemCount;

		/**
		 * @return the value
		 */
		public String getItemValue() {
			return itemValue;
		}

		/**
		 * @param itemValue
		 *            the value to set
		 */
		public void setItemValue(String itemValue) {
			this.itemValue = itemValue;
		}

		/**
		 * @return the count
		 */
		public Long getItemCount() {
			return itemCount;
		}

		/**
		 * @param itemCount
		 *            the count to set
		 */
		public void setItemCount(Long itemCount) {
			this.itemCount = itemCount;
		}
	}

	/**
	 * Restituisce loggetto relativo specificando classe ed id
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param id a {@link java.lang.Object} object.
	 * @param <T> an Serializable
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T find2(Class<T> classObj, Object id) throws Exception {
		T returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Calcolo l'oggetto */
			returnValue = (T) em.find(classObj, id);
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
		}
		return returnValue;
	}

	/**
	 * Metodo che restituisce l'oggetto cercato specificando la classe
	 * dell'oggetto, la query di ricerca e la mappa dei parametri
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @param <T> an Serializable
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T findSingle2(Class<T> classObj, String query, Map<String, Object> map) throws Exception {
		T returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Map.Entry entry = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Creo la query */
			q = em.createNamedQuery(query);

			/* Verifico che la mappa dei parametri sia valida */
			if (map != null) {

				/* Per ogni oggetto della mappa setto il parametro */
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}
			try {
				/* Lancio la query */
				returnValue = (T) q.getSingleResult();
			} catch (NoResultException ex) {
				/* Se non ci son risultati ritorno null */
				returnValue = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			entry = null;
			q = null;
			i = null;
		}
		return returnValue;
	}

	/**
	 *
	 * Metodo che restituisce una collezione di oggetti specificati come
	 * parametro, tramite la query da lanciare, la mappa dei parametri,
	 * l'elemento di inizio e il numero di elementi desiderati (mettendo a 0
	 * questo parametro li restituisce tutti)
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @param firstItem a int.
	 * @param batchSize a int.
	 * @param <T> an Serializable
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findList2(Class<T> classObj, String query, Map<String, Object> map, int firstItem, int batchSize) throws Exception {
		List<T> returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Map.Entry entry = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Genero la query */
			q = em.createNamedQuery(query);

			/*
			 * Se il numero di elementi è diverso da 0 specifico quanti e da
			 * dove cominciare
			 */
			if (batchSize > 0) {
				q.setFirstResult(firstItem);
				q.setMaxResults(batchSize);
			}

			/* Verifico la validità della mappa */
			if (map != null) {
				/* Per ogni elemento della mappa setto il parametro */
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}

			/* Calcolo la collezione di elementi desiderati */
			returnValue = (List<T>) q.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			entry = null;
			q = null;
			i = null;
		}
		return returnValue;
	}

	/**
	 *
	 * Metodo che restituisce una collezione di oggetti specificati come
	 * parametro, tramite la query da lanciare, la mappa dei parametri,
	 * l'elemento di inizio e il numero di elementi desiderati (mettendo a 0
	 * questo parametro li restituisce tutti)
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @param firstItem a int.
	 * @param batchSize a int.
	 * @param <T> an Serializable
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findListCustomQuery(Class<T> classObj, String query, Map<String, Object> map, int firstItem, int batchSize) throws Exception {
		List<T> returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Map.Entry entry = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Genero la query */
			q = em.createQuery(query);

			/*
			 * Se il numero di elementi è diverso da 0 specifico quanti e da
			 * dove cominciare
			 */
			if (batchSize > 0) {
				q.setFirstResult(firstItem);
				q.setMaxResults(batchSize);
			}

			/* Verifico la validità della mappa */
			if (map != null) {
				/* Per ogni elemento della mappa setto il parametro */
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}

			/* Calcolo la collezione di elementi desiderati */
			returnValue = (List<T>) q.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			entry = null;
			q = null;
			i = null;
		}
		return returnValue;
	}

	/**
	 *
	 * Metodo che restituisce tutta la collezione di oggetti specificati come
	 * parametro, tramite l'elemento di inizio e il numero di elementi
	 * desiderati (mettendo a 0 questo parametro li restituisce tutti)
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param firstItem a int.
	 * @param batchSize a int.
	 * @param <T> an Serializable
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findAll(Class<T> classObj, int firstItem, int batchSize) throws Exception {
		List<T> returnValue = new ArrayList<T>(); // Non piu' null cosi' posso
													// semplificare il codice
													// del chiamante

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzia l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Crea la query */
			q = em.createQuery("from " + classObj.getName());

			/*
			 * Se il numero di elementi è diverso da 0 specifico quanti e da
			 * dove cominciare
			 */
			if (batchSize > 0) {
				q.setFirstResult(firstItem);
				q.setMaxResults(batchSize);
			}

			/* Calcolo la collezione di elementi desiderati */
			returnValue = q.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			i = null;
		}
		return returnValue;
	}

	/**
	 * <p>findAll.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findAll(Class<T> classObj) throws Exception {
		return findAll(classObj, 0, 0);
	}

	/**
	 *
	 * Metodo che restituisce il numero di elementi dato un certo tipo
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param <T> an Serializable
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> int getItemCount3(Class<T> classObj) throws Exception {
		int returnValue = 0;

		StringBuffer hsqlQuery = null;
		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Creo la query */
			hsqlQuery = new StringBuffer();
			hsqlQuery.append("select count(*) from "); // hsqlQuery.append("select count(o) from ");
			// ho cambiato
			hsqlQuery.append(classObj.getCanonicalName());
			hsqlQuery.append(" as o");
			q = em.createQuery(hsqlQuery.toString());

			/* Istanzio il valore di retutn */
			returnValue = ((Long) q.getSingleResult()).intValue();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			hsqlQuery = null;
			q = null;
		}
		return returnValue;
	}

	/**
	 * <p>getDisinctStringValues.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param property a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<String> getDisinctStringValues(Class<T> classObj, String property) throws Exception {
		List<String> returnValue = null;

		StringBuffer hsqlQuery = null;
		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Creo la query */
			hsqlQuery = new StringBuffer();
			hsqlQuery.append("select distinct(o." + property + ") from ");
			hsqlQuery.append(classObj.getCanonicalName());
			hsqlQuery.append(" as o");

			q = em.createQuery(hsqlQuery.toString());

			/* Istanzio il valore di retutn */
			returnValue = q.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			hsqlQuery = null;
			q = null;
		}
		return returnValue;
	}

	/**
	 * <p>getDisinctStringValuesCount.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param property a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<StringValuesCount> getDisinctStringValuesCount(Class<T> classObj, String property) throws Exception {
		List<StringValuesCount> returnValue = null;

		StringBuffer hsqlQuery = null;
		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Creo la query */
			hsqlQuery = new StringBuffer();
			hsqlQuery.append("select o." + property + " as itemValue, count(o." + property + ") as itemCount from ");
			hsqlQuery.append(classObj.getCanonicalName());
			hsqlQuery.append(" as o");
			hsqlQuery.append(" group by o." + property + "");
			hsqlQuery.append(" order by 2 desc");
			// hsqlQuery.append("select u.publisher, count(u.publisher) from Softwares u group by u.publisher");

			q = em.createQuery(hsqlQuery.toString());

			/* Istanzio il valore di retutn */
			// List res = q.getResultList();
			returnValue = new ArrayList<StringValuesCount>();

			List<Object[]> result1 = q.getResultList();
			for (Object[] resultElement : result1) {
				String value = (String) resultElement[0];
				Long count = (Long) resultElement[1];

				StringValuesCount a = new StringValuesCount();
				a.setItemValue(value);
				a.setItemCount(count);
				returnValue.add(a);
			}

			// for (Iterator it = res.iterator(); it.hasNext();) {
			// Object obj = it.next();
			// // StringValuesCount object = (StringValuesCount)it.next();
			// // returnValue.add(object);
			// }

		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			hsqlQuery = null;
			q = null;
		}
		return returnValue;
	}

	/**
	 *
	 * Metodo che restituisce il numero di elementi dato un certo tipo, la query
	 * e la mappa dei parametri
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public int getItemCount2(Class<? extends Serializable> classObj, String query, Map<String, Object> map) throws Exception {
		int returnValue = 0;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Map.Entry entry = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Creo la query */
			q = em.createNamedQuery(query);

			/* Verifico la validità della mappa dei parametri */
			if (map != null) {

				/* Per ogni elemento della mappo setto un parametro */
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}

			/* Calcolo il valore di ritorno */
			returnValue = ((Long) q.getSingleResult()).intValue();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			entry = null;
			q = null;
			i = null;
		}
		return returnValue;
	}

	/**
	 *
	 * Metodo che restituisce il numero di elementi dato un certo tipo, la query
	 * e la mappa dei parametri
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param map a {@link java.util.Map} object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public int getItemCountCustomQuery(Class<? extends Serializable> classObj, String query, Map<String, Object> map) throws Exception {
		int returnValue = 0;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Map.Entry entry = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			/* Creo la query */
			q = em.createQuery(query);

			/* Verifico la validità della mappa dei parametri */
			if (map != null) {

				/* Per ogni elemento della mappo setto un parametro */
				for (i = map.entrySet().iterator(); i.hasNext();) {
					entry = (Map.Entry) i.next();
					q.setParameter((String) entry.getKey(), entry.getValue());
				}
			}

			/* Calcolo il valore di ritorno */
			returnValue = ((Long) q.getSingleResult()).intValue();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			i = null;
			entry = null;
		}
		return returnValue;
	}

	/**
	 * Funziona SOLO CON IMPLEMENTAZIONE HIBERNATE
	 * @param classObj a {@link java.lang.Class} object.
	 * @param aFilter a {@link java.lang.String} object.
	 * @param <T> an Serializable
	 * @return a {@link java.util.ArrayList} object.
	 * @throws Exception java.lang.Exception if any.
	 */
	public <T extends Serializable> ArrayList<T> getFilteredList(Class<T> classObj, String aFilter) throws Exception {
		ArrayList<T> returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Session session = null;
		Criteria cri = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			session = (Session) em.getDelegate();

			org.hibernate.Query q = session.createQuery("from " + classObj.getName());
			q.setFirstResult(0);
			q.setMaxResults(100);
			List<T> resAll = q.list();

			org.hibernate.Query filterQuery = session.createFilter(resAll, aFilter);

			/* Effettuo la query */
			returnValue = (ArrayList) filterQuery.list();
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			session = null;
			cri = null;
		}
		return returnValue;
	}

	/**
	 * <p>findFilter.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param filtro a E object.
	 * @param <T> a T object.
	 * @param <E> a E object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable, E extends AbstractFiltroJpa> List<T> findFilter(Class<T> classObj, E filtro) throws Exception {
		List<T> returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzia l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			StringBuilder hqlQuery = new StringBuilder();

			hqlQuery.append("select t from " + classObj.getName() + " t ");

			hqlQuery.append(" WHERE ");
			if (filtro != null && StringUtils.isNotEmpty(filtro.getSQLWhere())) {
				hqlQuery.append(filtro.getSQLWhere());
			}

			if (filtro != null) {
				hqlQuery.append(filtro.getSQLSort());
			}

			/* Crea la query */
			logger.debug(hqlQuery.toString());
			q = em.createQuery(hqlQuery.toString());

			/*
			 * Se il numero di elementi e' diverso da 0 specifico quanti e da
			 * dove cominciare
			 */
			if (filtro != null && filtro.getRighePerPagina() > 0) {
				q.setMaxResults(filtro.getRighePerPagina());
				if (filtro.getPagina() > 0) {
					q.setFirstResult((filtro.getPagina() - 1) * filtro.getRighePerPagina());

				}
			}

			if (filtro != null && filtro.getListaParametri() != null) {
				for (Entry<String, Object> parametro : filtro.getListaParametri().entrySet()) {
					q.setParameter(parametro.getKey(), parametro.getValue());
				}
			}

			/* Calcolo la collezione di elementi desiderati */
			returnValue = q.getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			i = null;
		}
		return returnValue;
	}

	/**
	 * <p>findFilterCount.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param filtro a E object.
	 * @param <T> a T object.
	 * @param <E> a E object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable, E extends AbstractFiltroJpa> int findFilterCount(Class<T> classObj, E filtro) throws Exception {
		int returnValue = 0;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Iterator i = null;
		Query q = null;
		try {
			/* Istanzia l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			StringBuilder hqlQuery = new StringBuilder();

			hqlQuery.append("select count(t) from " + classObj.getName() + " t ");

			hqlQuery.append(" WHERE ");
			if (filtro != null && StringUtils.isNotEmpty(filtro.getSQLWhere())) {
				hqlQuery.append(filtro.getSQLWhere());
			}

			if (filtro != null) {
				hqlQuery.append(filtro.getSQLSort());
			}

			/* Crea la query */
			logger.debug(hqlQuery.toString());
			q = em.createQuery(hqlQuery.toString());

			/*
			 * Se il numero di elementi è diverso da 0 specifico quanti e da
			 * dove cominciare
			 */
			// if (filtro != null && filtro.getRighePerPagina() > 0) {
			// q.setFirstResult(filtro.getPagina() *
			// filtro.getRighePerPagina());
			// q.setMaxResults(filtro.getRighePerPagina());
			// }

			if (filtro != null && filtro.getListaParametri() != null) {
				for (Entry<String, Object> parametro : filtro.getListaParametri().entrySet()) {
					q.setParameter(parametro.getKey(), parametro.getValue());
				}
			}

			/* Calcolo la collezione di elementi desiderati */
			returnValue = Integer.parseInt(q.getSingleResult().toString());

		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			q = null;
			i = null;
		}
		return returnValue;
	}

	private EntityManagerFactory nonScopedEMF = null;

	/**
	 * <p>getEntityManagerFactory.</p>
	 *
	 * @return a {@link javax.persistence.EntityManagerFactory} object.
	 */
	protected EntityManagerFactory getEntityManagerFactory() {
		EntityManagerFactory res = null;

		/*
		 * Mirco: per il progetto katia-server modifico in modo che eventuali
		 * richieste non-scooped creino istanze normali, precedentemente usavo
		 * il ManagerTEST (forse per qualche progetto con multi PU)
		 */
		if (scoopedEM) {
			// res =
			// Persistence.createEntityManagerFactory(getPersistenceUnitName());
			// res =
			// MultiplePersistenceManagerTest.getInstance().getEntityManagerFactory(getPersistenceUnitName());
			logger.debug("retriving scoped emf "+PersistenceManager.getPersistenceUnit()+"...");
			res = PersistenceManager.getInstance().getEntityManagerFactory();
			logger.debug("get scoped emf multiple pu");
		} else {
			if (nonScopedEMF == null) {
				logger.debug("creating un-scoped emf "+getPersistenceUnitName()+"...");
				nonScopedEMF = Persistence.createEntityManagerFactory(getPersistenceUnitName());
				logger.debug("create un-scoped emf");
			}
			res = nonScopedEMF;
			// res =
			// MultiplePersistenceManagerTest.getInstance().getEntityManagerFactory(getPersistenceUnitName());
			// logger.warn("requested un-scoped emf (WARN: actually is SCOOPED)");
		}

		return res;
	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		if (scoopedEM) {
			// MultiplePersistenceManagerTest.getInstance().closeEntityManagerFactory(persistenceUnitName);
		} else {
			// MultiplePersistenceManagerTest.getInstance().closeEntityManagerFactory(persistenceUnitName);
			// Mirco: gestione dell'istanza non scooped che ho tentuto in
			// nonScopedEMF
			if (!passedEm) {
				if (em != null) {
					em.close();
					em = null;
				}
				if (nonScopedEMF != null) {
					nonScopedEMF.close();
					nonScopedEMF = null;
				}
				logger.debug("closed em and emf not scoped");
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
		logger.debug(this.toString() + " finalized");
	}

	/**
	 * <p>closeResource.</p>
	 */
	protected void closeResource() {

	}

	/**
	 * <p>isTableEmpty.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a boolean.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> boolean isTableEmpty(Class<T> classObj) throws Exception {
		List<T> res = findAll(classObj, 0, 1);
		return ListUtils.isEmpty(res);
	}

	/**
	 * <p>listByExample.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param anExample a T object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> listByExample(Class<T> classObj, T anExample) throws Exception {
		List<T> returnValue = null;

		EntityManagerFactory emf = null;
		// EntityManager em = null;
		Session session = null;
		Criteria cri = null;
		try {
			/* Istanzio l'entity manager */

			if (!passedEm) {
				emf = getEntityManagerFactory();
				em = emf.createEntityManager();
			}

			session = (Session) em.getDelegate();

			List res = session.createCriteria(classObj).add(Example.create(anExample).excludeZeroes().enableLike()).list();

			// org.hibernate.Query filterQuery = session.createFilter(resAll,
			// aFilter);

			/* Effettuo la query */
			returnValue = (List<T>) res;
		} catch (Exception e) {
			throw e;
		} finally {
			if (!passedEm) {
				PersistenceManagerUtil.close(em);
				em = null;
			}
			session = null;
			cri = null;
		}
		return returnValue;
	}

	/*
	 * NUOVI
	 */

	/**
	 * <p>findByExample.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param anExample a T object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findByExample(Class<T> clazz, T anExample) throws Exception {
		List<T> res = new ArrayList<T>();

		EntityManager em = getEntityManagerFactory().createEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			session = (Session) em.getDelegate();

			res = session.createCriteria(clazz).add(Example.create(anExample).excludeZeroes().enableLike()).list();

			// res = em.createQuery("SELECT e FROM " + clazz.getCanonicalName()
			// + " e", clazz).getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

		return res;
	}

	// public <T extends Serializable> List<T> findBy(Class<T> clazz, String
	// query) throws Exception {
	// List<T> res = new ArrayList<T>();
	//
	// EntityManager em = getEntityManagerFactory().createEntityManager();
	// Session session = null;
	// Criteria cri = null;
	//
	// try {
	//
	// session = (Session) em.getDelegate();
	//
	// // res =
	// //
	// session.createCriteria(clazz).add(Example.create(anExample).excludeZeroes().enableLike()).list();
	//
	// res = em.createQuery(query, clazz).getResultList();
	//
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// // Close the database connection:
	// if (em.getTransaction().isActive())
	// em.getTransaction().rollback();
	// em.close();
	// }
	//
	// return res;
	// }

	/**
	 * costruisce una mappa di parametri
	 *
	 * @param names
	 *            of parameters param1,param2,...
	 * @param objects
	 *            values of params
	 * @return mappa di parametri
	 * @throws java.lang.Exception if any.
	 */
	public Map<String, Object> buildParams(String names, Object... objects) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();

		String[] strings = names.split(",");

		if (strings.length != objects.length) {
			throw new Exception("the number of parameters differs from the number of values");
		}

		int i = 0;
		for (String paramName : strings) {
			res.put(paramName.trim(), objects[i]);
			i++;
		}
		return res;
	}

	/**
	 * Use for close this Controller
	 *
	 * @param istance istanza
	 */
	public static void close(ControllerStandard istance) {
		if (istance != null) {
			istance.close();
		}
	}

	protected <T> void beforeCreate(T entity) throws Exception {
		if (entity instanceof JpaEntityWithListeners) {
			((JpaEntityWithListeners)entity).beforeCreate();
		}
	}

	protected <T> void afterCreate(T entity) throws Exception {
		if (entity instanceof JpaEntityWithListeners) {
			((JpaEntityWithListeners)entity).afterCreate();
		}
	}

	protected <T> void beforeUpdate(T entity) throws Exception {
		if (entity instanceof JpaEntityWithListeners) {
			((JpaEntityWithListeners)entity).beforeUpdate();
		}
	}

	protected <T> void afterUpdate(T entity) throws Exception {
		if (entity instanceof JpaEntityWithListeners) {
			((JpaEntityWithListeners)entity).afterUpdate();
		}
	}

	protected <T> void beforeDelete(T entity) throws Exception {
		if (entity instanceof JpaEntityWithListeners) {
			((JpaEntityWithListeners)entity).beforeDelete();
		}
	}

	protected <T> void afterDelete(T entity) throws Exception {
		if (entity instanceof JpaEntityWithListeners) {
			((JpaEntityWithListeners)entity).afterDelete();
		}
	}
}
