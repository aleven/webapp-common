/*
    Copyright (c) 2012,2018 Mirco Attocchi
	
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

import java.text.MessageFormat;

public class MessageUtils {

	/**
	 * Uso di {@link MessageFormat} per formattare un messaggio
	 * @param message il pattern del messaggio da formattare
	 * @param params parametri
	 * @return
	 */
	public static String parse(String message, Object... params) {
		String res = message;

		if (message != null && !message.isEmpty()) {
			// Object[] args = { username, password };
			MessageFormat fmt = new MessageFormat(message);
			res = fmt.format(params);
		}

		return res;
	}
}
