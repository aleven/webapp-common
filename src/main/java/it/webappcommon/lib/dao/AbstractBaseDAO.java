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

package it.webappcommon.lib.dao;

import it.attocchi.sql.QueryBuilder;
import it.webappcommon.lib.jsf.AbstractFiltro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Classe astratta per la definizione dei metodi base che un dao deve supportare
 * In questo modo chiunque fa i metodi base allo stesso modo
 *
 * @author Mirco
 * @version $Id: $Id
 */
public abstract class AbstractBaseDAO<T extends AbstractDataMappingBase, F extends AbstractFiltro> {

	protected Logger logger = Logger.getLogger(this.getClass().getName());

	public class LabelCount {
		private String label;
		private String count;

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

	}

	/** Constant <code>SUFFISSO_UTENTE_CREAZIONE_USR01="_utente_creazione_usr01"</code> */
	protected static final String SUFFISSO_UTENTE_CREAZIONE_USR01 = "_utente_creazione_usr01";

	/** Constant <code>SUFFISSO_DT_CREAZIONE="_dt_creazione"</code> */
	protected static final String SUFFISSO_DT_CREAZIONE = "_dt_creazione";

	/** Constant <code>SUFFISSO_UTENTE_MODIFICA_USR01="_utente_modifica_usr01"</code> */
	protected static final String SUFFISSO_UTENTE_MODIFICA_USR01 = "_utente_modifica_usr01";

	/** Constant <code>SUFFISSO_TS_MODIFICA="_ts_modifica"</code> */
	protected static final String SUFFISSO_TS_MODIFICA = "_ts_modifica";

	/** Constant <code>SUFFISSO_UTENTE_CANCELLAZIONE_USR01="_utente_cancellazione_usr01"</code> */
	protected static final String SUFFISSO_UTENTE_CANCELLAZIONE_USR01 = "_utente_cancellazione_usr01";

	/** Constant <code>SUFFISSO_DT_CANCELLAZIONE="_dt_cancellazione"</code> */
	protected static final String SUFFISSO_DT_CANCELLAZIONE = "_dt_cancellazione";

	protected String nomeTabella;
	protected String campoID;
	protected String prefissoCampi;
	protected Connection conn;
	/*
	 * Variabile importantissima che gestisce il comportamento unificato del DAO
	 * La gestione delle transazioni e la chiusura della connessione al termine
	 * delle azioni del dao dipende da questa variabile Si cambia
	 * automaticamente di valore se la connessione viene aperta dal DAO e non
	 * viene passata dall'esterno
	 */
	protected Boolean connApertaQui = false;

	/** Constant <code>MSG_ID_VALORIZZATO="Impossibile inserire un nuovo oggetto c"{trunked}</code> */
	protected static final String MSG_ID_VALORIZZATO = "Impossibile inserire un nuovo oggetto che ha id %s";
	/** Constant <code>MSG_ID_NON_VALORIZZATO="Impossibile aggiornare un oggetto che n"{trunked}</code> */
	protected static final String MSG_ID_NON_VALORIZZATO = "Impossibile aggiornare un oggetto che non ha id ";

	private String campoDataCreazioneNonStd;
	private String campoIDUtenteCreazioneNonStd;
	private String campoDataModificaNonStd;
	private String campoIDUtenteModificaNonStd;
	private String campoDataCancellazioneNonStd;
	private String campoIDUtenteCancellazioneNonStd;

	protected boolean uiUtentePrivilegiato;

	//
	// private PreparedStatement prpStmt = null;
	// private StringBuilder sqlQuery = null;
	// private ResultSet rs = null;

	/**
	 * E' il costruttore di default da usare E' stato fatto per le classi che
	 * ereditano in modo che possano comunicare il nome della tabella a cui fare
	 * riferimento e il nome del campo della data cancellazione per fare delle
	 * query di base
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 * @param nomeTabella a {@link java.lang.String} object.
	 * @param campoID a {@link java.lang.String} object.
	 * @param prefissoCampi a {@link java.lang.String} object.
	 */
	public AbstractBaseDAO(Connection conn, String nomeTabella, String campoID, String prefissoCampi) {

		this.setConnection(conn);

		this.setNomeTabella(nomeTabella);
		this.setCampoID(campoID);
		this.setPrefissoCampi(prefissoCampi);
	}

	/**
	 * Questo costruttore permette di specificare i nomi dei campi non
	 * starndard. Nel caso un campo non sia presente nella tabella, ad esempio
	 * "utente modifica" basta specificarlo come stringa vuota e non viene
	 * usato.
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 * @param nomeTabella a {@link java.lang.String} object.
	 * @param campoID a {@link java.lang.String} object.
	 * @param campoDataCreazioneNonStandard a {@link java.lang.String} object.
	 * @param campoIDUtenteCreazioneNonStd a {@link java.lang.String} object.
	 * @param campoDataModificaNonStandard a {@link java.lang.String} object.
	 * @param campoIDUtenteModificaNonStd a {@link java.lang.String} object.
	 * @param campoDataCancellazioneNonStandard a {@link java.lang.String} object.
	 * @param campoIDUtenteCancellazioneNonStd a {@link java.lang.String} object.
	 */
	public AbstractBaseDAO(Connection conn, String nomeTabella, String campoID, String campoDataCreazioneNonStandard, String campoIDUtenteCreazioneNonStd, String campoDataModificaNonStandard, String campoIDUtenteModificaNonStd, String campoDataCancellazioneNonStandard, String campoIDUtenteCancellazioneNonStd) {

		this.setConnection(conn);

		this.setNomeTabella(nomeTabella);
		this.setCampoID(campoID);
		this.campoDataCreazioneNonStd = campoDataCreazioneNonStandard;
		this.campoIDUtenteCreazioneNonStd = campoIDUtenteCreazioneNonStd;
		this.campoDataModificaNonStd = campoDataModificaNonStandard;
		this.campoIDUtenteModificaNonStd = campoIDUtenteModificaNonStd;
		this.campoDataCancellazioneNonStd = campoDataCancellazioneNonStandard;
		this.campoIDUtenteCancellazioneNonStd = campoIDUtenteCancellazioneNonStd;
	}

	/**
	 * <p>getNum.</p>
	 *
	 * @param filtro a F object.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public int getNum(F filtro) throws Exception {
		int res = 0;

		PreparedStatement prpStmt = null;
		StringBuilder sqlQuery = null;
		ResultSet rs = null;

		try {
			sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT COUNT(*) as TOT FROM ");
			sqlQuery.append(" " + getNomeTabella() + " ");
			sqlQuery.append("WHERE ");
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLWhere());
			} else {
				sqlQuery.append(QueryBuilder.ALWAYS_TRUE); // TODO: se il
				// filtro e' null
				// non
				// posso accodare il resto delle
				// conzioni di base se non faccio
				// questo.
			}
			if (getCampoDataCancellazione() != null && !filtro.isIncludeDeleted()) {
				sqlQuery.append(" AND " + getCampoDataCancellazione() + " IS NULL");
			}

			String sqlString = sqlQuery.toString();
			logger.debug(sqlString);
			prpStmt = getConnection().prepareStatement(sqlString);
			logger.debug(prpStmt);
			rs = prpStmt.executeQuery();

			if (rs.first()) {
				res = rs.getInt("TOT");
			}

		} catch (Exception ex) {
			// rollback();
			logger.error(prpStmt, ex);
			throw ex;
		} finally {
			DAOUtils.close(getConnection(), prpStmt, rs, connApertaQui);
		}

		return res;
	}

	/**
	 * <p>getAll.</p>
	 *
	 * @param filtro filtro da applicare
	 * @return un nuovo ArrayList anche se non ci sono risultati dall query
	 * @throws java.lang.Exception eccezione
	 */
	public List<T> getAll(F filtro) throws Exception {
		List<T> returnValue = new ArrayList<T>();

		PreparedStatement prpStmt = null;
		StringBuilder sqlQuery = null;
		ResultSet rs = null;

		try {

			sqlQuery = new StringBuilder();

			sqlQuery.append("SELECT * FROM ");
			sqlQuery.append(" " + getNomeTabella() + " ");
			sqlQuery.append("WHERE ");
			// Verifico se e' specificato un filtro
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLWhere());
			} else {
				sqlQuery.append(QueryBuilder.ALWAYS_TRUE); // TODO: se il
				// filtro e' null
				// non
				// posso accodare il resto delle
				// conzioni di base se non faccio
				// questo.
			}
			// Verifico se e' specificato un campo cancellazione
			if (getCampoDataCancellazione() != null && ((filtro == null) || (filtro != null && !filtro.isIncludeDeleted()))) {
				sqlQuery.append(" AND " + getCampoDataCancellazione() + " IS NULL");
			}
			// Verifico se e' specificato un ordinamento
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLSort());
			}
			// Verifico se e' specificato un limit per le paginazioni
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLLimit());
			}

			// prpStmt = getConnection().prepareStatement(sqlQuery.toString());
			String sqlString = sqlQuery.toString();
			logger.debug(sqlString);
			prpStmt = getConnection().prepareStatement(StringUtils.normalizeSpace(sqlString));
			logger.debug(prpStmt);
			rs = prpStmt.executeQuery();

			while (rs.next()) {
				returnValue.add(this.filler(rs));
			}

			// commit();
		} catch (Exception ex) {
			// rollback();
			logger.error(prpStmt, ex);
			throw ex;
		} finally {
			DAOUtils.close(getConnection(), prpStmt, rs, connApertaQui);
		}

		return returnValue;
	}

	/**
	 * <p>getTop1.</p>
	 *
	 * @param filtro a F object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public T getTop1(F filtro) throws Exception {
		T returnValue = null;

		PreparedStatement prpStmt = null;
		StringBuilder sqlQuery = null;
		ResultSet rs = null;

		try {

			sqlQuery = new StringBuilder();

			sqlQuery.append("SELECT * FROM ");
			sqlQuery.append(" " + getNomeTabella() + " ");
			sqlQuery.append("WHERE ");
			// Verifico se e' specificato un filtro
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLWhere());
			} else {
				sqlQuery.append(QueryBuilder.ALWAYS_TRUE); // TODO: se il
				// filtro e' null
				// non
				// posso accodare il resto delle
				// conzioni di base se non faccio
				// questo.
			}
			// Verifico se e' specificato un campo cancellazione
			if (getCampoDataCancellazione() != null && !filtro.isIncludeDeleted()) {
				sqlQuery.append(" AND " + getCampoDataCancellazione() + " IS NULL");
			}
			// Verifico se e' specificato un ordinamento
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLSort());
			}
			// Verifico se e' specificato un limit per le paginazioni
			if (filtro != null) {
				// sqlQuery.append(filtro.getSQLLimit());
				sqlQuery.append(" LIMIT 1 ");
			}

			String sqlString = sqlQuery.toString();
			logger.debug(sqlString);
			prpStmt = getConnection().prepareStatement(sqlString);
			logger.debug(prpStmt);
			rs = prpStmt.executeQuery();

			// if (rs.first()) {
			while (rs.next()) {
				returnValue = this.filler(rs);
				break;
			}
			// }

			// commit();
		} catch (Exception ex) {
			// rollback();
			logger.error(prpStmt, ex);
			throw ex;
		} finally {
			DAOUtils.close(getConnection(), prpStmt, rs, connApertaQui);
		}

		return returnValue;
	}

	/**
	 * Permette di ricercare un Campo ID e di Ritornare una Lista di Interi E'
	 * possibile scegliere il campo sul quale fare il distinct cosi' si puo' *
	 * usare anche per i LINKS efare il distinct su id tabella 1 o 2 Se non
	 * viene specificato un campoID viene fatto sul campo ID del Dao
	 *
	 * @param filtro filtro da applicare
	 * @param campoIdDistinct campo ID su cui applicare DISTINCT
	 * @return lista da ID
	 * @throws java.lang.Exception errore
	 */
	public List<Integer> getAllIdDistinct(F filtro, String campoIdDistinct) throws Exception {
		List<Integer> res = new ArrayList<Integer>();

		PreparedStatement prpStmt = null;
		StringBuilder sqlQuery = null;
		ResultSet rs = null;

		try {
			sqlQuery = new StringBuilder();

			if (StringUtils.isEmpty(campoIdDistinct)) {
				campoIdDistinct = getCampoID();
			}

			sqlQuery.append("SELECT DISTINCT(" + campoIdDistinct + ") as IDTROVATI FROM ");
			sqlQuery.append(" " + getNomeTabella() + " ");
			sqlQuery.append("WHERE ");

			// Verifico se e' specificato un filtro
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLWhere());
			} else {
				sqlQuery.append(QueryBuilder.ALWAYS_TRUE); // TODO: se il
				// filtro e' null
				// non
				// posso accodare il resto delle
				// conzioni di base se non faccio
				// questo.
			}
			// Verifico se e' specificato un campo cancellazione
			if (getCampoDataCancellazione() != null && !filtro.isIncludeDeleted()) {
				sqlQuery.append(" AND " + getCampoDataCancellazione() + " IS NULL");
			}
			/*
			 * NON ORDINO, i campi predefiniti di ordinamento del filtro
			 * potrebbero essere non specificati nel distinct e query con MySQL
			 * 5.7 va in errore
			 */
			// Verifico se e' specificato un ordinamento
//			if (filtro != null) {
//				sqlQuery.append(filtro.getSQLSort());
//			}
			// Verifico se e' specificato un limit per le paginazioni
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLLimit());
			}

			String sqlString = sqlQuery.toString();
			logger.debug(sqlString);
			prpStmt = getConnection().prepareStatement(sqlString);
			logger.debug(prpStmt);
			rs = prpStmt.executeQuery();

			while (rs.next()) {
				res.add(rs.getInt("IDTROVATI"));
			}

		} catch (Exception ex) {
			// rollback();
			logger.error(prpStmt, ex);
			throw ex;
		} finally {
			DAOUtils.close(getConnection(), prpStmt, rs, connApertaQui);
		}

		return res;

	}

	/**
	 * <p>getAllFieldDistinct.</p>
	 *
	 * @param filtro a F object.
	 * @param field a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <X> a X object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <X> List<X> getAllFieldDistinct(F filtro, String field, Class<X> clazz) throws Exception {
		List<X> res = new ArrayList<X>();

		PreparedStatement prpStmt = null;
		StringBuilder sqlQuery = null;
		ResultSet rs = null;

		try {
			sqlQuery = new StringBuilder();

			if (StringUtils.isEmpty(field)) {
				field = getCampoID();
			}

			sqlQuery.append("SELECT DISTINCT(" + field + ") as IDTROVATI FROM ");
			sqlQuery.append(" " + getNomeTabella() + " ");
			sqlQuery.append("WHERE ");

			// Verifico se e' specificato un filtro
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLWhere());
			} else {
				sqlQuery.append(QueryBuilder.ALWAYS_TRUE); // TODO: se il
				// filtro e' null
				// non
				// posso accodare il resto delle
				// conzioni di base se non faccio
				// questo.
			}
			// Verifico se e' specificato un campo cancellazione
			if (getCampoDataCancellazione() != null && !filtro.isIncludeDeleted()) {
				sqlQuery.append(" AND " + getCampoDataCancellazione() + " IS NULL");
			}
			// Verifico se e' specificato un ordinamento
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLSort());
			}
			// Verifico se e' specificato un limit per le paginazioni
			if (filtro != null) {
				sqlQuery.append(filtro.getSQLLimit());
			}

			String sqlString = sqlQuery.toString();
			logger.debug(sqlString);
			prpStmt = getConnection().prepareStatement(sqlString);
			logger.debug(prpStmt);
			rs = prpStmt.executeQuery();

			while (rs.next()) {
				res.add(clazz.cast(rs.getObject("IDTROVATI")));
			}

		} catch (Exception ex) {
			// rollback();
			logger.error(prpStmt, ex);
			throw ex;
		} finally {
			DAOUtils.close(getConnection(), prpStmt, rs, connApertaQui);
		}

		return res;

	}

	/**
	 * <p>filler.</p>
	 *
	 * @param rs a {@link java.sql.ResultSet} object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public abstract T filler(ResultSet rs) throws Exception;

	/**
	 * Si preoccupa di caricare i dati base di tutti gli AbstractDataMappingBase
	 *
	 * @param oggettoDaCaricare a T object.
	 * @param rs a {@link java.sql.ResultSet} object.
	 * @throws java.lang.Exception if any.
	 */
	protected void fillerBase(T oggettoDaCaricare, ResultSet rs) throws Exception {

		// Campi standard per Abstract
		if (this.getCampoDataCreazione() != null) {
			oggettoDaCaricare.setDataCreazione(rs.getTimestamp(this.getCampoDataCreazione()));
		}
		if (this.getCampoIDUtenteCreazione() != null) {
			oggettoDaCaricare.setIdUtenteCreazione(rs.getInt(this.getCampoIDUtenteCreazione()));
		}
		if (this.getCampoDataModifica() != null) {
			oggettoDaCaricare.setDataModifica(rs.getTimestamp(this.getCampoDataModifica()));
		}
		if (this.getCampoIDUtenteModifica() != null) {
			oggettoDaCaricare.setIdUtenteModifica(rs.getInt(this.getCampoIDUtenteModifica()));
		}
		if (this.getCampoDataCancellazione() != null) {
			oggettoDaCaricare.setDataCancellazione(rs.getTimestamp(this.getCampoDataCancellazione()));
		}
		if (this.getCampoIDUtenteCancellazione() != null) {
			oggettoDaCaricare.setIdUtenteCancellazione(rs.getInt(this.getCampoIDUtenteCancellazione()));
		}

		oggettoDaCaricare.setUiUtentePrivilegiato(uiUtentePrivilegiato);

	}

	/**
	 * <p>insert.</p>
	 *
	 * @param oggettoDaInserire a T object.
	 * @param idUtente a int.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public abstract int insert(T oggettoDaInserire, int idUtente) throws Exception;

	/**
	 * <p>insertAndFill.</p>
	 *
	 * @param oggettoDaInserire a T object.
	 * @param idUtente a int.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public T insertAndFill(T oggettoDaInserire, int idUtente) throws Exception {
		T returnValue = null;

		int id = this.insert(oggettoDaInserire, idUtente);

		returnValue = this.getById(id);

		return returnValue;
	}

	/**
	 * <p>update.</p>
	 *
	 * @param oggettoDaAggiornare a T object.
	 * @param idUtente a int.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public abstract int update(T oggettoDaAggiornare, int idUtente) throws Exception;

	/**
	 * <p>updateAndFill.</p>
	 *
	 * @param oggettoDaAggiornare a T object.
	 * @param id a int.
	 * @param idUtente a int.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public T updateAndFill(T oggettoDaAggiornare, int id, int idUtente) throws Exception {
		T returnValue = null;

		// int id = oggettoDaAggiornare.getId();
		this.update(oggettoDaAggiornare, idUtente);

		returnValue = this.getById(id);

		return returnValue;
	}

	/**
	 * <p>delete.</p>
	 *
	 * @param idElemento a int.
	 * @param idUtente a int.
	 * @throws java.lang.Exception if any.
	 */
	public void delete(int idElemento, int idUtente) throws Exception {
		// T returnValue = null;

		PreparedStatement prpStmt = null;
		StringBuilder sqlQuery = null;
		int res = 0;

		try {
			if (idElemento > 0 && getCampoDataCancellazione() != null && getCampoID() != null) {
				beginTransaction();

				sqlQuery = new StringBuilder();

				sqlQuery.append("UPDATE ");
				sqlQuery.append(" " + getNomeTabella() + " ");
				sqlQuery.append(" SET " + getCampoDataCancellazione() + " = NOW(), ");
				sqlQuery.append(" " + getCampoIDUtenteCancellazione() + " = " + idUtente + " ");
				sqlQuery.append("WHERE TRUE");

				sqlQuery.append(" AND " + getCampoID() + " = " + idElemento);

				String sqlString = sqlQuery.toString();
				logger.debug(sqlString);
				prpStmt = getConnection().prepareStatement(sqlString);
				logger.debug(prpStmt);
				res = prpStmt.executeUpdate();

				// if (rs.first()) {
				// returnValue = this.filler(rs);
				// }

				commit();
			}
		} catch (Exception ex) {
			rollback();
			logger.error(prpStmt, ex);
			throw ex;
		} finally {
			DAOUtils.close(getConnection(), prpStmt, connApertaQui);
		}

		// return returnValue;
	}

	/**
	 * <p>getById.</p>
	 *
	 * @param id a int.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public T getById(int id) throws Exception {
		T returnValue = null;

		PreparedStatement prpStmt = null;
		StringBuilder sqlQuery = null;
		ResultSet rs = null;

		try {

			sqlQuery = new StringBuilder();

			sqlQuery.append("SELECT * FROM ");
			sqlQuery.append(" " + getNomeTabella() + " ");
			sqlQuery.append("WHERE TRUE");
			// Verifico se e' specificato un filtro
			if (getCampoID() != null) {
				sqlQuery.append(" AND " + getCampoID() + " = " + String.valueOf(id));
			}
			// Verifico se e' specificato un campo cancellazione
			if (getCampoDataCancellazione() != null) {
				sqlQuery.append(" AND " + getCampoDataCancellazione() + " IS NULL");
			}

			String sqlString = sqlQuery.toString();
			logger.debug(sqlString);
			prpStmt = getConnection().prepareStatement(sqlString);
			logger.debug(prpStmt);
			rs = prpStmt.executeQuery();

			if (rs.first()) {
				returnValue = this.filler(rs);
			}

			// commit();
		} catch (Exception ex) {
			// rollback();
			logger.error(prpStmt, ex);
			throw ex;
		} finally {
			DAOUtils.close(getConnection(), prpStmt, rs, connApertaQui);
		}

		return returnValue;
	}

	/**
	 * <p>Setter for the field <code>nomeTabella</code>.</p>
	 *
	 * @param nomeTabella a {@link java.lang.String} object.
	 */
	protected void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

	/**
	 * <p>Getter for the field <code>nomeTabella</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNomeTabella() {
		return nomeTabella;
	}

	/**
	 * <p>getCampoDataCancellazione.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCampoDataCancellazione() {
		// return (StringUtils.isEmpty(campoDataCancellazioneNonStd)) ?
		// prefissoCampi + SUFFISSO_DT_CANCELLAZIONE :
		// campoDataCancellazioneNonStd;
		return (campoDataCancellazioneNonStd == null) ? prefissoCampi + SUFFISSO_DT_CANCELLAZIONE : (campoDataCancellazioneNonStd.isEmpty()) ? null : campoDataCancellazioneNonStd;
	}

	/**
	 * <p>getCampoIDUtenteCancellazione.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCampoIDUtenteCancellazione() {
		// return (StringUtils.isEmpty(campoIDUtenteCancellazioneNonStd)) ?
		// prefissoCampi + SUFFISSO_UTENTE_CANCELLAZIONE_USR01 :
		// campoIDUtenteCancellazioneNonStd;
		return (campoIDUtenteCancellazioneNonStd == null) ? prefissoCampi + SUFFISSO_UTENTE_CANCELLAZIONE_USR01 : (campoIDUtenteCancellazioneNonStd.isEmpty()) ? null : campoIDUtenteCancellazioneNonStd;
	}

	/**
	 * <p>getCampoDataModifica.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCampoDataModifica() {
		// return (StringUtils.isEmpty(campoDataModificaNonStd)) ? prefissoCampi
		// + SUFFISSO_TS_MODIFICA : campoDataModificaNonStd;
		return (campoDataModificaNonStd == null) ? prefissoCampi + SUFFISSO_TS_MODIFICA : (campoDataModificaNonStd.isEmpty()) ? null : campoDataModificaNonStd;
	}

	/**
	 * <p>getCampoIDUtenteModifica.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCampoIDUtenteModifica() {
		// return (StringUtils.isEmpty(campoIDUtenteModificaNonStd)) ?
		// prefissoCampi + SUFFISSO_UTENTE_MODIFICA_USR01 :
		// campoIDUtenteModificaNonStd;
		return (campoIDUtenteModificaNonStd == null) ? prefissoCampi + SUFFISSO_UTENTE_MODIFICA_USR01 : (campoIDUtenteModificaNonStd.isEmpty()) ? null : campoIDUtenteModificaNonStd;
	}

	/**
	 * <p>getCampoDataCreazione.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCampoDataCreazione() {
		return (campoDataCreazioneNonStd == null) ? prefissoCampi + SUFFISSO_DT_CREAZIONE : (campoDataCreazioneNonStd.isEmpty()) ? null : campoDataCreazioneNonStd;
	}

	/**
	 * <p>getCampoIDUtenteCreazione.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCampoIDUtenteCreazione() {
		// return (StringUtils.isEmpty(campoIDUtenteCreazioneNonStd)) ?
		// prefissoCampi + SUFFISSO_UTENTE_CREAZIONE_USR01 :
		// campoIDUtenteCreazioneNonStd;
		return (campoIDUtenteCreazioneNonStd == null) ? prefissoCampi + SUFFISSO_UTENTE_CREAZIONE_USR01 : (campoIDUtenteCreazioneNonStd.isEmpty()) ? null : campoIDUtenteCreazioneNonStd;
	}

	/**
	 * <p>Setter for the field <code>prefissoCampi</code>.</p>
	 *
	 * @param prefissoCampi a {@link java.lang.String} object.
	 */
	public void setPrefissoCampi(String prefissoCampi) {
		this.prefissoCampi = prefissoCampi;
	}

	/**
	 * <p>Getter for the field <code>prefissoCampi</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPrefissoCampi() {
		return prefissoCampi;
	}

	/**
	 * <p>Getter for the field <code>campoID</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCampoID() {
		return campoID;
	}

	/**
	 * <p>Setter for the field <code>campoID</code>.</p>
	 *
	 * @param campoID a {@link java.lang.String} object.
	 */
	public void setCampoID(String campoID) {
		this.campoID = campoID;
	}

	// protected Connection getConnection() {
	// try {
	// if (conn == null || conn.isClosed()) {
	// conn = new AtreeflowDB().getConnection();
	// connApertaQui = true;
	// }
	// } catch (Exception ex) {
	// logger.error(ex);
	// }
	//
	// return conn;
	// }

	/**
	 * <p>getConnection.</p>
	 *
	 * @return a {@link java.sql.Connection} object.
	 */
	protected abstract Connection getConnection();

	/**
	 * <p>setConnection.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 */
	protected void setConnection(Connection conn) {
		this.conn = conn;
	}

	// /**
	// *
	// */
	// public void close() {
	// // Ho commentato per fare in modo che quando lo chiamo sempre venga
	// // chiusa
	// if (connApertaQui) {
	// DAOUtils.close(getConnection());
	// }
	// }

	/**
	 * <p>beginTransaction.</p>
	 */
	protected void beginTransaction() {
		try {
			getConnection();
			if (connApertaQui) {
				getConnection().setAutoCommit(false);
			}
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	/**
	 * <p>commit.</p>
	 */
	protected void commit() {
		try {
			if (connApertaQui && !getConnection().getAutoCommit()) {
				getConnection().commit();
			}
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	/**
	 * <p>rollback.</p>
	 */
	protected void rollback() {
		try {
			if (connApertaQui && !getConnection().getAutoCommit()) {
				getConnection().rollback();
			}
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	/**
	 * <p>executeUpdate.</p>
	 *
	 * @param aSqlQuery a {@link java.lang.String} object.
	 * @return a int.
	 * @throws java.sql.SQLException if any.
	 */
	public int executeUpdate(String aSqlQuery) throws SQLException {
		PreparedStatement prpStmt = null;
		int res = 0;

		try {
			if (StringUtils.isNotEmpty(aSqlQuery)) {

				/*
				 * Non posso Normalizzare gli spazi nel caso in cui ci sia un
				 * where ad esempio impostato dall'utente con doppio spazio
				 */
				logger.debug(aSqlQuery);
				prpStmt = getConnection().prepareStatement(aSqlQuery);
				logger.debug(prpStmt);
				res = prpStmt.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			// if (closeConnection) {
			DAOUtils.close(getConnection(), prpStmt, connApertaQui);
			// }
		}

		return res;
	}

	/**
	 * Impostato nel dao viene usato nel filler per impostare su ogni singolo
	 * oggetto se e' stato caricato da un utente privilegiato
	 *
	 * @param uiUtentePrivilegiato uiUtentePrivilegiato
	 */
	public void setUiUtentePrivilegiato(boolean uiUtentePrivilegiato) {
		this.uiUtentePrivilegiato = uiUtentePrivilegiato;
	}

	// protected void finalize() throws Throwable
	// {
	// //do finalization here
	// super.finalize(); //not necessary if extending Object.
	// }

	/** {@inheritDoc} */
	@Override
	protected void finalize() throws Throwable {
		if (connApertaQui) {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
			}
		}
		super.finalize();
	}
}
