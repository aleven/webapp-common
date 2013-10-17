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

public class HttpClientUtils {

	protected static final Logger logger = Logger.getLogger(HttpClientUtils.class.getName());

	/**
	 * 
	 * @param url
	 *            un url dove scaricare il file
	 * @param urlFileNameParam
	 *            specifica il parametro nell'url che definisce il nome del
	 *            file, se non specificato il nome corrisponde al nome nell'url
	 * @param dest
	 *            dove salvare il file, se non specificato crea un file
	 *            temporaneo
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
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
				dest = File.createTempFile(baseName + "_", "." + extension);

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
