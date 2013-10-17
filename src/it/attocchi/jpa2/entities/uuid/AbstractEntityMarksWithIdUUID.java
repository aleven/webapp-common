package it.attocchi.jpa2.entities.uuid;

/**
 * An entity with markable attributes and id long
 * 
 * @author Mirco
 * 
 */
public abstract class AbstractEntityMarksWithIdUUID extends AbstactEntityMarksUUID implements IEntityUUID {

	public abstract String getUuid();

	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	@Override
	public int hashCode() {
		// return this.getUuid().hashCode();
		int hash = 0;
		hash += (getUuid() != null ? getUuid().hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return String.format("%s[%s]", this.getClass().getName(), this.getUuid());
	}

	@Override
	public boolean equals(Object object) {
		// return super.equals(obj);
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof IEntityUUID)) {
			return false;
		}
		IEntityUUID other = (IEntityUUID) object;
		if ((this.getUuid() == null && other.getUuid() != null) || (this.getUuid() != null && !this.getUuid().equals(other.getUuid()))) {
			return false;
		}
		return true;
	}

}
