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

package it.attocchi.jpa2.entities;

import java.util.Date;

import org.apache.log4j.Logger;

public abstract class AbstactEntityMarks extends EntityBase {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());
	
	// private static final long serialVersionUID = 1L;

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
	
	public void markAsUnDeleted(long idUtente) {
		init();
		getEntityMarks().utenteCancellazioneId = 0;
		getEntityMarks().dataCancellazione = null;
	}

	private void init() {
		if (getEntityMarks() == null) {
			setEntityMarks(new EntityMarks());
		}
	}

}
