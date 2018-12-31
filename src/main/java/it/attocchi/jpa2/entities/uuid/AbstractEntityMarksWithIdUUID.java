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

/**
 * An entity with markable attributes and id long
 *
 * @author Mirco
 * @version $Id: $Id
 */
public abstract class AbstractEntityMarksWithIdUUID extends AbstactEntityMarksUUID implements IEntityUUID {

	/**
	 * <p>getUuid.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public abstract String getUuid();

	/** {@inheritDoc} */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		// return this.getUuid().hashCode();
		int hash = 0;
		hash += (getUuid() != null ? getUuid().hashCode() : 0);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("%s[%s]", this.getClass().getName(), this.getUuid());
	}

	/** {@inheritDoc} */
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
