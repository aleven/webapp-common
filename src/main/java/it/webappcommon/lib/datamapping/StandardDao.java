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

package it.webappcommon.lib.datamapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StandardDao<T extends StorableObject> {

	T type;

	public Class getGenericType() {
		return type.getClass();
	}

	private String tableName;
	private String primaryKeyField;

	private List<String> fieldList;

	public int insert(T objectToSave) {
		int generatedId = 0;

		/*
		 * 
		 */
		Class c = objectToSave.getClass(); // Class.forName(new T().getClass());

		Method m[] = c.getDeclaredMethods();
		for (int i = 0; i < m.length; i++) {
			System.out.println(m[i].toString());
		}

		StoreInTable storeInTable = (StoreInTable) c.getAnnotation(StoreInTable.class);
		if (storeInTable != null) {
			System.out.println("Is storeInTable");

			tableName = storeInTable.tableName();
			primaryKeyField = storeInTable.primaryKeyField();

			Field f[] = c.getFields();
			for (int i = 0; i < f.length; i++) {
				System.out.println(f[i].toString());

				try {
					PropertyDescriptor pdValue = new PropertyDescriptor(f[i].toString(), objectToSave.getClass());
					Method methodValue = pdValue.getReadMethod();

					// Class c = pdValue.getPropertyType();

					StoreInField storeInField = (StoreInField) methodValue.getAnnotation(StoreInField.class);
					if (storeInField != null) {

						if (fieldList == null) {
							fieldList = new ArrayList<String>();
						}

						fieldList.add(storeInField.fieldName());

						// Object methodValueRes = methodValue.invoke(objectToSave);
					}
				} catch (Exception ex) {

				}
				
			}

		}

		return generatedId;
	}
}
