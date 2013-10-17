/*
    Copyright (c) 2012 Mirco Attocchi
	
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

	public String getUuidUtenteCreazione() {
		return uuidUtenteCreazione;
	}

	public void setUuidUtenteCreazione(String uuidUtenteCreazione) {
		this.uuidUtenteCreazione = uuidUtenteCreazione;
	}

	public String getUuidUtenteModifica() {
		return uuidUtenteModifica;
	}

	public void setUuidUtenteModifica(String uuidUtenteModifica) {
		this.uuidUtenteModifica = uuidUtenteModifica;
	}

	public String getUuidUtenteCancellazione() {
		return uuidUtenteCancellazione;
	}

	public void setUuidUtenteCancellazione(String uuidUtenteCancellazione) {
		this.uuidUtenteCancellazione = uuidUtenteCancellazione;
	}

}
