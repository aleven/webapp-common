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

import it.attocchi.jpa2.JPAEntityFilter;
import it.attocchi.jpa2.JpaController;
import it.attocchi.jsf2.PageBaseAuth;
import it.attocchi.utils.Crono;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Mirco
 *
 * @param <T>
 * @param <F>
 */
public abstract class PageBaseList<T extends Serializable, F extends JPAEntityFilter<T>> extends PageBaseAuth {

	protected List<T> elenco;
	protected F filtro;
	protected Class<T> clazz;
	protected long count;
	protected String persistentUnit;

	public List<T> getElenco() {
		return elenco;
	}

	public void setElenco(List<T> elenco) {
		this.elenco = elenco;
	}

	public F getFiltro() {
		return filtro;
	}

	public void setFiltro(F filtro) {
		this.filtro = filtro;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	protected void initLogged() throws Exception {

		// Lo Spostiamo prima di fare la verfica del login altrimenti abbiamo il
		// filtro che e' a null
		inizializeMembers();

		Crono.start("onPreLoadData");
		onPreLoadData();
		Crono.stopAndLogDebug("onPreLoadData", logger);

		Crono.start("loadData");
		loadData();
		Crono.stopAndLogDebug("loadData", logger);

		Crono.start("onPostLoadData");
		onPostLoadData();
		Crono.stopAndLogDebug("onPostLoadData", logger);

		// postInit();
	}

	protected void loadData() throws Exception {

		if (StringUtils.isNotBlank(persistentUnit))
			elenco = JpaController.callFindPU(persistentUnit, clazz, filtro);
		else
			elenco = JpaController.callFind(getEmfShared(), clazz, filtro);

		if (elenco == null || elenco.size() == 0) {
			addErrorMessage("Nessun Dato");
		} else {
			// count = elenco.size();
			if (StringUtils.isNotBlank(persistentUnit))
				count = JpaController.callCountPU(persistentUnit, clazz, filtro);
			else
				count = JpaController.callCount(getEmfShared(), clazz, filtro);

		}
	}

	protected void loadDataException() {
		try {
			loadData();
			onPostLoadData();
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
	protected abstract void inizializeMembers() throws Exception;

	// abstract postInit();
	protected abstract void onPreLoadData() throws Exception;

	protected abstract void onPostLoadData() throws Exception;

	public String actionReload() {
		loadDataException();
		return "";
	}

	public void actionListenerReload(ActionEvent event) {
		loadDataException();
	}
}
