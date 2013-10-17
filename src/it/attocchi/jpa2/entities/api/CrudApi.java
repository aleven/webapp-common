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
