package it.attocchi.jpa2.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class EntityMarks implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "dt_creazione")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCreazione;

	@Column(name = "ts_modifica")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataModifica;

	@Column(name = "dt_cancellazione")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCancellazione;
	//
	@Column(name = "id_utente_creazione")
	protected int idUtenteCreazione;

	@Column(name = "id_utente_modifica")
	protected int idUtenteModifica;

	@Column(name = "id_utente_cancellazione")
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

}
