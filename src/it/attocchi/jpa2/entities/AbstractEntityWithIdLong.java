package it.attocchi.jpa2.entities;

/**
 * An entity with id long
 * @author Mirco
 *
 */
public abstract class AbstractEntityWithIdLong extends EntityBase implements IEntityWithIdLong {

	public abstract long getId();

	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	@Override
	public int hashCode() {
		
		return super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s[%s]", this.getClass().getName(), this.getId());
	}

	@Override
	public boolean equals(Object obj) {
		
		return super.equals(obj);
	}

}
