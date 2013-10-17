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
import it.attocchi.jsf2.PageBaseAuthString;
import it.attocchi.utils.Crono;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Mirco
 * 
 * @param <T>
 * @param <F>
 */
public abstract class PageBaseUUIDDetail<T extends IEntityUUID> extends PageBaseAuthString {

	protected T elemento;
	protected String uUID;
	protected Class<T> clazz;
	protected String persistentUnit;

	public T getElemento() {
		return elemento;
	}

	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public String getUUID() {
		return uUID;
	}

	public void setUUID(String uUID) {
		this.uUID = uUID;
	}

	@Override
	protected void init() throws Exception {

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

	protected void loadDataException() {
		try {
			loadData();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}

	/**
	 * 
	 * @param filtro
	 * @param clazz
	 */
	// protected abstract void inizializeMembers(F filtro, Class<T> clazz);
	// protected abstract void inizializeMembers(Class<T> clazz) throws
	// Exception;
	protected abstract void inizializeMembers() throws Exception;

	// abstract postInit();
	protected abstract void onPreLoadData() throws Exception;

	protected abstract void onPostLoadData() throws Exception;

	// protected String actionSave() {
	// try {
	// JpaController.callUpdate(getEmfShared(), elemento);
	// } catch (Exception ex) {
	// addErrorMessage(ex);
	// }
	// }

}
