package it.attocchi.jpa2.entities;

/**
 * An entity with markable attributes and id long
 * 
 * @author Mirco
 * 
 */
public abstract class AbstractEntityMarksWithIdLong extends AbstactEntityMarks implements IEntityWithIdLong, Comparable<Long> {

	public abstract long getId();

	// @Override
	// protected Object clone() throws CloneNotSupportedException {
	// return super.clone();
	// }

	@Override
	public int hashCode() {
		// return super.hashCode();
		return new Long(getId()).hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s[%s]", this.getClass().getName(), this.getId());
	}

	@Override
	public boolean equals(Object obj) {

		// return super.equals(obj);
		// return new Long(this.getId()).equals(obj);
		// return this.toString().equals(obj);

		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		if (obj instanceof AbstractEntityMarksWithIdLong) {
			AbstractEntityMarksWithIdLong cast = (AbstractEntityMarksWithIdLong) obj;
			return new Long(this.getId()).equals(cast.getId());
		} else
			return false;

		// http://javarevisited.blogspot.com/2011/02/how-to-write-equals-method-in-java.html#ixzz2bI4axUuS
	}

	@Override
	public int compareTo(Long o) {
		return new Long(this.getId()).compareTo(o);
	}
}
