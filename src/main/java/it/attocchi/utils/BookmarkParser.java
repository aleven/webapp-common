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

package it.attocchi.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

public class BookmarkParser {

	protected static final Logger logger = Logger.getLogger(BookmarkParser.class.getName());

	public static String parse(String testo, Map<String, Object> parameters) throws Exception {
		String returnValue = null;
		String strOggetto = null;
		String strProperty = null;
		PropertyDescriptor pd = null;
		Method getMethod = null;
		String strValore = null;
		Object oggetto = null;
		Date dateValue = null;
		Integer integerValue = null;
		Calendar cal = null;
		String strCalValue = null;
		String testoInput = null;
		Object retobj = null;
		try {
			testoInput = testo;
			returnValue = "";
			if (testoInput != null && !testoInput.equals("")) {
				while (testoInput != null && !testoInput.equals("")) {
					int indexLow = testoInput.indexOf("${");
					if (indexLow == -1) {
						returnValue = returnValue + testoInput;
						testoInput = "";
					} else {
						returnValue = returnValue + testoInput.substring(0, indexLow);
						testoInput = testoInput.substring(indexLow + 2);
						int indexUp = testoInput.indexOf("}");
						if (indexUp == -1) {
							throw new Exception("Errore nel caricamento della stringa dal db");
						}
						strOggetto = testoInput.substring(0, indexUp);
						testoInput = testoInput.substring(indexUp + 1);
						int indexPunto = strOggetto.indexOf(".");
						if (indexPunto != -1) {
							String[] arrayProperty = strOggetto.split("\\.");
							strOggetto = arrayProperty[0];
							oggetto = parameters.get(strOggetto);
							for (int i = 1; i < arrayProperty.length; i++) {
								strProperty = arrayProperty[i];
								if (oggetto != null) {
									/*
									 * Se e' popolato l'oggetto retobj siamo
									 * nella situazione in cui devo prendereil
									 * valore da stampare da un oggetto
									 * complesso
									 */
									if (retobj != null) {
										oggetto = retobj;
									}
									try {
										pd = new PropertyDescriptor(strProperty, oggetto.getClass());
										getMethod = pd.getReadMethod();
										/*
										 * Se sono all' ultima string dell'array
										 * allora devo prendere il valore da
										 * stampare
										 */
										if (arrayProperty.length == i + 1) {
											// if (getMethod.getReturnType() !=
											// Date.class &&
											// getMethod.getReturnType() !=
											// Integer.class &&
											// getMethod.getReturnType() !=
											// int.class) {
											if (getMethod.getReturnType() != Date.class) {
												// strValore = (String)
												// getMethod.invoke(oggetto);
												/*
												 * if the argument is null, then
												 * a string equal to "null"
												 */
												strValore = String.valueOf(getMethod.invoke(oggetto));
												if (strValore == null || strValore.equals("null")) {
													strValore = "";
												}
												returnValue = returnValue + strValore;
											} else if (getMethod.getReturnType() == Date.class) {
												dateValue = (Date) getMethod.invoke(oggetto);
												if (dateValue != null) {
													SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
													strCalValue = formatter.format(dateValue);
												}
												returnValue = returnValue + strCalValue;
												// } else if
												// (getMethod.getReturnType() ==
												// Integer.class ||
												// getMethod.getReturnType() ==
												// int.class) {
												// integerValue = (Integer)
												// getMethod.invoke(oggetto);
												// if (integerValue != null) {
												// strCalValue =
												// String.valueOf(integerValue);
												// }
												// returnValue = returnValue +
												// strCalValue;
											}
											retobj = null;
										} else {
											/*
											 * Non devo prendere il valore da
											 * stampare mal'oggetto complesso
											 * contenuto nell'oggetto che sto
											 * parsando ora
											 */
											Class cls = getMethod.getReturnType();
											Constructor ct = cls.getConstructor(new Class[0]);
											retobj = ct.newInstance(new Object[0]);
											retobj = getMethod.invoke(oggetto);
										}
									} catch (IntrospectionException e) {
										returnValue = returnValue + "#no_property#";
									}
								} else {
									/*
									 * Se l'oggetto e' nullo allora imposto il
									 * segnaposto a ""
									 */
									// returnValue = "";

									/*
									 * TODO: attenzione con questa modifica
									 * eventuali campi null nel DB non vengono
									 * inseriti in modelli come vuoto ma come
									 * #null#
									 */
									returnValue = returnValue + "#null#";
								}
							}
						} else {
							/*
							 * Potrebbe essere una chiamata ad una variabile
							 * direttamente impostata
							 */
							oggetto = parameters.get(strOggetto);
							returnValue = returnValue + oggetto.toString();

							// returnValue = returnValue + "########";
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("parse", ex);
			returnValue = testoInput;
		} finally {
			strOggetto = null;
			strProperty = null;
			pd = null;
			getMethod = null;
			strValore = null;
			oggetto = null;
			dateValue = null;
			cal = null;
			strCalValue = null;
			testoInput = null;
		}
		return returnValue;
	}
}
