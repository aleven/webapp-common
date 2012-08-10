/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.espressoft.jpa2;

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

	public static int Numero = 0;
	private int numero = 0;

	/**
	 * use DEFAULT_PU
	 */
	public JpaController() {
		super();
		assegnaNumero();

		this.persistenceUnit = DEFAULT_PU;
		passedEmf = false;
		// System.gc();
	}

	public JpaController(EntityManagerFactory emf) {
		this();

		if (emf != null) {
			this.emf = emf;
			passedEmf = true;
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

	private void assegnaNumero() {

		JpaController.Numero++;

		this.numero = JpaController.Numero;

		logger.debug(String.format("Creazione Controller %s", numero));
	}

	// // // private Class<T> aClass;
	// // // private T type;
	// final private Class<T> returnedClass;
	//
	// //
	// // public Class getGenericType() {
	// //
	// // return returnedClass;
	// //
	// // // return type.getClass();
	// // // return ((ParameterizedType)
	// // //
	// //
	// getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass();
	// // // return getClass().get
	// // }
	// //
	// // /**
	// // * Questo tipo di costruttore e' necessario perche' al momento e'
	// // * impossibile risalire al tipo dal parametro Generics
	// // *
	// // * @param clazz
	// // */
	// // public GenericJpaController(Class<T> clazz) {
	// // returnedClass = clazz;
	// // }
	//
	// // private Type type;
	// //
	// // private void TypeRef() {
	// // ParameterizedType superclass = (ParameterizedType)
	// // getClass().getGenericSuperclass();
	// // type = superclass.getActualTypeArguments()[0];
	// // }
	//
	// public GenericJpaController(Class<T> clazz, EntityManagerFactory emf) {
	// this.emf = emf;
	// this.returnedClass = clazz;
	// }
	//
	// private EntityManagerFactory emf = null;
	//
	// public EntityManager getEntityManager() {
	// return emf.createEntityManager();
	// }
	//
	// public void create(T o) {
	// EntityManager em = null;
	// try {
	// em = getEntityManager();
	// em.getTransaction().begin();
	// em.persist(o);
	// em.getTransaction().commit();
	// } finally {
	// if (em != null) {
	// em.close();
	// }
	// }
	// }
	//
	// public void edit(T o) throws NonexistentEntityException, Exception {
	// EntityManager em = null;
	// try {
	// em = getEntityManager();
	// em.getTransaction().begin();
	// o = em.merge(o);
	// em.getTransaction().commit();
	// } catch (Exception ex) {
	// String msg = ex.getLocalizedMessage();
	// if (msg == null || msg.length() == 0) {
	// Integer id = o.getId();
	// if (findEntity(id) == null) {
	// throw new NonexistentEntityException("The Entity with id " + id +
	// " no longer exists.");
	// }
	// }
	// throw ex;
	// } finally {
	// if (em != null) {
	// em.close();
	// }
	// }
	// }
	//
	// public void destroy(Integer id) throws NonexistentEntityException {
	// EntityManager em = null;
	// try {
	// em = getEntityManager();
	// em.getTransaction().begin();
	// T o;
	// try {
	// o = (T) em.getReference(getGenericType(), id);
	// o.getId();
	// } catch (EntityNotFoundException enfe) {
	// throw new NonexistentEntityException("The Entity with id " + id +
	// " no longer exists.", enfe);
	// }
	// em.remove(o);
	// em.getTransaction().commit();
	// } finally {
	// if (em != null) {
	// em.close();
	// }
	// }
	// }
	//
	// public List<T> findEntityEntities() {
	// return findEntityEntities(true, -1, -1);
	// }
	//
	// public List<T> findEntityEntities(int maxResults, int firstResult) {
	// return findEntityEntities(false, maxResults, firstResult);
	// }
	//
	// private List<T> findEntityEntities(boolean all, int maxResults, int
	// firstResult) {
	// EntityManager em = getEntityManager();
	// try {
	// CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	// cq.select(cq.from(getGenericType()));
	// Query q = em.createQuery(cq);
	// if (!all) {
	// q.setMaxResults(maxResults);
	// q.setFirstResult(firstResult);
	// }
	// return q.getResultList();
	// } finally {
	// em.close();
	// }
	// }
	//
	// public List<T> findEntityEntities(String semeRicerca, boolean all, int
	// maxResults, int firstResult) {
	// EntityManager em = getEntityManager();
	// try {
	// CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	//
	// javax.persistence.criteria.CriteriaBuilder cb =
	// getEntityManager().getCriteriaBuilder();
	//
	// Root<T> rt = cq.from(getGenericType());
	//
	// cq.select(rt);
	// // cq.where(Restrictions.like("ragioneSociale1", "%" + semeRicerca +
	// // "%"))
	// if (semeRicerca != null && !semeRicerca.isEmpty()) {
	// // cq.where(cb.like(rt.get(Entity_.ragioneSociale1), "%"
	// // + semeRicerca + "%"));
	// }
	//
	// Query q = em.createQuery(cq);
	// if (!all) {
	// q.setMaxResults(maxResults);
	// q.setFirstResult(firstResult);
	// }
	// return q.getResultList();
	// } finally {
	// em.close();
	// }
	// }
	//
	// public T findEntity(Integer id) {
	// EntityManager em = getEntityManager();
	// try {
	// return (T) em.find(getGenericType(), id);
	// } finally {
	// em.close();
	// }
	// }
	//
	// public int getEntityCount(String search) {
	// EntityManager em = getEntityManager();
	// try {
	// CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
	// Root<T> rt = cq.from(getGenericType());
	// cq.select(em.getCriteriaBuilder().count(rt));
	//
	// if (search != null && !search.isEmpty()) {
	// //
	// cq.where(getEntityManager().getCriteriaBuilder().like(rt.get(Entity_.),
	// // "%" + search + "%"));
	// }
	//
	// Query q = em.createQuery(cq);
	// return ((Long) q.getSingleResult()).intValue();
	// } finally {
	// em.close();
	// }
	// }

	public <T extends Serializable> void insert(T o) throws Exception {

		EntityManager em = getEmf().createEntityManager();

		try {

			em.getTransaction().begin();
			em.persist(o);
			em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

	}

	public <T extends Serializable> void update(T o) throws Exception {

		EntityManager em = getEmf().createEntityManager();

		try {

			em.getTransaction().begin();
			em.merge(o);
			em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

	}

	public <T extends Serializable> void delete(Class<T> clazz, T o, Object id) throws Exception {

		EntityManager em = getEmf().createEntityManager();

		try {

			em.getTransaction().begin();

			T attached = em.find(clazz, id);
			if (attached != null) {
				em.remove(attached);
				// em.remove(o);
			}

			em.getTransaction().commit();

		} catch (Exception e) {
			throw e;
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

	}

	public <T extends Serializable> List<T> findAll(Class<T> clazz) throws Exception {
		List<T> res = new ArrayList<T>();
		// EntityManager em = getEmf().createEntityManager();
		//
		// try {
		//
		// res = em.createQuery("SELECT e FROM " + clazz.getCanonicalName() +
		// " e", clazz).getResultList();
		//
		// } catch (Exception e) {
		// throw e;
		// } finally {
		// // Close the database connection:
		// if (em.getTransaction().isActive())
		// em.getTransaction().rollback();
		// em.close();
		// }

		String query = "SELECT o FROM " + clazz.getCanonicalName() + " o";
		res = findBy(clazz, query);

		return res;
	}

	public <T extends Serializable> T find(Class<T> clazz, Long id) throws Exception {
		T res = null;
		EntityManager em = getEmf().createEntityManager();

		try {

			res = em.find(clazz, id);

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

	public <T extends Serializable> T findFirst(Class<T> clazz, String query, Object... params) throws Exception {
		T res = null;
		List<T> list = null;
		// EntityManagerFactory emf = (EntityManagerFactory)
		// getSession().getAttribute("emf");

		EntityManager em = getEmf().createEntityManager();

		try {

			// list = em.createQuery(query, clazz).getResultList();

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
			// setErrorMessage(e.getMessage(), "");
			throw e;
		} finally {
			// Close the database connection:
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

		return res;
	}

	public <T extends Serializable> T findFirst(Class<T> clazz) throws Exception {
		T res = null;
		List<T> list = null;
		// EntityManagerFactory emf = (EntityManagerFactory)
		// getSession().getAttribute("emf");

		// EntityManager em = getEmf().createEntityManager();
		//
		// try {
		//
		// list = em.createQuery("SELECT e FROM " + clazz.getCanonicalName() +
		// " e", clazz).getResultList();
		//
		// if (list != null && list.size() > 0) {
		// res = list.get(0);
		// }
		//
		// } catch (Exception e) {
		// // setErrorMessage(e.getMessage(), "");
		// throw e;
		// } finally {
		// // Close the database connection:
		// if (em.getTransaction().isActive())
		// em.getTransaction().rollback();
		// em.close();
		// }

		String query = "SELECT o FROM " + clazz.getCanonicalName() + " o";
		res = findFirst(clazz, query);

		return res;
	}

	private EntityManagerFactory getEmf() {
		// EntityManagerFactory res = null;

		if (!passedEmf) {

			// Memorizzo sullo Statico, ma ora usiamo i Listener
			// if (sharedEmf == null || !sharedEmf.isOpen()) {
			// sharedEmf =
			// Persistence.createEntityManagerFactory(persistenceUnit);
			// }
			// emf = sharedEmf;
			if (emf == null || !emf.isOpen()) {
				if (dbConf == null) {
					emf = Persistence.createEntityManagerFactory(persistenceUnit);
				} else {
					emf = Persistence.createEntityManagerFactory(persistenceUnit, dbConf);
				}
			}
		}
		// res = emf;

		return emf;
	}

	public void close() {
		if (!passedEmf) {
			if (getEmf() != null) {
				logger.debug(String.format("Close Controller %s", numero));
				getEmf().close();
			}
		}
	}

	public <T extends Serializable> List<T> findByExample(Class<T> clazz, T anExample) throws Exception {
		List<T> res = new ArrayList<T>();

		EntityManager em = getEmf().createEntityManager();
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

	public <T extends Serializable> List<T> findBy(Class<T> clazz, String query) throws Exception {
		List<T> res = new ArrayList<T>();

		EntityManager em = getEmf().createEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			// session = (Session) em.getDelegate();

			// res =
			// session.createCriteria(clazz).add(Example.create(anExample).excludeZeroes().enableLike()).list();

			res = em.createQuery(query, clazz).getResultList();

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
	
	@Deprecated
	public <T extends Serializable> List<T> findBy(Class<T> clazz, JPAEntityFilter filter) throws Exception {
		List<T> res = new ArrayList<T>();

		EntityManager em = getEmf().createEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			// session = (Session) em.getDelegate();

			// res =
			// session.createCriteria(clazz).add(Example.create(anExample).excludeZeroes().enableLike()).list();

			String query = "SELECT o FROM " + clazz.getCanonicalName() + " o " + filter.getHQLQueryString(em) ;
			
			res = em.createQuery(query, clazz).getResultList();

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

		EntityManager em = getEmf().createEntityManager();
		Session session = null;
		Criteria cri = null;

		try {

			session = (Session) em.getDelegate();

			// res =
			// session.createCriteria(clazz).add(Example.create(anExample).excludeZeroes().enableLike()).list();

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
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

		return res;
	}

	public int executeUpdate(String query, Object... params) throws Exception {
		int res = 0;

		EntityManager em = getEmf().createEntityManager();
		Criteria cri = null;

		try {

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

			em.getTransaction().commit();

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

		logger.debug(String.format("Finalize Controller %s", numero));
		close();

		super.finalize();

		// System.gc();
	}

	/**
	 * 
	 * @return
	 */
	public EntityManagerFactory test() {
		return getEmf();
	}

	public static void close(JpaController istance) {
		if (istance != null) {
			istance.close();
		}
	}

	public int getItemCount(Class classObj) throws Exception {
		int returnValue = 0;

		EntityManager em = getEmf().createEntityManager();

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
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			em.close();
		}

	}

	@Deprecated
	public void beginTransaction() throws Exception {

		// EntityManager em = getEmf().createEntityManager();
		//
		// em.getTransaction().begin();

	}

	@Deprecated
	public void commitTransaction() throws Exception {

		// EntityManager em = getEmf().createEntityManager();
		//
		// em.getTransaction().commit();

	}

	@Deprecated
	public void rollbackTransaction() throws Exception {

		// EntityManager em = getEmf().createEntityManager();
		//
		// if (em.getTransaction().isActive())
		// em.getTransaction().rollback();

	}

}
