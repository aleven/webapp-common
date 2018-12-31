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

package it.webappcommon.lib.dao;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * <p>Abstract AbstractDataMappingBase class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public abstract class AbstractDataMappingBase implements Cloneable, Serializable {

	/** Constant <code>logger</code> */
	protected static Logger logger = Logger.getLogger(AbstractDataMappingBase.class.getName());

	//
	protected Date dataCreazione;
	protected Date dataModifica;
	protected Date dataCancellazione;
	//
	protected int idUtenteCreazione;
	protected int idUtenteModifica;
	protected int idUtenteCancellazione;

	/**
	 * <p>Getter for the field <code>dataCreazione</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getDataCreazione() {
		return dataCreazione;
	}

	/**
	 * <p>Setter for the field <code>dataCreazione</code>.</p>
	 *
	 * @param dataCreazione a {@link java.util.Date} object.
	 */
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/**
	 * <p>Getter for the field <code>dataModifica</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getDataModifica() {
		return dataModifica;
	}

	/**
	 * <p>Setter for the field <code>dataModifica</code>.</p>
	 *
	 * @param dataModifica a {@link java.util.Date} object.
	 */
	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	/**
	 * <p>Getter for the field <code>dataCancellazione</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	/**
	 * <p>Setter for the field <code>dataCancellazione</code>.</p>
	 *
	 * @param dataCancellazione a {@link java.util.Date} object.
	 */
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	/**
	 * <p>Getter for the field <code>idUtenteCreazione</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIdUtenteCreazione() {
		return idUtenteCreazione;
	}

	/**
	 * <p>Setter for the field <code>idUtenteCreazione</code>.</p>
	 *
	 * @param idUtenteCreazione a int.
	 */
	public void setIdUtenteCreazione(int idUtenteCreazione) {
		this.idUtenteCreazione = idUtenteCreazione;
	}

	/**
	 * <p>Getter for the field <code>idUtenteModifica</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIdUtenteModifica() {
		return idUtenteModifica;
	}

	/**
	 * <p>Setter for the field <code>idUtenteModifica</code>.</p>
	 *
	 * @param idUtenteModifica a int.
	 */
	public void setIdUtenteModifica(int idUtenteModifica) {
		this.idUtenteModifica = idUtenteModifica;
	}

	/**
	 * <p>Getter for the field <code>idUtenteCancellazione</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIdUtenteCancellazione() {
		return idUtenteCancellazione;
	}

	/**
	 * <p>Setter for the field <code>idUtenteCancellazione</code>.</p>
	 *
	 * @param idUtenteCancellazione a int.
	 */
	public void setIdUtenteCancellazione(int idUtenteCancellazione) {
		this.idUtenteCancellazione = idUtenteCancellazione;
	}

	/**
	 * Creates a new instance of DataMappingBase
	 */
	public AbstractDataMappingBase() {
	}

	/*
	 * Properieta' utili per JSF
	 */
	protected boolean uiSelezionato;
	protected boolean uiUtentePrivilegiato;

	/**
	 * <p>isUiSelezionato.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isUiSelezionato() {
		return uiSelezionato;
	}

	/**
	 * <p>Setter for the field <code>uiSelezionato</code>.</p>
	 *
	 * @param uiSelezionato a boolean.
	 */
	public void setUiSelezionato(boolean uiSelezionato) {
		this.uiSelezionato = uiSelezionato;
	}

	/**
	 * <p>isUiUtentePrivilegiato.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isUiUtentePrivilegiato() {
		return uiUtentePrivilegiato;
	}

	/**
	 * <p>Setter for the field <code>uiUtentePrivilegiato</code>.</p>
	 *
	 * @param uiUtentePrivilegiato a boolean.
	 */
	public void setUiUtentePrivilegiato(boolean uiUtentePrivilegiato) {
		this.uiUtentePrivilegiato = uiUtentePrivilegiato;
	}

	/** {@inheritDoc} */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * <p>introspect.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 * @throws java.lang.Exception if any.
	 */
	public Map<String, Object> introspect() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		BeanInfo info = Introspector.getBeanInfo(this.getClass());
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			Method reader = pd.getReadMethod();
			if (reader != null)
				result.put(pd.getName(), reader.invoke(this));
		}
		return result;
	}
}
