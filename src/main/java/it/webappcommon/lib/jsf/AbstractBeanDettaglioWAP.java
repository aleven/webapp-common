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
 * 
 * @param <T>
 * @param <P>
 *            Classe che Estende PageBase specifica del progetto, per dare
 *            visibilita' dei metodi specifici
 */
public abstract class AbstractBeanDettaglioWAP<T extends Serializable, P extends AbstractPageBase> extends AbstractPageBase {

	private static final String PARAM_ID = "id";

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

	public int getIdSelezionato() {
		return idSelezionato;
	}

	public void setIdSelezionato(int idSelezionato) {
		this.idSelezionato = idSelezionato;
	}

	public String getIdStringSelezionato() {
		return idStringSelezionato;
	}

	public void setIdStringSelezionato(String idStringSelezionato) {
		this.idStringSelezionato = idStringSelezionato;
	}

	public T getElementoSelezionato() {
		return elementoSelezionato;
	}

	public void setElementoSelezionato(T elementoSelezionato) {
		this.elementoSelezionato = elementoSelezionato;
	}

	public boolean isNuovo() {
		nuovo = ((isIDNumerico() && getIdSelezionato() == 0) || (!isIDNumerico() && (getIdStringSelezionato() == null || getIdStringSelezionato().isEmpty())));
		return nuovo;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}

	public boolean isUtentePrivilegiato() {
		return utentePrivilegiato;
	}

	public void setUtentePrivilegiato(boolean utentePrivilegiato) {
		this.utentePrivilegiato = utentePrivilegiato;
	}

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

	protected abstract void verificaUtentePrivilegiato();

	/**
	 * Carica i dati dal database oppure crea un oggetto nuovo
	 */
	protected abstract void caricaDati() throws Exception;

	/**
	 * Inizializza alcuni dati dopo il caricamento (esempio valorizza campi)
	 */
	protected abstract void caricaDatiPost() throws Exception;

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
	 */
	public abstract boolean isReadOnly();

	protected abstract void eseguiAzioniDaParametri();

	public P getPageBaseOfProject() {
		// TODO: Forse e' il caso di farsi passare una istanza?
		// if (pageBaseOfProject == null) {
		// pageBaseOfProject = P.getIstance();
		// }
		return pageBaseOfProject;
	}

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
