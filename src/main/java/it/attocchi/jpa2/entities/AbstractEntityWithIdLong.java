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
 * An entity with id long
 *
 * @author Mirco
 * @version $Id: $Id
 */
public abstract class AbstractEntityWithIdLong extends EntityBase implements IEntityWithIdLong {

	/**
	 * <p>getId.</p>
	 *
	 * @return a long.
	 */
	public abstract long getId();

	/** {@inheritDoc} */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return new Long(getId()).hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("%s[%s]", this.getClass().getName(), this.getId());
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
