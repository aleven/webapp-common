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
 * <p>Abstract PageBaseList class.</p>
 *
 * @author Mirco
 * @param <T> Serializable
 * @param <F> JPAEntityFilter
 * @version $Id: $Id
 */
public abstract class PageBaseList<T extends Serializable, F extends JPAEntityFilter<T>> extends PageBaseAuth {

	protected List<T> elenco;
	protected F filtro;
	protected Class<T> clazz;
	protected long count;
	protected String persistentUnit;

	/**
	 * <p>Getter for the field <code>elenco</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<T> getElenco() {
		return elenco;
	}

	/**
	 * <p>Setter for the field <code>elenco</code>.</p>
	 *
	 * @param elenco a {@link java.util.List} object.
	 */
	public void setElenco(List<T> elenco) {
		this.elenco = elenco;
	}

	/**
	 * <p>Getter for the field <code>filtro</code>.</p>
	 *
	 * @return a F object.
	 */
	public F getFiltro() {
		return filtro;
	}

	/**
	 * <p>Setter for the field <code>filtro</code>.</p>
	 *
	 * @param filtro a F object.
	 */
	public void setFiltro(F filtro) {
		this.filtro = filtro;
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
	 * <p>Getter for the field <code>count</code>.</p>
	 *
	 * @return a long.
	 */
	public long getCount() {
		return count;
	}

	/**
	 * <p>Setter for the field <code>count</code>.</p>
	 *
	 * @param count a long.
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/** {@inheritDoc} */
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

	/**
	 * <p>loadData.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
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

	/**
	 * <p>loadDataException.</p>
	 */
	protected void loadDataException() {
		try {
			loadData();
			onPostLoadData();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}

	// protected abstract void inizializeMembers(F filtro, Class<T> clazz);
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
	 * <p>actionReload.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String actionReload() {
		loadDataException();
		return "";
	}

	/**
	 * <p>actionListenerReload.</p>
	 *
	 * @param event a {@link javax.faces.event.ActionEvent} object.
	 */
	public void actionListenerReload(ActionEvent event) {
		loadDataException();
	}
}
