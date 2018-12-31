/*
    Copyright (c) 2007,2014 Mirco Attocchi
	
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

package it.webappcommon.lib.jpa2;

import java.io.Serializable;

/**
 * <p>Abstract EntityWithId class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public abstract class EntityWithId implements Serializable {

	// public EntityWithId() {
	// super();
	// }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>getId.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public abstract Integer getId();

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (getId() != null ? getId().hashCode() : 0);
		return hash;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof EntityWithId)) {
			return false;
		}
		EntityWithId other = (EntityWithId) object;
		if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
			return false;
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return this.getClass().getCanonicalName() + "[id=" + getId() + "]";
	}

}
