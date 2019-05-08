package it.attocchi.test.jpa2.entities;

import it.attocchi.jpa2.entities.AbstractEntityMarksWithIdLong;
import it.attocchi.jpa2.entities.EntityMarks;

public class Gruppo extends AbstractEntityMarksWithIdLong<Gruppo> {

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
	
	public Gruppo(long id) {
		this.id = id;
	}

}
