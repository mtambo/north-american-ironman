package com.ironman.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.net.ssl.SSLSocketFactory;

public class SSLTest {

	public static final String TARGET_HTTPS_SERVER = "";
	public static final int TARGET_HTTPS_PORT = 443;
//  https://hc.apache.org/httpcomponents-client-4.3.x/httpclient/apidocs/org/apache/http/conn/ssl/SSLSocketFactory.html
// 	http://www.smartjava.org/content/how-analyze-java-ssl-errors
//	To download and store the certificate run the following command, changing $ADDRESS for the sites address. 
//	echo -n | openssl s_client -connect $ADDRESS:443 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > /tmp/$ADDRESS.cert
	
//	sudo keytool -importcert -alias "$ALIAS" -file /tmp/$ADDRESS.cert -keystore $PATH/cacerts -storepass changeit
	
//	verify if a given root cert, intermediate cert(s), and CA-signed cert match to form a complete SSL chain.  
//	openssl verify -verbose -purpose sslserver -CAfile <file containing both root and intermediates> <file containing signed cert> 
	
	public static void main(String[] args) throws Exception {
		try {
			// Load the JDK's cacerts keystore file
			String filename = System.getProperty("java.home")
					+ "/lib/security/cacerts".replace('/', File.separatorChar);
			FileInputStream is = new FileInputStream(filename);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			String password = "changeit";
			keystore.load(is, password.toCharArray());

			// This class retrieves the most-trusted CAs from the keystore
			PKIXParameters params = new PKIXParameters(keystore);

			// Get the set of trust anchors, which contain the most-trusted CA
			// certificates
			@SuppressWarnings("rawtypes")
			Iterator it = params.getTrustAnchors().iterator();
			while (it.hasNext()) {
				TrustAnchor ta = (TrustAnchor) it.next();
				// Get certificate
				X509Certificate cert = ta.getTrustedCert();
				System.out.println(cert.getSubjectDN()+"\n");
			}
		} catch (CertificateException e) {
		} catch (KeyStoreException e) {
		} catch (NoSuchAlgorithmException e) {
		} catch (InvalidAlgorithmParameterException e) {
		} catch (IOException e) {
		}

		/*
		 * System.setProperty("javax.net.ssl.trustStore",
		 * "/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.60-2.4.7.0.fc20.x86_64/jre/lib/security/cacerts"
		 * ); System.setProperty("javax.net.ssl.trustStorePassword",
		 * "changeit");
		 */

		System.setProperty("javax.net.debug", "ssl");
		Socket socket = SSLSocketFactory.getDefault().createSocket(
				TARGET_HTTPS_SERVER, TARGET_HTTPS_PORT);
		try {
			Writer out = new OutputStreamWriter(socket.getOutputStream(),
					"ISO-8859-1");
			out.write("GET / HTTP/1.1\r\n");
			out.write("Host: " + TARGET_HTTPS_SERVER + ":" + TARGET_HTTPS_PORT
					+ "\r\n");
			out.write("Agent: SSL-TEST\r\n");
			out.write("\r\n");
			out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "ISO-8859-1"));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
			socket.close();
		}
	}
}