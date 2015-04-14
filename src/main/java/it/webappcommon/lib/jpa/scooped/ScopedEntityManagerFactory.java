package it.webappcommon.lib.jpa.scooped;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author puche
 */
public class ScopedEntityManagerFactory extends EntityManagerFactoryProxy implements LazyCloseListener {

	private final ThreadLocal<LazyCloseEntityManager> threadLocal;

	protected ScopedEntityManagerFactory(EntityManagerFactory emf) {

		super(emf);
		this.threadLocal = new ThreadLocal<LazyCloseEntityManager>();
	}

	public EntityManager createEntityManager(Map map) {

		LazyCloseEntityManager em = threadLocal.get();
		if (em == null) {
			em = new LazyCloseEntityManager(super.createEntityManager(map));
			createEntityManager(em);
		}
		return em;
	}

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

	protected LazyCloseEntityManager getEntityManager() {

		return threadLocal.get();
	}

	public void lazilyClosed() {

		threadLocal.set(null);
	}
}