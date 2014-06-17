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

import it.webappcommon.lib.jpa.entities.annotations.Description;
import it.webappcommon.lib.jpa.entities.annotations.ToCheckOnDelete;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;

public abstract class EntityBaseStandard implements Serializable {

	// public abstract Object getPKValue();

	protected static final Logger logger = Logger.getLogger(EntityBaseStandard.class.getName());

	private boolean _selected;

	/** Creates a new instance of EntityBase */
	public EntityBaseStandard() {
		super();
	}

	public void beforeCreate() throws Exception {
	}

	public void afterCreate() throws Exception {
	}

	public void beforeUpdate() throws Exception {
	}

	public void afterUpdate() throws Exception {
	}

	public void beforeDelete() throws Exception {
	}

	public void afterDelete() throws Exception {
	}

	public final boolean isDeletable() throws Exception {
		boolean res = true;
		Method[] methods = null;
		ToCheckOnDelete methodToCheckOnDelete = null;
		Collection collection = null;
		try {
			methods = getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				methodToCheckOnDelete = methods[i].getAnnotation(ToCheckOnDelete.class);
				if (methodToCheckOnDelete != null) {
					if (methods[i].getReturnType().equals(java.util.Collection.class)) {
						collection = (Collection) methods[i].invoke(this);
						if ((collection != null) && (collection.size() > 0)) {
							res = false;
							break;
						}
					}
				}
				methodToCheckOnDelete = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			methods = null;
			methodToCheckOnDelete = null;
			collection = null;
		}
		return res;
	}

	public abstract void updateEntityLastModification() throws Exception;

	// protected final void updateEntityLastModification() throws Exception {
	// EntityLastModification entityLastModification;
	// try {
	// entityLastModification = new EntityLastModification(
	// this.getClass().getCanonicalName(), new Date());
	// new ControllerKatiaServer().edit(entityLastModification);
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// entityLastModification = null;
	// }
	// }

	public String getValueOf(String field) {
		String returnValue = null;
		Class c = null;
		Method[] methods = null;
		try {
			c = this.getClass();
			methods = c.getMethods();
			returnValue = "";
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().toUpperCase().equals(field.toUpperCase())) {
					Object objReturnValue = methods[i].invoke(this);
					if (objReturnValue != null) {
						if (methods[i].getReturnType() == Date.class) {
							Calendar data = new GregorianCalendar();
							data.setTime((Date) objReturnValue);
							StringBuffer stringa = new StringBuffer();
							if (data.get(Calendar.DAY_OF_MONTH) < 10) {
								stringa.append("0");
							}
							stringa.append(String.valueOf(data.get(Calendar.DAY_OF_MONTH)));
							stringa.append("/");
							if (data.get(Calendar.MONTH) < 9) {
								stringa.append("0");
							}
							stringa.append(String.valueOf(data.get(Calendar.MONTH) + 1));
							stringa.append("/");
							stringa.append(String.valueOf(data.get(Calendar.YEAR)));
							stringa.append(" ");
							if (data.get(Calendar.HOUR_OF_DAY) < 10) {
								stringa.append("0");
							}
							stringa.append(String.valueOf(data.get(Calendar.HOUR_OF_DAY)));
							stringa.append(":");
							if (data.get(Calendar.MINUTE) < 10) {
								stringa.append("0");
							}
							stringa.append(String.valueOf(data.get(Calendar.MINUTE)));
							stringa.append(":");
							if (data.get(Calendar.SECOND) < 10) {
								stringa.append("0");
							}
							stringa.append(String.valueOf(data.get(Calendar.SECOND)));
							returnValue = stringa.toString();
						} else if (methods[i].getReturnType() == ArrayList.class) {
							if (objReturnValue != null) {
								returnValue = objReturnValue.toString();
								returnValue = returnValue.substring(1, returnValue.length() - 1);
							} else {
								returnValue = "";
							}

						} else {
							returnValue = objReturnValue.toString();
						}
					}
					break;
				}
			}

		} catch (Exception e) {
			returnValue = "";
		}
		return returnValue;
	}

	public String getDescriptionOf(String field) {
		String returnValue = null;
		Class c = null;
		Method[] methods = null;
		try {
			c = this.getClass();
			methods = c.getMethods();
			returnValue = "";
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().toUpperCase().equals(field.toUpperCase())) {
					Description des = methods[i].getAnnotation(Description.class);
					if (des != null) {
						returnValue = des.value();
					}
					break;
				}
			}
		} catch (Exception e) {
			returnValue = "";
		}
		return returnValue;
	}

	@XmlTransient
	public boolean isSelected() {
		return _selected;
	}

	public void setSelected(boolean selected) {
		this._selected = selected;
	}

	// / Mettiamo questa se dovesse servirci nel Controller ri-caricare una
	// entit� passata e non sappiamo come recuperare l'ID
	// public abstract Long getId();

	/*
	 * MERGE DA ATREEHELPDESK
	 */
	/**
	 * Questoi metodo permette di inserire nella base di dati l'EntityBase in
	 * oggetto
	 * 
	 * @throws java.lang.Exception
	 */
	// @Deprecated
	// public void inserisci() throws Exception {
	// EntityManager em = null;
	// boolean tActive = false;
	// try {
	// /*
	// * Verifico che la transizione sia attiva, in caso negativo la
	// * attivo
	// */
	// em =
	// MultiplePersistenceManagerTest.getInstance().getEntityManagerFactory()
	// .createEntityManager();
	// tActive = em.getTransaction().isActive();
	// if (!tActive) {
	// em.getTransaction().begin();
	// }
	//
	// /* Inserisco l'EntityBase nella base di dati */
	// em.persist(this);
	//
	// /* Chiudo la transazione solamente se l'ho aperta in questo metodo */
	// if (!tActive && em.getTransaction().isActive()) {
	// em.getTransaction().commit();
	// }
	// } catch (Exception ex) {
	// /*
	// * In caso di errore devo effettuare il rollBack solamente se ho
	// * aperto la transazione in questo metodo
	// */
	// if (!tActive && em.getTransaction().isActive()) {
	// em.getTransaction().rollback();
	// }
	// throw ex;
	// }
	// }

	/**
	 * 
	 * Metodo che permette la modifica dell'oggetto EntityBase
	 * 
	 * @throws java.lang.Exception
	 */
	// @Deprecated
	// public void modifica() throws Exception {
	// EntityManager em = null;
	// boolean tActive = false;
	// try {
	// /*
	// * Verifico che la transazione non sia aperta, ed in caso negativo
	// * la devo aprire
	// */
	// em = PersistenceManager.getInstance().getEntityManagerFactory()
	// .createEntityManager();
	// tActive = em.getTransaction().isActive();
	// if (!tActive) {
	// em.getTransaction().begin();
	// }
	//
	// /* Effettuo la modifica */
	// em.merge(this);
	//
	// /* Committo la transazione solamente se l'ho aperta in questo metodo */
	// if (!tActive && em.getTransaction().isActive()) {
	// em.getTransaction().commit();
	// }
	// } catch (Exception ex) {
	// /*
	// * In caso di errore devo effettuare il rollBack solamente se ho
	// * aperto la transazione in questo metodo
	// */
	// if (!tActive && em.getTransaction().isActive()) {
	// em.getTransaction().rollback();
	// }
	// throw ex;
	// }
	// }

	/**
	 * 
	 * Metodo che permette la cancellazione dell'oggetto EntityBase
	 * 
	 * @throws java.lang.Exception
	 */
	// @Deprecated
	// public void cancella() throws Exception {
	// EntityManager em = null;
	// boolean tActive = false;
	// try {
	// /* Verifico che la transazione sia aperta, in caso negativo la apro */
	// em =
	// MultiplePersistenceManagerTest.getInstance().getEntityManagerFactory()
	// .createEntityManager();
	// tActive = em.getTransaction().isActive();
	// if (!tActive) {
	// em.getTransaction().begin();
	// }
	//
	// /* Effettuo la cancellazione */
	// em.remove(em.merge(this));
	//
	// /* Se ho aperto la transazione in questo metodo la committo */
	// if (!tActive && em.getTransaction().isActive()) {
	// em.getTransaction().commit();
	// }
	// } catch (Exception ex) {
	//
	// /*
	// * In caso di errore faccio il rollBack della transazione solamente
	// * se l'ho aperta in questo metodo
	// */
	// if (!tActive && em.getTransaction().isActive()) {
	// em.getTransaction().rollback();
	// }
	// throw ex;
	// }
	// }
}