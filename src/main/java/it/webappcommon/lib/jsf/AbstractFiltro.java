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

import it.attocchi.sql.QueryBuilder;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public abstract class AbstractFiltro implements Serializable {

	protected static Logger logger = Logger.getLogger(AbstractFiltro.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractFiltro() {
		initSort();
		initVistePredefinite();
	}

	public AbstractFiltro(Connection conn) {
		this();
		this.connection = conn;
	}

	protected int pagina;
	//
	protected String sort;
	protected List<SelectItem> ordinamenti = new ArrayList<SelectItem>();
	protected int righePerPagina;
	protected boolean ascending = false;
	protected int idUtenteCreazione;
	protected Date dataCreazione;

	protected Date dataCreazioneFrom;
	protected Date dataCreazioneTo;

	protected String semeRicerca;
	protected StringBuilder condizioneWhere = new StringBuilder(QueryBuilder.ALWAYS_TRUE);

	protected boolean includeDeleted = false;

	// protected boolean uiUtentePrivilegiato = false;

	/**
	 * Obbiettivo: Serve a dire se c'e' qualcosa di impostato nei filtri. Nel
	 * caso dei filtri nidificati questo e' utile per evitare di concatenarli se
	 * non ci sono parametri di ricerca
	 */
	protected boolean someInFilter = false;
	//
	protected String vistaSelezionata;
	/*
	 * impostato a new per usare subito il get
	 */
	protected List<SelectItem> viste = new ArrayList<SelectItem>();

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public int getIdUtenteCreazione() {
		return idUtenteCreazione;
	}

	public void setIdUtenteCreazione(int idUtente) {
		this.idUtenteCreazione = idUtente;
	}

	public String getSemeRicerca() {
		return semeRicerca;
	}

	public void setSemeRicerca(String semeRicerca) {
		this.semeRicerca = semeRicerca;
	}

	public int getRighePerPagina() {
		return righePerPagina;
	}

	public void setRighePerPagina(int righePerPagina) {
		this.righePerPagina = righePerPagina;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getVistaSelezionata() {
		return vistaSelezionata;
	}

	public void setVistaSelezionata(String vistaSelezionata) {
		this.vistaSelezionata = vistaSelezionata;
	}

	public List<SelectItem> getViste() {
		return viste;
	}

	public void setViste(List<SelectItem> viste) {
		this.viste = viste;
	}

	public List<SelectItem> getOrdinamenti() {
		return ordinamenti;
	}

	public void setOrdinamenti(List<SelectItem> ordinamenti) {
		this.ordinamenti = ordinamenti;
	}

	public boolean isIncludeDeleted() {
		return includeDeleted;
	}

	public void setIncludeDeleted(boolean includeDeleted) {
		this.includeDeleted = includeDeleted;
	}

	public Date getDataCreazioneFrom() {
		return dataCreazioneFrom;
	}

	public void setDataCreazioneFrom(Date dataCreazioneFrom) {
		this.dataCreazioneFrom = dataCreazioneFrom;
	}

	public Date getDataCreazioneTo() {
		return dataCreazioneTo;
	}

	public void setDataCreazioneTo(Date dataCreazioneTo) {
		this.dataCreazioneTo = dataCreazioneTo;
	}

	@XmlTransient
	public boolean isSomeInFilter() {

		/*
		 * Richiamo la Costruzione della Query. In Questo modo posso capire se
		 * c'e' qualcosa da filtrare prima di richiamarla.
		 */
		try {
			condizioneWhereAppends();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return someInFilter;
	}

	public void setSomeInFilter(boolean someInFilter) {
		this.someInFilter = someInFilter;
	}

	/**
	 * Ritorna la condizione Where da concatenare alla query in funzione dei
	 * criteri impostati
	 * 
	 * @return
	 */
	@XmlTransient
	public String getSQLWhere() throws Exception {
		initSQLWhere();

		condizioneWhereAppends();

		return condizioneWhere.toString();
	}

	public abstract void condizioneWhereAppends() throws Exception;

	/**
	 * Questo metodo viene usato quando si costruiscer il sqlWhere per evitare
	 * che si concatenino una serie di condizioni continuando a cliccare cerca e
	 * con il filtro savestatato
	 */
	protected void initSQLWhere() {
		condizioneWhere = new StringBuilder(QueryBuilder.ALWAYS_TRUE);
	}

	@XmlTransient
	public String getSQLSort() {
		StringBuilder sort = new StringBuilder("");

		// if (getSort() != null && !getSort().isEmpty()) {
		if (StringUtils.isNotEmpty(getSort())) {
			sort.append(" ");
			sort.append("ORDER BY");
			sort.append(" " + getSort() + " ");
			sort.append(isAscending() ? "ASC" : "DESC");
		}

		return sort.toString();
	}

	@XmlTransient
	public String getSQLLimit() {
		StringBuilder limit = new StringBuilder("");

		if (righePerPagina > 0) {
			limit.append(" LIMIT ");
			limit.append(this.getRighePerPagina());
			if (getPagina() > 0) {
				limit.append(" OFFSET ");
				limit.append(this.getRighePerPagina() * (this.getPagina() - 1));
			}
		}

		return limit.toString();
	}

	/**
	 * Questo metodo serve per specificare l'orinamento iniziale da usare
	 * Esempio: sort = "tel01_callstart"; ascending = true;
	 */
	public abstract void initSort();

	public abstract void initVistePredefinite();

	private Connection connection;

	/**
	 * Ci sono casi in cui il filtro usa la connessione per caricare ad esempio
	 * gli ID della LINKS da filtrare in quei casi aper una sua connessione ma
	 * forse pu� essere utile passare al filtro la connessione e farla usare
	 * al filtro stesso. Nel caso del programma di test qeusto e' utile ma anche
	 * nel caso di transazioni
	 * 
	 * @return
	 */
	@XmlTransient
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection conn) {
		this.connection = conn;
	}

	// /**
	// * Passando al Filtro se e' un utente privilegiato posso popolare nel dao
	// l'elenco di oggetti con questa informazione.
	// * Se devo caricare dei campi LAZY posso usare questa info passata (es.
	// commenti in richieste interne)
	// * @return
	// */
	// public boolean isUiUtentePrivilegiato() {
	// return uiUtentePrivilegiato;
	// }
	//
	// public void setUiUtentePrivilegiato(boolean uiUtentePrivilegiato) {
	// this.uiUtentePrivilegiato = uiUtentePrivilegiato;
	// }

}
