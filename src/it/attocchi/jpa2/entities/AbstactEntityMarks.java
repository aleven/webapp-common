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

	public void markAsCreated(int idUtente) {
		init();
		getEntityMarks().idUtenteCreazione = idUtente;
		getEntityMarks().dataCreazione = new Date();

		markAsUpdated(idUtente);
	}

	public void markAsUpdated(int idUtente) {
		init();
		getEntityMarks().idUtenteModifica = idUtente;
		getEntityMarks().dataModifica = new Date();
	}

	public void markAsDeleted(int idUtente) {
		init();
		getEntityMarks().idUtenteCancellazione = idUtente;
		getEntityMarks().dataCancellazione = new Date();
	}

	private void init() {
		if (getEntityMarks() == null) {
			setEntityMarks(new EntityMarks());
		}
	}

}
