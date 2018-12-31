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

package it.webappcommon.lib.jpa.scooped.multiple;

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

abstract class EntityManagerProxyTest implements EntityManager {

	protected final EntityManager delegate;

	/**
	 * <p>Constructor for EntityManagerProxyTest.</p>
	 *
	 * @param delegate a {@link javax.persistence.EntityManager} object.
	 */
	public EntityManagerProxyTest(EntityManager delegate) {

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
	public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
		return delegate.find(entityClass, primaryKey, properties);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
		return delegate.find(entityClass, primaryKey, lockMode);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
		return delegate.find(entityClass, primaryKey, lockMode, properties);
	}

	/** {@inheritDoc} */
	@Override
	public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		delegate.lock(entity, lockMode, properties);
	}

	/** {@inheritDoc} */
	@Override
	public void refresh(Object entity, Map<String, Object> properties) {
		delegate.refresh(entity, properties);
	}

	/** {@inheritDoc} */
	@Override
	public void refresh(Object entity, LockModeType lockMode) {
		delegate.refresh(entity, lockMode);
	}

	/** {@inheritDoc} */
	@Override
	public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		delegate.refresh(entity, lockMode, properties);
	}

	/** {@inheritDoc} */
	@Override
	public void detach(Object entity) {
		delegate.detach(entity);
	}

	/** {@inheritDoc} */
	@Override
	public LockModeType getLockMode(Object entity) {
		return delegate.getLockMode(entity);
	}

	/** {@inheritDoc} */
	@Override
	public void setProperty(String propertyName, Object value) {
		delegate.setProperty(propertyName, value);
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, Object> getProperties() {
		return delegate.getProperties();
	}

	/** {@inheritDoc} */
	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return delegate.createQuery(criteriaQuery);
	}

	/** {@inheritDoc} */
	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return delegate.createQuery(qlString, resultClass);
	}

	/** {@inheritDoc} */
	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		return delegate.createNamedQuery(name, resultClass);
	}

	/** {@inheritDoc} */
	@Override
	public <T> T unwrap(Class<T> cls) {
		return delegate.unwrap(cls);
	}

	/** {@inheritDoc} */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return delegate.getEntityManagerFactory();
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
}
