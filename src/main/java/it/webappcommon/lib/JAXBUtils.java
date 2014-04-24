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

package it.webappcommon.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtils {

	public static <T> void serializeToFile(Class<T> clazz, T object, String outputFile) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(clazz);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		m.marshal(object, new FileOutputStream(outputFile));
	}

	public static <T> T deserializeFromFile(Class<T> clazz, String inputFile) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);

		Unmarshaller u = context.createUnmarshaller();

		File f = new File(inputFile);

		return (T) u.unmarshal(f);
	}

	public static <T> String serializeToString(Class<T> clazz, T object) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(clazz);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();

		m.marshal(object, sw);

		return sw.toString();
	}

	public static <T> T deserializeFromString(Class<T> clazz, String aString) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(clazz);

		Unmarshaller u = context.createUnmarshaller();

		StringReader sr = new StringReader(aString);

		return (T) u.unmarshal(sr);
	}

}
