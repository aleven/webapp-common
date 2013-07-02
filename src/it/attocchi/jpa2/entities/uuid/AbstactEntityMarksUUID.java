package it.attocchi.jpa2.entities.uuid;

import it.attocchi.jpa2.entities.EntityBase;

import java.util.Date;

public abstract class AbstactEntityMarksUUID extends EntityBase {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @return
	 */
	public abstract EntityMarksUUID getEntityMarks();

	public abstract void setEntityMarks(EntityMarksUUID entityMarks);

	public void markAsCreated(String uuidUtente) {
		init();
		getEntityMarks().uuidUtenteCreazione = uuidUtente;
		getEntityMarks().dataCreazione = new Date();

		markAsUpdated(uuidUtente);
	}

	public void markAsUpdated(String uuidUtente) {
		init();
		getEntityMarks().uuidUtenteModifica = uuidUtente;
		getEntityMarks().dataModifica = new Date();
	}

	public void markAsDeleted(String uuidUtente) {
		init();
		getEntityMarks().uuidUtenteCancellazione = uuidUtente;
		getEntityMarks().dataCancellazione = new Date();
	}

	private void init() {
		if (getEntityMarks() == null) {
			setEntityMarks(new EntityMarksUUID());
		}
	}
}
