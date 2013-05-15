package it.attocchi.jsf2.pages;

import it.attocchi.jpa2.JPAEntityFilter;
import it.attocchi.jpa2.JpaController;
import it.attocchi.jsf2.PageBaseNoAuth;
import it.attocchi.utils.Crono;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Mirco
 * 
 * @param <T>
 * @param <F>
 */
public abstract class PageBaseList<T extends Serializable, F extends JPAEntityFilter<T>> extends PageBaseNoAuth {

	protected List<T> elenco;
	protected F filtro;
	protected Class<T> clazz;
	protected int count;
	protected String persistentUnit;

	public List<T> getElenco() {
		return elenco;
	}

	public void setElenco(List<T> elenco) {
		this.elenco = elenco;
	}

	public F getFiltro() {
		return filtro;
	}

	public void setFiltro(F filtro) {
		this.filtro = filtro;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	protected void init() throws Exception {

		// Lo Spostiamo prima di fare la verfica del login altrimenti abbiamo il
		// filtro che e' a null
		inizializeMembers();

		Crono.start("onPreLoadData");
		onPreLoadData();
		logger.debug(Crono.stopAndLog("onPreLoadData"));

		Crono.start("loadData");
		loadData();
		logger.debug(Crono.stopAndLog("loadData"));

		Crono.start("onPostLoadData");
		onPostLoadData();
		logger.debug(Crono.stopAndLog("onPostLoadData"));

		// postInit();
	}

	protected void loadData() throws Exception {

		if (StringUtils.isNotBlank(persistentUnit))
			elenco = JpaController.callFindPU(persistentUnit, clazz, filtro);
		else
			elenco = JpaController.callFind(getEmfShared(), clazz, filtro);

		if (elenco == null || elenco.size() == 0) {
			addErrorMessage("Nessun Dato");
		} else {
			count = elenco.size();
		}
	}

	protected void loadDataException() {
		try {
			loadData();
			onPostLoadData();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}

	/**
	 * 
	 * @param filtro
	 * @param clazz
	 */
	// protected abstract void inizializeMembers(F filtro, Class<T> clazz);
	protected abstract void inizializeMembers() throws Exception;

	// abstract postInit();
	protected abstract void onPreLoadData() throws Exception;

	protected abstract void onPostLoadData() throws Exception;

	public String actionReload() {
		loadDataException();
		return "";
	}
	
	public void actionListenerReload(ActionEvent event) {
		loadDataException();
	}
}
