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

package it.atreeblu.lib.test;

import it.webappcommon.lib.DateUtils;
import it.webappcommon.lib.StringHelper;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * @author Mirco
 */
public class Main {

	protected static Logger logger = Logger.getLogger(Main.class.getName());

	// private static final String REGEX = "\\bdog\\b";
	// private static final String INPUT = "dog dog dog doggie dogg";

	// private static String REGEX = "a*b";
	// private static String INPUT = "aabfooaabfooabfoob";
	// private static String REPLACE = "-";

	// private static String REGEX = "<TAG\\b[^>]*>(.*?)</TAG>";
	// private static String INPUT = "<TAG>one<TAG>two</TAG>one</TAG>";
	// private static String REPLACE = "-";

	private static String REGEX = "This message(.*?)Thank You.";
	private static String INPUT = "OK, Giorgio, metti Diego in lista n�1 (se qualcuno non ti ha gi� risposto ad interventi) � Diego: magari vediamo anche a voce quali sono le funzionalit� che ti servono per capire se il SW fa al caso tuo � Marco� � This message and any attachments are solely for the use of the intended recipients. They may contain privileged and/or confidential information or other information protected from disclosure. If you are not an intended recipient, you are hereby notified that you received this email in error and that any review, dissemination, distribution or copying of this email and any attachment is strictly prohibited. If you have received this email in error, please contact the sender and delete the message and any attachment from your system. Thank You. � Da: Pigozzo Diego Inviato: luned� 14 marzo 2011 13:45 A: Calesella Marco Oggetto: R: Richiesta per disponibilt� a testare nuovo PDF Editor � Se serve io son qua � Diego Pigozzo Large Hydro - Sales Commercial � ANDRITZ HYDRO S.r.l Via D. Manin 16/18 36015 Schio (VI) - Italy Phone : +39 0445 678245 Mobile : +39 335 7849565 Fax :�+39 0445 678218 E-Mail : diego.pigozzo@andritz.com http://www.andritz-hydro.com � This message and any attachments are solely for the use of the intended recipients. They may contain privileged and/or confidential information or other information protected from disclosure. If you are not an intended recipient, you are hereby notified that you received this email in error and that any review, dissemination, distribution or copying of this email and any attachment is strictly prohibited. If you have received this email in error, please contact the sender and delete the message and any attachment from your system. Thank You. Da: Calesella Marco Inviato: luned� 14 marzo 2011 13.41 A: HYDRO-IT.Schio Oggetto: Richiesta per disponibilt� a testare nuovo PDF Editor Priorit�: Alta � Oggetto: PDF editor di dotazione standard � Nell�ottica di trovare uno strumento migliore dell�attuale Jaws Editor (standard Andritz), vogliamo sondare la disponibilit� a testare un nuovo software che sembra avere caratteristiche interessanti. In particolare sembra coprire funzionalit� un po� pi� evolute richieste da vari utenti, ma non presenti in Jaws. � Precisiamo che stiamo parlando dell�editor che fa parte della dotazione standard rivolta a tutti gli utenti, quindi non ci stiamo rivolgendo agli utenti che hanno quotidianamente necessit� avanzate di editing dei PDF e per i quali � stata richiesta l�adozione di Adobe Pro. � L�invito a partecipare a questa prova � rivolto a chi: � 1)������ ha riscontrato di dover utilizzare funzionalit� non presenti nel Jaws editor 2)������ ha necessit� di utilizzare queste funzionalit� a breve termine (aprile/maggio sarebbe ideale) cos� da rendere il test significativo 3)������ � disposto ad imparare ad utilizzare un nuovo software in modo autonomo pur non avendo la certezza che poi venga adottato 4)������ e disposto a darci un feedback �concreto� (Tradotto: ci interessa sapere se il nuovo software � giudicato sufficiente a coprire, se non la totalit�, una percentuale accettabile delle proprie esigenze, se in modo migliore e pi� completo del Jaws e se vengono riscontrati problemi particolari nell�utilizzo. In questo senso il confronto non deve essere fatto con Adobe Acrobat PRO. L�ottica non � sostituire questo prodotto che si rivolge ad un�altra fascia di utenti. E� chiaro che Adobe PRO va sicuramente meglio ed ha anche molte funzioni in pi�, ma ha anche un costo 15 volte superiore e quindi l�acquisto di una licenza � giustificato solo per chi ne fa uso come strumento primario di lavoro). � � Chi � interessato a testare questo software mandi un email entro il 22/03/2011 ad interventi@andritz.com specificando: � 1)������ funzionalit� non presenti in Jaws che si intendono testare 2)������ periodo in cui si prevede di utilizzarle (ideale il periodo aprile/maggio) � � Questo ci permetter� di verificare se ha senso fare questo test e nel caso di pianificarne le fasi con gli interessati. Qualora si decidesse di procedere acquisteremo alcune licenze (approssimativamente 5, in dipendenza delle richieste ricevute) e quindi le daremo ai primi che ci avranno risposto. � In caso di esito positivo del test l�idea � di adottare questo software come standard al posto di Jaws per tutti gli utenti (esclusi, come detto, quelli che per motivi di utilizzo avanzato sono gi� dotati di Adobe Pro). Per questo, con motivazioni documentate dai test effettuati dagli utenti, verr� inoltrata richiesta ad Andritz perch� il nuovo software sostituisca lo standard attuale JAWS o perlomeno venga ad esso affiancato come alternativa. � Grazie � Marco� � This message and any attachments are solely for the use of the intended recipients. They may contain privileged and/or confidential information or other information protected from disclosure. If you are not an intended recipient, you are hereby notified that you received this email in error and that any review, dissemination, distribution or copying of this email and any attachment is strictly prohibited. If you have received this email in error, please contact the sender and delete the message and any attachment from your system. Thank You. � �";
	private static String REPLACE = "-";

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		testURLUtils();

		// testSize();

		// testStringHelper();

		// testDateUtils();

		// testJSFHelper();

		// System.out.println(TimeUtils.floatToTime(2.5f));
		// System.out.println(TimeUtils.floatToTime(5.25f));
		// System.out.println(TimeUtils.floatToTime(2.75f));
		// System.out.println(TimeUtils.floatToTime(7.00f));
		//
		// System.out.println(TimeUtils.timeToFloat(""));
		// System.out.println(TimeUtils.timeToFloat("0:00"));
		// System.out.println(TimeUtils.timeToFloat("5"));
		// System.out.println(TimeUtils.timeToFloat("5:15"));
		// System.out.println(TimeUtils.timeToFloat("5:00"));
		// System.out.println(TimeUtils.timeToFloat("5:30"));
		// System.out.println(TimeUtils.timeToFloat("5:45"));

		//
		//
		// LDAPHelper ldapserver = new LDAPHelper("schsms001", "ANDRITZ",
		// "schbes01sa", "4Ward2Mob!le",
		// "ou=Users,ou=Accounts,ou=SCH,ou=IT,ou=Europe,ou=Hydro Group,dc=andritz,dc=com");
		// // Map<String, String> data =
		// ldapserver.findActiveUserByAccountName("Mirco");
		// // // SCHGIU04
		// // // //
		// // // SCHGIO02
		// // // Giorgio
		//
		//
		// // LDAPHelper ldapserver = new LDAPHelper("172.27.17.1", "SANITA",
		// "ldapatree", "Larc5a3ldap", "ou=ULSS5 Utenti,dc=sanita,dc=vi");
		// // LDAPHelper ldapserver = new LDAPHelper("10.1.9.1", "SANITA",
		// "ldapatree", "Larc5a3ldap", "ou=ULSS5 Utenti,dc=sanita,dc=vi");
		// // Map<String, String> data =
		// ldapserver.findActiveUserByAccountName("Mirco");
		// List<UserInfo> data = ldapserver.findActiveUsers(null);
		//
		// if (data != null) {
		// System.out.println(String.format("Trovati %s", data.size()));
		//
		// for (UserInfo user : data) {
		// System.out.println(String.format("%s\t%s\t%s\t%s\t%s",user.getNome(),user.getCognome(),user.getNomeCompleto(),
		// user.getEmail(), user.getAccount()));
		// }
		// } else {
		// System.out.println("non trovato");
		// }
		//
		//
		//
		// String account = "ANDRITZ\\schgiu05";
		//
		// System.out.println();
		// System.out.println("Cerco " + account);
		//
		// if (account.indexOf("\\") > 0) {
		// account = account.substring(account.indexOf("\\"));
		// }
		// List<UserInfo> domainData =
		// ldapserver.findActiveUserByAccountName(account);
		// if (domainData != null) {
		// for (UserInfo userInfo : domainData) {
		// System.out.println(userInfo.getNomeCompleto());
		// System.out.println(userInfo.getEmail());
		// System.out.println(userInfo.getTel());
		// System.out.println(userInfo.getUfficio());
		// }
		// }
	}

	private static void testDateUtils() {

		// System.out.println(DateUtils.differenzaInGiorni(new Date(), new
		// Date()));
		//
		// for (Date data : DateUtils.calcolaDateIntermedie(new
		// Date(2010,10,15), new Date(2010,10,18))) {
		// System.out.println(data);
		// }

		Date test1 = new Date(2010, 10, 10, 12, 00);
		Date test2 = new Date(2010, 10, 10, 12, 15);
		Date test3 = new Date(2010, 10, 10, 11, 30);
		Date test4 = new Date(2010, 10, 10, 12, 30);

		System.out.println(DateUtils.verificaSovrapposizione(test1, test2, test3, test4, false));
		System.out.println(DateUtils.verificaSovrapposizione(test1, test2, test3, test4, true));

	}

	private static void testJSFHelper() {
		// System.out.println(HtmlUtils.encodeWebUrl("www.google.com"));
		// System.out.println(HtmlUtils.encodeWebUrl("http://localhost:8080/atreeflow/Auth/frames.jspx"));
		//
		// System.out.println(HtmlUtils.encodeWebUrl("http://www.google.com"));
		// System.out.println(HtmlUtils.encodeWebUrl("https://www.google.com"));
		// System.out.println(HtmlUtils.encodeWebUrl("ftps://www.google.com"));
	}

	private static void testStringHelper() {

		System.out.println(StringHelper.stringPart("AABBCCDDEE", 8));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 5));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 10));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 2));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 1));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 0));
		System.out.println(StringHelper.stringPart("", 0));

	}

	private static void testRegEx() {
		// System.out.println(MailUtils.removeDisclaimer("OK, Giorgio, metti Diego in lista n�1 (se qualcuno non ti ha gi� risposto ad interventi) � Diego: magari vediamo anche a voce quali sono le funzionalit� che ti servono per capire se il SW fa al caso tuo � Marco� � This message and any attachments are solely for the use of the intended recipients. They may contain privileged and/or confidential information or other information protected from disclosure. If you are not an intended recipient, you are hereby notified that you received this email in error and that any review, dissemination, distribution or copying of this email and any attachment is strictly prohibited. If you have received this email in error, please contact the sender and delete the message and any attachment from your system. Thank You. � Da: Pigozzo Diego Inviato: luned� 14 marzo 2011 13:45 A: Calesella Marco Oggetto: R: Richiesta per disponibilt� a testare nuovo PDF Editor � Se serve io son qua � Diego Pigozzo Large Hydro - Sales Commercial � ANDRITZ HYDRO S.r.l Via D. Manin 16/18 36015 Schio (VI) - Italy Phone : +39 0445 678245 Mobile : +39 335 7849565 Fax :�+39 0445 678218 E-Mail : diego.pigozzo@andritz.com http://www.andritz-hydro.com � This message and any attachments are solely for the use of the intended recipients. They may contain privileged and/or confidential information or other information protected from disclosure. If you are not an intended recipient, you are hereby notified that you received this email in error and that any review, dissemination, distribution or copying of this email and any attachment is strictly prohibited. If you have received this email in error, please contact the sender and delete the message and any attachment from your system. Thank You. Da: Calesella Marco Inviato: luned� 14 marzo 2011 13.41 A: HYDRO-IT.Schio Oggetto: Richiesta per disponibilt� a testare nuovo PDF Editor Priorit�: Alta � Oggetto: PDF editor di dotazione standard � Nell�ottica di trovare uno strumento migliore dell�attuale Jaws Editor (standard Andritz), vogliamo sondare la disponibilit� a testare un nuovo software che sembra avere caratteristiche interessanti. In particolare sembra coprire funzionalit� un po� pi� evolute richieste da vari utenti, ma non presenti in Jaws. � Precisiamo che stiamo parlando dell�editor che fa parte della dotazione standard rivolta a tutti gli utenti, quindi non ci stiamo rivolgendo agli utenti che hanno quotidianamente necessit� avanzate di editing dei PDF e per i quali � stata richiesta l�adozione di Adobe Pro. � L�invito a partecipare a questa prova � rivolto a chi: � 1)������ ha riscontrato di dover utilizzare funzionalit� non presenti nel Jaws editor 2)������ ha necessit� di utilizzare queste funzionalit� a breve termine (aprile/maggio sarebbe ideale) cos� da rendere il test significativo 3)������ � disposto ad imparare ad utilizzare un nuovo software in modo autonomo pur non avendo la certezza che poi venga adottato 4)������ e disposto a darci un feedback �concreto� (Tradotto: ci interessa sapere se il nuovo software � giudicato sufficiente a coprire, se non la totalit�, una percentuale accettabile delle proprie esigenze, se in modo migliore e pi� completo del Jaws e se vengono riscontrati problemi particolari nell�utilizzo. In questo senso il confronto non deve essere fatto con Adobe Acrobat PRO. L�ottica non � sostituire questo prodotto che si rivolge ad un�altra fascia di utenti. E� chiaro che Adobe PRO va sicuramente meglio ed ha anche molte funzioni in pi�, ma ha anche un costo 15 volte superiore e quindi l�acquisto di una licenza � giustificato solo per chi ne fa uso come strumento primario di lavoro). � � Chi � interessato a testare questo software mandi un email entro il 22/03/2011 ad interventi@andritz.com specificando: � 1)������ funzionalit� non presenti in Jaws che si intendono testare 2)������ periodo in cui si prevede di utilizzarle (ideale il periodo aprile/maggio) � � Questo ci permetter� di verificare se ha senso fare questo test e nel caso di pianificarne le fasi con gli interessati. Qualora si decidesse di procedere acquisteremo alcune licenze (approssimativamente 5, in dipendenza delle richieste ricevute) e quindi le daremo ai primi che ci avranno risposto. � In caso di esito positivo del test l�idea � di adottare questo software come standard al posto di Jaws per tutti gli utenti (esclusi, come detto, quelli che per motivi di utilizzo avanzato sono gi� dotati di Adobe Pro). Per questo, con motivazioni documentate dai test effettuati dagli utenti, verr� inoltrata richiesta ad Andritz perch� il nuovo software sostituisca lo standard attuale JAWS o perlomeno venga ad esso affiancato come alternativa. � Grazie � Marco� � This message and any attachments are solely for the use of the intended recipients. They may contain privileged and/or confidential information or other information protected from disclosure. If you are not an intended recipient, you are hereby notified that you received this email in error and that any review, dissemination, distribution or copying of this email and any attachment is strictly prohibited. If you have received this email in error, please contact the sender and delete the message and any attachment from your system. Thank You. � �",
		// "This", "Thank", false));

		// MailUtils.removeDisclaimer(INPUT, REGEX, false);

		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(INPUT);
		// int count = 0;
		// while (m.find()) {
		// count++;
		// System.out.println("Match number " + count);
		// System.out.println("start(): " + m.start());
		// System.out.println("end(): " + m.end());
		// }

		System.out.println(INPUT.replaceAll(REGEX, REPLACE));

		// System.out.println("".replaceAll("", ""));
	}

	private static void testSize() {
		// System.out.println(FileUtils.parseSize(1000));
		// System.out.println(FileUtils.parseSize(5000));
		// System.out.println(FileUtils.parseSize(50000));
		// System.out.println(FileUtils.parseSize(75000));
		// System.out.println(FileUtils.parseSize(1250000));
		// System.out.println(FileUtils.parseSize(225000000));
		// System.out.println(FileUtils.parseSize(202400000000l));
	}

	private static void testURLUtils() {
		// System.out.println(URLUtils.callProc("http://www.google.com"));
		// System.out.println(HtmlHelper.callUrl("http://192.168.0.230:8080/AFWHMSync/main.xhtml?do"));
	}
}
