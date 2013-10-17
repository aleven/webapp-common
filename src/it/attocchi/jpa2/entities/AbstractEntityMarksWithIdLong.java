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
