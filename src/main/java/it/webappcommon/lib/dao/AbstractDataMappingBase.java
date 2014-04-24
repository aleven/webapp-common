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

public abstract class AbstractDataMappingBase implements Cloneable, Serializable {

	protected static Logger logger = Logger.getLogger(AbstractDataMappingBase.class.getName());

	//
	protected Date dataCreazione;
	protected Date dataModifica;
	protected Date dataCancellazione;
	//
	protected int idUtenteCreazione;
	protected int idUtenteModifica;
	protected int idUtenteCancellazione;

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public int getIdUtenteCreazione() {
		return idUtenteCreazione;
	}

	public void setIdUtenteCreazione(int idUtenteCreazione) {
		this.idUtenteCreazione = idUtenteCreazione;
	}

	public int getIdUtenteModifica() {
		return idUtenteModifica;
	}

	public void setIdUtenteModifica(int idUtenteModifica) {
		this.idUtenteModifica = idUtenteModifica;
	}

	public int getIdUtenteCancellazione() {
		return idUtenteCancellazione;
	}

	public void setIdUtenteCancellazione(int idUtenteCancellazione) {
		this.idUtenteCancellazione = idUtenteCancellazione;
	}

	/** Creates a new instance of DataMappingBase */
	public AbstractDataMappingBase() {
	}

	/*
	 * Properieta' utili per JSF
	 */
	protected boolean uiSelezionato;
	protected boolean uiUtentePrivilegiato;

	public boolean isUiSelezionato() {
		return uiSelezionato;
	}

	public void setUiSelezionato(boolean uiSelezionato) {
		this.uiSelezionato = uiSelezionato;
	}

	public boolean isUiUtentePrivilegiato() {
		return uiUtentePrivilegiato;
	}

	public void setUiUtentePrivilegiato(boolean uiUtentePrivilegiato) {
		this.uiUtentePrivilegiato = uiUtentePrivilegiato;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

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
