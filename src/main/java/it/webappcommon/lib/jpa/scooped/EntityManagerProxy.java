package it.webappcommon.lib.jpa.scooped;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author puche
 */
abstract class EntityManagerProxy implements EntityManager {

	protected final EntityManager delegate;

	/**
	 * <p>Constructor for EntityManagerProxy.</p>
	 *
	 * @param delegate a {@link javax.persistence.EntityManager} object.
	 */
	public EntityManagerProxy(EntityManager delegate) {

		this.delegate = delegate;
	}

	/** {@inheritDoc} */
	public void persist(Object object) {

		delegate.persist(object);
	}

	/**
	 * <p>merge.</p>
	 *
	 * @param entity a T object.
	 * @param <T> a T object.
	 * @return a T object.
	 */
	public <T> T merge(T entity) {

		return delegate.merge(entity);
	}

	/** {@inheritDoc} */
	public void remove(Object object) {

		delegate.remove(object);
	}

	/** {@inheritDoc} */
	public <T> T find(Class<T> entityClass, Object primaryKey) {

		return delegate.find(entityClass, primaryKey);
	}

	/** {@inheritDoc} */
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {

		return delegate.getReference(entityClass, primaryKey);
	}

	/**
	 * <p>flush.</p>
	 */
	public void flush() {

		delegate.flush();
	}

	/** {@inheritDoc} */
	public void setFlushMode(FlushModeType flushModeType) {

		delegate.setFlushMode(flushModeType);
	}

	/**
	 * <p>getFlushMode.</p>
	 *
	 * @return a {@link javax.persistence.FlushModeType} object.
	 */
	public FlushModeType getFlushMode() {

		return delegate.getFlushMode();
	}

	/** {@inheritDoc} */
	public void lock(Object object, LockModeType lockModeType) {

		delegate.lock(object, lockModeType);
	}

	/** {@inheritDoc} */
	public void refresh(Object object) {

		delegate.refresh(object);
	}

	/**
	 * <p>clear.</p>
	 */
	public void clear() {

		delegate.clear();
	}

	/** {@inheritDoc} */
	public boolean contains(Object object) {

		return delegate.contains(object);
	}

	/**
	 * <p>createQuery.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 * @return a {@link javax.persistence.Query} object.
	 */
	public Query createQuery(String string) {

		return delegate.createQuery(string);
	}

	/** {@inheritDoc} */
	public Query createNamedQuery(String string) {

		return delegate.createNamedQuery(string);
	}

	/** {@inheritDoc} */
	public Query createNativeQuery(String string) {

		return delegate.createNativeQuery(string);
	}

	/**
	 * <p>createNativeQuery.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 * @param aClass a {@link java.lang.Class} object.
	 * @return a {@link javax.persistence.Query} object.
	 */
	public Query createNativeQuery(String string, Class aClass) {

		return delegate.createNativeQuery(string, aClass);
	}

	/** {@inheritDoc} */
	public Query createNativeQuery(String string, String string0) {

		return delegate.createNativeQuery(string, string0);
	}

	/**
	 * <p>joinTransaction.</p>
	 */
	public void joinTransaction() {

		delegate.joinTransaction();
	}

	/**
	 * <p>Getter for the field <code>delegate</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getDelegate() {

		return delegate.getDelegate();
	}

	/**
	 * <p>close.</p>
	 */
	public void close() {

		delegate.close();
	}

	/**
	 * <p>isOpen.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isOpen() {

		return delegate.isOpen();
	}

	/**
	 * <p>getTransaction.</p>
	 *
	 * @return a {@link javax.persistence.EntityTransaction} object.
	 */
	public EntityTransaction getTransaction() {

		return delegate.getTransaction();
	}

	/** {@inheritDoc} */
	@Override
	public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1) {
		return delegate.createNamedQuery(arg0, arg1);
	}

	/** {@inheritDoc} */
	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0) {
		return delegate.createQuery(arg0);
	}

	/** {@inheritDoc} */
	@Override
	public <T> TypedQuery<T> createQuery(String arg0, Class<T> arg1) {
		return delegate.createQuery(arg0, arg1);
	}

	/** {@inheritDoc} */
	@Override
	public void detach(Object arg0) {
		delegate.detach(arg0);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2) {
		return delegate.find(arg0, arg1, arg2);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {
		return delegate.find(arg0, arg1, arg2);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2, Map<String, Object> arg3) {
		return delegate.find(arg0, arg1, arg2, arg3);
	}

	/** {@inheritDoc} */
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return delegate.getCriteriaBuilder();
	}

	/** {@inheritDoc} */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return delegate.getEntityManagerFactory();
	}

	/** {@inheritDoc} */
	@Override
	public LockModeType getLockMode(Object arg0) {
		return delegate.getLockMode(arg0);
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
	public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
		delegate.lock(arg0, arg1, arg2);

	}

	/** {@inheritDoc} */
	@Override
	public void refresh(Object arg0, Map<String, Object> arg1) {
		delegate.refresh(arg0, arg1);

	}

	/** {@inheritDoc} */
	@Override
	public void refresh(Object arg0, LockModeType arg1) {
		delegate.refresh(arg0, arg1);

	}

	/** {@inheritDoc} */
	@Override
	public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
		delegate.refresh(arg0, arg1, arg2);

	}

	/** {@inheritDoc} */
	@Override
	public void setProperty(String arg0, Object arg1) {
		delegate.setProperty(arg0, arg1);

	}

	/** {@inheritDoc} */
	@Override
	public <T> T unwrap(Class<T> arg0) {

		return delegate.unwrap(arg0);
	}
}
