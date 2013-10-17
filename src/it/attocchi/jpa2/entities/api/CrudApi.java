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

package it.attocchi.jpa2.entities.api;

import it.attocchi.jpa2.entities.IEntityWithIdLong;

import org.apache.log4j.Logger;

// public class CrudApi<T extends IEntityWithIdLong, C extends Class<T>> {
public class CrudApi<T extends IEntityWithIdLong> {

	protected static final Logger logger = Logger.getLogger(CrudApi.class.getName());

	// <U extends AbstractEntityMarksWithIdLong> {

	// public <T extends AbstractEntityMarksWithIdLong> T
	// salva(EntityManagerFactory emf, U utenteLoggato, T contattoDaSalvare)
	// throws Exception {
	//
	// contattoDaSalvare.markAsUpdated(utenteLoggato.getId());
	//
	// if (contattoDaSalvare.getId() > 0)
	// JpaController.callUpdate(emf, contattoDaSalvare);
	// else
	// JpaController.callInsert(emf, contattoDaSalvare);
	//
	// return JpaController.callFindById(emf, T.class,
	// contattoDaSalvare.getId());
	// }

	// public T cerca(EntityManagerFactory emf, long clienteId) throws Exception
	// {
	// C clazz;
	// T res = JpaController.callFindById(emf, clazz.getClass(), clienteId);
	// return res;
	// }
}
