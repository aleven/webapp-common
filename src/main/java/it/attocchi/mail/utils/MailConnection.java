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
 * 
 * @author Mirco
 * 
 */
public class MailConnection {

	protected static final Logger logger = Logger.getLogger(MailConnection.class.getName());

	public static final String PROTOCOL_IMAP = "imap";
	public static final String PROTOCOL_IMAPS = "imaps";
	public static final String PROTOCOL_POP3 = "pop3";
	public static final String PROTOCOL_POP3S = "pop3s";

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

	public MailConnection(String host, String user, String password) {
		this(host, 0, user, password);
	}

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

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isEnableSSLNoCertCheck() {
		return enableSSLNoCertCheck;
	}

	public void setEnableSSLNoCertCheck(boolean enableSSLNoCertCheck) {
		this.enableSSLNoCertCheck = enableSSLNoCertCheck;
	}

	private void connect(String protocollo) throws MessagingException {
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

	private Store getStore() throws MessagingException {
		if (store == null) {
			throw new MessagingException("Cant find default namespace");
		}
		return store;
	}

	public Folder getDefaultFolder() throws MessagingException {

		Folder folder = getStore().getDefaultFolder();

		if (folder == null) {
			throw new MessagingException("Cant find default namespace");
			// // logger.debug("Cant find default namespace");
			// // System.exit(1);
		}

		return folder;
	}

	public Folder getFolder(String mailBox) throws MessagingException {
		Folder folder = store.getDefaultFolder().getFolder(mailBox);

		if (folder == null) {
			throw new MessagingException("Invalid folder");
			// logger.debug("Cant find default namespace");
			// System.exit(1);
		}

		return folder;
	}

	public List<Folder> getFolders() throws MessagingException {

		if (folderList == null) {

			folderList = new ArrayList<Folder>();

			Folder rootFolder = getDefaultFolder();
			// dumpFolder(rootFolder, false, "", false);

			dumpFolder(rootFolder, true, "", false, folderList);
		}

		return folderList;

	}

	static void dumpFolder(Folder folder, boolean recurse, String tab, boolean verbose, List<Folder> folderList) throws MessagingException {

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

	public List<Message> getMessages(int msgNum) throws MessagingException {
		return getMessages(1, msgNum);
	}

	public List<Message> getMessages() throws MessagingException {
		return getMessages(0, 0);
	}

	private int folderMode = Folder.READ_ONLY;

	public List<Message> getMessages(int start, int end) throws MessagingException {
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

	public List<Message> getMessagesUnread() throws MessagingException {
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

	public void enableFolderWrite() {
		folderMode = Folder.READ_WRITE;
	}

	public List<Message> deleteMessagesFromServer() throws MessagingException {
		return getMessages(0, 0);
	}

	public void deleteMessagesFromServer(int start, int end) throws MessagingException {

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

	public void connectIMAP() throws MessagingException {
		connect(PROTOCOL_IMAP);
	}

	public void connectIMAPS() throws MessagingException {
		connect(PROTOCOL_IMAPS);
	}

	public void connectPOP3() throws MessagingException {
		connect(PROTOCOL_POP3);
	}

	public void connectPOP3S() throws MessagingException {
		// enableSSL = true;
		connect(PROTOCOL_POP3S);
	}

	private void closeFolder(Folder folder) throws MessagingException {
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

	public void close() throws MessagingException {
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

	public static void close(MailConnection conn) throws MessagingException {
		if (conn != null) {
			conn.close();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			close();
		} finally {
			super.finalize();
		}
	}

	public int getMessageCount() throws MessagingException {
		return getCurrentFolder().getMessageCount();
	}

	public int getUnreadMessageCount() throws MessagingException {
		return getCurrentFolder().getUnreadMessageCount();
	}

	public int getNewMessageCount() throws MessagingException {
		return getCurrentFolder().getNewMessageCount();
	}

	public void setCurrentFolder(String folderName) throws MessagingException {
		currentFolder = this.getFolder(folderName);
	}

	public Folder getCurrentFolder() throws MessagingException {
		if (currentFolder == null) {
			setCurrentFolder(MailConnection.FOLDER_INBOX);
			// throw new MessagingException("Current Folder is not Setted");
		}
		return currentFolder;
	}

	public MimeMessage getMimeMessage(Message message) throws MessagingException, IOException {
		MimeMessage m2 = new MimeMessage(session, message.getInputStream());
		return m2;
	}

	private boolean enableDeleteMessageFromServer;

	public boolean isEnableDeleteMessageFromServer() {
		return enableDeleteMessageFromServer;
	}

	public void setEnableDeleteMessageFromServer(boolean enableDeleteMessageFromServer) {
		this.enableDeleteMessageFromServer = enableDeleteMessageFromServer;
	}

	public void markMessageDeleted(Message mail) throws MessagingException {
		mail.setFlag(Flags.Flag.DELETED, true);
	}

	public void markMessageAsRead(Message mail) throws MessagingException {
		mail.setFlag(Flags.Flag.SEEN, true);
	}

	public void markMessageAsUnRead(Message mail) throws MessagingException {
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
