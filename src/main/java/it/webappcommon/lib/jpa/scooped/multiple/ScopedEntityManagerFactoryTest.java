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

/**
 * <p>ScopedEntityManagerFactoryTest class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class ScopedEntityManagerFactoryTest extends EntityManagerFactoryProxyTest implements ILazyCloseListenerTest {

	private final ThreadLocal<LazyCloseEntityManagerTest> threadLocal;

	/**
	 * <p>Constructor for ScopedEntityManagerFactoryTest.</p>
	 *
	 * @param emf a {@link javax.persistence.EntityManagerFactory} object.
	 */
	protected ScopedEntityManagerFactoryTest(EntityManagerFactory emf) {

		super(emf);
		this.threadLocal = new ThreadLocal<LazyCloseEntityManagerTest>();
	}

	/** {@inheritDoc} */
	public EntityManager createEntityManager(Map map) {

		LazyCloseEntityManagerTest em = threadLocal.get();
		if (em == null) {
			em = new LazyCloseEntityManagerTest(super.createEntityManager(map));
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

		LazyCloseEntityManagerTest em = threadLocal.get();
		if (em == null) {
			em = new LazyCloseEntityManagerTest(super.createEntityManager());
			createEntityManager(em);
		}
		return em;
	}

	private void createEntityManager(LazyCloseEntityManagerTest em) {

		threadLocal.set(em);
		em.setLazyCloseListener(this);
	}

	/**
	 * <p>getEntityManager.</p>
	 *
	 * @return a {@link it.webappcommon.lib.jpa.scooped.multiple.LazyCloseEntityManagerTest} object.
	 */
	protected LazyCloseEntityManagerTest getEntityManager() {

		return threadLocal.get();
	}

	/**
	 * <p>lazilyClosed.</p>
	 */
	public void lazilyClosed() {

		threadLocal.set(null);
	}
}
