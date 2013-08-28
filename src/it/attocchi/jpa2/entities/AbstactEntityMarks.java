package it.attocchi.jpa2.entities;

import java.util.Date;

public abstract class AbstactEntityMarks extends EntityBase {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @return
	 */
	public abstract EntityMarks getEntityMarks();

	public abstract void setEntityMarks(EntityMarks entityMarks);

	public void markAsCreated(long idUtente) {
		init();
		getEntityMarks().utenteCreazioneId = idUtente;
		getEntityMarks().dataCreazione = new Date();

		markAsUpdated(idUtente);
	}

	public void markAsUpdated(long idUtente) {
		init();
		getEntityMarks().utenteModificaId = idUtente;
		getEntityMarks().dataModifica = new Date();
	}

	public void markAsDeleted(long idUtente) {
		init();
		getEntityMarks().utenteCancellazioneId = idUtente;
		getEntityMarks().dataCancellazione = new Date();
	}

	private void init() {
		if (getEntityMarks() == null) {
			setEntityMarks(new EntityMarks());
		}
	}

}
