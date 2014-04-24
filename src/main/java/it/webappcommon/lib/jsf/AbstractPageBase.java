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

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

//import org.apache.shale.view.AbstractViewController;
/**
 * 
 * @author Mirco
 */
public abstract class AbstractPageBase implements Serializable {

	public class PageBaseInfo implements Serializable {
		private String titolo;
		private String titoloPagina;
		private String nomeOggetto;
		private String nomeOggetti;

		public String getTitolo() {
			return titolo;
		}

		public void setTitolo(String titolo) {
			this.titolo = titolo;
		}

		public String getTitoloPagina() {
			return titoloPagina;
		}

		public void setTitoloPagina(String titoloPagina) {
			this.titoloPagina = titoloPagina;
		}

		public String getNomeOggetto() {
			return nomeOggetto;
		}

		public void setNomeOggetto(String nomeOggetto) {
			this.nomeOggetto = nomeOggetto;
		}

		public String getNomeOggetti() {
			return nomeOggetti;
		}

		public void setNomeOggetti(String nomeOggetti) {
			this.nomeOggetti = nomeOggetti;
		}

	}

	// PhaseListener

	/**
     *
     */
	// private static final long serialVersionUID = 1L;
	//
	// protected static Logger logger =
	// Logger.getLogger(AbstractPageBase.class.getName());
	//
	private String _js_alert_message = "";
	private String m_titolo_pagina;
	private String m_titolo;
	private int m_righe_per_pagina;
	//
	protected PageBaseInfo pageInfo;

	//
	// private Connection requestScopedConnection;
	//
	// public Connection getRequestScopedConnection() {
	//
	// try {
	// if (requestScopedConnection == null ||
	// requestScopedConnection.isClosed()) {
	// requestScopedConnection = new AtreeflowDB().getConnection();
	// logger.debug("requestScopedConnection open");
	// }
	// } catch (Exception ex) {
	// logger.error(ex);
	// }
	//
	// return requestScopedConnection;
	// }
	//
	// public void setRequestScopedConnection(Connection
	// requestScopedConnection) {
	// this.requestScopedConnection = requestScopedConnection;
	// }

	// /** Creates a new instance of PageBase */
	// public PageBase() {
	// // try {
	// //
	// if(Configuration.getIstance().get(ChiaveConfig.GEN_ABILITA_AUTENTICAZIONE_WINDOWS).equalsIgnoreCase("true"))
	// {
	// // eseguiAccessoWindows();
	// // }
	// // } catch (Exception e) {
	// // System.out.println(new Date() + " Errore PageBase " + e.getMessage());
	// // }
	// }

	// private void eseguiAccessoWindows() throws Exception {
	// FacesContext facesContext = null;
	// String auth = null;
	// String user_agent = null;
	// String workstation = null;
	// String domain = null;
	// String username = null;
	// Utente nuovoUtente = null;
	//
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	//
	// HttpServletRequest httpRequest = (HttpServletRequest)
	// facesContext.getExternalContext().getRequest();
	// HttpServletResponse httpResponse = (HttpServletResponse)
	// facesContext.getExternalContext().getResponse();
	// String requestPath = httpRequest.getServletPath();
	// logger.debug("requestPath " + requestPath);
	//
	//
	// if ((requestPath != null && requestPath.endsWith("index.jspx")) ||
	// (requestPath != null && requestPath.endsWith("status.jspx"))) {
	// return;
	// }
	//
	// if (isValidate()) {
	// logger.debug("Dati in sessione o richiesta pagina di login. " +
	// httpRequest.getContextPath());
	// } else {
	//
	// /* COMMENTATO per problemi POST JSF */
	// // // <editor-fold defaultstate="collapsed"
	// desc="DETERMINAZIONE UTENTE WINDOWS">
	//
	// /* La tecnica utilizzata (settaggio header) per la determinazione
	// * dell'utente Windows e' funzionante esclusivamente con browser
	// * Microsoft Internet Explorer. Se altro browser salta questo step
	// */
	// user_agent = httpRequest.getHeader("user-agent");
	// if ((user_agent != null) && (user_agent.indexOf("MSIE") > -1)) {
	// auth = httpRequest.getHeader("Authorization");
	// if (auth == null) {
	// httpResponse.setStatus(httpResponse.SC_UNAUTHORIZED);
	// httpResponse.setHeader("WWW-Authenticate", "NTLM");
	// httpResponse.flushBuffer();
	// return;
	// }
	//
	// if (auth.startsWith("NTLM ")) {
	// byte[] msg = Base64.decode(auth.substring(5));
	// // byte[] msg = new
	// sun.misc.BASE64Decoder().decodeBuffer(auth.substring(5));
	// int off = 0, length, offset;
	// if (msg[8] == 1) {
	//
	// byte z = 0;
	// byte[] msg1 = {(byte) 'N', (byte) 'T', (byte) 'L', (byte) 'M',
	// (byte) 'S', (byte) 'S', (byte) 'P',
	// z, (byte) 2, z, z, z, z, z, z, z, (byte) 40, z, z, z,
	// (byte) 1, (byte) 130, z, z, z, (byte) 2, (byte) 2,
	// (byte) 2, z, z, z, z, z, z, z, z, z, z, z, z
	// };
	// httpResponse.setHeader("WWW-Authenticate", "NTLM " + new
	// sun.misc.BASE64Encoder().encodeBuffer(msg1));
	// httpResponse.setHeader("WWW-Authenticate", "NTLM " +
	// Base64.encode(msg1));
	//
	// httpResponse.sendError(httpResponse.SC_UNAUTHORIZED);
	//
	// return;
	// } else if (msg[8] == 3) {
	//
	// off = 30;
	// length = msg[off + 17] * 256 + msg[off + 16];
	// offset = msg[off + 19] * 256 + msg[off + 18];
	// workstation = new String(msg, offset, length);
	//
	// length = msg[off + 1] * 256 + msg[off];
	// offset = msg[off + 3] * 256 + msg[off + 2];
	// domain = new String(msg, offset, length);
	//
	// length = msg[off + 9] * 256 + msg[off + 8];
	// offset = msg[off + 11] * 256 + msg[off + 10];
	// username = new String(msg, offset, length);
	//
	// char a = 0;
	// char b = 32;
	// logger.debug("Username:" + username.trim().replace(a, b).replaceAll("",
	// ""));
	// username = username.trim().replace(a, b).replaceAll(" ", "");
	// workstation = workstation.trim().replace(a, b).replaceAll(" ", "");
	// domain = domain.trim().replace(a, b).replaceAll(" ", "");
	//
	// logger.debug("Username: " + username);
	// logger.debug("RemoteHost: " + workstation);
	// logger.debug("Domain: " + domain);
	// }
	// }
	// }
	//
	// try {
	// if (username != null) {
	// nuovoUtente = UtentiDAO.getUtenteByWindowsLogin(username);
	// }
	// } catch (Exception e) {
	// }
	// if (nuovoUtente != null) {
	// auth_authenticate test = new auth_authenticate();
	// test.setUsername(nuovoUtente.getUsername());
	// test.setPassword(nuovoUtente.getPassword());
	// String result = test.getValidUser();
	// if (result.equalsIgnoreCase(auth_authenticate.LOGIN_EFFETTUATO)) {
	// logger.debug("Autenticazione mediante Windows come " + username + ".");
	// logger.debug(result);
	// }
	//
	//
	// } else {
	// logger.debug("Inoltro alla pagina di login.");
	// httpResponse.sendRedirect(httpRequest.getContextPath() +
	// "/Auth/index.jspx");
	// }
	// }
	// } catch (Exception ex) {
	// throw ex;
	// }
	// }

	// /**
	// *
	// * Metodo che restituisce il valore di un parametro dato il nome del
	// parammetro
	// *
	// * @param parameter_name
	// * @return Object
	// */
	// public Object getParamObject(String parameter_name) {
	// Object returnValue = null;
	//
	// FacesContext facesContext = null;
	// Map params = null;
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// params = facesContext.getExternalContext().getRequestParameterMap();
	// returnValue = params.get(parameter_name);
	// } catch (Exception e) {
	// returnValue = null;
	// } finally {
	// facesContext = null;
	// params = null;
	// }
	//
	// return returnValue;
	// }

	// public String getParamObjectAsString(String parameter_name) {
	// Object tmp = null;
	// String returnValue = null;
	//
	// FacesContext facesContext = null;
	// Map params = null;
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// params = facesContext.getExternalContext().getRequestParameterMap();
	// tmp = params.get(parameter_name);
	// if (tmp != null) {
	// returnValue = String.valueOf(tmp);
	// }
	//
	// } catch (Exception e) {
	// returnValue = null;
	// } finally {
	// facesContext = null;
	// params = null;
	// tmp = null;
	// }
	//
	// return returnValue;
	// }

	// public Object getParamObject(String parameter_name) {
	//
	// FacesContext facesContext = null;
	// Map params = null;
	// Object returnValue = null;
	//
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// params = facesContext.getExternalContext().getRequestParameterMap();
	// returnValue = params.get(parameter_name);
	// } catch (Exception e) {
	// returnValue = null;
	// } finally {
	// facesContext = null;
	// params = null;
	// }
	//
	// return returnValue;
	// }

	// public Object getSessionObject(String object_name) {
	//
	// FacesContext facesContext = null;
	// Map session_objects = null;
	// Object returnValue = null;
	//
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// session_objects = facesContext.getExternalContext().getSessionMap();
	// returnValue = session_objects.get(object_name);
	// } catch (Exception e) {
	// returnValue = null;
	// } finally {
	// facesContext = null;
	// session_objects = null;
	// }
	//
	// return returnValue;
	// }

	public Object getApplicationObject(String object_name) {

		FacesContext facesContext = null;
		Map application_objects = null;
		Object returnValue = null;

		try {
			facesContext = FacesContext.getCurrentInstance();
			application_objects = facesContext.getExternalContext().getApplicationMap();
			returnValue = application_objects.get(object_name);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			application_objects = null;
		}

		return returnValue;
	}

	// public HttpServletResponse getResponse() {
	//
	// FacesContext facesContext = null;
	// HttpServletResponse response = null;
	//
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// response = (HttpServletResponse)
	// facesContext.getCurrentInstance().getExternalContext().getResponse();
	// } catch (Exception e) {
	// response = null;
	// } finally {
	// facesContext = null;
	// }
	//
	// return response;
	// }
	//
	// public HttpServletRequest getRequest() {
	//
	// FacesContext facesContext = null;
	// HttpServletRequest request = null;
	//
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// request = (HttpServletRequest)
	// facesContext.getCurrentInstance().getExternalContext().getRequest();
	// } catch (Exception e) {
	// request = null;
	// } finally {
	// facesContext = null;
	// }
	//
	// return request;
	// }

	// public Object setSessionObject(String object_name, Object object) {
	//
	// FacesContext facesContext = null;
	// Map session_objects = null;
	// Object returnValue = null;
	//
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// session_objects = facesContext.getExternalContext().getSessionMap();
	// session_objects.remove(object_name);
	// returnValue = session_objects.put(object_name, object);
	// } catch (Exception e) {
	// returnValue = null;
	// } finally {
	// facesContext = null;
	// session_objects = null;
	// }
	//
	// return returnValue;
	// }

	public Object setApplicationObject(String object_name, Object object) {

		FacesContext facesContext = null;
		Map application_objects = null;
		Object returnValue = null;

		try {
			facesContext = FacesContext.getCurrentInstance();
			application_objects = facesContext.getExternalContext().getApplicationMap();
			returnValue = application_objects.put(object_name, object);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			application_objects = null;
		}

		return returnValue;
	}

	/**
	 * Rimuove dalla sessione l'oggetto specificato.
	 * 
	 * @param object_name
	 *            Oggetto da rimuovere dalla sessione.
	 * @return Ritorna il valore dell'oggetto prima della rimozione, oppure null
	 *         se non viene trovato l'oggetto in sessione.
	 * 
	 */
	// public Object removeSessionObject(String object_name) {
	//
	// FacesContext facesContext = null;
	// Map session_objects = null;
	// Object returnValue = null;
	//
	// try {
	// facesContext = FacesContext.getCurrentInstance();
	// session_objects = facesContext.getExternalContext().getSessionMap();
	// returnValue = session_objects.remove(object_name);
	// } catch (Exception e) {
	// returnValue = null;
	// } finally {
	// facesContext = null;
	// session_objects = null;
	// }
	//
	// return returnValue;
	// }

	public String getJs_alert_message() {
		/*
		 * Essendo che questa stringa e' stata pensata come parametro di un
		 * alert() JavaScript nella pagina JSP, e' necessario rimpiazzare gli
		 * apici (') con (\\'). Per� al momento degli action i get dei
		 * back-bean vengono effettuati pi� volte (2) e quindi i rimpiazzi
		 * risulterebbero troppi, ecco perch� prima si tolgono e poi si
		 * rimettono.
		 */
		_js_alert_message = _js_alert_message.replace("\\'", "'");
		_js_alert_message = _js_alert_message.replace("'", "\\'");
		_js_alert_message = _js_alert_message.replaceAll("\n\t", " ");
		_js_alert_message = _js_alert_message.replaceAll("\n", " ");
		_js_alert_message = _js_alert_message.replaceAll("\r", " ");
		return _js_alert_message;
	}

	public void setJs_alert_message(String js_alert_message) {
		if (js_alert_message == null) {
			js_alert_message = "Errore sconosciuto!";
		}
		this._js_alert_message = js_alert_message;
	}

	protected void addInfoMessage(String testo) {
		addInfoMessage(testo, null);
	}

	protected void addInfoMessage(String testo, String dettaglio) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary(testo);
		if (StringUtils.isNotEmpty(dettaglio)) {
			message.setDetail(dettaglio);
		}
		getFacesContext().addMessage(testo, message);
	}

	protected void addErrorMessage(String testo) {
		addErrorMessage(testo, null);
	}

	protected void addErrorMessage(String testo, String dettaglio) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		message.setSummary(testo);
		if (StringUtils.isNotEmpty(dettaglio)) {
			message.setDetail(dettaglio);
		}
		getFacesContext().addMessage(testo, message);
	}

	public boolean isPostBack() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("JSFPostBack") != null;
	}

	/**
	 * Metodo che permette di ordinare, in ordine crescente o decrescente in
	 * base ad ascending, un arraylist di oggetti di qualsiasi tipo in base al
	 * parametro column. In particolare, con la reflection, viene gestito
	 * l'ordinamento per column se essa e' int, long, double, float, boolean,
	 * String, Date, Integer. Se non e' di uno di questi tipi, l'ordinamento non
	 * fa niente in quanto il compareTo del comparator definito all'interno del
	 * metodo ritorna sempre 0. La column, va indicata, come sempre del resto,
	 * senza il get o l'is davanti.
	 * 
	 * @param list
	 *            ArrayList da ordinare
	 * @param column
	 *            Nome della colonna/propriet� dell'oggetto in base alla quale
	 *            effettuare l'ordinamento
	 * @param ascending
	 *            Booleano che specifica se l'ordinamento deve essere ascendente
	 *            o meno
	 */
	public void sort(List list, final String column, final boolean ascending) {
		// TODO: Gestione di property a pi� livelli: ad esempio
		// ElementBL.element.nome
		Comparator comparator = null;

		if ((column != null) && (!column.equals("")) && (list != null) && (list.size() > 0)) {

			final Class item_class = list.get(0).getClass();

			comparator = new Comparator() {

				public int compare(Object o1, Object o2) {

					PropertyDescriptor column_descriptor = null;
					Method read_method = null;
					Object obj1 = null;
					Object obj2 = null;
					Object resInvoke1 = null;
					Object resInvoke2 = null;
					Date date1 = null;
					Date date2 = null;
					String str1 = null;
					String str2 = null;
					Integer Int1 = null;
					Integer Int2 = null;
					Double Double1 = null;
					Double Double2 = null;
					Float Float1 = null;
					Float Float2 = null;
					int returnValue = 0;

					try {

						if ((column == null) || (o1 == null) || (o2 == null)) {

							/*
							 * In caso di non specificazione della colonna o di
							 * uno dei due oggetti ritorna 0, facendo in modo
							 * che l'ordinamento non ha alcun effetto
							 */
							returnValue = 0;

						} else {

							/*
							 * Tenta di ottenere un property descriptor a
							 * partire dalla colonna
							 */
							try {

								column_descriptor = new PropertyDescriptor(column, item_class);
								read_method = column_descriptor.getReadMethod();

							} catch (Exception e) {

								read_method = null;

							}

							if (read_method != null) {

								obj1 = item_class.cast(o1);
								obj2 = item_class.cast(o2);

								/*
								 * Viene gestito l'ordinamento per String,
								 * Boolean, int
								 */
								if (read_method.getReturnType() == int.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo primitivo int">
									/*
									 * Variabili di tipo primitivo e'
									 * impossibile che siano NULL
									 */
									str1 = read_method.invoke(obj1).toString();
									str2 = read_method.invoke(obj2).toString();

									int int1 = Integer.parseInt(str1);
									int int2 = Integer.parseInt(str2);

									if (ascending) {

										if (int1 < int2) {
											returnValue = -1;
										} else if (int1 > int2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									} else {

										if (int1 > int2) {
											returnValue = -1;
										} else if (int1 < int2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == long.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo primitivo long">
									/*
									 * Variabili di tipo primitivo e'
									 * impossibile che siano NULL
									 */
									str1 = read_method.invoke(obj1).toString();
									str2 = read_method.invoke(obj2).toString();

									long lng1 = Long.parseLong(str1);
									long lng2 = Long.parseLong(str2);

									if (ascending) {

										if (lng1 < lng2) {
											returnValue = -1;
										} else if (lng1 > lng2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									} else {

										if (lng1 > lng2) {
											returnValue = -1;
										} else if (lng1 < lng2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == double.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo primitivo double">
									/*
									 * Variabili di tipo primitivo e'
									 * impossibile che siano NULL
									 */
									str1 = read_method.invoke(obj1).toString();
									str2 = read_method.invoke(obj2).toString();

									double dbl1 = Double.parseDouble(str1);
									double dbl2 = Double.parseDouble(str2);

									if (ascending) {

										if (dbl1 < dbl2) {
											returnValue = -1;
										} else if (dbl1 > dbl2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									} else {

										if (dbl1 > dbl2) {
											returnValue = -1;
										} else if (dbl1 < dbl2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == float.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo primitivo float">
									/*
									 * Variabili di tipo primitivo e'
									 * impossibile che siano NULL
									 */
									str1 = read_method.invoke(obj1).toString();
									str2 = read_method.invoke(obj2).toString();

									float flt1 = Float.parseFloat(str1);
									float flt2 = Float.parseFloat(str2);

									if (ascending) {

										if (flt1 < flt2) {
											returnValue = -1;
										} else if (flt1 > flt2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									} else {

										if (flt1 > flt2) {
											returnValue = -1;
										} else if (flt1 < flt2) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == Float.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo object Float">
									/*
									 * Variabili di tipo object e' impossibile
									 * che siano NULL
									 */
									/*
									 * Pu� essere che la propriet� degli
									 * oggetti sia NULL. In quel caso il
									 * toString() genera errore se non gestito.
									 * Se resInvoke1 oppure resInvoke2 sono
									 * NULL, anche le rispettive conversioni in
									 * interi lo devono essere
									 */
									resInvoke1 = read_method.invoke(obj1);
									resInvoke2 = read_method.invoke(obj2);
									if (resInvoke1 != null) {
										Float1 = (Float) resInvoke1;
									}
									if (resInvoke2 != null) {
										Float2 = (Float) resInvoke2;
									}

									if (ascending) {

										if ((Float1 != null) && (Float2 != null)) {
											returnValue = Float1.compareTo(Float2);
										} else if ((Float1 == null) && (Float2 != null)) {
											returnValue = -1;
										} else if ((Float1 != null) && (Float2 == null)) {
											returnValue = 1;
										} else if ((Float1 == null) && (Float2 == null)) {
											returnValue = 0;
										}

									} else {

										if ((Float1 != null) && (Float2 != null)) {
											returnValue = Float2.compareTo(Float1);
										} else if ((Float1 != null) && (Float2 == null)) {
											returnValue = -1;
										} else if ((Float1 == null) && (Float2 != null)) {
											returnValue = 1;
										} else if ((Float1 == null) && (Float2 == null)) {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == boolean.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo primitivo boolean">
									/*
									 * Variabili di tipo primitivo e'
									 * impossibile che siano NULL
									 */
									str1 = read_method.invoke(obj1).toString();
									str2 = read_method.invoke(obj2).toString();

									boolean bool1 = Boolean.parseBoolean(str1);
									boolean bool2 = Boolean.parseBoolean(str2);

									if (ascending) {

										if ((!bool1) && (bool2)) {
											returnValue = -1;
										} else if ((bool1) && (!bool2)) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									} else {

										if ((bool1) && (!bool2)) {
											returnValue = -1;
										} else if ((!bool1) && (bool2)) {
											returnValue = 1;
										} else {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == String.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo object String">
									/*
									 * Pu� essere che la propriet� degli
									 * oggetti sia NULL. In quel caso il
									 * toString() genera errore se non gestito.
									 * Se resInvoke1
									 * 
									 * oppure resInvoke2 sono NULL, anche le
									 * rispettive conversioni in stringa lo
									 * devono essere
									 */
									resInvoke1 = read_method.invoke(obj1);
									resInvoke2 = read_method.invoke(obj2);
									if (resInvoke1 != null) {
										str1 = resInvoke1.toString().toUpperCase();
									}
									if (resInvoke2 != null) {
										str2 = resInvoke2.toString().toUpperCase();
									}

									if (ascending) {

										if ((str1 != null) && (str2 != null)) {
											returnValue = str1.compareTo(str2);
										} else if ((str1 == null) && (str2 != null)) {
											returnValue = -1;
										} else if ((str1 != null) && (str2 == null)) {
											returnValue = 1;
										} else if ((str1 == null) && (str2 == null)) {
											returnValue = 0;
										}

									} else {

										if ((str1 != null) && (str2 != null)) {
											returnValue = str2.compareTo(str1);
										} else if ((str1 != null) && (str2 == null)) {
											returnValue = -1;
										} else if ((str1 == null) && (str2 != null)) {
											returnValue = 1;
										} else if ((str1 == null) && (str2 == null)) {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == Date.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo object Date">
									/*
									 * Pu� essere che la propriet� degli
									 * oggetti sia NULL. In quel caso il
									 * toString() genera errore se non gestito.
									 * Se resInvoke1 oppure resInvoke2 sono
									 * NULL, anche le rispettive conversioni in
									 * date lo devono essere
									 */
									resInvoke1 = read_method.invoke(obj1);
									resInvoke2 = read_method.invoke(obj2);
									if (resInvoke1 != null) {
										date1 = (Date) resInvoke1;
									}
									if (resInvoke2 != null) {
										date2 = (Date) resInvoke2;
									}

									if (ascending) {

										if ((date1 != null) && (date2 != null)) {
											returnValue = date1.compareTo(date2);
										} else if ((date1 == null) && (date2 != null)) {
											returnValue = -1;
										} else if ((date1 != null) && (date2 == null)) {
											returnValue = 1;
										} else if ((date1 == null) && (date2 == null)) {
											returnValue = 0;
										}

									} else {

										if ((date1 != null) && (date2 != null)) {
											returnValue = date2.compareTo(date1);
										} else if ((date1 != null) && (date2 == null)) {
											returnValue = -1;
										} else if ((date1 == null) && (date2 != null)) {
											returnValue = 1;
										} else if ((date1 == null) && (date2 == null)) {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == Integer.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo object Integer">
									/*
									 * Pu� essere che la propriet� degli
									 * oggetti sia NULL. In quel caso il
									 * toString() genera errore se non gestito.
									 * Se resInvoke1 oppure resInvoke2 sono
									 * NULL, anche le rispettive conversioni in
									 * interi lo devono essere
									 */
									resInvoke1 = read_method.invoke(obj1);
									resInvoke2 = read_method.invoke(obj2);
									if (resInvoke1 != null) {
										Int1 = (Integer) resInvoke1;
									}
									if (resInvoke2 != null) {
										Int2 = (Integer) resInvoke2;
									}

									if (ascending) {

										if ((Int1 != null) && (Int2 != null)) {
											returnValue = Int1.compareTo(Int2);
										} else if ((Int1 == null) && (Int2 != null)) {
											returnValue = -1;
										} else if ((Int1 != null) && (Int2 == null)) {
											returnValue = 1;
										} else if ((Int1 == null) && (Int2 == null)) {
											returnValue = 0;
										}

									} else {

										if ((Int1 != null) && (Int2 != null)) {
											returnValue = Int2.compareTo(Int1);
										} else if ((Int1 != null) && (Int2 == null)) {
											returnValue = -1;
										} else if ((Int1 == null) && (Int2 != null)) {
											returnValue = 1;
										} else if ((Int1 == null) && (Int2 == null)) {
											returnValue = 0;
										}

									}
									// </editor-fold>

								} else if (read_method.getReturnType() == Double.class) {

									// <editor-fold defaultstate="collapsed"
									// desc="Gestione tipo object Double">
									/*
									 * Pu� essere che la propriet� degli
									 * oggetti sia NULL. In quel caso il
									 * toString() genera errore se non gestito.
									 * Se resInvoke1 oppure resInvoke2 sono
									 * NULL, anche le rispettive conversioni in
									 * interi lo devono essere
									 */
									resInvoke1 = read_method.invoke(obj1);
									resInvoke2 = read_method.invoke(obj2);
									if (resInvoke1 != null) {
										Double1 = (Double) resInvoke1;
									}
									if (resInvoke2 != null) {
										Double2 = (Double) resInvoke2;
									}

									if (ascending) {

										if ((Double1 != null) && (Double2 != null)) {
											returnValue = Double1.compareTo(Double2);
										} else if ((Double1 == null) && (Double2 != null)) {
											returnValue = -1;
										} else if ((Double1 != null) && (Double2 == null)) {
											returnValue = 1;
										} else if ((Double1 == null) && (Double2 == null)) {
											returnValue = 0;
										}

									} else {

										if ((Double1 != null) && (Double2 != null)) {
											returnValue = Double2.compareTo(Double1);
										} else if ((Double1 != null) && (Double2 == null)) {
											returnValue = -1;
										} else if ((Double1 == null) && (Double2 != null)) {
											returnValue = 1;
										} else if ((Double1 == null) && (Double2 == null)) {
											returnValue = 0;
										}

									}
									// </editor-fold>

								}

							} else {

								/*
								 * Nel caso in cui non ci sia il metodo get
								 * della colonna passata, ritorna 0, facendo in
								 * modo che l'ordinamento non ha alcun effetto
								 */
								returnValue = 0;

							}

						}

					} catch (Exception e) {

						/*
						 * In caso d'errore in Comparator ritorna 0, facendo in
						 * modo che l'ordinamento non ha alcun effetto
						 */

						returnValue = 0;

					} finally {

						/* Clean-up oggetti */
						column_descriptor = null;
						read_method = null;
						obj1 = null;
						obj2 = null;
						resInvoke1 = null;
						resInvoke2 = null;
						date1 = null;
						date2 = null;
						str1 = null;
						str2 = null;
						Int1 = null;
						Int2 = null;
						Float1 = null;
						Float2 = null;

					}

					return returnValue;

				}
			};

			if (comparator != null) {
				Collections.sort(list, comparator);
			}

		}

		comparator = null;

	}

	public TimeZone getTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
		return TimeZone.getDefault();
	}

	public Locale getLocale() {
		return Locale.getDefault();
	}

	/**
	 * Questa funzione ritorna il valore della chiave specificata prelevandola
	 * dalla tabella cfg02_opzioni_pagine. Se l'opzione non viene trovata nel DB
	 * viene inserita con valore = valore_default.
	 */
	// public String getOpzionePagina(String chiave, String valore_default)
	// throws Exception {
	// String returnValue = null;
	// IOpzione_pagina opzione_pagina = null;
	// String nome_classe = null;
	//
	// try {
	//
	// nome_classe = this.getClass().getCanonicalName();
	//
	// if ((valore_default == null) || (valore_default.equals(""))) {
	// throw new
	// Exception("Valore default non definito! Impossibile ottenere l'opzione dal database!");
	// }
	//
	// opzione_pagina =
	// Opzioni_pagineDAO.getOpzione_paginaBy(getIdUtenteLoggato(), nome_classe,
	// chiave);
	//
	// /*
	// * Se la chiave non esiste essa viene inserita nel db col valore di
	// * default passato come parametro alla funzione
	// */
	// if (opzione_pagina == null) {
	//
	// setOpzionePagina(chiave, valore_default);
	// returnValue = valore_default;
	//
	// } else {
	//
	// /*
	// * Se la chiave esiste ed il valore e' nullo o stringa vuota
	// * viene aggiornato con il valore di default passato come
	// * parametro alla funzione
	// */
	// if ((opzione_pagina.getValore() == null) ||
	// (opzione_pagina.getValore().equals(""))) {
	//
	// setOpzionePagina(chiave, valore_default);
	// returnValue = valore_default;
	//
	// } else {
	//
	// returnValue = opzione_pagina.getValore();
	//
	// }
	// }
	//
	// } catch (Exception e) {
	//
	// throw e;
	//
	// } finally {
	//
	// opzione_pagina = null;
	// nome_classe = null;
	//
	// }
	// return returnValue;
	// }

	/**
	 * Questa funzione ritorna il valore della chiave specificata prelevandola
	 * dalla tabella cfg02_opzioni_pagine. Se l'opzione non viene trovata nel DB
	 * viene inserita con valore = valore_default.
	 */
	// public String getOpzionePaginaNome_classe(String chiave, String
	// nome_classe, String valore_default) throws Exception {
	// String returnValue = null;
	// Opzione_pagina opzione_pagina = null;
	//
	// try {
	//
	// if ((valore_default == null) || (valore_default.equals(""))) {
	// throw new
	// Exception("Valore default non definito! Impossibile ottenere l'opzione dal database!");
	// }
	//
	// opzione_pagina =
	// Opzioni_pagineDAO.getOpzione_paginaBy(getIdUtenteLoggato, nome_classe,
	// chiave);
	//
	// /*
	// * Se la chiave non esiste essa viene inserita nel db col valore di
	// * default passato come parametro alla funzione
	// */
	// if (opzione_pagina == null) {
	//
	// setOpzionePaginaNome_classe(chiave, nome_classe, valore_default);
	// returnValue = valore_default;
	//
	// } else {
	//
	// /*
	// * Se la chiave esiste ed il valore e' nullo o stringa vuota
	// * viene aggiornato con il valore di default passato come
	// * parametro alla funzione
	// */
	// if ((opzione_pagina.getValore() == null) ||
	// (opzione_pagina.getValore().equals(""))) {
	//
	// setOpzionePagina(chiave, valore_default);
	// returnValue = valore_default;
	//
	// } else {
	//
	// returnValue = opzione_pagina.getValore();
	//
	// }
	// }
	//
	// } catch (Exception e) {
	//
	// throw e;
	//
	// } finally {
	//
	// opzione_pagina = null;
	//
	// }
	// return returnValue;
	// }

	/**
	 * Questa funzione ritorna il valore della chiave specificata prelevandola
	 * dalla tabella cfg02_opzioni_pagine. Se l'opzione non viene trovata nel DB
	 * viene inserita con valore = valore_default.
	 */
	// public String getOpzionePaginaBy(String chiave, String nome_classe,
	// String valore_default, int idUtente) throws Exception {
	// String returnValue = null;
	// Opzione_pagina opzione_pagina = null;
	//
	// try {
	//
	// if ((valore_default == null) || (valore_default.equals(""))) {
	// throw new
	// Exception("Valore default non definito! Impossibile ottenere l'opzione dal database!");
	// }
	//
	// opzione_pagina = Opzioni_pagineDAO.getOpzione_paginaBy(idUtente,
	// nome_classe, chiave);
	//
	// /*
	// * Se la chiave non esiste essa viene inserita nel db col valore di
	// * default passato come parametro alla funzione
	// */
	// if (opzione_pagina == null) {
	//
	// setOpzionePaginaNome_classe(chiave, nome_classe, valore_default);
	// returnValue = valore_default;
	//
	// } else {
	//
	// /*
	// * Se la chiave esiste ed il valore e' nullo o stringa vuota
	// * viene aggiornato con il valore di default passato come
	// * parametro alla funzione
	// */
	// if ((opzione_pagina.getValore() == null) ||
	// (opzione_pagina.getValore().equals(""))) {
	//
	// setOpzionePagina(chiave, valore_default);
	// returnValue = valore_default;
	//
	// } else {
	//
	// returnValue = opzione_pagina.getValore();
	//
	// }
	// }
	//
	// } catch (Exception e) {
	//
	// throw e;
	//
	// } finally {
	//
	// opzione_pagina = null;
	//
	// }
	// return returnValue;
	// }

	/**
	 * Metodo che effettua l'inserimento o l'aggiornamento della chiave opzione
	 * specificata.
	 */
	// public void setOpzionePagina(String chiave, String valore) throws
	// Exception {
	// Opzione_pagina opzione_pagina = null;
	// String nome_classe = null;
	//
	// try {
	//
	// nome_classe = this.getClass().getCanonicalName();
	//
	// opzione_pagina =
	// Opzioni_pagineDAO.getOpzione_paginaBy(getIdUtenteLoggato, nome_classe,
	// chiave);
	//
	// if (opzione_pagina == null) {
	//
	// opzione_pagina = new Opzione_pagina();
	// opzione_pagina.setId_utente(UtenteLoggato.getId_utenteLoggato());
	// opzione_pagina.setNome_classe(nome_classe);
	// opzione_pagina.setChiave(chiave);
	// opzione_pagina.setValore(valore);
	//
	// Opzioni_pagineDAO.insert(opzione_pagina);
	//
	// } else {
	//
	// opzione_pagina.setValore(valore);
	// Opzioni_pagineDAO.update(opzione_pagina);
	//
	// }
	//
	// } catch (Exception e) {
	//
	// throw e;
	//
	// } finally {
	//
	// opzione_pagina = null;
	// nome_classe = null;
	//
	// }
	// }

	/**
	 * Metodo che effettua l'inserimento o l'aggiornamento della chiave opzione
	 * specificata.
	 */
	// public void setOpzionePaginaNome_classe(String chiave, String
	// nome_classe, String valore) throws Exception {
	// Opzione_pagina opzione_pagina = null;
	//
	// try {
	//
	// opzione_pagina =
	// Opzioni_pagineDAO.getOpzione_paginaBy(getIdUtenteLoggato(), nome_classe,
	// chiave);
	//
	// if (opzione_pagina == null) {
	//
	// opzione_pagina = new Opzione_pagina();
	// opzione_pagina.setId_utente(UtenteLoggato.getId_utenteLoggato());
	// opzione_pagina.setNome_classe(nome_classe);
	// opzione_pagina.setChiave(chiave);
	// opzione_pagina.setValore(valore);
	//
	// Opzioni_pagineDAO.insert(opzione_pagina);
	//
	// } else {
	//
	// opzione_pagina.setValore(valore);
	// Opzioni_pagineDAO.update(opzione_pagina);
	//
	// }
	//
	// } catch (Exception e) {
	//
	// throw e;
	//
	// } finally {
	//
	// opzione_pagina = null;
	// nome_classe = null;
	//
	// }
	// }

	/**
	 * Metodo che effettua l'inserimento o l'aggiornamento della chiave opzione
	 * specificata.
	 */
	// public void setOpzionePaginaBy(String chiave, String nome_classe, int
	// idUtente, String valore) throws Exception {
	// Opzione_pagina opzione_pagina = null;
	//
	// try {
	//
	// opzione_pagina = Opzioni_pagineDAO.getOpzione_paginaBy(idUtente,
	// nome_classe, chiave);
	//
	// if (opzione_pagina == null) {
	//
	// opzione_pagina = new Opzione_pagina();
	// opzione_pagina.setId_utente(idUtente);
	// opzione_pagina.setNome_classe(nome_classe);
	// opzione_pagina.setChiave(chiave);
	// opzione_pagina.setValore(valore);
	//
	// Opzioni_pagineDAO.insert(opzione_pagina);
	//
	// } else {
	//
	// opzione_pagina.setValore(valore);
	// Opzioni_pagineDAO.update(opzione_pagina);
	//
	// }
	//
	// } catch (Exception e) {
	//
	// throw e;
	//
	// } finally {
	//
	// opzione_pagina = null;
	// nome_classe = null;
	//
	// }
	// }

	// /**
	// * Metodo privato che restituisce la lista di numeri concessi per le
	// * dataTable
	// **/
	// private ArrayList<SelectItem> getNumeroRighePerPagina() {
	// ArrayList<SelectItem> returnValue = null;
	// try {
	// returnValue = new ArrayList<SelectItem>();
	// returnValue.add(new SelectItem("5", "5"));
	// returnValue.add(new SelectItem("10", "10"));
	// returnValue.add(new SelectItem("15", "15"));
	// returnValue.add(new SelectItem("20", "20"));
	// returnValue.add(new SelectItem("25", "25"));
	// returnValue.add(new SelectItem("30", "30"));
	// returnValue.add(new SelectItem("35", "35"));
	// returnValue.add(new SelectItem("40", "40"));
	// returnValue.add(new SelectItem("45", "45"));
	// returnValue.add(new SelectItem("50", "50"));
	// returnValue.add(new SelectItem("60", "60"));
	// returnValue.add(new SelectItem("80", "80"));
	// returnValue.add(new SelectItem("100", "100"));
	// // returnValue.add(new SelectItem("150", "150"));
	// // returnValue.add(new SelectItem("200", "200"));
	// } catch (Exception e) {
	// returnValue = new ArrayList<SelectItem>();
	// }
	// return returnValue;
	// }

	/**
	 * Metodo pubblico che restituisce i numeri concessi e l'opzione Tutti
	 * (maschile)
	 **/
	public ArrayList<SelectItem> getNumeroRighePerPaginaMaschile() {
		ArrayList<SelectItem> returnValue = null;
		try {
			returnValue = new ArrayList<SelectItem>();
			// returnValue.add(new SelectItem("1000000000", "(Tutti)"));
			returnValue.addAll(this.getNumeroRighePerPagina());
		} catch (Exception e) {
			returnValue = new ArrayList<SelectItem>();
		}
		return returnValue;
	}

	/**
	 * Metodo pubblico che restituisce i numeri concessi e l'opzione Tutte
	 * (femminile)
	 **/
	public ArrayList<SelectItem> getNumeroRighePerPaginaFemminile() {
		ArrayList<SelectItem> returnValue = null;
		try {
			returnValue = new ArrayList<SelectItem>();
			// returnValue.add(new SelectItem("1000000000", "(Tutte)"));
			returnValue.addAll(this.getNumeroRighePerPagina());
		} catch (Exception e) {
			returnValue = new ArrayList<SelectItem>();
		}
		return returnValue;
	}

	public String getTitolo_pagina() {
		return m_titolo_pagina;
	}

	/**
	 * Rappresenta il titolo HTML della pagina
	 * 
	 * @param titolo_pagina
	 */
	public void setTitolo_pagina(String titolo_pagina) {
		this.m_titolo_pagina = titolo_pagina;
	}

	public String getTitolo() {
		return m_titolo;
	}

	/**
	 * Rappresenta il titolo H1 della pagina
	 * 
	 * @param titolo
	 */
	public void setTitolo(String titolo) {
		this.m_titolo = titolo;
	}

	public int getRighe_per_pagina() {
		return m_righe_per_pagina;
	}

	public void setRighe_per_pagina(int m_righe_per_pagina) {
		this.m_righe_per_pagina = m_righe_per_pagina;
	}

	/*
	 * Important: With Tomahawk 1.1.5 for RI compatibility, and adherence to the
	 * spec, itemValues can no longer coerce to the backing bean type. You may
	 * get the validation error "Value is not a valid option." when this occurs.
	 * Previous versions of MyFaces/Tomahawk did coerce the itemValue to the
	 * type of the backing bean, so applications that relied on that will no
	 * longer work as expected. Resolve this issue with creation of a String
	 * property that set and get value to a int property
	 */
	public String getRighe_per_pagina_string() {
		return String.valueOf(m_righe_per_pagina);
	}

	public void setRighe_per_pagina_string(String righe_per_pagina_string) {
		if (righe_per_pagina_string != null) {
			this.m_righe_per_pagina = Integer.parseInt(righe_per_pagina_string);
		}
	}

	// public int getIdUtenteLoggato() {
	// return UtenteLoggato.getId_utenteLoggato();
	// }
	//
	// public boolean isAdmin() {
	// boolean returnValue = false;
	// try {
	// returnValue = UtenteLoggato.isAdmin();
	// } catch (Exception e) {
	// returnValue = false;
	// }
	// return returnValue;
	// }

	// public boolean isValidate() {
	// // inizializzato l'errore... se accade un'eccezzione verr� cmq permesso
	// // al sw di proseguire,
	// // mostrando per� una pagina di errore
	// boolean resultValue = false;
	// SetSession validUser = null;
	// Object objectUser = null;
	// try {
	// resultValue = false;
	// // ricevo dalla sessione l'utente loggato
	// objectUser = this.getSessionObject(UtenteLoggato.VALID_USER);
	// // se non c'� in sessione sparo fuori l'errore
	// // ATTENZIONE!! teoricamente questa evenienza dovrebbe essere gia
	// // stata catturata dalla pagina jsp
	// if (objectUser != null) {
	// validUser = (SetSession) objectUser;
	// // dal ValidUser ricevo il ticket e controllo se e' valido
	// if
	// (TicketsBL.IsTicketValidWithResponse(validUser.getTicket()).equalsIgnoreCase("logIn"))
	// {
	// resultValue = true;
	// }
	// } else {
	// resultValue = false;
	// }
	// } catch (Exception e) {
	// e.getMessage();
	// } finally {
	// validUser = null;
	// objectUser = null;
	// }
	// return resultValue;
	// }

	public final void beforePhase(PhaseEvent event) {
		if (event.getPhaseId().equals(PhaseId.APPLY_REQUEST_VALUES)) {
			beforeApplyRequestValues(event);
		} else if (event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
			beforeInvokeApplication(event);
		} else if (event.getPhaseId().equals(PhaseId.PROCESS_VALIDATIONS)) {
			beforeProcessValidations(event);
		} else if (event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
			beforeRenderResponse(event);
		} else if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			beforeRestoreView(event);
		} else if (event.getPhaseId().equals(PhaseId.UPDATE_MODEL_VALUES)) {
			beforeUpdateModelValues(event);
		}
	}

	public final void afterPhase(PhaseEvent event) {

		if (event.getPhaseId().equals(PhaseId.APPLY_REQUEST_VALUES)) {
			afterApplyRequestValues(event);
		} else if (event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
			afterInvokeApplication(event);
		} else if (event.getPhaseId().equals(PhaseId.PROCESS_VALIDATIONS)) {
			afterProcessValidations(event);
		} else if (event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
			afterRenderResponse(event);
		} else if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
			afterRestoreView(event);
		} else if (event.getPhaseId().equals(PhaseId.UPDATE_MODEL_VALUES)) {
			afterUpdateModelValues(event);
		}
	}

	public void beforeApplyRequestValues(PhaseEvent event) {
	}

	public void beforeInvokeApplication(PhaseEvent event) {
	}

	public void beforeProcessValidations(PhaseEvent event) {
	}

	public void beforeRenderResponse(PhaseEvent event) {
	}

	public void beforeRestoreView(PhaseEvent event) {
	}

	public void beforeUpdateModelValues(PhaseEvent event) {
	}

	public void afterApplyRequestValues(PhaseEvent event) {
	}

	public void afterInvokeApplication(PhaseEvent event) {
	}

	public void afterProcessValidations(PhaseEvent event) {
	}

	public void afterRenderResponse(PhaseEvent event) {

		// logger.debug("afterRenderResponse");
		// try {
		// if (requestScopedConnection != null) {
		// requestScopedConnection.close();
		// logger.debug("requestScopedConnection closed");
		// }
		// } catch (Exception ex) {
		// logger.error(ex);
		// }

	}

	public void afterRestoreView(PhaseEvent event) {
	}

	public void afterUpdateModelValues(PhaseEvent event) {
	}

	// protected FacesContext getFacesContext() {
	// return FacesContext.getCurrentInstance();
	// }

	protected String getActionAttribute(ActionEvent event, String name) {
		return (String) event.getComponent().getAttributes().get(name);
	}

	protected Integer getActionAttributeAsInt(ActionEvent event, String name) {
		return (Integer) event.getComponent().getAttributes().get(name);
	}

	/**
	 * Mirco: ascolto tutto
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	protected void redirect(String url) {
		try {
			getFacesContext().getExternalContext().redirect(url);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public PageBaseInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageBaseInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	static Logger logger = Logger.getLogger(AbstractPageBase.class.getName());
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public AbstractPageBase() {
		// logger.debug("Verifico dati Login");
		// DatiSessione datiSessione = (DatiSessione)
		// getSessionObject(DatiSessione.SESSION_KEY);
		// if (datiSessione == null) {
		// // Redirect to Login
		// try {
		// getResponse().sendRedirect("faces/login.jsp");
		// } catch (Exception ex) {
		// logger.error("Impossibile eseguire il redirect a Login");
		// }
		// }
	}

	/**
	 * Metodo che restituisce un oggetto messo in sessione, dato il nome
	 * dell'oggetto stesso
	 * 
	 * @param object_name
	 * @return Object
	 */
	public Object getSessionObject(String object_name) {
		Object returnValue = null;

		FacesContext facesContext = null;
		Map sessionObjects = null;
		try {
			facesContext = FacesContext.getCurrentInstance();
			sessionObjects = facesContext.getExternalContext().getSessionMap();
			returnValue = sessionObjects.get(object_name);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			sessionObjects = null;
		}

		return returnValue;
	}

	/**
	 * 
	 * Metodo che mette un oggetto in sessione con il nome specificato dal
	 * parametro Ritorna l'oggetto stesso
	 * 
	 * @param object_name
	 * @param object
	 * @return Object
	 */
	public Object setSessionObject(String object_name, Object object) {
		Object returnValue = null;

		FacesContext facesContext = null;
		Map sessionObjects = null;
		try {
			facesContext = FacesContext.getCurrentInstance();
			sessionObjects = facesContext.getExternalContext().getSessionMap();
			sessionObjects.remove(object_name);
			returnValue = sessionObjects.put(object_name, object);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			sessionObjects = null;
		}

		return returnValue;
	}

	/**
	 * Metodo che rimuove un oggetto dalla sessione Ritorna l'oggetto rimosso
	 * 
	 * @param object_name
	 * @return Object
	 */
	public Object removeSessionObject(String object_name) {
		Object returnValue = null;

		FacesContext facesContext = null;
		Map sessionObjects = null;
		try {
			facesContext = FacesContext.getCurrentInstance();
			sessionObjects = facesContext.getExternalContext().getSessionMap();
			returnValue = sessionObjects.remove(object_name);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			sessionObjects = null;
		}

		return returnValue;
	}

	/**
	 * 
	 * Metodo che restituisce il valore di un parametro dato il nome del
	 * parammetro
	 * 
	 * @param parameter_name
	 * @return Object
	 */
	public Object getParamObject(String parameter_name) {
		Object returnValue = null;

		FacesContext facesContext = null;
		Map params = null;
		try {
			facesContext = FacesContext.getCurrentInstance();
			params = facesContext.getExternalContext().getRequestParameterMap();
			returnValue = params.get(parameter_name);
		} catch (Exception e) {
			returnValue = null;
		} finally {
			facesContext = null;
			params = null;
		}

		return returnValue;
	}

	public String getParamObjectAsString(String parameter_name) {
		// Object tmp = null;
		// String returnValue = null;
		//
		// FacesContext facesContext = null;
		// Map params = null;
		// try {
		// facesContext = FacesContext.getCurrentInstance();
		// params = facesContext.getExternalContext().getRequestParameterMap();
		// tmp = params.get(parameter_name);
		// if (tmp != null) {
		// returnValue = String.valueOf(tmp);
		// }
		//
		// } catch (Exception e) {
		// returnValue = null;
		// } finally {
		// facesContext = null;
		// params = null;
		// tmp = null;
		// }
		//
		// return returnValue;
		return String.valueOf(getParamObject(parameter_name));
	}

	// Faces objects
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	protected javax.faces.application.Application getApplication() {
		return getFacesContext().getApplication();
	}

	// /**
	// * Metodo che restituisce vero se è un postBack, falso altrimenti
	// *
	// * @return
	// */
	// public boolean isPostBack() {
	// return FacesContext.getCurrentInstance().getExternalContext()
	// .getRequestParameterMap().get("JSFPostBack") != null;
	// }

	/**
	 * Metodo che restituisce un arraylist di select item contenente la
	 * quantità di elementi visualizzabili per pagina selezionabile
	 * 
	 * @return
	 */
	public ArrayList<SelectItem> getNumeroRighePerPagina() {
		ArrayList<SelectItem> returnValue = null;
		try {
			returnValue = new ArrayList<SelectItem>();
			returnValue.add(new SelectItem("5", "5"));
			returnValue.add(new SelectItem("10", "10"));
			returnValue.add(new SelectItem("15", "15"));
			returnValue.add(new SelectItem("20", "20"));
			returnValue.add(new SelectItem("25", "25"));
			returnValue.add(new SelectItem("30", "30"));
			returnValue.add(new SelectItem("35", "35"));
			returnValue.add(new SelectItem("40", "40"));
			returnValue.add(new SelectItem("45", "45"));
			returnValue.add(new SelectItem("50", "50"));
			returnValue.add(new SelectItem("60", "60"));
			returnValue.add(new SelectItem("80", "80"));
			returnValue.add(new SelectItem("100", "100"));
		} catch (Exception e) {
			returnValue = new ArrayList<SelectItem>();
		}
		return returnValue;
	}

	public void setMessaggioErrore(String msg) {
		if (msg == null) {
			msg = "Errore sconosciuto!!";
		}
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_ERROR, messaggioErrore, ""));
		// FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		// msg, msg);
		// FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		setMessaggioErrore(msg, null);
	}

	// protected void addErrorMessage(String testo) {
	// addErrorMessage(testo, null);
	// }
	//
	protected void setMessaggioErrore(String msg, String dettaglio) {
		FacesMessage facesMsg = new FacesMessage();
		facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
		facesMsg.setSummary(msg);
		if (StringUtils.isNotEmpty(dettaglio)) {
			facesMsg.setDetail(dettaglio);
		}
		getFacesContext().addMessage(msg, facesMsg);
	}

	public void setMessaggioSuccesso(String msg) {
		if (msg == null) {
			msg = "Azione avvenuta correttamente.";
		}
		// FacesContext.getCurrentInstance().addMessage(null, new
		// FacesMessage(FacesMessage.SEVERITY_ERROR, messaggioErrore, ""));
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	/**
	 * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	 * <tt>ArrayList</tt> of <tt>SelectedItem</tt>.
	 **/
	protected ArrayList<SelectItem> listToSelectItems(List list) throws Exception {
		ArrayList<SelectItem> retVal = null;
		try {
			retVal = new ArrayList<SelectItem>();
			for (Object obj : list) {

				retVal.add(new SelectItem(obj.toString(), obj.toString()));

			}
		} catch (Exception e) {
			throw e;
		}
		return retVal;
	}

	// /**
	// * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	// <tt>ArrayList</tt> of <tt>SelectedItem</tt>.
	// **/
	// public static ArrayList<SelectItem> listToSelectItems(List list, String
	// itemValue, String itemLabel) throws Exception {
	// ArrayList<SelectItem> retVal = null;
	// try {
	// retVal = new ArrayList<SelectItem>();
	// for (Object obj : list) {
	// PropertyDescriptor pdValue = new PropertyDescriptor(itemValue,
	// obj.getClass());
	// Method methodValue = pdValue.getReadMethod();
	// Object methodValueRes = methodValue.invoke(obj);
	//
	// PropertyDescriptor pdLabel = new PropertyDescriptor(itemLabel,
	// obj.getClass());
	// Method methodLabel = pdLabel.getReadMethod();
	// Object methodLabelRes = methodLabel.invoke(obj);
	// if (methodValueRes == null) {
	// methodValueRes = "";
	// }
	// if (methodLabelRes == null) {
	// methodLabelRes = "";
	// }
	// retVal.add(new SelectItem(methodValueRes.toString(),
	// methodLabelRes.toString()));
	//
	// pdValue = null;
	// methodValue = null;
	// methodValueRes = null;
	//
	// pdLabel = null;
	// methodLabel = null;
	// methodLabelRes = null;
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	// return retVal;
	// }
	//
	// /**
	// * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	// <tt>ArrayList</tt> of <tt>SelectedItem</tt>.
	// * This method allow to concatenate more than one Object Property.
	// */
	// protected ArrayList<SelectItem> listToSelectItems(List list, String
	// itemValue, String[] itemLabel, String separatorForLabel) throws Exception
	// {
	// ArrayList<SelectItem> retVal = null;
	// StringBuilder sumLabel = null;
	//
	// try {
	// retVal = new ArrayList<SelectItem>();
	// for (Object obj : list) {
	//
	// PropertyDescriptor pdValue = new PropertyDescriptor(itemValue,
	// obj.getClass());
	// Method methodValue = pdValue.getReadMethod();
	// Object methodValueRes = methodValue.invoke(obj);
	//
	// sumLabel = new StringBuilder();
	// for (String str : itemLabel) {
	// PropertyDescriptor pdLabel = new PropertyDescriptor(str, obj.getClass());
	// Method methodLabel = pdLabel.getReadMethod();
	// Object methodLabelRes = methodLabel.invoke(obj);
	// if (methodLabelRes == null) {
	// methodLabelRes = "";
	// }
	// if (sumLabel.length() > 0) {
	// sumLabel.append(separatorForLabel);
	// sumLabel.append(methodLabelRes.toString());
	// } else {
	// sumLabel.append(methodLabelRes.toString());
	// }
	//
	// str = null;
	//
	// pdLabel = null;
	// methodLabel = null;
	// methodLabelRes = null;
	// }
	// if (methodValueRes == null) {
	// methodValueRes = "";
	// }
	//
	// retVal.add(new SelectItem(methodValueRes.toString(),
	// sumLabel.toString()));
	//
	// sumLabel = null;
	//
	// pdValue = null;
	// methodValue = null;
	// methodValueRes = null;
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	//
	// return retVal;
	// }
	//
	// /**
	// * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	// <tt>ArrayList</tt> of <tt>SelectedItem</tt>.
	// **/
	// protected ArrayList<SelectItem> listToSelectItems(List list, String
	// itemLabel) throws Exception {
	// ArrayList<SelectItem> retVal = null;
	// try {
	// retVal = new ArrayList<SelectItem>();
	// for (Object obj : list) {
	// //PropertyDescriptor pdValue = new PropertyDescriptor(itemValue,
	// obj.getClass());
	// //Method methodValue = pdValue.getReadMethod();
	// //Object methodValueRes = methodValue.invoke(obj);
	//
	// PropertyDescriptor pdLabel = new PropertyDescriptor(itemLabel,
	// obj.getClass());
	// Method methodLabel = pdLabel.getReadMethod();
	// Object methodLabelRes = methodLabel.invoke(obj);
	// // if (methodValueRes == null) {
	// // methodValueRes = "";
	// // }
	// if (methodLabelRes == null) {
	// methodLabelRes = "";
	// }
	// retVal.add(new SelectItem(obj, methodLabelRes.toString()));
	//
	// // pdValue = null;
	// // methodValue = null;
	// // methodValueRes = null;
	//
	// pdLabel = null;
	// methodLabel = null;
	// methodLabelRes = null;
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	// return retVal;
	// }
	//
	// /**
	// * Method that convert a <tt>List</tt> of <tt>Object</tt> to an
	// <tt>ArrayList</tt> of <tt>SelectedItem</tt>.
	// * This method allow to concatenate more than one Object Property.
	// */
	// protected ArrayList<SelectItem> listToSelectItems(List list, String[]
	// itemLabel, String separatorForLabel) throws Exception {
	// ArrayList<SelectItem> retVal = null;
	// StringBuilder sumLabel = null;
	//
	// try {
	// retVal = new ArrayList<SelectItem>();
	// for (Object obj : list) {
	//
	// sumLabel = new StringBuilder();
	// for (String str : itemLabel) {
	// PropertyDescriptor pdLabel = new PropertyDescriptor(str, obj.getClass());
	// Method methodLabel = pdLabel.getReadMethod();
	// Object methodLabelRes = methodLabel.invoke(obj);
	// if (methodLabelRes == null) {
	// methodLabelRes = "";
	// }
	// if (sumLabel.length() > 0) {
	// sumLabel.append(separatorForLabel);
	// sumLabel.append(methodLabelRes.toString());
	// } else {
	// sumLabel.append(methodLabelRes.toString());
	// }
	//
	// str = null;
	//
	// pdLabel = null;
	// methodLabel = null;
	// methodLabelRes = null;
	// }
	//
	// retVal.add(new SelectItem(obj, sumLabel.toString()));
	//
	// sumLabel = null;
	//
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	//
	// return retVal;
	// }
	// protected ArrayList<SelectItem> getSelectOptionFromText(String text)
	// throws Exception {
	// ArrayList<SelectItem> returnValue = null;
	// String[] valori = null;
	// String string = null;
	// try {
	// if (text != null) {
	// valori = text.split("\n");
	// }
	// returnValue = new ArrayList<SelectItem>();
	// for (int i = 0; i < valori.length; i++) {
	// string = valori[i];
	// if (string != null && string.length() > 1) {
	// string = string.trim();
	// returnValue.add(new SelectItem(string, string));
	// }
	// }
	// } catch (Exception exception) {
	// throw exception;
	// } finally {
	// valori = null;
	// string = null;
	// }
	// return returnValue;
	//
	// }

	// protected String listToJSArrayItems(List list, String item) throws
	// Exception {
	// StringBuilder retVal = null;
	// try {
	// retVal = new StringBuilder();
	// for (Object obj : list) {
	//
	// PropertyDescriptor pdLabel = new PropertyDescriptor(item,
	// obj.getClass());
	// Method methodLabel = pdLabel.getReadMethod();
	// Object methodLabelRes = methodLabel.invoke(obj);
	//
	// if (methodLabelRes == null) {
	// methodLabelRes = "";
	// }
	// retVal.append(methodLabelRes.toString());
	// retVal.append(",");
	//
	// pdLabel = null;
	// methodLabel = null;
	// methodLabelRes = null;
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	// return retVal.toString();
	// }

	// Getters
	// -----------------------------------------------------------------------------------
	public static Object getApplicationMapValue(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
	}

	// Setters
	// -----------------------------------------------------------------------------------
	public static void setApplicationMapValue(String key, Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
	}

	public ServletContext getServletContext() {
		return (ServletContext) getFacesContext().getExternalContext().getContext();
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
	}

	// public abstract static AbstractPageBase getIstance();

}
