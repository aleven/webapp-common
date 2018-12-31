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

package it.webappcommon.lib.jsf;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * Questo backbean astratto serve come struttura per tutti i backbean delle
 * classi che devono visualizzare i dati di un singolo elemento, ad esempio una
 * pagina di modifica o creazione nuovo oggetto
 *
 * @author Mirco
 * @param <T> classe che estende Serializable
 * @param <P>
 *            Classe che Estende PageBase specifica del progetto, per dare
 *            visibilita' dei metodi specifici
 * @version $Id: $Id
 */
public abstract class AbstractBeanDettaglioWAP<T extends Serializable, P extends AbstractPageBase> extends AbstractPageBase {

	private static final String PARAM_ID = "id";

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(AbstractBeanDettaglioWAP.class.getName());

	protected enum TIPO_ID {
		numerico,
		stringa
	};

	protected P pageBaseOfProject;

	/**
     *
     */
	private static final long serialVersionUID = 1L;

	// Selezioni
	/**
	 * Rappresenta l'id dell'oggetto che devo visualizzare
	 */
	protected int idSelezionato;
	protected String idStringSelezionato;
	/**
	 * Rappresenta l'oggetto che devo visualizzare
	 */
	protected T elementoSelezionato;
	protected boolean nuovo;

	/*
	 * Identifica se per questo modulo l'utente puo vedere delle cose in piu' ad
	 * esempio se e' un tecnico che possa vedere le richieste degli altri
	 */
	protected boolean utentePrivilegiato;

	/**
	 * <p>Getter for the field <code>idSelezionato</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIdSelezionato() {
		return idSelezionato;
	}

	/**
	 * <p>Setter for the field <code>idSelezionato</code>.</p>
	 *
	 * @param idSelezionato a int.
	 */
	public void setIdSelezionato(int idSelezionato) {
		this.idSelezionato = idSelezionato;
	}

	/**
	 * <p>Getter for the field <code>idStringSelezionato</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getIdStringSelezionato() {
		return idStringSelezionato;
	}

	/**
	 * <p>Setter for the field <code>idStringSelezionato</code>.</p>
	 *
	 * @param idStringSelezionato a {@link java.lang.String} object.
	 */
	public void setIdStringSelezionato(String idStringSelezionato) {
		this.idStringSelezionato = idStringSelezionato;
	}

	/**
	 * <p>Getter for the field <code>elementoSelezionato</code>.</p>
	 *
	 * @return a T object.
	 */
	public T getElementoSelezionato() {
		return elementoSelezionato;
	}

	/**
	 * <p>Setter for the field <code>elementoSelezionato</code>.</p>
	 *
	 * @param elementoSelezionato a T object.
	 */
	public void setElementoSelezionato(T elementoSelezionato) {
		this.elementoSelezionato = elementoSelezionato;
	}

	/**
	 * <p>isNuovo.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isNuovo() {
		nuovo = ((isIDNumerico() && getIdSelezionato() == 0) || (!isIDNumerico() && (getIdStringSelezionato() == null || getIdStringSelezionato().isEmpty())));
		return nuovo;
	}

	/**
	 * <p>Setter for the field <code>nuovo</code>.</p>
	 *
	 * @param nuovo a boolean.
	 */
	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}

	/**
	 * <p>isUtentePrivilegiato.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isUtentePrivilegiato() {
		return utentePrivilegiato;
	}

	/**
	 * <p>Setter for the field <code>utentePrivilegiato</code>.</p>
	 *
	 * @param utentePrivilegiato a boolean.
	 */
	public void setUtentePrivilegiato(boolean utentePrivilegiato) {
		this.utentePrivilegiato = utentePrivilegiato;
	}

	/**
	 * <p>Constructor for AbstractBeanDettaglioWAP.</p>
	 */
	protected AbstractBeanDettaglioWAP() {
		try {

			initProgectPageBase();

			/*
			 * Bisogna verificare prima del resto per avere sapere se l'utente
			 * e' privilegiato prima di applicare filtri o caricamenti dati(
			 */
			verificaUtentePrivilegiato();

			if (!isPostBack()) {

				Object objId = this.getParamObject(PARAM_ID);
				if (objId != null) {
					if (isIDNumerico()) {
						idSelezionato = Integer.valueOf(String.valueOf(objId));
					} else {
						idStringSelezionato = getParamObjectAsString(PARAM_ID);
					}
				}

				initPageInfo();

				/*
				 * OLD: Lo faccio sempre coso se ci sono problemi vengono sempre
				 * visualizzati Ho provato a metterlo fuori dal postback ma
				 * allora le pagine aggiornano i dati solo la seconda volta (due
				 * volte cerca ad esempio)
				 */
				verificaImpostazioniSoftware();

				// inizializzaContatto();
				//
				// this.setRighe_per_pagina(Integer.parseInt(this.getOpzionePagina(this.NUMERO_RIGHE_PAGINA,
				// "15")));
				//
				caricaDatiAccessori();
				// initVistePredefinite();
				//
				// impostaFiltroBase(boolean reset);
				caricaDati();

				caricaDatiPost();

				eseguiAzioniDaParametri();
			}
		} catch (Exception ex) {
			setMessaggioErrore(ex.getMessage());
		}
	}

	/**
	 * <p>verificaUtentePrivilegiato.</p>
	 */
	protected abstract void verificaUtentePrivilegiato();

	/**
	 * Carica i dati dal database oppure crea un oggetto nuovo
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract void caricaDati() throws Exception;

	/**
	 * Inizializza alcuni dati dopo il caricamento (esempio valorizza campi)
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract void caricaDatiPost() throws Exception;

	/**
	 * <p>caricaDatiAccessori.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	protected abstract void caricaDatiAccessori() throws Exception;

	/**
	 * Serve per impostare le informazioni di base della pagina Ad esempio i
	 * titoli
	 */
	protected abstract void initPageInfo();

	/**
	 * Specifica se il tipo di chiave da usare e' numerico o stringa
	 *
	 * @return true se numerico
	 */
	protected abstract boolean isIDNumerico();

	/**
	 * Verifica se e' stato passato un id di un oggetto
	 *
	 * @return true se numerico maggiore di zero o non numerico e diverso da stringa vuota
	 */
	public boolean isEditMode() {
		// return (idSelezionato > 0);
		return ((isIDNumerico() && idSelezionato > 0) || (!isIDNumerico() && !idStringSelezionato.isEmpty()));
	}

	/**
	 * Serve per verificare che le cose necessarie sono satate condfigurate. Ad
	 * esempio un nodo di codifica Non lo metto insieme al resto del codice coso
	 * si possono fare dei messaggi specifici senza bloccare oppure impostare
	 * una variabile errori per nascondere dei pezzi
	 */
	protected abstract void verificaImpostazioniSoftware();

	/**
	 * Serve per determinare se l'elemento e' modificabile dall'utente
	 *
	 * @return a boolean.
	 */
	public abstract boolean isReadOnly();

	/**
	 * <p>eseguiAzioniDaParametri.</p>
	 */
	protected abstract void eseguiAzioniDaParametri();

	/**
	 * <p>Getter for the field <code>pageBaseOfProject</code>.</p>
	 *
	 * @return a P object.
	 */
	public P getPageBaseOfProject() {
		// TODO: Forse e' il caso di farsi passare una istanza?
		// if (pageBaseOfProject == null) {
		// pageBaseOfProject = P.getIstance();
		// }
		return pageBaseOfProject;
	}

	/**
	 * <p>Setter for the field <code>pageBaseOfProject</code>.</p>
	 *
	 * @param pageBaseOfProject a P object.
	 */
	public void setPageBaseOfProject(P pageBaseOfProject) {
		this.pageBaseOfProject = pageBaseOfProject;
	}

	/**
	 * Obbiettivo del metodo e' creare una istanza della page base del progetto.
	 * No posso crearne una istanza in questa abstract con new P(); quindi devo
	 * farlo nelle classi figlie
	 */
	protected abstract void initProgectPageBase();

}
