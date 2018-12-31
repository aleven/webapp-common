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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.attocchi.sql.QueryBuilder;

/**
 * <p>Abstract AbstractFiltro class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public abstract class AbstractFiltro implements Serializable {

	/** Constant <code>logger</code> */
	protected static Logger logger = Logger.getLogger(AbstractFiltro.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for AbstractFiltro.</p>
	 */
	public AbstractFiltro() {
		initSort();
		initVistePredefinite();
	}

	/**
	 * <p>Constructor for AbstractFiltro.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 */
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

	/**
	 * <p>Getter for the field <code>pagina</code>.</p>
	 *
	 * @return a int.
	 */
	public int getPagina() {
		return pagina;
	}

	/**
	 * <p>Setter for the field <code>pagina</code>.</p>
	 *
	 * @param pagina a int.
	 */
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	/**
	 * <p>Getter for the field <code>sort</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * <p>Setter for the field <code>sort</code>.</p>
	 *
	 * @param sort a {@link java.lang.String} object.
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * <p>isAscending.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * <p>Setter for the field <code>ascending</code>.</p>
	 *
	 * @param ascending a boolean.
	 */
	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	/**
	 * <p>Getter for the field <code>idUtenteCreazione</code>.</p>
	 *
	 * @return a int.
	 */
	public int getIdUtenteCreazione() {
		return idUtenteCreazione;
	}

	/**
	 * <p>Setter for the field <code>idUtenteCreazione</code>.</p>
	 *
	 * @param idUtente a int.
	 */
	public void setIdUtenteCreazione(int idUtente) {
		this.idUtenteCreazione = idUtente;
	}

	/**
	 * <p>Getter for the field <code>semeRicerca</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSemeRicerca() {
		return semeRicerca;
	}

	/**
	 * <p>Setter for the field <code>semeRicerca</code>.</p>
	 *
	 * @param semeRicerca a {@link java.lang.String} object.
	 */
	public void setSemeRicerca(String semeRicerca) {
		this.semeRicerca = semeRicerca;
	}

	/**
	 * <p>Getter for the field <code>righePerPagina</code>.</p>
	 *
	 * @return a int.
	 */
	public int getRighePerPagina() {
		return righePerPagina;
	}

	/**
	 * <p>Setter for the field <code>righePerPagina</code>.</p>
	 *
	 * @param righePerPagina a int.
	 */
	public void setRighePerPagina(int righePerPagina) {
		this.righePerPagina = righePerPagina;
	}

	/**
	 * <p>Getter for the field <code>dataCreazione</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getDataCreazione() {
		return dataCreazione;
	}

	/**
	 * <p>Setter for the field <code>dataCreazione</code>.</p>
	 *
	 * @param dataCreazione a {@link java.util.Date} object.
	 */
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/**
	 * <p>Getter for the field <code>vistaSelezionata</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getVistaSelezionata() {
		return vistaSelezionata;
	}

	/**
	 * <p>Setter for the field <code>vistaSelezionata</code>.</p>
	 *
	 * @param vistaSelezionata a {@link java.lang.String} object.
	 */
	public void setVistaSelezionata(String vistaSelezionata) {
		this.vistaSelezionata = vistaSelezionata;
	}

	/**
	 * <p>Getter for the field <code>viste</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<SelectItem> getViste() {
		return viste;
	}

	/**
	 * <p>Setter for the field <code>viste</code>.</p>
	 *
	 * @param viste a {@link java.util.List} object.
	 */
	public void setViste(List<SelectItem> viste) {
		this.viste = viste;
	}

	/**
	 * <p>Getter for the field <code>ordinamenti</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<SelectItem> getOrdinamenti() {
		return ordinamenti;
	}

	/**
	 * <p>Setter for the field <code>ordinamenti</code>.</p>
	 *
	 * @param ordinamenti a {@link java.util.List} object.
	 */
	public void setOrdinamenti(List<SelectItem> ordinamenti) {
		this.ordinamenti = ordinamenti;
	}

	/**
	 * <p>isIncludeDeleted.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isIncludeDeleted() {
		return includeDeleted;
	}

	/**
	 * <p>Setter for the field <code>includeDeleted</code>.</p>
	 *
	 * @param includeDeleted a boolean.
	 */
	public void setIncludeDeleted(boolean includeDeleted) {
		this.includeDeleted = includeDeleted;
	}

	/**
	 * <p>Getter for the field <code>dataCreazioneFrom</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getDataCreazioneFrom() {
		return dataCreazioneFrom;
	}

	/**
	 * <p>Setter for the field <code>dataCreazioneFrom</code>.</p>
	 *
	 * @param dataCreazioneFrom a {@link java.util.Date} object.
	 */
	public void setDataCreazioneFrom(Date dataCreazioneFrom) {
		this.dataCreazioneFrom = dataCreazioneFrom;
	}

	/**
	 * <p>Getter for the field <code>dataCreazioneTo</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getDataCreazioneTo() {
		return dataCreazioneTo;
	}

	/**
	 * <p>Setter for the field <code>dataCreazioneTo</code>.</p>
	 *
	 * @param dataCreazioneTo a {@link java.util.Date} object.
	 */
	public void setDataCreazioneTo(Date dataCreazioneTo) {
		this.dataCreazioneTo = dataCreazioneTo;
	}

	/**
	 * <p>isSomeInFilter.</p>
	 *
	 * @return a boolean.
	 */
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

	/**
	 * <p>Setter for the field <code>someInFilter</code>.</p>
	 *
	 * @param someInFilter a boolean.
	 */
	public void setSomeInFilter(boolean someInFilter) {
		this.someInFilter = someInFilter;
	}

	/**
	 * Ritorna la condizione Where da concatenare alla query in funzione dei
	 * criteri impostati
	 *
	 * @return a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	@XmlTransient
	public String getSQLWhere() throws Exception {
		initSQLWhere();

		condizioneWhereAppends();

		return condizioneWhere.toString();
	}

	/**
	 * <p>condizioneWhereAppends.</p>
	 *
	 * @throws java.lang.Exception if any.
	 */
	public abstract void condizioneWhereAppends() throws Exception;

	/**
	 * Questo metodo viene usato quando si costruiscer il sqlWhere per evitare
	 * che si concatenino una serie di condizioni continuando a cliccare cerca e
	 * con il filtro savestatato
	 */
	protected void initSQLWhere() {
		condizioneWhere = new StringBuilder(QueryBuilder.ALWAYS_TRUE);
	}

	/**
	 * <p>getSQLSort.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
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

	/**
	 * <p>getSQLLimit.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
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

	/**
	 * <p>initVistePredefinite.</p>
	 */
	public abstract void initVistePredefinite();

	private Connection connection;

	/**
	 * Ci sono casi in cui il filtro usa la connessione per caricare ad esempio
	 * gli ID della LINKS da filtrare in quei casi aper una sua connessione ma
	 * forse puo' essere utile passare al filtro la connessione e farla usare
	 * al filtro stesso. Nel caso del programma di test qeusto e' utile ma anche
	 * nel caso di transazioni
	 *
	 * @return a {@link java.sql.Connection} object.
	 */
	@XmlTransient
	public Connection getConnection() {
		return connection;
	}

	/**
	 * <p>Setter for the field <code>connection</code>.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 */
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

	/* rappresenta un frammeto di query che può essere concatenato alla query composta dal filtro */
	protected String queryFragment;
	
	/**
	 * <p>Setter for the field <code>queryFragment</code>.</p>
	 *
	 * @param queryFragment a {@link java.lang.String} object.
	 */
	public void setQueryFragment(String queryFragment) {
		this.queryFragment = queryFragment;
	}
	
	/**
	 * <p>Getter for the field <code>queryFragment</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getQueryFragment() {
		return queryFragment;
	}
	
}
