package it.attocchi.mail.utils.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * <p>DummySSLSocketFactory class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class DummySSLSocketFactory extends SSLSocketFactory {
	private SSLSocketFactory factory;

	/**
	 * <p>Constructor for DummySSLSocketFactory.</p>
	 */
	public DummySSLSocketFactory() {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new DummyTrustManager() }, null);
			factory = (SSLSocketFactory) sslcontext.getSocketFactory();
		} catch (Exception ex) {
			// ignore
		}
	}

	/**
	 * <p>getDefault.</p>
	 *
	 * @return a {@link javax.net.SocketFactory} object.
	 */
	public static SocketFactory getDefault() {
		return new DummySSLSocketFactory();
	}

	/**
	 * <p>createSocket.</p>
	 *
	 * @return a {@link java.net.Socket} object.
	 * @throws java.io.IOException if any.
	 */
	public Socket createSocket() throws IOException {
		return factory.createSocket();
	}

	/** {@inheritDoc} */
	public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException {
		return factory.createSocket(socket, s, i, flag);
	}

	/** {@inheritDoc} */
	public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1, int j) throws IOException {
		return factory.createSocket(inaddr, i, inaddr1, j);
	}

	/** {@inheritDoc} */
	public Socket createSocket(InetAddress inaddr, int i) throws IOException {
		return factory.createSocket(inaddr, i);
	}

	/** {@inheritDoc} */
	public Socket createSocket(String s, int i, InetAddress inaddr, int j) throws IOException {
		return factory.createSocket(s, i, inaddr, j);
	}

	/** {@inheritDoc} */
	public Socket createSocket(String s, int i) throws IOException {
		return factory.createSocket(s, i);
	}

	/**
	 * <p>getDefaultCipherSuites.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public String[] getDefaultCipherSuites() {
		return factory.getDefaultCipherSuites();
	}

	/**
	 * <p>getSupportedCipherSuites.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public String[] getSupportedCipherSuites() {
		return factory.getSupportedCipherSuites();
	}

}
