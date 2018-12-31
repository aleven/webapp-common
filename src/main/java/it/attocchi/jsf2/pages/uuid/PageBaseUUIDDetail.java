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

package it.attocchi.jsf2.pages.uuid;

import it.attocchi.jpa2.JpaController;
import it.attocchi.jpa2.entities.uuid.IEntityUUID;
import it.attocchi.jsf2.PageBaseAuth;
import it.attocchi.utils.Crono;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Abstract PageBaseUUIDDetail class.</p>
 *
 * @author Mirco
 * @param <T> IEntityUUID
 * @version $Id: $Id
 */
public abstract class PageBaseUUIDDetail<T extends IEntityUUID> extends PageBaseAuth {

	protected T elemento;
	protected String uUID;
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
	 * <p>Getter for the field <code>uUID</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUUID() {
		return uUID;
	}

	/**
	 * <p>Setter for the field <code>uUID</code>.</p>
	 *
	 * @param uUID a {@link java.lang.String} object.
	 */
	public void setUUID(String uUID) {
		this.uUID = uUID;
	}

	/** {@inheritDoc} */
	@Override
	protected void initLogged() throws Exception {

		uUID = getParamObject("uuid");

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

		if (uUID != null && !uUID.isEmpty()) {
			if (StringUtils.isNotBlank(persistentUnit))
				elemento = JpaController.callFindByUUIDPU(persistentUnit, clazz, uUID);
			else
				elemento = JpaController.callFindById(getEmfShared(), clazz, uUID);

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

	// protected String actionSave() {
	// try {
	// JpaController.callUpdate(getEmfShared(), elemento);
	// } catch (Exception ex) {
	// addErrorMessage(ex);
	// }
	// }

}
