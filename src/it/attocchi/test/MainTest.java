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

package it.attocchi.test;

import it.attocchi.utils.DateUtilsLT;

import java.util.Date;

public class MainTest {

	public static void main(String[] args) {
		System.out.println(DateUtilsLT.isBetween(new Date(), new Date(), new Date()));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), -1), new Date()));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), -10), DateUtilsLT.addDays(new Date(), 10)));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), 10), DateUtilsLT.addDays(new Date(), 50)));
	}

}
