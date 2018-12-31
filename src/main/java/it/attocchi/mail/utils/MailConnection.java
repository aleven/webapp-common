package it.attocchi.mail.utils;

import it.attocchi.mail.utils.ssl.DummySSLSocketFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import org.apache.log4j.Logger;

import com.sun.mail.imap.IMAPFolder;

/**
 * <p>MailConnection class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class MailConnection {

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(MailConnection.class.getName());

	/** Constant <code>PROTOCOL_IMAP="imap"</code> */
	public static final String PROTOCOL_IMAP = "imap";
	/** Constant <code>PROTOCOL_IMAPS="imaps"</code> */
	public static final String PROTOCOL_IMAPS = "imaps";
	/** Constant <code>PROTOCOL_POP3="pop3"</code> */
	public static final String PROTOCOL_POP3 = "pop3";
	/** Constant <code>PROTOCOL_POP3S="pop3s"</code> */
	public static final String PROTOCOL_POP3S = "pop3s";

	/** Constant <code>FOLDER_INBOX="INBOX"</code> */
	public static final String FOLDER_INBOX = "INBOX";

	// private String protocol = "imap";
	private String host = null;
	private int port = 0;
	private String user = null;
	private String password = null;
	private String mbox = "INBOX";
	private String url = null;

	// private boolean enableSSL = false;

	private boolean debug = false;

	private Session session = null;
	private Store store = null;
	private Folder currentFolder = null;
	List<Folder> folderList = null;

	private boolean enableSSLNoCertCheck;

	// private boolean restoreOriginalSysProp;
	// private Map<String, String> originalMailValues = new HashMap<String,
	// String>();

	/**
	 * <p>Constructor for MailConnection.</p>
	 *
	 * @param host a {@link java.lang.String} object.
	 * @param user a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 */
	public MailConnection(String host, String user, String password) {
		this(host, 0, user, password);
	}

	/**
	 * <p>Constructor for MailConnection.</p>
	 *
	 * @param host a {@link java.lang.String} object.
	 * @param port a int.
	 * @param user a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 */
	public MailConnection(String host, int port, String user, String password) {
		super();
		// this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		// this.mbox = mbox;
		// this.url = url;
		// this.debug = debug;
	}

	/**
	 * <p>isDebug.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * <p>Setter for the field <code>debug</code>.</p>
	 *
	 * @param debug a boolean.
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * <p>isEnableSSLNoCertCheck.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEnableSSLNoCertCheck() {
		return enableSSLNoCertCheck;
	}

	/**
	 * <p>Setter for the field <code>enableSSLNoCertCheck</code>.</p>
	 *
	 * @param enableSSLNoCertCheck a boolean.
	 */
	public void setEnableSSLNoCertCheck(boolean enableSSLNoCertCheck) {
		this.enableSSLNoCertCheck = enableSSLNoCertCheck;
	}

	private synchronized void connect(String protocollo) throws MessagingException {
		// Get a Properties object
		Properties props = System.getProperties();

		if (protocollo.equals(PROTOCOL_POP3S) || protocollo.equals(PROTOCOL_IMAPS)) {

			if (protocollo.equals(PROTOCOL_POP3S)) {
				props.setProperty("mail.pop3s.ssl.enable", "true");

				if (enableSSLNoCertCheck) {

					String key = "mail.pop3s.ssl.socketFactory.class";
					// originalMailValues.put(key, props.getProperty(key));
					props.setProperty(key, DummySSLSocketFactory.class.getName());

					key = "mail.pop3s.ssl.socketFactory.fallback";
					// originalMailValues.put(key, props.getProperty(key));
					props.setProperty(key, "false");
				}

			} else {

				props.setProperty("mail.imap.ssl.enable", "true");

				if (enableSSLNoCertCheck) {
					// props.setProperty("mail.imap.enable", "true");
					String key = "mail.imap.socketFactory.class";
					// originalMailValues.put(key, props.getProperty(key));
					props.setProperty(key, DummySSLSocketFactory.class.getName());

					key = "mail.imap.socketFactory.fallback";
					// originalMailValues.put(key, props.getProperty(key));
					props.setProperty(key, "false");

					key = "mail.imap.ssl.socketFactory.class";
					// originalMailValues.put(key, props.getProperty(key));
					props.setProperty(key, DummySSLSocketFactory.class.getName());

					key = "mail.imap.ssl.socketFactory.fallback";
					// originalMailValues.put(key, props.getProperty(key));
					props.setProperty(key, "false");
				}
			}
			// props.put("java.security.debug", "certpath");
			// props.put("javax.net.debug", "trustmanager");
		}

		// Get a Session object
		// Session session = Session.getInstance(props, null);
		session = Session.getInstance(props, null);
		session.setDebug(debug);

		// Get a Store object
		// Store store = null;
		if (url != null) {
			URLName urln = new URLName(url);
			store = session.getStore(urln);
			store.connect();
		} else {
			if (protocollo != null)
				store = session.getStore(protocollo);
			else
				store = session.getStore();

			// Connect
			if (host != null || user != null || password != null)
				if (port > 0)
					store.connect(host, port, user, password);
				else
					store.connect(host, user, password);
			else
				store.connect();
		}

		// Folder folder = store.getDefaultFolder();
		// if (folder == null) {
		// logger.debug("Cant find default namespace");
		// System.exit(1);
		// }
		//
		// folder = folder.getFolder(mbox);
		// if (folder == null) {
		// logger.debug("Invalid folder");
		// System.exit(1);
		// }

		// Message[] msgs = folder.search(term);

		// store.close();
	}

	private synchronized Store getStore() throws MessagingException {
		if (store == null) {
			throw new MessagingException("Cant find default namespace");
		}
		return store;
	}

	/**
	 * <p>getDefaultFolder.</p>
	 *
	 * @return a {@link javax.mail.Folder} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized Folder getDefaultFolder() throws MessagingException {

		Folder folder = getStore().getDefaultFolder();

		if (folder == null) {
			throw new MessagingException("Cant find default namespace");
			// // logger.debug("Cant find default namespace");
			// // System.exit(1);
		}

		return folder;
	}

	/**
	 * <p>getFolder.</p>
	 *
	 * @param mailBox a {@link java.lang.String} object.
	 * @return a {@link javax.mail.Folder} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized Folder getFolder(String mailBox) throws MessagingException {
		Folder folder = store.getDefaultFolder().getFolder(mailBox);

		if (folder == null) {
			throw new MessagingException("Invalid folder");
			// logger.debug("Cant find default namespace");
			// System.exit(1);
		}

		return folder;
	}

	/**
	 * <p>getFolders.</p>
	 *
	 * @return a {@link java.util.List} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized List<Folder> getFolders() throws MessagingException {

		if (folderList == null) {

			folderList = new ArrayList<Folder>();

			Folder rootFolder = getDefaultFolder();
			// dumpFolder(rootFolder, false, "", false);

			dumpFolder(rootFolder, true, "", false, folderList);
		}

		return folderList;

	}

	static synchronized void dumpFolder(Folder folder, boolean recurse, String tab, boolean verbose, List<Folder> folderList) throws MessagingException {

		logger.debug(tab + "Name:      " + folder.getName());
		logger.debug(tab + "Full Name: " + folder.getFullName());
		logger.debug(tab + "URL:       " + folder.getURLName());

		if (verbose) {
			if (!folder.isSubscribed())
				logger.debug(tab + "Not Subscribed");

			if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
				if (folder.hasNewMessages())
					logger.debug(tab + "Has New Messages");
				logger.debug(tab + "Total Messages:  " + folder.getMessageCount());
				logger.debug(tab + "New Messages:    " + folder.getNewMessageCount());
				logger.debug(tab + "Unread Messages: " + folder.getUnreadMessageCount());
			}
			if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0)
				logger.debug(tab + "Is Directory");

			/*
			 * Demonstrate use of IMAP folder attributes returned by the IMAP
			 * LIST response.
			 */
			if (folder instanceof IMAPFolder) {
				IMAPFolder f = (IMAPFolder) folder;
				String[] attrs = f.getAttributes();
				if (attrs != null && attrs.length > 0) {
					logger.debug(tab + "IMAP Attributes:");
					for (int i = 0; i < attrs.length; i++)
						logger.debug(tab + "    " + attrs[i]);
				}
			}
		}

		folderList.add(folder);

		logger.debug("");

		if ((folder.getType() & Folder.HOLDS_FOLDERS) != 0) {
			if (recurse) {
				Folder[] f = folder.list();
				for (int i = 0; i < f.length; i++) {
					dumpFolder(f[i], recurse, tab + "    ", verbose, folderList);
				}
			}
		}
	}

	/**
	 * <p>getMessages.</p>
	 *
	 * @param msgNum a int.
	 * @return a {@link java.util.List} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized List<Message> getMessages(int msgNum) throws MessagingException {
		return getMessages(1, msgNum);
	}

	/**
	 * <p>getMessages.</p>
	 *
	 * @return a {@link java.util.List} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized List<Message> getMessages() throws MessagingException {
		return getMessages(0, 0);
	}

	private int folderMode = Folder.READ_ONLY;

	/**
	 * <p>getMessages.</p>
	 *
	 * @param start a int.
	 * @param end a int.
	 * @return a {@link java.util.List} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized List<Message> getMessages(int start, int end) throws MessagingException {
		List<Message> res = new ArrayList<Message>();
		if (getCurrentFolder() != null) {

			// int folderMode = Folder.READ_ONLY;
			if (enableDeleteMessageFromServer) {
				folderMode = Folder.READ_WRITE;
			}
			getCurrentFolder().open(folderMode);

			Message[] msgs = null;
			if (start == 0 && end == 0) {
				msgs = getCurrentFolder().getMessages();

			} else {
				msgs = getCurrentFolder().getMessages(start, end);
			}

			res = Arrays.asList(msgs);
		}
		return res;
	}

	/**
	 * <p>getMessagesUnread.</p>
	 *
	 * @return a {@link java.util.List} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized List<Message> getMessagesUnread() throws MessagingException {
		List<Message> res = new ArrayList<Message>();
		if (getCurrentFolder() != null) {

			// int folderMode = Folder.READ_ONLY;
			if (enableDeleteMessageFromServer) {
				folderMode = Folder.READ_WRITE;
			}
			getCurrentFolder().open(folderMode);

			// search for all "unseen" messages
			boolean readStatus = false;
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, readStatus);

			Message[] msgs = null;
			msgs = getCurrentFolder().search(unseenFlagTerm);

			res = Arrays.asList(msgs);
		}
		return res;
	}

	/**
	 * <p>enableFolderWrite.</p>
	 */
	public synchronized void enableFolderWrite() {
		folderMode = Folder.READ_WRITE;
	}

	/**
	 * <p>deleteMessagesFromServer.</p>
	 *
	 * @return a {@link java.util.List} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized List<Message> deleteMessagesFromServer() throws MessagingException {
		return getMessages(0, 0);
	}

	/**
	 * <p>deleteMessagesFromServer.</p>
	 *
	 * @param start a int.
	 * @param end a int.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void deleteMessagesFromServer(int start, int end) throws MessagingException {

		if (getCurrentFolder() != null) {

			getCurrentFolder().open(Folder.READ_WRITE);

			Message[] msgs = null;
			if (start == 0 && end == 0) {
				msgs = getCurrentFolder().getMessages();

			} else {
				msgs = getCurrentFolder().getMessages(start, end);
			}

			for (int i = 0, n = msgs.length; i < n; i++) {
				msgs[i].setFlag(Flags.Flag.DELETED, true);
			}

		}

	}

	/**
	 * <p>connectIMAP.</p>
	 *
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void connectIMAP() throws MessagingException {
		connect(PROTOCOL_IMAP);
	}

	/**
	 * <p>connectIMAPS.</p>
	 *
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void connectIMAPS() throws MessagingException {
		connect(PROTOCOL_IMAPS);
	}

	/**
	 * <p>connectPOP3.</p>
	 *
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void connectPOP3() throws MessagingException {
		connect(PROTOCOL_POP3);
	}

	/**
	 * <p>connectPOP3S.</p>
	 *
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void connectPOP3S() throws MessagingException {
		// enableSSL = true;
		connect(PROTOCOL_POP3S);
	}

	private synchronized void closeFolder(Folder folder) throws MessagingException {
		if (folder != null && folder.isOpen()) {

			// http://www.jguru.com/faq/view.jsp?EID=17035

			boolean deleteOnClose = false;
			if (enableDeleteMessageFromServer) {
				deleteOnClose = true;
			}

			folder.close(deleteOnClose);
			// folder.close(false);

		}
	}

	/**
	 * <p>close.</p>
	 *
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void close() throws MessagingException {
		if (store != null) {
			try {
				closeFolder(currentFolder);

				if (folderList != null) {
					for (Folder folder : folderList) {
						closeFolder(folder);
					}
				}

				// if (restoreOriginalSysProp) {
				// Properties props = System.getProperties();
				// for (String key : originalMailValues.keySet()) {
				// props.setProperty(key, originalMailValues.get(key));
				// }
				// }

			} finally {
				store.close();
				logger.debug("Store Closed");
			}
		}
	}

	/**
	 * <p>close.</p>
	 *
	 * @param conn a {@link it.attocchi.mail.utils.MailConnection} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized static void close(MailConnection conn) throws MessagingException {
		if (conn != null) {
			conn.close();
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void finalize() throws Throwable {
		try {
			close();
		} finally {
			super.finalize();
		}
	}

	/**
	 * <p>getMessageCount.</p>
	 *
	 * @return a int.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized int getMessageCount() throws MessagingException {
		return getCurrentFolder().getMessageCount();
	}

	/**
	 * <p>getUnreadMessageCount.</p>
	 *
	 * @return a int.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized int getUnreadMessageCount() throws MessagingException {
		return getCurrentFolder().getUnreadMessageCount();
	}

	/**
	 * <p>getNewMessageCount.</p>
	 *
	 * @return a int.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized int getNewMessageCount() throws MessagingException {
		return getCurrentFolder().getNewMessageCount();
	}

	/**
	 * <p>Setter for the field <code>currentFolder</code>.</p>
	 *
	 * @param folderName a {@link java.lang.String} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void setCurrentFolder(String folderName) throws MessagingException {
		currentFolder = this.getFolder(folderName);
	}

	/**
	 * <p>Getter for the field <code>currentFolder</code>.</p>
	 *
	 * @return a {@link javax.mail.Folder} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized Folder getCurrentFolder() throws MessagingException {
		if (currentFolder == null) {
			setCurrentFolder(MailConnection.FOLDER_INBOX);
			// throw new MessagingException("Current Folder is not Setted");
		}
		return currentFolder;
	}

	/**
	 * <p>getMimeMessage.</p>
	 *
	 * @param message a {@link javax.mail.Message} object.
	 * @return a {@link javax.mail.internet.MimeMessage} object.
	 * @throws javax.mail.MessagingException if any.
	 * @throws java.io.IOException if any.
	 */
	public synchronized MimeMessage getMimeMessage(Message message) throws MessagingException, IOException {
		MimeMessage m2 = new MimeMessage(session, message.getInputStream());
		return m2;
	}

	private boolean enableDeleteMessageFromServer;

	/**
	 * <p>isEnableDeleteMessageFromServer.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEnableDeleteMessageFromServer() {
		return enableDeleteMessageFromServer;
	}

	/**
	 * <p>Setter for the field <code>enableDeleteMessageFromServer</code>.</p>
	 *
	 * @param enableDeleteMessageFromServer a boolean.
	 */
	public synchronized void setEnableDeleteMessageFromServer(boolean enableDeleteMessageFromServer) {
		this.enableDeleteMessageFromServer = enableDeleteMessageFromServer;
	}

	/**
	 * <p>markMessageDeleted.</p>
	 *
	 * @param mail a {@link javax.mail.Message} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void markMessageDeleted(Message mail) throws MessagingException {
		mail.setFlag(Flags.Flag.DELETED, true);
	}

	/**
	 * <p>markMessageAsRead.</p>
	 *
	 * @param mail a {@link javax.mail.Message} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void markMessageAsRead(Message mail) throws MessagingException {
		mail.setFlag(Flags.Flag.SEEN, true);
	}

	/**
	 * <p>markMessageAsUnRead.</p>
	 *
	 * @param mail a {@link javax.mail.Message} object.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void markMessageAsUnRead(Message mail) throws MessagingException {
		mail.setFlag(Flags.Flag.SEEN, false);
	}

	// public void saveToEml(Message mail, File emlFile) throws Exception {
	// OutputStream os = null;
	// try {
	// os = new FileOutputStream(emlFile);
	//
	// mail.writeTo(os);
	// } finally {
	// if (os != null) {
	// os.close();
	// }
	// }
	// }

}
