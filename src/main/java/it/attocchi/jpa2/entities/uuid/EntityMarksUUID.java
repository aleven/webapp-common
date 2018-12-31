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

package it.attocchi.jpa2.entities.uuid;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>EntityMarksUUID class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
@Embeddable
public class EntityMarksUUID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "mark_dt_creazione")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCreazione;

	@Column(name = "mark_ts_modifica")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataModifica;

	@Column(name = "mark_dt_cancellazione")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCancellazione;
	//
	@Column(name = "mark_uuid_utente_creazione")
	protected String uuidUtenteCreazione;

	@Column(name = "mark_uuid_utente_modifica")
	protected String uuidUtenteModifica;

	@Column(name = "mark_uuid_utente_cancellazione")
	protected String uuidUtenteCancellazione;

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
	 * <p>Getter for the field <code>uuidUtenteCreazione</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUuidUtenteCreazione() {
		return uuidUtenteCreazione;
	}

	/**
	 * <p>Setter for the field <code>uuidUtenteCreazione</code>.</p>
	 *
	 * @param uuidUtenteCreazione a {@link java.lang.String} object.
	 */
	public void setUuidUtenteCreazione(String uuidUtenteCreazione) {
		this.uuidUtenteCreazione = uuidUtenteCreazione;
	}

	/**
	 * <p>Getter for the field <code>uuidUtenteModifica</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUuidUtenteModifica() {
		return uuidUtenteModifica;
	}

	/**
	 * <p>Setter for the field <code>uuidUtenteModifica</code>.</p>
	 *
	 * @param uuidUtenteModifica a {@link java.lang.String} object.
	 */
	public void setUuidUtenteModifica(String uuidUtenteModifica) {
		this.uuidUtenteModifica = uuidUtenteModifica;
	}

	/**
	 * <p>Getter for the field <code>uuidUtenteCancellazione</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUuidUtenteCancellazione() {
		return uuidUtenteCancellazione;
	}

	/**
	 * <p>Setter for the field <code>uuidUtenteCancellazione</code>.</p>
	 *
	 * @param uuidUtenteCancellazione a {@link java.lang.String} object.
	 */
	public void setUuidUtenteCancellazione(String uuidUtenteCancellazione) {
		this.uuidUtenteCancellazione = uuidUtenteCancellazione;
	}

}
