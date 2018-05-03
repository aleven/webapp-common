package it.attocchi.mail.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.attocchi.mail.parts.EmailBody;
import it.attocchi.mail.parts.MailAttachmentUtil;
import it.attocchi.utils.ListUtils;


public class MailUtils {

	protected static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

	public static final String CONTENT_TYPE_MULTIPART = "multipart/*";
	public static final String CONTENT_TYPE_MULTIPART_ALTERNATIVE = "multipart/alternative";
	public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
	public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";

	public static final String CONTENT_TYPE_MULTIPART_MIXED = "multipart/mixed";

	public static final String CONTENT_TYPE_MESSAGE_RFC822 = "message/rfc822";

	public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 
	 * @param mailText
	 * @param textStart
	 * @param textEnd
	 * @param isHtml
	 * @return
	 */
	public static String removeDisclaimer(String mailText, String textStart, String textEnd, boolean isHtml) {

		String res = mailText;

		// This message
		// Thank You.

		if (StringUtils.isNotEmpty(mailText)) {

			// String defaultStart = isHtml ? "--/n" : "--<BR>";

			if (StringUtils.isNotEmpty(textStart) && StringUtils.isNotEmpty(textEnd)) {

				// Un primo filtro per torglierli tutti, alcuni vedo che non
				// vengono tolti allora li rimuovo a mano
				res = res.replaceAll("" + textStart + "(.*?)" + textEnd + "", "");

				StringBuffer returnMessage = new StringBuffer(res);

				int startPosition = returnMessage.indexOf(textStart);
				int endPosition = returnMessage.indexOf(textEnd);
				while (startPosition != -1 && endPosition != -1) {
					returnMessage.delete(startPosition, endPosition + textEnd.length());

					startPosition = returnMessage.indexOf(textStart);
					endPosition = returnMessage.indexOf(textEnd);
				}

				res = returnMessage.toString();

				// int posStart =
				// res.toUpperCase().indexOf(textStart.toUpperCase());
				// int posEnd =
				// res.toUpperCase().indexOf(textEnd.toUpperCase());
				//
				// if (posStart > 0 && posEnd > 0) {
				// // res = res.substring(posStart, posEnd + textEnd.length());
				// res = mailText.substring(0, posStart);
				// res = res + mailText.substring(posEnd + textEnd.length(),
				// mailText.length());
				// }
			}

			// int pos = res.toUpperCase().indexOf(defaultStart);
			// if (pos > 0) {
			// res = res.substring(0, pos);
			// }

			res = res.trim();
		}

		return res;

	}

	/**
	 * Estrae il Body di una email anche nel caso sia Html o MultiPart
	 * 
	 * @param message
	 * @return
	 * @throws MessagingException
	 * @throws java.io.IOException
	 */
	public static EmailBody getBody(Message message) throws MessagingException, java.io.IOException {
		String testo = null;
		EmailBody res = null;

		logger.info("extracting body from message...");
		
		/* Gestione javax.mail.MessagingException: Unable to load BODYSTRUCTURE */
		// Object content = message.getContent();
		Object content = getEmailContent(message);
		logger.debug("message content-type={}", message.getContentType());
		
		if (message.isMimeType(MailUtils.CONTENT_TYPE_TEXT_PLAIN)) {
			testo = (String) content;
			res = new EmailBody(testo, false);
		} else if (message.isMimeType(MailUtils.CONTENT_TYPE_TEXT_HTML)) {
			testo = (String) content;
			res = new EmailBody(testo, true);
		} else if (message.isMimeType(MailUtils.CONTENT_TYPE_MULTIPART_ALTERNATIVE) || message.isMimeType(MailUtils.CONTENT_TYPE_MULTIPART)) {
			/* Codice per Estrazione Testo nel Caso di Email Posta PEC */
			Multipart mp = (Multipart) message.getContent();
			res = extractMultiPartText(mp);

		} else {
			testo = "";
		}

		return res;
	}
	
	/**
	 * Gestione javax.mail.MessagingException: Unable to load BODYSTRUCTURE
	 * @param email
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	private static Object getEmailContent(Message email) throws IOException, MessagingException {
        Object content;
        try {
            content = email.getContent();
        } catch (MessagingException e) {
        	logger.warn(e.getMessage() + " for Message of type + " + email.getClass().getName());
            // did this due to a bug
            // check: http://goo.gl/yTScnE and http://goo.gl/P4iPy7
            if (email instanceof MimeMessage && "Unable to load BODYSTRUCTURE".equalsIgnoreCase(e.getMessage())) {
                content = new MimeMessage((MimeMessage) email).getContent();
                logger.warn("content loaded as MimeMessage");
            } else {
                throw e;
            }
        }
        return content;
    } 

	/**
	 * 
	 * @param mp
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private static EmailBody extractMultiPartText(Multipart mp) throws MessagingException, IOException {
		String testo = "";
		EmailBody res = null;

		int numParts = mp.getCount();
		logger.debug("part content-type: {}", mp.getContentType());
		logger.debug("part sub-parts: {}", numParts);
		
		for (int i = 0; i < numParts; ++i) {
			BodyPart bodyPart = mp.getBodyPart(i);
			logger.debug("sub-part {} {}", i, bodyPart.getContentType());
			if (bodyPart.isMimeType(MailUtils.CONTENT_TYPE_TEXT_PLAIN)) {
				testo = manageBodyPart(bodyPart); // (String) bodyPart.getContent();
				res = new EmailBody(testo, false);
				break;
			} else if (bodyPart.isMimeType(MailUtils.CONTENT_TYPE_TEXT_HTML)) {
				testo = manageBodyPart(bodyPart); // (String) bodyPart.getContent();
				res = new EmailBody(testo, true);
				break;
			} else if (bodyPart.isMimeType(MailUtils.CONTENT_TYPE_MULTIPART_ALTERNATIVE) || bodyPart.isMimeType(MailUtils.CONTENT_TYPE_MULTIPART) || bodyPart.isMimeType(MailUtils.CONTENT_TYPE_MULTIPART_MIXED)) {
				Multipart sub = (Multipart) bodyPart.getContent();
				res = extractMultiPartText(sub);
				break;
			}
		}

		return res;
	}
	
	/**
	 * Gestione errore Unknown encoding quoted-printable-- con javamail 1.5 non si presenta problema
	 * @param bodyPart
	 * @return
	 */
	private static String manageBodyPart(BodyPart bodyPart) throws MessagingException, IOException {
		String res = null;
		try {
			res = (String) bodyPart.getContent();
		} catch (IOException e1) {
			logger.error("manageBodyPart IOException", e1);
			throw e1;
		} catch (MessagingException e2) {
			logger.error("manageBodyPart MessagingException", e2);
			throw e2;
		}
		return res;
	}

	public static String getSenderAddress(Message message) throws MessagingException {
		Address[] froms = message.getFrom();
		String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
		return email;
	}
	
	public static String getReplyToAddress(Message message) throws MessagingException {
		Address[] froms = message.getReplyTo();
		String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
		return email;
	}	

	public static List<String> getAllSenders(Message message) throws MessagingException {
		List<String> fromAddresses = new ArrayList<String>();

		Address[] senders = message.getFrom();
		for (Address sender : senders) {
			fromAddresses.add(sender.toString());
		}

		return fromAddresses;
	}

	public static String getAllSendersAsString(Message message) throws MessagingException {
		return ListUtils.toCommaSeparedNoBracket(getAllSenders(message));
	}

	public static List<String> getAllRecipents(Message message) throws MessagingException {
		List<String> toAddresses = new ArrayList<String>();

		// Address[] recipients =
		// message.getRecipients(Message.RecipientType.TO);
		Address[] recipients = message.getAllRecipients();
		for (Address address : recipients) {
			toAddresses.add(address.toString());
		}

		return toAddresses;
	}

	public static List<MailAttachmentUtil> getAttachments(Message message) throws MessagingException, IOException {

		List<MailAttachmentUtil> res = new ArrayList<MailAttachmentUtil>();

		// logger.debug(message.getContentType());

		if (message.isMimeType(MailUtils.CONTENT_TYPE_MULTIPART_ALTERNATIVE) || message.isMimeType(MailUtils.CONTENT_TYPE_MULTIPART)) {
			Multipart multipart = (Multipart) message.getContent();

			for (int i = 0, n = multipart.getCount(); i < n; i++) {

				Part part = multipart.getBodyPart(i);

				// logger.debug(part.getContentType());

				String disposition = part.getDisposition();

				if ((disposition != null) && (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE))) {
					// saveFile(part.getFileName(), part.getInputStream());
					res.add(new MailAttachmentUtil(part.getContentType(), part.getInputStream(), part.getFileName(), part.getSize()));
				}

				if (disposition == null) {
					// Check if plain
					MimeBodyPart mbp = (MimeBodyPart) part;

					// System.out.println(part.getContentType());

					if (mbp.isMimeType(MailUtils.CONTENT_TYPE_TEXT_PLAIN) || mbp.isMimeType(MailUtils.CONTENT_TYPE_TEXT_HTML)) {
						// Handle plain
					} else {

						// String mime = mbp.getContentType();

						// saveFile(part.getFileName(), part.getInputStream());
						res.add(new MailAttachmentUtil(mbp.getContentType(), mbp.getInputStream(), mbp.getFileName(), mbp.getSize()));
					}

				}
			}

		}

		return res;
	}

	public static void saveFile(MailAttachmentUtil m) {
		saveFile(m.getFileName(), m.getStream());
	}

	private static void saveFile(String filename, InputStream in) {
		// from saveFile()
		File file = new File(filename);
		for (int i = 0; file.exists(); i++) {

			String soloFilename = FileNameUtils.getFileNameWithOutExtension(file);
			String ext = FileNameUtils.getFileExtension(file, true);

			file = new File(soloFilename + "_" + i + ext);

		}

		try {
			file.createNewFile();

			// System.out.println(file.getAbsolutePath());

			OutputStream out = new FileOutputStream(file);
			byte buf[] = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);

			out.close();
			in.close();

		} catch (IOException ex) {
			logger.error("saveFile", ex);
		} finally {

		}
	}

	/**
	 * Rimuove un indirizzo email da una serie che sono stati specificati
	 * separati da virgola o punto e virgola
	 * 
	 * @param indirizzoNotifica
	 * @param mail
	 * @return
	 */
	@Deprecated
	public static String removeAddress(String indirizziMultipli, String mail) {

		String res = indirizziMultipli;

		if (StringUtils.isNotBlank(mail)) {
			if (res.indexOf(mail) > -1) {

				res = res.replaceAll(mail, "");

				// Per sicurezza rimuovo eventuali doppie
				// virgole o doppi punti e virgola
				res = res.replaceAll(",,", ",");
				res = res.replaceAll(";;", ";");

				res = res.trim();

				if (res.endsWith(",")) {
					res = StringUtils.chop(res);
				}
				if (res.endsWith(";")) {
					res = StringUtils.chop(res);
				}

				if (res.startsWith(",")) {
					res = res.substring(1);
				}
				if (res.startsWith(";")) {
					res = res.substring(1);
				}

				res = res.trim();

				logger.debug(String.format("Rimosso %s dai destinatari: %s", mail, indirizziMultipli));
			}
		}
		return res;
	}

	/**
	 * 
	 * @param elencoIndirizzi
	 * @param emailDaRimuovere
	 * @return
	 */
	public static String cleanDuplicatesAndRemoveAddress2(String elencoIndirizzi, String emailDaRimuovere) {
		String res = elencoIndirizzi;
		if (elencoIndirizzi != null && !elencoIndirizzi.isEmpty()) {
			Set<String> indirizziUnici = new HashSet<String>();
			String[] indirizzi = StringUtils.split(elencoIndirizzi, ",");
			for (String indirizzo : indirizzi) {
				if (indirizzo != null && !indirizzo.trim().isEmpty()) {
					indirizzo = indirizzo.trim();
					if (!indirizziUnici.contains(indirizzo)) {
						if (emailDaRimuovere != null && !emailDaRimuovere.trim().equalsIgnoreCase(indirizzo.trim())) {
							indirizziUnici.add(indirizzo);
						}
					}
				}
			}
			res = ListUtils.toCommaSeparedNoBracket(indirizziUnici);
		}
		return res;
	}

	public static void saveToEml(Message mail, File emlFile) throws IOException, MessagingException {
		OutputStream os = null;
		try {
			os = new FileOutputStream(emlFile);

			mail.writeTo(os);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	@Deprecated
	public static String removeDuplicateAddresses(String indirizziNotifica) {
		String res = indirizziNotifica;

		if (StringUtils.isNotBlank(indirizziNotifica)) {
			String[] indirizzi = indirizziNotifica.split(",");
			if (indirizzi == null)
				indirizzi = indirizziNotifica.split(";");

			if (indirizzi != null) {
				List<String> cleaned = new ArrayList<String>();

				for (String indirizzo : indirizzi) {

					indirizzo = indirizzo.trim();

					if (!cleaned.contains(indirizzo)) {
						cleaned.add(indirizzo);
					}
				}

				res = ListUtils.toCommaSeparedNoBracket(cleaned);
			}
		}
		return res;
	}

	/***
	 * Nelle notifiche in cui concateno come destinatario + campi potrei trovare
	 * dei casi in cui i segnaposti sono vuoti
	 * 
	 * @param addresses
	 * @return
	 */
	@Deprecated
	public static String cleanEmptyAddresses(String addresses) {
		String indirizziNotifica = addresses;

		if (indirizziNotifica.startsWith(",") || indirizziNotifica.startsWith(", ")) {
			indirizziNotifica = indirizziNotifica.substring(1);
			indirizziNotifica = indirizziNotifica.trim();
		} else if (indirizziNotifica.endsWith(",") || indirizziNotifica.endsWith(", ")) {
			indirizziNotifica = indirizziNotifica.trim();
			indirizziNotifica = indirizziNotifica.substring(0, indirizziNotifica.length() - 1);
		}
		return indirizziNotifica;
	}
	
	public static Map<String, String> getAllHeaders(Message mail) throws MessagingException {
		Map<String, String> res = new HashMap<String, String>();
		
		if (mail.getAllHeaders() != null) {
			Enumeration headers = mail.getAllHeaders();
			while (headers.hasMoreElements()) {
				Header h = (Header) headers.nextElement();
				
				String headerName = h.getName();
				String headerValue = h.getValue();
				
				res.put(headerName, headerValue);
			}
		}	
		
		return res;
	}
}
