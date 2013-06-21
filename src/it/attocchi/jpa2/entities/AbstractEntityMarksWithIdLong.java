package it.attocchi.jpa2.entities;

/**
 * An entity with markable attributes and id long
 * 
 * @author Mirco
 * 
 */
public abstract class AbstractEntityMarksWithIdLong extends AbstactEntityMarks implements IEntityWithIdLong {

	public abstract long getId();

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s[%s]", this.getClass().getName(), this.getId());
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}
