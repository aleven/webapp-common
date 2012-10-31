package it.espressoft.jsf2.converter;

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
			res.add(new SelectItem(item.getCode(), item.getDescrizione()));
		}
		
//		for (Object o : aList) {
//			if (o instanceof ISelectItemConvertible) {
//				ISelectItemConvertible c = (ISelectItemConvertible) o;
//				res.add(new SelectItem(c.getCode(), c.getDescrizione()));
//			}
//		}

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
