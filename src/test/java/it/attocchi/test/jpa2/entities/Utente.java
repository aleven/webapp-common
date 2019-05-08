package it.attocchi.test.jpa2.entities;

import it.attocchi.jpa2.entities.AbstractEntityMarksWithIdLong;
import it.attocchi.jpa2.entities.EntityMarks;

public class Utente extends AbstractEntityMarksWithIdLong<Utente> {

	long id;

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public EntityMarks getEntityMarks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntityMarks(EntityMarks entityMarks) {
		// TODO Auto-generated method stub

	}

	public Utente(long id) {
		this.id = id;
	}
}
