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
abstract class EntityManagerFactoryProxyTest implements EntityManagerFactory {

	protected final EntityManagerFactory delegate;

	/**
	 * <p>Constructor for EntityManagerFactoryProxyTest.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 */
	protected EntityManagerFactoryProxyTest(EntityManagerFactory emf) {

		this.delegate = emf;
	}

	/**
	 * <p>createEntityManager.</p>
	 *
	 * @return a {@link javax.persistence.EntityManager} object.
	 */
	public EntityManager createEntityManager() {

		return delegate.createEntityManager();
	}

	/** {@inheritDoc} */
	public EntityManager createEntityManager(Map map) {

		return delegate.createEntityManager(map);
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
	 * <p>close.</p>
	 */
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
