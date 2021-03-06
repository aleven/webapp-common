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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import it.attocchi.jpa2.entities.IEntityWithIdLong;

/**
 * <p>JpaController class.</p>
 *
 * @author Mirco Attocchi
 * @version $Id: $Id
 */
public class JpaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Constant <code>DEFAULT_PU="DEFAULT_PU"</code> */
	public static final String DEFAULT_PU = "DEFAULT_PU";

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(JpaController.class.getName());

	private EntityManagerFactory emf = null;
	private boolean passedEmf = false;

	private Map<String, String> dbConf;

	// private static EntityManagerFactory sharedEmf;

	protected String persistenceUnit;

	// public static int Numero = 0;
	// private int numero = 0;

	/**
	 * use DEFAULT_PU
	 */
	public JpaController() {
		super();
		// assegnaNumero();

		this.persistenceUnit = DEFAULT_PU;
		passedEmf = false;
		// System.gc();
	}

	/**
	 * <p>Constructor for JpaController.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 */
	public JpaController(EntityManagerFactory emf) {
		this();

		if (emf != null) {
			this.emf = emf;
			passedEmf = true;
			logger.debug("created jpacontroller with scoped emf");
		} else {

		}
	}

	/**
	 * <p>Constructor for JpaController.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 */
	public JpaController(String persistenceUnit) {
		this();
		if (StringUtils.isNotBlank(persistenceUnit)) {
			this.persistenceUnit = persistenceUnit;
		}
		// passedEmf = false;

	}

	/**
	 * <p>Constructor for JpaController.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param dbConf a {@link java.util.Map} object.
	 */
	public JpaController(String persistenceUnit, Map<String, String> dbConf) {
		this(persistenceUnit);
		this.dbConf = dbConf;
	}

	// private void assegnaNumero() {
	// JpaController.Numero++;
	// this.numero = JpaController.Numero;
	// logger.debug(String.format("Creazione Controller %s", numero));
	// }

	/**
	 * <p>insert.</p>
	 *
	 * @param o a T object.
	 * @param <T> a T object.
	 * @throws it.attocchi.jpa2.JpaControllerException if any.
	 */
	public <T extends Serializable> void insert(T o) throws JpaControllerException {
		EntityManager em = getEntityManager();
		try {
			if (!controllerTransactionOpen)
				em.getTransaction().begin();
			em.persist(o);
			if (!controllerTransactionOpen)
				em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaControllerException("error insert", e);
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}
	}

	/**
	 * <p>update.</p>
	 *
	 * @param o a T object.
	 * @param <T> a T object.
	 * @throws it.attocchi.jpa2.JpaControllerException if any.
	 */
	public <T extends Serializable> void update(T o) throws JpaControllerException {
		EntityManager em = getEntityManager();
		try {
			if (!controllerTransactionOpen)
				em.getTransaction().begin();
			em.merge(o);
			if (!controllerTransactionOpen)
				em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaControllerException("error insert", e);
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}
	}

	/**
	 * <p>delete.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param o a T object.
	 * @param id a {@link java.lang.Object} object.
	 * @param <T> a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> void delete(Class<T> clazz, T o, Object id) throws Exception {
		testClazz(clazz);
		EntityManager em = getEntityManager();
		try {
			if (!controllerTransactionOpen)
				em.getTransaction().begin();
			T attached = em.find(clazz, id);
			if (attached != null) {
				em.remove(attached);
				// em.remove(o);
			}
			if (!controllerTransactionOpen)
				em.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

	}

	/**
	 * <p>findAll.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.ClassNotFoundException if any.
	 */
	public <T extends Serializable> List<T> findAll(Class<T> clazz) throws ClassNotFoundException {
		List<T> res = new ArrayList<T>();

		res = findAll(clazz, null);

		return res;
	}

	/**
	 * <p>findAll.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param orderBy a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.ClassNotFoundException if any.
	 */
	public <T extends Serializable> List<T> findAll(Class<T> clazz, String orderBy) throws ClassNotFoundException {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		String query = "SELECT o FROM " + clazz.getCanonicalName() + " o";
		if (StringUtils.isNotEmpty(orderBy)) {
			query = query + " ORDER BY " + orderBy;
		}

		res = findBy(clazz, query);

		return res;
	}

	/**
	 * <p>find.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a long.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T find(Class<T> clazz, long id) throws Exception {
		T res = null;

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			res = em.find(clazz, id);

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>find.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a int.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T find(Class<T> clazz, int id) throws Exception {
		T res = null;

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			res = em.find(clazz, id);

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>find.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T find(Class<T> clazz, String id) throws Exception {
		T res = null;

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			res = em.find(clazz, id);

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>findFirst.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param params a {@link java.lang.Object} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T findFirst(Class<T> clazz, String query, Object... params) throws Exception {
		T res = null;
		List<T> list = null;

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			TypedQuery<T> q = em.createQuery(query, clazz);

			if (params != null) {
				int i = 1;
				for (Object o : params) {
					q.setParameter(i, o);
					i++;
				}
			}

			list = q.getResultList();

			if (list != null && list.size() > 0) {
				res = list.get(0);
			}

		} catch (Exception e) {

			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>findFirst.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T findFirst(Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		List<T> list = new ArrayList<T>();
		T res = null;

		// try {

		list = findBy(clazz, filter);

		if (list != null && list.size() > 0) {
			res = list.get(0);
		}

		// } catch (Exception ex) {
		// logger.error("findFirst", ex);
		// throw ex;
		// } finally {
		//
		// }

		return res;
	}

	/**
	 * <p>findFirst.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T findFirst(Class<T> clazz) throws Exception {
		T res = null;
		List<T> list = null;

		testClazz(clazz);

		String query = "SELECT o FROM " + clazz.getCanonicalName() + " o";
		res = findFirst(clazz, query);

		return res;
	}

	/**
	 * User for speed test
	 *
	 * @return a {@link javax.persistence.EntityManagerFactory} object.
	 */
	@Deprecated
	public EntityManagerFactory getEntityManagetFactory() {
		return getEmf();
	}

	private EntityManagerFactory getEmf() {

		if (!passedEmf) {

			/*
			 * Questo controllo sembra causare il problema con AJAX che
			 * "EMF e' gia' registrato"
			 */
			// if (emf == null || !emf.isOpen()) {

			if (emf == null) {
				if (dbConf == null) {
					emf = Persistence.createEntityManagerFactory(persistenceUnit);
				} else {
					emf = Persistence.createEntityManagerFactory(persistenceUnit, dbConf);
				}
			} else if (!emf.isOpen()) {
				logger.warn("exist emf istance but is close");
			}
		}

		return emf;
	}

	private void closeEm() {
		/* Gestione delle Transazioni */
		if (!controllerTransactionOpen) {
			if (em != null) {
				em.close();
				em = null;
				if (closedFromFinalize)
					logger.warn("closed jpacontroller em from finalize");
				else
					logger.debug("closed jpacontroller em");
			}
		} else {
			logger.debug("jpacontroller em cannot be closed: controller active transaction");
		}
	}

	/**
	 * Close EM if is in use for a global transaction, and close EMF if is not
	 * passed from outside
	 */
	public void closeEmAndEmf() {
		closeEm();
		if (!passedEmf) {
			if (emf != null) {
				emf.close();
				emf = null;
				if (closedFromFinalize)
					logger.warn("closed jpacontroller emf from finalize");
				else
					logger.debug("closed jpacontroller emf");
			}
		} else {
			logger.debug("jpacontroller emf cannot be closed: emf come from outside controller");
		}
	}

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

		testClazz(clazz);

		EntityManager em = getEntityManager();
		Session session = null;

		try {
			session = (Session) em.getDelegate();
			res = session.createCriteria(clazz).add(Example.create(anExample).excludeZeroes().enableLike()).list();
		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>findBy.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.ClassNotFoundException if any.
	 */
	public <T extends Serializable> List<T> findBy(Class<T> clazz, String query) throws ClassNotFoundException {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			res = em.createQuery(query, clazz).getResultList();

//		} catch (Exception e) {
//			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * a query with Ordinal Parameters (?index)
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param query a {@link java.lang.String} object.
	 * @param params a {@link java.lang.Object} object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findBy(Class<T> clazz, String query, Object... params) throws Exception {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			TypedQuery<T> q = em.createQuery(query, clazz);

			if (params != null) {
				int i = 1;
				for (Object o : params) {
					q.setParameter(i, o);
					i++;
				}
			}

			res = q.getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>findBy.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.ClassNotFoundException if any.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findBy(Class<T> clazz, JPAEntityFilter<T> filter) throws ClassNotFoundException, Exception {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			if (filter != null) {
				CriteriaQuery<T> cq = filter.getCriteria(clazz, getEmf());
				TypedQuery<T> q = em.createQuery(cq);

				q.setFirstResult(filter.getLimit() * filter.getPageNumber());
				q.setMaxResults(filter.getLimit());

				res = q.getResultList();
			} else {
				res = findAll(clazz);
			}

//		} catch (JpaEntityFilterException e) {
//			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>count.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.Long} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> Long count(Class<T> clazz) throws Exception {
		return countBy(clazz, null);
	}

	/**
	 * <p>countBy.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.Long} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> Long countBy(Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		Long res = 0L;

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			if (filter != null) {

				// Source:
				// http://stackoverflow.com/questions/5349264/total-row-count-for-pagination-using-jpa-criteria-api

				CriteriaQuery<T> cq = filter.getCriteria(clazz, getEmf());

				// TypedQuery<T> q = em.createQuery(cq);
				//
				// q.setFirstResult(filter.getLimit() * filter.getPageNumber());
				// q.setMaxResults(filter.getLimit());
				//
				// res = Long.valueOf(q.getResultList().size());

				// CriteriaBuilder qb = em.getCriteriaBuilder();
				// CriteriaQuery<Long> countQuery = qb.createQuery(Long.class);
				// countQuery.select(qb.count(cq.from(clazz)));
				// // cq.where(/*your stuff*/);
				// return em.createQuery(cq).getSingleResult();

				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<Long> cqCount = builder.createQuery(Long.class);
				cqCount.select(builder.count(cqCount.from(clazz)));

				// Following line if commented causes
				// [org.hibernate.hql.ast.QuerySyntaxException: Invalid path:
				// 'generatedAlias1.enabled' [select count(generatedAlias0) from
				// xxx.yyy.zzz.Brand as generatedAlias0 where (
				// generatedAlias1.enabled=:param0 ) and (
				// lower(generatedAlias1.description) like :param1 )]]
				em.createQuery(cqCount);

				// filter.getCriteria(clazz, getEmf());
				cqCount.where(filter.getWherePredicate(clazz, getEmf()));

				res = em.createQuery(cqCount).getSingleResult();

			} else {
				// res = findAll(clazz);

				CriteriaBuilder qb = em.getCriteriaBuilder();
				CriteriaQuery<Long> cq = qb.createQuery(Long.class);
				cq.select(qb.count(cq.from(clazz)));
				// cq.where(/*your stuff*/);
				res = em.createQuery(cq).getSingleResult();

			}

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>findBy.</p>
	 *
	 * @param criteria a {@link javax.persistence.criteria.CriteriaQuery} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> List<T> findBy(CriteriaQuery<T> criteria) throws Exception {
		List<T> res = new ArrayList<T>();

		EntityManager em = getEntityManager();

		try {
			res = em.createQuery(criteria).getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>executeUpdate.</p>
	 *
	 * @param query a {@link java.lang.String} object.
	 * @param params a {@link java.lang.Object} object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public int executeUpdate(String query, Object... params) throws Exception {
		int res = 0;

		EntityManager em = getEntityManager();

		try {
			if (!controllerTransactionOpen)
				em.getTransaction().begin();

			Query q = em.createQuery(query);

			if (params != null) {
				int i = 1;
				for (Object o : params) {
					q.setParameter(i, o);
					i++;
				}
			}

			res = q.executeUpdate();

			if (!controllerTransactionOpen)
				em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	private boolean closedFromFinalize = false;
	/** {@inheritDoc} */
	@Override
	protected void finalize() throws Throwable {

		/*
		 * Non facciamo piu la chiusura qui perche' messa in jpasessionlister
		 * apertura chiusura
		 * 
		 * 25/05 la imposto nuovamente per qui progetti che non usano i listener
		 * ed aprono e chiudono quando serve (in caso di errore o non chiusura
		 * almeno cosi' si chiude)
		 */

		// logger.debug(String.format("Finalize Controller %s", numero));
		closeEmAndEmf();
		super.finalize();
		logger.debug("jpacontroller finalized");
		// System.gc();
	}

	// /**
	// *
	// * @return
	// */
	// public EntityManagerFactory test() {
	// return getEmf();
	// }

	/**
	 * <p>getItemCount.</p>
	 *
	 * @param classObj a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> int getItemCount(Class<T> classObj) throws Exception {
		int returnValue = 0;

		EntityManager em = getEntityManager();

		try {

			// StringBuffer hsqlQuery = new StringBuffer();
			// hsqlQuery.append("select count(*) from ");
			// hsqlQuery.append(classObj.getCanonicalName());
			// hsqlQuery.append(" as o");
			// Query q = em.createQuery(hsqlQuery.toString());
			//
			// returnValue = ((Long) q.getSingleResult()).intValue();

			CriteriaBuilder qb = em.getCriteriaBuilder();
			CriteriaQuery<Long> cq = qb.createQuery(Long.class);
			cq.select(qb.count(cq.from(classObj)));

			Long res = em.createQuery(cq).getSingleResult();

			return res.intValue();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

	}

	private EntityManager em = null;
	private boolean controllerTransactionOpen = false;
	private int nestedTransactionCount = 0;

	private EntityManager getEntityManager() {

		if (em == null || !em.isOpen()) {
			em = getEmf().createEntityManager();
		}

		return em;

		// return getEmf().createEntityManager();
	}

	/**
	 * <p>beginTransaction.</p>
	 */
	public void beginTransaction() {
		if (!controllerTransactionOpen) {
			EntityManager em = getEntityManager();

			em.getTransaction().begin();

			controllerTransactionOpen = true;
		}
		nestedTransactionCount++;
	}

	/**
	 * <p>commitTransaction.</p>
	 */
	public void commitTransaction() {
		if (controllerTransactionOpen && nestedTransactionCount == 1) {
			EntityManager em = getEntityManager();
			if (em.getTransaction().isActive()) {
				em.getTransaction().commit();
			}
			controllerTransactionOpen = false;
		}
		nestedTransactionCount--;
	}

	/**
	 * <p>rollbackTransaction.</p>
	 */
	public void rollbackTransaction() {
		if (controllerTransactionOpen && nestedTransactionCount == 1) {
			EntityManager em = getEntityManager();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			controllerTransactionOpen = false;
		}
		nestedTransactionCount--;
	}

	/**
	 * Use for close the Controller in a try-catch-finally block.
	 *
	 * @param aController a {@link it.attocchi.jpa2.JpaController} object.
	 */
	public static void callCloseEmf(JpaController aController) {
		if (aController != null) {
			aController.closeEmAndEmf();
		}
	}

	/**
	 * <p>callRollback.</p>
	 *
	 * @param aController a {@link it.attocchi.jpa2.JpaController} object.
	 */
	public static void callRollback(JpaController aController) {
		if (aController != null) {
			aController.rollbackTransaction();
		}
	}

	/**
	 * Ready to use method to search entities using JPAEntityFilter
	 * @param emf emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz clazz a {@link java.lang.Class} object.
	 * @param filter filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a Serializable object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> List<T> callFind(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		List<T> res = new ArrayList<T>();

		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.findBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("callFind", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindPU.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> List<T> callFindPU(String persistenceUnit, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		List<T> res = new ArrayList<T>();

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);
			res = controller.findBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("callFindPU", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindById.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a long.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindById(EntityManagerFactory emf, Class<T> clazz, long id) throws Exception {

		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.find(clazz, id);
		} catch (Exception ex) {
			logger.error("find", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindById.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a int.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindById(EntityManagerFactory emf, Class<T> clazz, int id) throws Exception {

		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.find(clazz, id);
		} catch (Exception ex) {
			logger.error("callFindById", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindById.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindById(EntityManagerFactory emf, Class<T> clazz, String id) throws Exception {

		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.find(clazz, id);
		} catch (Exception ex) {
			logger.error("callFindById", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindByIdPU.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a long.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindByIdPU(String persistenceUnit, Class<T> clazz, long id) throws Exception {

		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);
			res = controller.find(clazz, id);
		} catch (Exception ex) {
			logger.error("callFindByIdPU", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindByUUIDPU.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param uuid a {@link java.lang.String} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindByUUIDPU(String persistenceUnit, Class<T> clazz, String uuid) throws Exception {

		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);
			res = controller.find(clazz, uuid);
		} catch (Exception ex) {
			logger.error("callFindByUUIDPU", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callCount.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.Long} object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> Long callCount(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		Long res = 0L;

		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.countBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("callCount", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callCountPU.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.Long} object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> Long callCountPU(String persistenceUnit, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		Long res = 0L;

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);
			res = controller.countBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("callCountPU", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindFirst.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.ClassNotFoundException if any.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindFirst(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws ClassNotFoundException, Exception {

		List<T> list = new ArrayList<T>();
		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			list = controller.findBy(clazz, filter);

			if (list != null && list.size() > 0) {
				res = list.get(0);
			}

		} catch (ClassNotFoundException ex) {
			logger.error("callFindFirst", ex);
			throw ex;
		} catch (JpaEntityFilterException ex) {
			logger.error("callFindFirst", ex);
			throw ex;	
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callFindFirstPU.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.ClassNotFoundException if any.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindFirstPU(String persistenceUnit, Class<T> clazz, JPAEntityFilter<T> filter) throws ClassNotFoundException, Exception {

		List<T> list = new ArrayList<T>();
		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);

			list = controller.findBy(clazz, filter);

			if (list != null && list.size() > 0) {
				res = list.get(0);
			}

		} catch (ClassNotFoundException ex) {
			logger.error("callFindFirstPU", ex);
			throw ex;
		} catch (JpaEntityFilterException ex) {
			logger.error("callFindFirstPU", ex);
			throw ex;			
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	/**
	 * <p>callUpdate.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param object a T object.
	 * @param <T> a T object.
	 * @return a boolean.
	 * @throws it.attocchi.jpa2.JpaControllerException if any.
	 */
	public static <T extends Serializable> boolean callUpdate(EntityManagerFactory emf, T object) throws JpaControllerException {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			controller.update(object);
			res = true;
		} catch (JpaControllerException ex) {
			logger.error("callInsert", ex);
			throw ex;
		} catch (Exception ex) {
			logger.error("callInsert", ex);
			throw new JpaControllerException("error calling update", ex);
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	/**
	 * <p>callInsert.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param object a T object.
	 * @param <T> a T object.
	 * @return a boolean.
	 * @throws it.attocchi.jpa2.JpaControllerException if any.
	 */
	public static <T extends Serializable> boolean callInsert(EntityManagerFactory emf, T object) throws JpaControllerException {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			controller.insert(object);
			res = true;
		} catch (JpaControllerException ex) {
			logger.error("callInsert", ex);
			throw ex;
		} catch (Exception ex) {
			logger.error("callInsert", ex);
			throw new JpaControllerException("error calling insert", ex);
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	/**
	 * <p>callInsertPU.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param object a T object.
	 * @param <T> a T object.
	 * @return a boolean.
	 * @throws it.attocchi.jpa2.JpaControllerException if any.
	 */
	public static <T extends Serializable> boolean callInsertPU(String persistenceUnit, T object) throws JpaControllerException {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);

			controller.insert(object);
			res = true;
		} catch (JpaControllerException ex) {
			logger.error("callInsert", ex);
			throw ex;
		} catch (Exception ex) {
			logger.error("callInsert", ex);
			throw new JpaControllerException("error calling insert pu", ex);
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	/**
	 * <p>callUpdatePU.</p>
	 *
	 * @param persistenceUnit a {@link java.lang.String} object.
	 * @param object a T object.
	 * @param <T> a T object.
	 * @return a boolean.
	 * @throws it.attocchi.jpa2.JpaControllerException if any.
	 */
	public static <T extends Serializable> boolean callUpdatePU(String persistenceUnit, T object) throws JpaControllerException {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);

			controller.update(object);
			res = true;
		} catch (JpaControllerException ex) {
			logger.error("callInsert", ex);
			throw ex;
		} catch (Exception ex) {
			logger.error("callInsert", ex);
			throw new JpaControllerException("error calling update pu", ex);
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	private void testClazz(Class clazz) throws ClassNotFoundException {
		if (clazz == null) {
			throw new ClassNotFoundException("JPAController Entity Class (clazz) not specified.");
		}
	}

	/**
	 * <p>callDelete.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param o a T object.
	 * @param id a {@link java.lang.Object} object.
	 * @param <T> a T object.
	 * @return a boolean.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> boolean callDelete(EntityManagerFactory emf, Class<T> clazz, T o, Object id) throws Exception {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			controller.delete(clazz, o, id);
			res = true;
		} catch (Exception ex) {
			logger.error("delete", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	/**
	 * Use findAsMap
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a Serializable object.
	 * @return a {@link java.util.Map} object.
	 * @throws java.lang.Exception if any.
	 */
	@Deprecated
	public <T extends IEntityWithIdLong> Map<Long, T> findAllAsMap(Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		return findAsMap(clazz, filter);
	}

	/**
	 * Return a Map based on element ID
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a Serializable object.
	 * @return a {@link java.util.Map} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends IEntityWithIdLong> Map<Long, T> findAsMap(Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		Map<Long, T> res = new LinkedHashMap<Long, T>();

		List<T> list = findBy(clazz, filter); // findAll(clazz, null);
		for (T item : list) {
			res.put(item.getId(), item);
		}

		return res;
	}

	/**
	 * Use callFindAsMap
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a Serializable object.
	 * @return a {@link java.util.Map} object.
	 * @throws java.lang.Exception if any.
	 */
	@Deprecated
	public static <T extends IEntityWithIdLong> Map<Long, T> callFindAllAsMap(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		return callFindAsMap(emf, clazz, filter);
	}

	/**
	 * <p>callFindAsMap.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.Map} object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends IEntityWithIdLong> Map<Long, T> callFindAsMap(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		Map<Long, T> res = new LinkedHashMap<Long, T>();
		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			res = controller.findAllAsMap(clazz, filter);
		} catch (Exception ex) {
			logger.error("callFindAllAsMap", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	/**
	 * <p>callFindProjection.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazzT a {@link java.lang.Class} object.
	 * @param clazzF a {@link java.lang.Class} object.
	 * @param fieldDefinition a {@link javax.persistence.metamodel.SingularAttribute} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @param <F> a F object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends IEntityWithIdLong, F> List<F> callFindProjection(EntityManagerFactory emf, Class<T> clazzT, Class<F> clazzF, SingularAttribute<T, F> fieldDefinition, JPAEntityFilter<T> filter) throws Exception {
		List<F> res = new ArrayList<F>();
		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.findProjection(emf, clazzT, clazzF, fieldDefinition, filter);
		} catch (Exception ex) {
			logger.error("callFindAllAsMap", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	/**
	 * <p>findProjection.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazzT a {@link java.lang.Class} object.
	 * @param clazzF a {@link java.lang.Class} object.
	 * @param fieldDefinition a {@link javax.persistence.metamodel.SingularAttribute} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @param <F> a F object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends IEntityWithIdLong, F> List<F> findProjection(EntityManagerFactory emf, Class<T> clazzT, Class<F> clazzF, SingularAttribute<T, F> fieldDefinition, JPAEntityFilter<T> filter) throws Exception {
		List<F> res = new ArrayList<F>();

		testClazz(clazzT);
		testClazz(clazzF);

		EntityManager em = getEntityManager();

		try {

			TypedQuery<F> query = em.createQuery("SELECT o." + fieldDefinition.getName() + " FROM " + clazzT.getCanonicalName() + " o", clazzF);
			res = query.getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!controllerTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * <p>findById.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a long.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T extends Serializable> T findById(Class<T> clazz, long id) throws Exception {
		return find(clazz, id);
	}

	/**
	 * <p>callFindBy.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param filter a {@link it.attocchi.jpa2.JPAEntityFilter} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> List<T> callFindBy(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		return callFind(emf, clazz, filter);
	}

	/**
	 * <p>callFindBy.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param id a long.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public static <T extends Serializable> T callFindBy(EntityManagerFactory emf, Class<T> clazz, long id) throws Exception {
		return callFindById(emf, clazz, id);
	}
}
