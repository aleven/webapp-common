package it.espressoft.jsf2.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class SelectItemConverter {

	// public static List<SelectItem> convert(List<ISelectItemConvertible>
	// aList) throws Exception {
	public static List<SelectItem> convert(List aList) throws Exception {
		List<SelectItem> res = new ArrayList<SelectItem>();

		// for (ISelectItemConvertible o : aList) {
		for (Object o : aList) {
			if (o instanceof ISelectItemConvertible) {
				ISelectItemConvertible c = (ISelectItemConvertible) o;
				res.add(new SelectItem(c.getCode(), c.getDescrizione()));
			}
		}

		return res;
	}
}
