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

package it.webappcommon.lib.jpa;

import it.webappcommon.lib.jsf.AbstractFiltro;

import java.util.HashMap;
import java.util.Map;

/**
 * Versione Evoluta di AbstractFiltro per supportare le Ricerche JPA che hanno
 * bisogno dei Parametri. Ci sono una serie di funzioni in più per gestire i
 * Parametri e le condizioni in OR fra questi nel caso di ricerche avanzate
 *
 * @author Mirco
 * @version $Id: $Id
 */
public abstract class AbstractFiltroJpa extends AbstractFiltro {

	// private int firstItem = 0;
	// private int batchSize = 0;
	private Map<String, Object> listaParametri;

	private boolean primoElementoBloccoOrAggiunto;
	private boolean dentroUnBloccoOR;

	// public int getFirstItem() {
	// return firstItem;
	// }
	//
	// public void setFirstItem(int firstItem) {
	// this.firstItem = firstItem;
	// }
	//
	// public int getBatchSize() {
	// return batchSize;
	// }
	//
	// public void setBatchSize(int batchSize) {
	// this.batchSize = batchSize;
	// }

	/**
	 * <p>addParameter.</p>
	 *
	 * @param paramName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.Object} object.
	 */
	protected void addParameter(String paramName, Object value) {
		if (listaParametri == null) {
			listaParametri = new HashMap<String, Object>();
		}

		listaParametri.put(paramName, value);

		logger.debug(String.format("%s) %s=%s", listaParametri.size(), paramName, value));
	}

	/**
	 * <p>Getter for the field <code>listaParametri</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Object> getListaParametri() {
		return listaParametri;
	}

	/**
	 * <p>initParameters.</p>
	 */
	protected void initParameters() {
		if (listaParametri != null) {
			listaParametri.clear();
			listaParametri = null;
		}
	}

	/**
	 * Verifica se ci sono da aggiungere
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	protected String parseJpaParameterValueForLike(String value) {
		String res = value;

		/*
		 * Caso di ricerca per valore UGUALE l'utennte scrive "ciao" e noi
		 * cerchiamo LIKE ciao in JPA
		 */
		if (res.startsWith("\"") && res.endsWith("\"")) {
			res = res.substring(1, res.length() - 1);
		} else if (!res.startsWith("%") && !res.endsWith("%")) {
			/*
			 * Aggiungiamo noi a mano la condizione necessaria al Like se
			 * l'utente non ha specificato qualocosa volutamente
			 */
			res = "%" + res + "%";
		}

		return res;
	}

	/*
	 * 
	 */
	/**
	 * <p>componiLikeSempliceJpa.</p>
	 *
	 * @param nomeProprieta a {@link java.lang.String} object.
	 * @param nomeParametroInQuery a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	protected String componiLikeSempliceJpa(String nomeProprieta, String nomeParametroInQuery) {

		String tmp = nomeParametroInQuery.startsWith(":") ? nomeParametroInQuery : ":" + nomeParametroInQuery;

		return nomeProprieta + " LIKE " + tmp;
	}

	/**
	 * <p>calcolaNomeParametro.</p>
	 *
	 * @param nomeParametroInQuery a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	protected String calcolaNomeParametro(String nomeParametroInQuery) {
		return nomeParametroInQuery.startsWith(":") ? nomeParametroInQuery.substring(1) : nomeParametroInQuery;
	}

	/**
	 * <p>componiLikeAvanzatoJpa.</p>
	 *
	 * @param nomeProprieta a {@link java.lang.String} object.
	 * @param nomeParametro a {@link java.lang.String} object.
	 * @param valore a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	protected String componiLikeAvanzatoJpa(String nomeProprieta, String nomeParametro, String valore) {

		StringBuilder condizioneAvanzata = new StringBuilder();

		if (isTestoConSpazi(valore)) {
			/*
			 * E' una stringa di ricerca con spazi
			 */
			// if (res.startsWith("%") || res.endsWith("%")) {
			// /*
			// * l'utente ha specificato un suo criterio e lo mantengo
			// */
			// condizioneAvanzata.append(componiLikeSempliceJpa(nomeProprieta,
			// nomeParametro))
			// }
			// {
			String[] multipleValues = valore.split(" ");

			if (multipleValues.length > 0) {
				condizioneAvanzata.append("(");
				StringBuilder perCampoLike = new StringBuilder();
				for (int i = 0; i < multipleValues.length; i++) {
					String singleValue = multipleValues[i];

					/*
					 * Per ogni valore della ricerca concateniamo una condizione
					 * like su un parametro con il nome con progressivo *
					 */

					if (perCampoLike.length() == 0) {
						perCampoLike.append(componiLikeSempliceJpa(nomeProprieta, nomeParametro + i));
					} else {
						perCampoLike.append(" AND " + componiLikeSempliceJpa(nomeProprieta, nomeParametro + i));
					}
				}
				condizioneAvanzata.append(perCampoLike);
				condizioneAvanzata.append(")");
			}
			// }
		}

		return condizioneAvanzata.toString();
	}

	/**
	 * <p>isTestoConSpazi.</p>
	 *
	 * @param valore a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	protected boolean isTestoConSpazi(String valore) {
		return (valore.trim().indexOf(" ") > -1);
	}

	/**
	 * <p>componiLikeSempliceOAvanzatoDaSemeRicerca.</p>
	 *
	 * @param nomeProprieta a {@link java.lang.String} object.
	 * @param nomeParametro a {@link java.lang.String} object.
	 */
	protected void componiLikeSempliceOAvanzatoDaSemeRicerca(String nomeProprieta, String nomeParametro) {

		if (isTestoConSpazi(semeRicerca)) {

			if (dentroUnBloccoOR) {
				if (primoElementoBloccoOrAggiunto) {
					condizioneWhere.append(" OR ");
				}
			} else {
				condizioneWhere.append(" AND ");
			}
			/*
			 * Compongo
			 */
			condizioneWhere.append(componiLikeAvanzatoJpa(nomeProprieta, nomeParametro, semeRicerca));

			primoElementoBloccoOrAggiunto = true;

			String[] singoliValoriRicerca = semeRicerca.split(" ");
			for (int i = 0; i < singoliValoriRicerca.length; i++) {
				addParameter(calcolaNomeParametro(nomeParametro + i), parseJpaParameterValueForLike(singoliValoriRicerca[i]));
			}

		} else {
			if (dentroUnBloccoOR) {
				if (primoElementoBloccoOrAggiunto) {
					condizioneWhere.append(" OR ");
				}
			} else {
				condizioneWhere.append(" AND ");
			}
			condizioneWhere.append(componiLikeSempliceJpa(nomeProprieta, nomeParametro));
			primoElementoBloccoOrAggiunto = true;
			addParameter(calcolaNomeParametro(nomeParametro), parseJpaParameterValueForLike(semeRicerca));
		}

	}

	/**
	 * <p>iniziaBloccoOR.</p>
	 */
	protected void iniziaBloccoOR() {
		condizioneWhere.append(" AND (");
		primoElementoBloccoOrAggiunto = false;
		dentroUnBloccoOR = true;
	}

	/**
	 * <p>finisciBloccoOR.</p>
	 */
	protected void finisciBloccoOR() {
		condizioneWhere.append(")");
		dentroUnBloccoOR = false;
	}

	/** {@inheritDoc} */
	@Override
	public String getSQLWhere() throws Exception {
		// // TODO Auto-generated method stub
		// return super.getSQLWhere();

		initSQLWhere();
		initParameters();

		condizioneWhereAppends();

		return condizioneWhere.toString();

	}

	/**
	 * Da Fare
	 *
	 * @param nomeProprieta a {@link java.lang.String} object.
	 * @param valore a {@link java.lang.String} object.
	 */
	protected void aggiungiCondizione(String nomeProprieta, String valore) {

	}
}
