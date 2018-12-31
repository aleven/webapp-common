package it.attocchi.mail.utils.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * <p>DummyTrustManager class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class DummyTrustManager implements X509TrustManager {

	/** {@inheritDoc} */
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/** {@inheritDoc} */
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	/** {@inheritDoc} */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// return null;
		return new X509Certificate[0];
	}

}
