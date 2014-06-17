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

package it.webappcommon.lib.jpa.scooped;

import javax.persistence.EntityManager;

/**
 * 
 * @author puche
 */
public class LazyCloseEntityManagerTest extends EntityManagerProxyTest {

	private ILazyCloseListenerTest listener;

	public LazyCloseEntityManagerTest(EntityManager delegate) {

		super(delegate);
	}

	public void setLazyCloseListener(ILazyCloseListenerTest listener) {

		this.listener = listener;
	}

	public ILazyCloseListenerTest getLazyCloseListener() {

		return listener;
	}

	@Override
	public void close() {

	}

	protected void lazyClose() {

		super.close();
		if (listener != null)
			listener.lazilyClosed();
	}
}