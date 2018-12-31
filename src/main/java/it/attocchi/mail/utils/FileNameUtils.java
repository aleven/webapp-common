package it.attocchi.mail.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>FileNameUtils class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class FileNameUtils {

	/**
	 * <p>parseSize.</p>
	 *
	 * @param size a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String parseSize(long size) {
		String[] suffix = { "bytes", "KB", "MB", "GB", "TB", "PB" };
		int tier = 0;

		while (size >= 1024) {
			size = size / 1024;
			tier++;
		}

		return Math.round(size * 10) / 10 + " " + suffix[tier];
	}

	/**
	 * <p>combine.</p>
	 *
	 * @param path1 a {@link java.lang.String} object.
	 * @param path2 a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String combine(String path1, String path2) {
		File file1 = new File(path1);
		File file2 = new File(file1, path2);
		return file2.getPath();
	}

	/**
	 * <p>copy.</p>
	 *
	 * @param src a {@link java.io.File} object.
	 * @param dst a {@link java.io.File} object.
	 * @throws java.io.IOException if any.
	 */
	public static void copy(File src, File dst) throws IOException {
		if (src.exists()) {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	// public static String encodeFileName(String fileName, String
	// defaultNameOnError) {
	// String res = ;
	//
	// try {
	// for (char ch : fileName.toCharArray()) {
	// if (ch.is)
	// }
	// } catch (Exception ex) {
	// res = defaultNameOnError;
	// }
	//
	// return res;
	// }

	/**
	 * <p>getFileNameWithOutExtension.</p>
	 *
	 * @param file a {@link java.io.File} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFileNameWithOutExtension(File file) {

		return getFileNameWithOutExtension(file.getName());
	}

	/**
	 * <p>getFileNameWithOutExtension.</p>
	 *
	 * @param fileName a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFileNameWithOutExtension(String fileName) {

		return StringUtils.left(fileName, fileName.lastIndexOf('.'));
	}

	/**
	 * <p>getFileExtension.</p>
	 *
	 * @param file a {@link java.io.File} object.
	 * @param includeDot a boolean.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFileExtension(File file, boolean includeDot) {

		return getFileExtension(file.getName(), includeDot);
	}

	/**
	 * <p>getFileExtension.</p>
	 *
	 * @param fileName a {@link java.lang.String} object.
	 * @param includeDot a boolean.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFileExtension(String fileName, boolean includeDot) {

		int pos = fileName.lastIndexOf('.');
		if (!includeDot)
			pos = pos + 1;

		return fileName.substring(pos, fileName.length());
	}
}
