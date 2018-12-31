package it.attocchi.mail.utils;

import it.attocchi.mail.parts.EmailBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;



/**
 * <p>PecParser class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class PecParser {

	/** Constant <code>logger</code> */
	protected static final Logger logger = LoggerFactory.getLogger(PecParser.class);

	int level = 0;
	boolean showStructure = false;
	boolean saveAttachments = true;
	int attnum = 1;
	static String indentStr = "                                               ";
	String emlFileName = "";
	boolean emlFound = false;
	File f;
	EmailBody testo;

	/**
	 * <p>Getter for the field <code>testo</code>.</p>
	 *
	 * @return a {@link it.attocchi.mail.parts.EmailBody} object.
	 */
	public EmailBody getTesto() {
		return testo;
	}

	/**
	 * <p>Constructor for PecParser.</p>
	 *
	 * @param emlFileName a {@link java.lang.String} object.
	 * @param saveFile a boolean.
	 * @param f a {@link java.io.File} object.
	 */
	public PecParser(String emlFileName, boolean saveFile, File f) {
		super();
		this.emlFileName = emlFileName;
		this.saveAttachments = saveFile;
		this.f = f;
	}

	/*
	 * 
	 */
	/**
	 * <p>dumpPart.</p>
	 *
	 * @param part a {@link javax.mail.Part} object.
	 * @throws java.lang.Exception if any.
	 */
	public void dumpPart(Part part) throws Exception {
		if (part instanceof Message)
			dumpEnvelope((Message) part);

//		/**
//		 * Dump input stream ..
//		 *
//		 * InputStream is = p.getInputStream(); // If "is" is not already
//		 * buffered, wrap a BufferedInputStream // around it. if (!(is
//		 * instanceof BufferedInputStream)) is = new BufferedInputStream(is);
//		 * int c; while ((c = is.read()) != -1) System.out.write(c);
//		 **/

		String ct = part.getContentType();
		try {
			log("CONTENT-TYPE: " + (new ContentType(ct)).toString());
		} catch (ParseException pex) {
			log("BAD CONTENT-TYPE: " + ct);
		}
		String filename = part.getFileName();
		if (filename != null)
			log("FILENAME: " + filename);

		/*
		 * Using isMimeType to determine the content type avoids fetching the
		 * actual content data until we need it.
		 */
		// if (p.isMimeType("text/plain")) {
		// if (p.isMimeType("text/*")) {
		if (part.isMimeType("text/plain") || part.isMimeType("text/html")) {
			log("This is plain text");
			log("---------------------------");
			if (!showStructure && !saveAttachments)
				logger.debug((String) part.getContent());

			if (emlFound) {
				testo = new EmailBody((String) part.getContent(), part.getContentType());
				return;
			}

		} else if (part.isMimeType("multipart/*")) {
			log("This is a Multipart");
			log("---------------------------");
			Multipart mp = (Multipart) part.getContent();
			level++;
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				dumpPart(mp.getBodyPart(i));
				/*
				 * Blocco se ne ho trovato il testo (per evitare di assegnare il
				 * contenuto di eventuali allegati txt
				 */
				if (testo != null)
					break;
			}
			level--;
		} else if (part.isMimeType("message/rfc822")) {
			log("This is a Nested Message");
			log("---------------------------");
			level++;

			if (filename.equals(emlFileName)) {
				saveAttachment((Part) part.getContent(), filename);
				emlFound = true;
			}

			dumpPart((Part) part.getContent());

			if (emlFound)
				return;

			level--;
		} else {
			if (!showStructure && !saveAttachments) {
				/*
				 * If we actually want to see the data, and it's not a MIME type
				 * we know, fetch it and check its Java type.
				 */
				Object o = part.getContent();
				if (o instanceof String) {
					log("This is a string");
					log("---------------------------");
					logger.debug((String) o);

					if (emlFound) {
						testo = new EmailBody((String) part.getContent(), part.getContentType());
						return;
					}

				} else if (o instanceof InputStream) {
					log("This is just an input stream");
					log("---------------------------");
					InputStream is = (InputStream) o;
					int c;
					while ((c = is.read()) != -1) {
						System.out.write(c);
					}
				} else {
					log("This is an unknown type");
					log("---------------------------");
					log(o.toString());

					if (emlFound) {
						testo = new EmailBody(o.toString(), part.getContentType());
						return;
					}
				}
			} else {
				// just a separator
				log("---------------------------");
			}
		}

		// /*
		// * If we're saving attachments, write out anything that looks like an
		// * attachment into an appropriately named file. Don't overwrite
		// existing
		// * files to prevent mistakes.
		// */
		// if (saveAttachments && level != 0 && p instanceof MimeBodyPart &&
		// !p.isMimeType("multipart/*")) {
		// String disp = p.getDisposition();
		// // many mailers don't include a Content-Disposition
		// if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
		// if (filename == null)
		// filename = "Attachment" + attnum++;
		// pr("Saving attachment to file " + filename);
		// try {
		// File f = new File(filename);
		// if (f.exists())
		// // XXX - could try a series of names
		// throw new IOException("file exists");
		// ((MimeBodyPart) p).saveFile(f);
		// } catch (IOException ex) {
		// pr("Failed to save attachment: " + ex);
		// }
		// pr("---------------------------");
		// }
		// }
	}

	/**
	 * <p>dumpEnvelope.</p>
	 *
	 * @param m a {@link javax.mail.Message} object.
	 * @throws java.lang.Exception if any.
	 */
	public void dumpEnvelope(Message m) throws Exception {
		log("This is the message envelope");
		log("---------------------------");
		Address[] a;
		// FROM
		if ((a = m.getFrom()) != null) {
			for (int j = 0; j < a.length; j++)
				log("FROM: " + a[j].toString());
		}

		// REPLY TO
		if ((a = m.getReplyTo()) != null) {
			for (int j = 0; j < a.length; j++)
				log("REPLY TO: " + a[j].toString());
		}

		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < a.length; j++) {
				log("TO: " + a[j].toString());
				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++)
						log("  GROUP: " + aa[k].toString());
				}
			}
		}

		// SUBJECT
		log("SUBJECT: " + m.getSubject());

		// DATE
		Date d = m.getSentDate();
		log("SendDate: " + (d != null ? d.toString() : "UNKNOWN"));

		// FLAGS
		Flags flags = m.getFlags();
		StringBuffer sb = new StringBuffer();
		Flags.Flag[] sf = flags.getSystemFlags(); // get the system flags

		boolean first = true;
		for (int i = 0; i < sf.length; i++) {
			String s;
			Flags.Flag f = sf[i];
			if (f == Flags.Flag.ANSWERED)
				s = "\\Answered";
			else if (f == Flags.Flag.DELETED)
				s = "\\Deleted";
			else if (f == Flags.Flag.DRAFT)
				s = "\\Draft";
			else if (f == Flags.Flag.FLAGGED)
				s = "\\Flagged";
			else if (f == Flags.Flag.RECENT)
				s = "\\Recent";
			else if (f == Flags.Flag.SEEN)
				s = "\\Seen";
			else
				continue; // skip it
			if (first)
				first = false;
			else
				sb.append(' ');
			sb.append(s);
		}
	}

	/**
	 * <p>log.</p>
	 *
	 * @param s a {@link java.lang.String} object.
	 */
	public void log(String s) {
		if (showStructure)
			logger.debug(indentStr.substring(0, level * 2));
		logger.debug(s);
	}

	private void saveAttachment(Part part, String filename) throws Exception {
		/*
		 * If we're saving attachments, write out anything that looks like an
		 * attachment into an appropriately named file. Don't overwrite existing
		 * files to prevent mistakes.
		 */
		// if (saveAttachments && level != 0 && p instanceof MimeBodyPart &&
		// !p.isMimeType("multipart/*")) {
		if (saveAttachments && level != 0 && part instanceof MimeMessage) {
			String disp = part.getDisposition();
			// many mailers don't include a Content-Disposition
			// if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
			if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE)) {
				if (filename == null)
					filename = "Attachment" + attnum++;
				log("Saving attachment to file " + filename);
				try {
					// File f = new File(filename);
					if (f == null)
						f = new File(filename);
					if (f.exists())
						// XXX - could try a series of names
						throw new IOException("file exists");
					// ((MimeBodyPart) p).saveFile(f);
					MailUtils.saveToEml((MimeMessage) part, f, true);
				} catch (IOException ex) {
					log("Failed to save attachment: " + ex);
				}
				log("---------------------------");
			}
		}
	}
}
