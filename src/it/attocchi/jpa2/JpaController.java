/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.attocchi.jpa2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * 
 * @author Mirco Attocchi
 */
public class JpaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_PU = "DEFAULT_PU";

	protected static final Logger logger = Logger.getLogger(JpaController.class.getName());

	private EntityManagerFactory emf;
	private boolean passedEmf;

	private Map<String, String> dbConf;

	// private static EntityManagerFactory sharedEmf;

	private String persistenceUnit;

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

	public JpaController(EntityManagerFactory emf) {
		this();

		if (emf != null) {
			this.emf = emf;
			passedEmf = true;
		} else {

		}
	}

	public JpaController(String persistenceUnit) {
		this();
		this.persistenceUnit = persistenceUnit;
		// passedEmf = false;

	}

	public JpaController(String persistenceUnit, Map<String, String> dbConf) {
		this(persistenceUnit);
		this.dbConf = dbConf;

	}

	// private void assegnaNumero() {
	// JpaController.Numero++;
	// this.numero = JpaController.Numero;
	// logger.debug(String.format("Creazione Controller %s", numero));
	// }

	public <T extends Serializable> void insert(T o) throws Exception {

		EntityManager em = getEntityManager();

		try {

			if (!globalTransactionOpen)
				em.getTransaction().begin();
			em.persist(o);
			if (!globalTransactionOpen)
				em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

	}

	public <T extends Serializable> void update(T o) throws Exception {

		EntityManager em = getEntityManager();

		try {
			if (!globalTransactionOpen)
				em.getTransaction().begin();
			em.merge(o);
			if (!globalTransactionOpen)
				em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

	}

	public <T extends Serializable> void delete(Class<T> clazz, T o, Object id) throws Exception {

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {
			if (!globalTransactionOpen)
				em.getTransaction().begin();

			T attached = em.find(clazz, id);
			if (attached != null) {
				em.remove(attached);
				// em.remove(o);
			}
			if (!globalTransactionOpen)
				em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {

			// Close the database connection:
			if (!globalTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

	}

	public <T extends Serializable> List<T> findAll(Class<T> clazz) throws Exception {
		List<T> res = new ArrayList<T>();

		res = findAll(clazz, null);

		return res;
	}

	public <T extends Serializable> List<T> findAll(Class<T> clazz, String orderBy) throws Exception {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		String query = "SELECT o FROM " + clazz.getCanonicalName() + " o";
		if (StringUtils.isNotEmpty(orderBy)) {
			query = query + " ORDER BY " + orderBy;
		}

		res = findBy(clazz, query);

		return res;
	}

	public <T extends Serializable> T find(Class<T> clazz, Long id) throws Exception {
		T res = null;

		testClazz(clazz);

		EntityManager em = getEntityManager();

		try {

			res = em.find(clazz, id);

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

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
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

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
	 * @return
	 */
	@Deprecated
	public EntityManagerFactory getEntityManagetFactory() {
		return getEmf();
	}

	private EntityManagerFactory getEmf() {

		if (!passedEmf) {

			/*
			 * Questo controllo sembra causare il problema con AJAX che
			 * "EMF e' già registrato"
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
		if (!globalTransactionOpen) {
			if (em != null) {
				// logger.debug(String.format("Close EM %s", numero));
				em.close();
				em = null;
			}
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
				// logger.debug(String.format("Close EMF %s", numero));
				emf.close();
				// emf = null;
			}
		}
	}

	public <T extends Serializable> List<T> findByExample(Class<T> clazz, T anExample) throws Exception {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		EntityManager em = getEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			session = (Session) em.getDelegate();

			res = session.createCriteria(clazz).add(Example.create(anExample).excludeZeroes().enableLike()).list();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	public <T extends Serializable> List<T> findBy(Class<T> clazz, String query) throws Exception {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		EntityManager em = getEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			res = em.createQuery(query, clazz).getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	/**
	 * 
	 * @param clazz
	 * @param query
	 *            a query with Ordinal Parameters (?index)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T extends Serializable> List<T> findBy(Class<T> clazz, String query, Object... params) throws Exception {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		EntityManager em = getEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			session = (Session) em.getDelegate();

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
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	public <T extends Serializable> List<T> findBy(Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		List<T> res = new ArrayList<T>();

		testClazz(clazz);

		EntityManager em = getEntityManager();
		Criteria cri = null;

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

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	public <T extends Serializable> Long count(Class<T> clazz) throws Exception {
		return countBy(clazz, null);
	}

	public <T extends Serializable> Long countBy(Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {
		Long res = 0L;

		testClazz(clazz);

		EntityManager em = getEntityManager();
		Criteria cri = null;

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

				filter.getCriteria(clazz, getEmf());
				cqCount.where(filter.getWherePredicate());
				
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
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	public <T extends Serializable> List<T> findBy(CriteriaQuery<T> criteria) throws Exception {
		List<T> res = new ArrayList<T>();

		EntityManager em = getEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			session = (Session) em.getDelegate();

			res = em.createQuery(criteria).getResultList();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	public int executeUpdate(String query, Object... params) throws Exception {
		int res = 0;

		EntityManager em = getEntityManager();
		Criteria cri = null;

		try {
			if (!globalTransactionOpen)
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

			if (!globalTransactionOpen)
				em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (!globalTransactionOpen) {
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

		return res;
	}

	@Override
	protected void finalize() throws Throwable {

		/*
		 * Non facciamo piu la chiusura qui perche' messa in jpasessionlister
		 * apertura chiusura
		 * 
		 * 25/05 la imposto nuovamente per qui progetti che non usano i listener
		 * ed aprono e chiudono quando serve (in caso di errore o non chiusura
		 * almeno così si chiude)
		 */

		// logger.debug(String.format("Finalize Controller %s", numero));

		closeEmAndEmf();

		super.finalize();

		// System.gc();
	}

	// /**
	// *
	// * @return
	// */
	// public EntityManagerFactory test() {
	// return getEmf();
	// }

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
			if (!globalTransactionOpen) {
				// if (em.getTransaction().isActive())
				// em.getTransaction().rollback();
				closeEm(); // em.close();
			}
		}

	}

	private EntityManager em = null;
	private boolean globalTransactionOpen = false;

	private EntityManager getEntityManager() {

		if (em == null || !em.isOpen()) {
			em = getEmf().createEntityManager();
		}

		return em;

		// return getEmf().createEntityManager();
	}

	public void beginTransaction() {
		if (!globalTransactionOpen) {
			EntityManager em = getEntityManager();

			em.getTransaction().begin();

			globalTransactionOpen = true;
		}
	}

	public void commitTransaction() {
		if (globalTransactionOpen) {
			EntityManager em = getEntityManager();
			if (em.getTransaction().isActive()) {
				em.getTransaction().commit();
			}
			globalTransactionOpen = false;
		}
	}

	public void rollbackTransaction() {
		if (globalTransactionOpen) {
			EntityManager em = getEntityManager();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			globalTransactionOpen = false;
		}

	}

	/**
	 * Use for close the Controller in a try-catch-finally block.
	 * 
	 * @param aController
	 */
	public static void callCloseEmf(JpaController aController) {
		if (aController != null) {
			aController.closeEmAndEmf();
		}
	}

	public static void callRollback(JpaController aController) {
		if (aController != null) {
			aController.rollbackTransaction();
		}
	}

	/**
	 * Ready to use method to search entities using JPAEntityFilter
	 * 
	 * @param clazz
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static <T extends Serializable> List<T> callFind(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		List<T> res = new ArrayList<T>();

		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.findBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("find", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	public static <T extends Serializable> List<T> callFindPU(String persistenceUnit, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		List<T> res = new ArrayList<T>();

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);
			res = controller.findBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("find", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	public static <T extends Serializable> Long callCount(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		Long res = 0L;

		JpaController controller = null;
		try {
			controller = new JpaController(emf);
			res = controller.countBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("find", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	public static <T extends Serializable> Long callCountPU(String persistenceUnit, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		Long res = 0L;

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);
			res = controller.countBy(clazz, filter);
		} catch (Exception ex) {
			logger.error("find", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	public static <T extends Serializable> T callFindFirst(EntityManagerFactory emf, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		List<T> list = new ArrayList<T>();
		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			list = controller.findBy(clazz, filter);

			if (list != null && list.size() > 0) {
				res = list.get(0);
			}

		} catch (Exception ex) {
			logger.error("findFirst", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}

	public static <T extends Serializable> T callFindFirstPU(String persistenceUnit, Class<T> clazz, JPAEntityFilter<T> filter) throws Exception {

		List<T> list = new ArrayList<T>();
		T res = null;

		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);

			list = controller.findBy(clazz, filter);

			if (list != null && list.size() > 0) {
				res = list.get(0);
			}

		} catch (Exception ex) {
			logger.error("findFirst", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}

		return res;
	}
	
	public static <T extends Serializable> boolean callUpdate(EntityManagerFactory emf, T object) throws Exception {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			controller.update(object);
			res = true;
		} catch (Exception ex) {
			logger.error("update", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	public static <T extends Serializable> boolean callInsert(EntityManagerFactory emf, T object) throws Exception {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(emf);

			controller.insert(object);
			res = true;
		} catch (Exception ex) {
			logger.error("insert", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	public static <T extends Serializable> boolean callInsertPU(String persistenceUnit, T object) throws Exception {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);

			controller.insert(object);
			res = true;
		} catch (Exception ex) {
			logger.error("insert", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	public static <T extends Serializable> boolean callUpdatePU(String persistenceUnit, T object) throws Exception {
		boolean res = false;
		JpaController controller = null;
		try {
			controller = new JpaController(persistenceUnit);

			controller.update(object);
			res = true;
		} catch (Exception ex) {
			logger.error("update", ex);
			throw ex;
		} finally {
			JpaController.callCloseEmf(controller);
		}
		return res;
	}

	private void testClazz(Class clazz) throws Exception {
		if (clazz == null)
			throw new Exception("JPAController Entity Class not specified.");
	}
}
