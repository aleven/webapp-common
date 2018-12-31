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

package it.webappcommon.lib.jpa.scooped.multiple;

import it.webappcommon.lib.jsf.AbstractFiltro;

/**
 * <p>QueriesTest class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class QueriesTest {

	/**
	 * Creates a new instance of Queries
	 */
	public QueriesTest() {
	}

	/**
	 * <p>ServiceOrdersSearchNamedQuery.</p>
	 *
	 * @param filter a {@link it.webappcommon.lib.jsf.AbstractFiltro} object.
	 * @param count a boolean.
	 * @return a {@link java.lang.String} object.
	 */
	public static String ServiceOrdersSearchNamedQuery(AbstractFiltro filter, boolean count) {
		StringBuilder query = new StringBuilder();

		if (count) {
			query.append("SELECT count(o) from ServiceOrder o WHERE 1 = 1 ");
		} else {
			query.append("SELECT o from ServiceOrder o WHERE 1 = 1 ");
		}
		if (filter != null) {
			if (filter.getSemeRicerca() != null && !filter.getSemeRicerca().isEmpty()) {
				query.append(" AND (o._agent like :searchFilter OR o._city._name like :searchFilter OR ");
				query.append(" o._vehicle._model like :searchFilter OR o._notes like :searchFilter) ");
			}
		}
		if (filter != null && filter.getDataCreazione() != null) {
			query.append(" AND o._competencyDate = :competencyDate ");
		}
		if (filter != null && filter.getSort() != null) {
			query.append(" ORDER BY " + filter.getSort());
			if (filter.isAscending()) {
				query.append(" ASC ");
			} else {
				query.append(" DESC ");
			}
		}

		return query.toString();
	}

}
