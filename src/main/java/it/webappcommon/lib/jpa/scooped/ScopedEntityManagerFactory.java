package it.webappcommon.lib.jpa.scooped;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * <p>ScopedEntityManagerFactory class.</p>
 *
 * @author puche
 * @version $Id: $Id
 */
public class ScopedEntityManagerFactory extends EntityManagerFactoryProxy implements LazyCloseListener {

	private final ThreadLocal<LazyCloseEntityManager> threadLocal;

	/**
	 * <p>Constructor for ScopedEntityManagerFactory.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 */
	protected ScopedEntityManagerFactory(EntityManagerFactory emf) {

		super(emf);
		this.threadLocal = new ThreadLocal<LazyCloseEntityManager>();
	}

	/** {@inheritDoc} */
	public EntityManager createEntityManager(Map map) {

		LazyCloseEntityManager em = threadLocal.get();
		if (em == null) {
			em = new LazyCloseEntityManager(super.createEntityManager(map));
			createEntityManager(em);
		}
		return em;
	}

	/**
	 * <p>createEntityManager.</p>
	 *
	 * @return a {@link javax.persistence.EntityManager} object.
	 */
	public EntityManager createEntityManager() {

		LazyCloseEntityManager em = threadLocal.get();
		if (em == null) {
			em = new LazyCloseEntityManager(super.createEntityManager());
			createEntityManager(em);
		}
		return em;
	}

	private void createEntityManager(LazyCloseEntityManager em) {

		threadLocal.set(em);
		em.setLazyCloseListener(this);
	}

	/**
	 * <p>getEntityManager.</p>
	 *
	 * @return a {@link it.webappcommon.lib.jpa.scooped.LazyCloseEntityManager} object.
	 */
	protected LazyCloseEntityManager getEntityManager() {

		return threadLocal.get();
	}

	/**
	 * <p>lazilyClosed.</p>
	 */
	public void lazilyClosed() {

		threadLocal.set(null);
	}
}
