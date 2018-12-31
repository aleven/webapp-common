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

package it.webappcommon.lib.jsf;

import it.attocchi.jsf2.converter.ISelectItemConvertible1;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * <p>JSFHelper class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class JSFHelper {

	/**
	 * Convert a list of ISelectItemConvertible to a list of SelectItem
	 *
	 * @param list
	 *            a list of ISelectItemConvertible object
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public static List<SelectItem> listToSelectItems(List list) throws Exception {
		List<SelectItem> retVal = null;
		try {
			if (list != null) {
				retVal = new ArrayList<SelectItem>();
				for (Object obj : list) {

					if (obj instanceof ISelectItemConvertible1) {

						ISelectItemConvertible1 selectItemConvertible = (ISelectItemConvertible1) obj;

						SelectItem selectItem = new SelectItem(selectItemConvertible.getItemValue(), selectItemConvertible.getItemLabel());
						// if (selectItemConvertible.isDefault()) {
						// selectItem.
						// }

						retVal.add(selectItem);
					}
					// PropertyDescriptor pdValue = new PropertyDescriptor(
					// itemValue, obj.getClass());
					// Method methodValue = pdValue.getReadMethod();
					// Object methodValueRes = methodValue.invoke(obj);
					//
					// PropertyDescriptor pdLabel = new PropertyDescriptor(
					// itemLabel, obj.getClass());
					// Method methodLabel = pdLabel.getReadMethod();
					// Object methodLabelRes = methodLabel.invoke(obj);
					// if (methodValueRes == null) {
					// methodValueRes = "";
					// }
					// if (methodLabelRes == null) {
					// methodLabelRes = "";
					// }
					// retVal.add(new SelectItem(methodValueRes.toString(),
					// methodLabelRes.toString()));

					// pdValue = null;
					// methodValue = null;
					// methodValueRes = null;
					//
					// pdLabel = null;
					// methodLabel = null;
					// methodLabelRes = null;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return retVal;
	}

	/**
	 * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	 * <tt>ArrayList</tt> of <tt>SelectedItem</tt>.
	 *
	 * @param list a {@link java.util.List} object.
	 * @param itemValue a {@link java.lang.String} object.
	 * @param itemLabel a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public static List<SelectItem> listToSelectItems(List list, String itemValue, String itemLabel) throws Exception {
		List<SelectItem> retVal = null;
		try {
			if (list != null) {
				retVal = new ArrayList<SelectItem>();
				for (Object obj : list) {
					PropertyDescriptor pdValue = new PropertyDescriptor(itemValue, obj.getClass());
					Method methodValue = pdValue.getReadMethod();
					Object methodValueRes = methodValue.invoke(obj);

					PropertyDescriptor pdLabel = new PropertyDescriptor(itemLabel, obj.getClass());
					Method methodLabel = pdLabel.getReadMethod();
					Object methodLabelRes = methodLabel.invoke(obj);
					if (methodValueRes == null) {
						methodValueRes = "";
					}
					if (methodLabelRes == null) {
						methodLabelRes = "";
					}
					retVal.add(new SelectItem(methodValueRes.toString(), methodLabelRes.toString()));

					pdValue = null;
					methodValue = null;
					methodValueRes = null;

					pdLabel = null;
					methodLabel = null;
					methodLabelRes = null;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return retVal;
	}

	/**
	 * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	 * <tt>ArrayList</tt> of <tt>SelectedItem</tt>. This method allow to
	 * concatenate more than one Object Property.
	 *
	 * @param list a {@link java.util.List} object.
	 * @param itemValue a {@link java.lang.String} object.
	 * @param itemLabel an array of {@link java.lang.String} objects.
	 * @param separatorForLabel a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	protected List<SelectItem> listToSelectItems(List list, String itemValue, String[] itemLabel, String separatorForLabel) throws Exception {
		List<SelectItem> retVal = null;
		StringBuilder sumLabel = null;

		try {
			if (list != null) {
				retVal = new ArrayList<SelectItem>();
				for (Object obj : list) {

					PropertyDescriptor pdValue = new PropertyDescriptor(itemValue, obj.getClass());
					Method methodValue = pdValue.getReadMethod();
					Object methodValueRes = methodValue.invoke(obj);

					sumLabel = new StringBuilder();
					for (String str : itemLabel) {
						PropertyDescriptor pdLabel = new PropertyDescriptor(str, obj.getClass());
						Method methodLabel = pdLabel.getReadMethod();
						Object methodLabelRes = methodLabel.invoke(obj);
						if (methodLabelRes == null) {
							methodLabelRes = "";
						}
						if (sumLabel.length() > 0) {
							sumLabel.append(separatorForLabel);
							sumLabel.append(methodLabelRes.toString());
						} else {
							sumLabel.append(methodLabelRes.toString());
						}

						str = null;

						pdLabel = null;
						methodLabel = null;
						methodLabelRes = null;
					}
					if (methodValueRes == null) {
						methodValueRes = "";
					}

					retVal.add(new SelectItem(methodValueRes.toString(), sumLabel.toString()));

					sumLabel = null;

					pdValue = null;
					methodValue = null;
					methodValueRes = null;
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return retVal;
	}

	/**
	 * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	 * <tt>ArrayList</tt> of <tt>SelectedItem</tt>.
	 *
	 * @param list a {@link java.util.List} object.
	 * @param itemLabel a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	protected List<SelectItem> listToSelectItems(List list, String itemLabel) throws Exception {
		List<SelectItem> retVal = null;
		try {
			if (list != null) {
				retVal = new ArrayList<SelectItem>();
				for (Object obj : list) {
					// PropertyDescriptor pdValue = new
					// PropertyDescriptor(itemValue, obj.getClass());
					// Method methodValue = pdValue.getReadMethod();
					// Object methodValueRes = methodValue.invoke(obj);

					PropertyDescriptor pdLabel = new PropertyDescriptor(itemLabel, obj.getClass());
					Method methodLabel = pdLabel.getReadMethod();
					Object methodLabelRes = methodLabel.invoke(obj);
					// if (methodValueRes == null) {
					// methodValueRes = "";
					// }
					if (methodLabelRes == null) {
						methodLabelRes = "";
					}
					retVal.add(new SelectItem(obj, methodLabelRes.toString()));

					// pdValue = null;
					// methodValue = null;
					// methodValueRes = null;

					pdLabel = null;
					methodLabel = null;
					methodLabelRes = null;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return retVal;
	}

	/**
	 * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	 * <tt>ArrayList</tt> of <tt>SelectedItem</tt>. This method allow to
	 * concatenate more than one Object Property.
	 *
	 * @param list a {@link java.util.List} object.
	 * @param itemLabel an array of {@link java.lang.String} objects.
	 * @param separatorForLabel a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	protected List<SelectItem> listToSelectItems(List list, String[] itemLabel, String separatorForLabel) throws Exception {
		List<SelectItem> retVal = null;
		StringBuilder sumLabel = null;

		try {
			if (list != null) {
				retVal = new ArrayList<SelectItem>();
				for (Object obj : list) {

					sumLabel = new StringBuilder();
					for (String str : itemLabel) {
						PropertyDescriptor pdLabel = new PropertyDescriptor(str, obj.getClass());
						Method methodLabel = pdLabel.getReadMethod();
						Object methodLabelRes = methodLabel.invoke(obj);
						if (methodLabelRes == null) {
							methodLabelRes = "";
						}
						if (sumLabel.length() > 0) {
							sumLabel.append(separatorForLabel);
							sumLabel.append(methodLabelRes.toString());
						} else {
							sumLabel.append(methodLabelRes.toString());
						}

						str = null;

						pdLabel = null;
						methodLabel = null;
						methodLabelRes = null;
					}

					retVal.add(new SelectItem(obj, sumLabel.toString()));

					sumLabel = null;

				}
			}
		} catch (Exception e) {
			throw e;
		}

		return retVal;
	}

}
