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

	@Column(name = "data_creazione")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCreazione;

	@Column(name = "data_modifica")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataModifica;

	@Column(name = "data_cancellazione")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCancellazione;

	@Column(name = "utente_creazione_id")
	protected long utenteCreazioneId;

	@Column(name = "utente_modifica_id")
	protected long utenteModificaId;

	@Column(name = "utente_cancellazione_id")
	protected long utenteCancellazioneId;

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

	public long getUtenteCreazioneId() {
		return utenteCreazioneId;
	}

	public void setUtenteCreazioneId(long utenteCreazioneId) {
		this.utenteCreazioneId = utenteCreazioneId;
	}

	public long getUtenteModificaId() {
		return utenteModificaId;
	}

	public void setUtenteModificaId(long utenteModificaId) {
		this.utenteModificaId = utenteModificaId;
	}

	public long getUtenteCancellazioneId() {
		return utenteCancellazioneId;
	}

	public void setUtenteCancellazioneId(long utenteCancellazioneId) {
		this.utenteCancellazioneId = utenteCancellazioneId;
	}

}
