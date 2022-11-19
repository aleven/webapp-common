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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 * <p>HttpClientUtils class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class HttpClientUtils {

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(HttpClientUtils.class.getName());

	/**
	 * <p>getFile.</p>
	 *
	 * @param url
	 *            un url dove scaricare il file
	 * @param urlFileNameParam
	 *            specifica il parametro nell'url che definisce il nome del
	 *            file, se non specificato il nome corrisponde al nome nell'url
	 * @param dest
	 *            dove salvare il file, se non specificato crea un file
	 *            temporaneo
	 * @return a {@link java.io.File} object.
	 * @throws org.apache.http.client.ClientProtocolException if any.
	 * @throws java.io.IOException if any.
	 */
	public static File getFile(String url, String urlFileNameParam, File dest) throws ClientProtocolException, IOException {

		DefaultHttpClient httpclient = null;
		HttpGet httpget = null;
		HttpResponse response = null;
		HttpEntity entity = null;

		try {
			httpclient = new DefaultHttpClient();

			url = url.trim();

			/* Per prima cosa creiamo il File */
			String name = getFileNameFromUrl(url, urlFileNameParam);
			String baseName = FilenameUtils.getBaseName(name);
			String extension = FilenameUtils.getExtension(name);

			if (dest == null)
				dest = Files.createTempFile(baseName + "_", "." + extension).toFile();

			/* Procediamo con il Download */
			httpget = new HttpGet(url);
			response = httpclient.execute(httpget);
			entity = response.getEntity();

			if (entity != null) {

				OutputStream fos = null;
				try {
					// var fos = new
					// java.io.FileOutputStream('c:\\temp\\myfile.ext');
					fos = new java.io.FileOutputStream(dest);
					entity.writeTo(fos);
				} finally {
					if (fos != null)
						fos.close();
				}

				logger.debug(fos);
			}
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			// org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpClient);
			// if (entity != null)
			// EntityUtils.consume(entity);
			//

			try {
				if (httpclient != null)
					httpclient.getConnectionManager().shutdown();
			} catch (Exception ex) {
				logger.error(ex);
			}
		}

		return dest;
	}

	/**
	 * <p>getFileNameFromUrl.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param paramName a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFileNameFromUrl(String url, String paramName) {
		String res = null;

		String baseName = "";
		String extension = "";
		if (paramName == null || paramName.isEmpty()) {
			baseName = FilenameUtils.getBaseName(url);
			extension = FilenameUtils.getExtension(url);

			res = FilenameUtils.getName(url);

		} else {
			String fileNameOnParam = null;
			List<NameValuePair> params = URLEncodedUtils.parse(url, Charset.defaultCharset());
			if (params != null)
				for (NameValuePair p : params) {
					if (p.getName().contains(paramName)) {
						fileNameOnParam = p.getValue();
						break;
					}
				}
			baseName = FilenameUtils.getBaseName(fileNameOnParam);
			extension = FilenameUtils.getExtension(fileNameOnParam);

			res = FilenameUtils.getName(fileNameOnParam);
		}

		return res;
	}

}
