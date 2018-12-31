/*
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

package it.webappcommon.lib.utils.version;

/**
 * Questa classe serve sia a gestire la versione di AtreeFLOW sia per gestire
 * quella del database e per confrontarle
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class Version implements Comparable {

	// Commento per ricerca 2.6.5
	private int major;
	private int minor;
	private int build;

	/**
	 * <p>Constructor for Version.</p>
	 */
	public Version() {
	}

	/**
	 * <p>Constructor for Version.</p>
	 *
	 * @param major a int.
	 * @param minor a int.
	 * @param build a int.
	 */
	public Version(int major, int minor, int build) {
		this.major = major;
		this.minor = minor;
		this.build = build;
	}

	/**
	 * Crea un oggetto AtreeFLOWVersion partendo dalla stringa nel fomato
	 * supportato major.minor.build
	 *
	 * @param version stringa versione
	 */
	public Version(String version) {
		String[] splitVersion = version.split("\\.");
		this.major = Integer.parseInt(splitVersion[0]);
		this.minor = Integer.parseInt(splitVersion[1]);
		this.build = Integer.parseInt(splitVersion[2]);
	}

	/**
	 * <p>Getter for the field <code>major</code>.</p>
	 *
	 * @return the major
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * <p>Setter for the field <code>major</code>.</p>
	 *
	 * @param major
	 *            the major to set
	 */
	public void setMajor(int major) {
		this.major = major;
	}

	/**
	 * <p>Getter for the field <code>minor</code>.</p>
	 *
	 * @return the minor
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * <p>Setter for the field <code>minor</code>.</p>
	 *
	 * @param minor
	 *            the minor to set
	 */
	public void setMinor(int minor) {
		this.minor = minor;
	}

	/**
	 * <p>Getter for the field <code>build</code>.</p>
	 *
	 * @return the build
	 */
	public int getBuild() {
		return build;
	}

	/**
	 * <p>Setter for the field <code>build</code>.</p>
	 *
	 * @param build
	 *            the build to set
	 */
	public void setBuild(int build) {
		this.build = build;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("%s.%s.%s", this.getMajor(), this.getMinor(), this.getBuild());
	}

	/** {@inheritDoc} */
	public int compareTo(Object anotherVersion) {

		if (!(anotherVersion instanceof Version)) {
			throw new ClassCastException("An Version object expected.");
		}

		int otherMajor = ((Version) anotherVersion).getMajor();
		int otherMinor = ((Version) anotherVersion).getMinor();
		int otherBuild = ((Version) anotherVersion).getBuild();

		if (this.major == otherMajor && this.minor == otherMinor && this.build == otherBuild) {
			return 0;
		} else if (this.major > otherMajor) {
			return 1;
		} else if (this.major >= otherMajor && this.minor > otherMinor) {
			return 1;
		} else if (this.major >= otherMajor && this.minor >= otherMinor && this.build > otherBuild) {
			return 1;
		} else {
			return -1;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Version)) {
			throw new ClassCastException("An AtreeFLOWVersion object expected.");
		}

		int otherMajor = ((Version) obj).getMajor();
		int otherMinor = ((Version) obj).getMinor();
		int otherBuild = ((Version) obj).getBuild();

		return (this.major == otherMajor && this.minor == otherMinor && this.build == otherBuild);
	}
}
