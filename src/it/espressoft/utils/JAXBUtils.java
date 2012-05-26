package it.espressoft.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 
 * @author Mirco Attocchi
 *
 */
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

	public static <T> String serializeToString(Class<T> clazz, T object, boolean formatted) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(clazz);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);

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
