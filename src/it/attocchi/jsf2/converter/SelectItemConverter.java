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

package it.attocchi.jsf2.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

public class SelectItemConverter {

	// public static List<SelectItem> convert(List<ISelectItemConvertible>
	// aList) throws Exception {
	public static <T extends ISelectItemConvertible> List<SelectItem> convert(List<T> aList) throws Exception {
		List<SelectItem> res = new ArrayList<SelectItem>();

		for (ISelectItemConvertible item : aList) {
			// res.add(new SelectItem(item.getCode(), item.getDescrizione()));
			res.add(new SelectItem(item.getItemValue(), item.getItemLabel()));
		}

		// for (Object o : aList) {
		// if (o instanceof ISelectItemConvertible) {
		// ISelectItemConvertible c = (ISelectItemConvertible) o;
		// res.add(new SelectItem(c.getCode(), c.getDescrizione()));
		// }
		// }

		return res;
	}

	public static List<SelectItem> convertFromStrings(Collection<String> aList, boolean sort) throws Exception {
		List<SelectItem> res = new ArrayList<SelectItem>();

		List<String> tmp = new ArrayList<String>();
		tmp.addAll(aList);

		if (sort)
			Collections.sort(tmp);

		// for (ISelectItemConvertible o : aList) {
		for (String s : tmp) {
			res.add(new SelectItem(s));
		}

		return res;
	}

}
