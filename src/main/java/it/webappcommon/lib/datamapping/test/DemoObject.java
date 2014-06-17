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

package it.webappcommon.lib.datamapping.test;

import it.webappcommon.lib.datamapping.StorableObject;
import it.webappcommon.lib.datamapping.StoreInField;
import it.webappcommon.lib.datamapping.StoreInTable;

import java.util.Date;

@StoreInTable(tableName = "dem01_table", tablePrefix = "dem01", primaryKeyField = "dem01_id")
public class DemoObject implements StorableObject {

	private int id;

	@StoreInField(fieldName = "dem01_name")
	private String stringField;

	private int intField;
	
	private boolean boolField;
	
	private Date dateField;
	
	
	
}
