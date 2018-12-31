package it.webappcommon.lib.jpa.scooped;

import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author puche
 */
abstract class EntityManagerFactoryProxy implements EntityManagerFactory {

	protected final EntityManagerFactory delegate;

	/**
	 * <p>Constructor for EntityManagerFactoryProxy.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 */
	protected EntityManagerFactoryProxy(EntityManagerFactory emf) {
		this.delegate = emf;
	}

	/** {@inheritDoc} */
	@Override
	public EntityManager createEntityManager() {
		return delegate.createEntityManager();
	}

	/** {@inheritDoc} */
	@Override
	public EntityManager createEntityManager(Map map) {
		return delegate.createEntityManager(map);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isOpen() {
		return delegate.isOpen();
	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		delegate.close();
	}

	/** {@inheritDoc} */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return delegate.getCriteriaBuilder();
	}

	/** {@inheritDoc} */
	@Override
	public Metamodel getMetamodel() {
		return delegate.getMetamodel();
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, Object> getProperties() {
		return delegate.getProperties();
	}

	/** {@inheritDoc} */
	@Override
	public Cache getCache() {
		return delegate.getCache();
	}

	/** {@inheritDoc} */
	@Override
	public PersistenceUnitUtil getPersistenceUnitUtil() {
		return delegate.getPersistenceUnitUtil();
	}
}
