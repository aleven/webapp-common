package it.attocchi.jpa2.entities;

import it.attocchi.jsf2.exceptions.ValidationException;

public interface IEntityWithValidation {
	public boolean validate() throws ValidationException;
}
