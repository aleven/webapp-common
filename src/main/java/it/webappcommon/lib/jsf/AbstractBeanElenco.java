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
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public abstract class AbstractBeanElenco<T extends Serializable, E extends AbstractFiltro, P extends AbstractPageBase> extends AbstractPageBase {

	protected static final Logger logger = Logger.getLogger(AbstractBeanElenco.class.getName());

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	// Filtri
	@Deprecated
	protected String semeRicerca = "";
	protected boolean filtriVisualizzati = false;
	protected E filtroImpostato;

	// protected String vistaSelezionata;
	// Dati visualizzati
	protected List<T> elencoDati;
	protected int recordTotali;
	// Selezioni
	protected int idSelezionato;
	protected T elementoSelezionato;
	protected boolean nuovo; // indica se l'elemento selezionato e' nuovo o in
	// modifica
	// Ordinamento
	// protected String sort;
	// protected boolean ascending = false;
	// Pagina corrente
	protected int pagina;
	protected int pagineTotali; /*
								 * serve come variabile d'appoggio per
								 * ricordarsi il numero delle pagine fra un
								 * postback e l'altro
								 */
	// Dati per la chiamata del bean come scheda del contatto
	int id_contatto;
	String tipo_contatto;
	//
	/*
	 * Identifica se per questo modulo l'utente puu' vedere delle cose in piu'
	 * ad esempio se e' un tecnico che possa vedere le richieste degli altri
	 */
	protected boolean utentePrivilegiato = false;
	//
	protected String nomeOggettoVisualizzato = "";
	protected String nomeOggettiVisualizzati = "";

	//
	protected final static String NUMERO_RIGHE_PAGINA = "NUMERO_RIGHE_PAGINA";

	protected P pageBaseOfProject;

	protected AbstractBeanElenco() {
		try {

			initProgectPageBase();

			/*
			 * Lo faccio prima cosi' poi se il BackBean ne ha bisogno puu'
			 * creare condizioni diverse nei filtri, ad esempio un privilegiato
			 * puu' vedere le attivita di altri
			 */
			verificaUtentePrivilegiato();

			if (!isPostBack()) {

				pagina = 1;

				initPageInfo();

				/*
				 * OLD: Lo faccio sempre cosi' se ci sono problemi vengono
				 * sempre visualizzati Ho provato a metterlo fuori dal postback
				 * ma allora le pagine aggiornano i dati solo la seconda volta
				 * (due volte cerca ad esempio)
				 */
				verificaImpostazioniSoftware();

				// inizializzaContatto();

				// this.setRighe_per_pagina(Integer.parseInt(this.getOpzionePagina(this.NUMERO_RIGHE_PAGINA,
				// "15")));
				// TODO: Temporaneamente in Assenza dell PAGE BASE DEDICATA
				initRighePerPagina();

				caricaDatiAccessori();
				// initVistePredefinite();

				impostaFiltroBase(false);
				if (filtroImpostato != null) {
					filtroImpostato.setRighePerPagina(getRighe_per_pagina());
					filtroImpostato.setPagina(getPagina());
				}

				caricaDati();
			}

		} catch (Exception ex) {
			this.addErrorMessage(ex.getMessage());
		}
	}

	protected abstract void verificaUtentePrivilegiato();

	public String actionCerca() {
		try {
			caricaDati();
		} catch (Exception ex) {
			logger.error(ex);
			addErrorMessage(ex.getMessage());
		}

		return null;
	}

	public String actionResetFiltri() {
		impostaFiltroBase(true);
		pagina = 1;
		muoviPagina();
		return null;
	}

	public String actionVisualizzaFiltri() {
		filtriVisualizzati = true;
		return null;
	}

	public String actionNascondiFiltri() {
		filtriVisualizzati = false;
		return null;
	}

	public String actionRefresh() {
		try {
			caricaDati();
		} catch (Exception ex) {
			logger.error(ex);
			addErrorMessage(ex.getMessage());
		}
		return null;
	}

	protected abstract void caricaDati() throws Exception;

	protected abstract void caricaDatiAccessori() throws Exception;

	protected abstract E impostaFiltroBase(boolean reset);

	public void setFiltriVisualizzati(boolean filtriVisualizzati) {
		this.filtriVisualizzati = filtriVisualizzati;
	}

	public boolean isFiltriVisualizzati() {
		return filtriVisualizzati;
	}

	public void setElencoDati(List<T> elencoDati) {
		this.elencoDati = elencoDati;
	}

	public List<T> getElencoDati() {
		return elencoDati;
	}

	public int getRecordTotali() {
		return recordTotali;
	}

	public void setRecordTotali(int recordTotali) {
		this.recordTotali = recordTotali;
	}

	public boolean isNuovo() {
		nuovo = (getIdSelezionato() == 0);
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

	public String actionChangeRighePagina() {
		try {
			pagina = 1;
			// this.setOpzionePagina(NUMERO_RIGHE_PAGINA, String.valueOf(this
			// .getRighe_per_pagina()));

			muoviPagina();
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
		return null;
	}

	public String actionChangePagina() {
		try {
			muoviPagina();
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
		return null;
	}

	public String actionPaginaPrima() {
		try {
			int numeroPagine = this.getPagineTotali();
			if (numeroPagine > 1 && pagina > 1) {
				pagina = 1;
				// _filtro_contatto_ditta.setPagina(_pagina);
				// caricaContatti(false);
				muoviPagina();
			}
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
		return null;
	}

	public String actionPaginaUltima() {
		try {
			int numeroPagine = this.getPagineTotali();
			if (numeroPagine > 1 && pagina < numeroPagine) {
				pagina = numeroPagine;
				// _filtro_contatto_ditta.setPagina(_pagina);
				// caricaContatti(false);
				muoviPagina();
			}
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
		return null;
	}

	public String actionPaginaAvanti() {
		try {
			int numeroPagine = this.getPagineTotali();
			if (numeroPagine > 1 && pagina < numeroPagine) {
				pagina++;
				// _filtro_contatto_ditta.setPagina(_pagina);
				// caricaContatti(false);
				muoviPagina();
			}
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
		return null;
	}

	public String actionPaginaIndietro() {
		try {
			int numeroPagine = this.getPagineTotali();
			if (numeroPagine > 1 && pagina > 1) {
				pagina--;
				// _filtro_contatto_ditta.setPagina(_pagina);
				// caricaContatti(false);
				muoviPagina();
			}
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
		return null;

	}

	public int getPagineTotali() {
		pagineTotali = 0;

		/* in caso di problemi col db (connessione) le righe potrebbero essere 0 */
		if (this.getRighe_per_pagina() > 0) {
			pagineTotali = getRecordTotali() / this.getRighe_per_pagina();
			if (getRecordTotali() % this.getRighe_per_pagina() != 0) {
				pagineTotali++;
			}
		}

		return pagineTotali;
	}

	public void setPagineTotali(int pagineTotali) {
		this.pagineTotali = pagineTotali;
	}

	public ArrayList<SelectItem> getPagineSelect() {
		ArrayList<SelectItem> returnValue = new ArrayList<SelectItem>();
		int pagineTotali = this.getPagineTotali();
		if (pagineTotali > 1) {
			for (int i = 0; i < pagineTotali; i++) {
				returnValue.add(new SelectItem(String.valueOf(i + 1), String.valueOf(i + 1)));
			}
		}
		return returnValue;
	}

	public String getPagina_string() {
		return String.valueOf(this.pagina);
	}

	public void setPagina_string(String pagina) {
		if (pagina != null && !pagina.equals("")) {
			this.pagina = Integer.parseInt(pagina);
		}
	}

	/**
	 * Verifica se la pagina corrente corrisponde all'ultima pagina
	 * 
	 * @return
	 */
	public boolean isUltimaPagina() {
		return (this.pagina == getPagineTotali());
	}

	/**
	 * Verifica se la pagina corrente e' la prima pagina
	 * 
	 * @return
	 */
	public boolean isPrimaPagina() {
		return (this.pagina == 0 || this.pagina == 1);
	}

	// public String getVistaSelezionata() {
	// return vistaSelezionata;
	// }
	//
	// public void setVistaSelezionata(String vistaSelezionata) {
	// this.vistaSelezionata = vistaSelezionata;
	// }

	public int getIdSelezionato() {
		return idSelezionato;
	}

	public void setIdSelezionato(int idSelezionato) {
		this.idSelezionato = idSelezionato;
	}

	public E getFiltroImpostato() {
		return filtroImpostato;
	}

	public void setFiltroImpostato(E filtroImpostato) {
		this.filtroImpostato = filtroImpostato;
	}

	public T getElementoSelezionato() {
		return elementoSelezionato;
	}

	public void setElementoSelezionato(T elementoSelezionato) {
		this.elementoSelezionato = elementoSelezionato;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getId_contatto() {
		return id_contatto;
	}

	public void setId_contatto(int idContatto) {
		this.id_contatto = idContatto;
	}

	public String getTipo_contatto() {
		return tipo_contatto;
	}

	public void setTipo_contatto(String tipoContatto) {
		this.tipo_contatto = tipoContatto;
	}

	// public abstract void initVistePredefinite();

	public String getNomeOggettoVisualizzato() {
		return nomeOggettoVisualizzato;
	}

	public void setNomeOggettoVisualizzato(String nomeOggettoVisualizzato) {
		this.nomeOggettoVisualizzato = nomeOggettoVisualizzato;
	}

	public String getNomeOggettiVisualizzati() {
		return nomeOggettiVisualizzati;
	}

	public void setNomeOggettiVisualizzati(String nomeOggettiVisualizzati) {
		this.nomeOggettiVisualizzati = nomeOggettiVisualizzati;
	}

	public abstract void initPageInfo();

	public void listenerRighe(ValueChangeEvent event) {
		// this.setRighe_per_pagina(Integer.parseInt(
		// event.getNewValue().toString()) );
		// caricaDati();
	}

	protected void muoviPagina() {

		try {
			filtroImpostato.setRighePerPagina(getRighe_per_pagina());
			filtroImpostato.setPagina(getPagina());
			caricaDati();
		} catch (Exception ex) {
			logger.error(ex);
			addErrorMessage(ex.getMessage());
		}

	}

	// /**
	// * In alcuni casi il backBean puo' essere usato per vedere i dati di un
	// * singolo contatto quindi verifico se possiede queste informazioni.
	// */
	// protected void inizializzaContatto() {
	//
	// Object objTipo_contatto = null;
	// Object objId_contatto = null;
	// try {
	// // Lettura parametri con settaggio dei membri di classe
	// objTipo_contatto = this.getParamObject("tipo_contatto");
	// objId_contatto = this.getParamObject("id_contatto");
	//
	// tipo_contatto = String.valueOf(objTipo_contatto);
	// id_contatto = Integer.valueOf(String.valueOf(objId_contatto));
	//
	// } catch (Exception ex) {
	// }
	//
	// }

	/**
	 * Server per verificare che le cose necessarie sono satate condfigurate. Ad
	 * esempio un nodo di codifica Non lo metto insieme al resto del codice
	 * cosi' si possono fare dei messaggi specifici senza bloccare oppure
	 * impostare una variabile errori per nascondere dei pezzi
	 */
	protected abstract void verificaImpostazioniSoftware();

	protected abstract void initRighePerPagina() throws Exception;

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
