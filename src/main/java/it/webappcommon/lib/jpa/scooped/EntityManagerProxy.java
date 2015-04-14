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

	public EntityManagerProxy(EntityManager delegate) {

		this.delegate = delegate;
	}

	public void persist(Object object) {

		delegate.persist(object);
	}

	public <T> T merge(T entity) {

		return delegate.merge(entity);
	}

	public void remove(Object object) {

		delegate.remove(object);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey) {

		return delegate.find(entityClass, primaryKey);
	}

	public <T> T getReference(Class<T> entityClass, Object primaryKey) {

		return delegate.getReference(entityClass, primaryKey);
	}

	public void flush() {

		delegate.flush();
	}

	public void setFlushMode(FlushModeType flushModeType) {

		delegate.setFlushMode(flushModeType);
	}

	public FlushModeType getFlushMode() {

		return delegate.getFlushMode();
	}

	public void lock(Object object, LockModeType lockModeType) {

		delegate.lock(object, lockModeType);
	}

	public void refresh(Object object) {

		delegate.refresh(object);
	}

	public void clear() {

		delegate.clear();
	}

	public boolean contains(Object object) {

		return delegate.contains(object);
	}

	public Query createQuery(String string) {

		return delegate.createQuery(string);
	}

	public Query createNamedQuery(String string) {

		return delegate.createNamedQuery(string);
	}

	public Query createNativeQuery(String string) {

		return delegate.createNativeQuery(string);
	}

	public Query createNativeQuery(String string, Class aClass) {

		return delegate.createNativeQuery(string, aClass);
	}

	public Query createNativeQuery(String string, String string0) {

		return delegate.createNativeQuery(string, string0);
	}

	public void joinTransaction() {

		delegate.joinTransaction();
	}

	public Object getDelegate() {

		return delegate.getDelegate();
	}

	public void close() {

		delegate.close();
	}

	public boolean isOpen() {

		return delegate.isOpen();
	}

	public EntityTransaction getTransaction() {

		return delegate.getTransaction();
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1) {
		return delegate.createNamedQuery(arg0, arg1);
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0) {
		return delegate.createQuery(arg0);
	}

	@Override
	public <T> TypedQuery<T> createQuery(String arg0, Class<T> arg1) {
		return delegate.createQuery(arg0, arg1);
	}

	@Override
	public void detach(Object arg0) {
		delegate.detach(arg0);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2) {
		return delegate.find(arg0, arg1, arg2);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {
		return delegate.find(arg0, arg1, arg2);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2, Map<String, Object> arg3) {
		return delegate.find(arg0, arg1, arg2, arg3);
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return delegate.getCriteriaBuilder();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return delegate.getEntityManagerFactory();
	}

	@Override
	public LockModeType getLockMode(Object arg0) {
		return delegate.getLockMode(arg0);
	}

	@Override
	public Metamodel getMetamodel() {

		return delegate.getMetamodel();
	}

	@Override
	public Map<String, Object> getProperties() {

		return delegate.getProperties();
	}

	@Override
	public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
		delegate.lock(arg0, arg1, arg2);

	}

	@Override
	public void refresh(Object arg0, Map<String, Object> arg1) {
		delegate.refresh(arg0, arg1);

	}

	@Override
	public void refresh(Object arg0, LockModeType arg1) {
		delegate.refresh(arg0, arg1);

	}

	@Override
	public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
		delegate.refresh(arg0, arg1, arg2);

	}

	@Override
	public void setProperty(String arg0, Object arg1) {
		delegate.setProperty(arg0, arg1);

	}

	@Override
	public <T> T unwrap(Class<T> arg0) {

		return delegate.unwrap(arg0);
	}
}