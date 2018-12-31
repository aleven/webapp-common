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

package it.attocchi.jpa2.entities;

/**
 * An entity with id String
 *
 * @author Mirco
 * @version $Id: $Id
 */
public abstract class AbstractEntityWithIdString extends EntityBase implements IEntityWithIdString {

	/**
	 * <p>getId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public abstract String getId();

	/** {@inheritDoc} */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("%s[%s]", this.getClass().getName(), this.getId());
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
	    if (this == object) return true;
	    if (object == null || this.getClass() != object.getClass()) return false;
	    final AbstractEntityWithIdString other = (AbstractEntityWithIdString) object;
	    if (this.getId() == null || other.getId() == null) return false;
		return this.getId().equals(other.getId());
	}

}
