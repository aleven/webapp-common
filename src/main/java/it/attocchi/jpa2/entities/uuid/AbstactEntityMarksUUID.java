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

import it.attocchi.jpa2.entities.EntityBase;

import java.util.Date;

/**
 * <p>Abstract AbstactEntityMarksUUID class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public abstract class AbstactEntityMarksUUID extends EntityBase {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>getEntityMarks.</p>
	 *
	 * @return a {@link it.attocchi.jpa2.entities.uuid.EntityMarksUUID} object.
	 */
	public abstract EntityMarksUUID getEntityMarks();

	/**
	 * <p>setEntityMarks.</p>
	 *
	 * @param entityMarks a {@link it.attocchi.jpa2.entities.uuid.EntityMarksUUID} object.
	 */
	public abstract void setEntityMarks(EntityMarksUUID entityMarks);

	/**
	 * <p>markAsCreated.</p>
	 *
	 * @param uuidUtente a {@link java.lang.String} object.
	 */
	public void markAsCreated(String uuidUtente) {
		init();
		getEntityMarks().uuidUtenteCreazione = uuidUtente;
		getEntityMarks().dataCreazione = new Date();

		markAsUpdated(uuidUtente);
	}

	/**
	 * <p>markAsUpdated.</p>
	 *
	 * @param uuidUtente a {@link java.lang.String} object.
	 */
	public void markAsUpdated(String uuidUtente) {
		init();
		getEntityMarks().uuidUtenteModifica = uuidUtente;
		getEntityMarks().dataModifica = new Date();
	}

	/**
	 * <p>markAsDeleted.</p>
	 *
	 * @param uuidUtente a {@link java.lang.String} object.
	 */
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
