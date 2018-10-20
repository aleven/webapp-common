package it.attocchi.jpa2.entities;

import it.attocchi.jsf2.exceptions.ValidationException;

/**
 * Interfaccia identificazione Entity che richiedono Validazione
 */
public interface IEntityWithValidation {

	/**
	 * metodo che effettua validazione dell'entity
	 * @param utentePrivilegiato identifica se utente correntemente loggato è identificato come ADMIN e/o GRUPPO GESTORE MODULO
	 * @return true se oggetto è validato
	 * @throws ValidationException eccezzione che identifica errore di validazione
	 */
	public boolean validate(boolean utentePrivilegiato) throws ValidationException;
}
