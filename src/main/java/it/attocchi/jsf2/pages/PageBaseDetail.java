/*
    Copyright (c) 2012,2013 Mirco Attocchi
	
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

package it.attocchi.jsf2.pages;

import it.attocchi.jpa2.JpaController;
import it.attocchi.jpa2.entities.IEntityWithIdLong;
import it.attocchi.jsf2.PageBaseAuth;
import it.attocchi.utils.Crono;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Abstract PageBaseDetail class.</p>
 *
 * @author Mirco
 * @param <T> IEntityWithIdLong
 * @version $Id: $Id
 */
public abstract class PageBaseDetail<T extends IEntityWithIdLong> extends PageBaseAuth {

	protected T elemento;
	protected long id;
	protected Class<T> clazz;
	protected String persistentUnit;

	/**
	 * <p>Getter for the field <code>elemento</code>.</p>
	 *
	 * @return a T object.
	 */
	public T getElemento() {
		return elemento;
	}

	/**
	 * <p>Setter for the field <code>elemento</code>.</p>
	 *
	 * @param elemento a T object.
	 */
	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	/**
	 * <p>Getter for the field <code>clazz</code>.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	public Class<T> getClazz() {
		return clazz;
	}

	/**
	 * <p>Setter for the field <code>clazz</code>.</p>
	 *
	 * @param clazz a {@link java.lang.Class} object.
	 */
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a long.
	 */
	public long getId() {
		return id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a long.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/** {@inheritDoc} */
	@Override
	protected void initLogged() throws Exception {

		id = getParamObjectAsLong("id");

		inizializeMembers(); // Spostato da qui prima del login

		Crono.start("onPreLoadData");
		onPreLoadData();
		logger.debug(Crono.stopAndLog("onPreLoadData"));

		Crono.start("loadData");
		loadData();
		logger.debug(Crono.stopAndLog("loadData"));

		Crono.start("onPostLoadData");
		onPostLoadData();
		logger.debug(Crono.stopAndLog("onPostLoadData"));

		// postInit();
	}

	/**
	 * <p>loadData.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected void loadData() throws Exception {

		if (id > 0) {
			if (StringUtils.isNotBlank(persistentUnit)) {
				elemento = JpaController.callFindByIdPU(persistentUnit, clazz, id);
			} else {
				elemento = JpaController.callFindById(getEmfShared(), clazz, id);
			}
			if (elemento == null) {
				addErrorMessage("Nessun Dato");
			}
		}

	}

	/**
	 * <p>loadDataException.</p>
	 */
	protected void loadDataException() {
		try {
			loadData();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}

	// protected abstract void inizializeMembers(F filtro, Class<T> clazz);
	// protected abstract void inizializeMembers(Class<T> clazz) throws
	// Exception;
	/**
	 * <p>inizializeMembers.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract void inizializeMembers() throws Exception;

	// abstract postInit();
	/**
	 * <p>onPreLoadData.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract void onPreLoadData() throws Exception;

	/**
	 * <p>onPostLoadData.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract void onPostLoadData() throws Exception;

	/**
	 * <p>doActionSave.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected void doActionSave() throws Exception {

		if (elemento.getId() > 0) {
			if (StringUtils.isNotBlank(persistentUnit)) {
				JpaController.callUpdatePU(persistentUnit, elemento);
			} else {
				JpaController.callUpdate(getEmfShared(), elemento);
			}
		} else {
			if (StringUtils.isNotBlank(persistentUnit)) {
				JpaController.callInsertPU(persistentUnit, elemento);
			} else {
				JpaController.callInsert(getEmfShared(), elemento);
			}
		}

	}
}
