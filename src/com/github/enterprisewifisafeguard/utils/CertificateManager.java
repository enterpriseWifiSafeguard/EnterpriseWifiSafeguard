package com.github.enterprisewifisafeguard.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.enterprisewifisafeguard.R;

import android.content.Context;
import android.util.Log;

/**
 * This class provides all included root certificates.
 * @author Maximilian Ortwein
 *
 */
public class CertificateManager {
	//Map <Certificate Name, Certificate> the store for all certificates
	Map<String, X509Certificate> certificates = new HashMap<String, X509Certificate>();
	//instance of this class
	private static CertificateManager instance = null;

	//Singleton implementation
	public static CertificateManager getInstance(Context context) {
		if (CertificateManager.instance == null) {
			CertificateManager.instance = new CertificateManager(context);
		}
		return CertificateManager.instance;
	}
     
	private CertificateManager(Context context) {
		CertificateFactory cf = null;
		//get all fields from R.raw
		Field[] fields = R.raw.class.getFields();

		try {
            //A new Certificate Factory for crating a X509 certificate
			cf = CertificateFactory.getInstance("X.509");
			//Read all certificate files from R.raw
			for (int count = 0; count < fields.length; count++) {
				int rid = fields[count].getInt(fields[count]);
				//Open the certificate file as stream
				InputStream fis = context.getResources().openRawResource(rid);
				//create a certificate from stream
				X509Certificate c = (X509Certificate) cf.generateCertificate(fis);
				//Get the DN
				String name = c.getSubjectDN().getName();
				//extrac CN from DN because we need it as key for the map
				String cn = "";
				Matcher m = Pattern.compile("CN=[A-Za-z]*[, ]*[ A-Za-z]*[0-9]*").matcher(name);
				if (m.find()) {
					cn = m.group();
				}
				//trim the CN string
				cn = cn.replace("CN=", "");
				cn = cn.trim();
				//accept only certificate names with more than one letter
				if (cn.length() > 1) {
					certificates.put(cn, c);
				}
			}
		} catch (Exception e) {
			Log.d("Cert", "Cert error " + e.toString());
		} finally {
		}
	}
    /**
     * Returns Certificate by Name
     * @param certName
     * @return the certificate
     */
	public X509Certificate getCertificate(String certName) {
		Log.d("ews-debug", certificates.get(certName).toString());
		return certificates.get(certName);
	}

	//Get all certificate names as Set
	public Set<String> getAllCertNames() {
		return certificates.keySet();
	}

}
