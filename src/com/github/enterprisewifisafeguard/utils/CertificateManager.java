package com.github.enterprisewifisafeguard.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.enterprisewifisafeguard.R;

import android.content.Context;


public class CertificateManager {
Map<String, X509Certificate> certificates = new HashMap<String, X509Certificate>();

public CertificateManager (Context context)
{	
    InputStream fis = null;
    CertificateFactory cf = null;
    ArrayList<X509Certificate> certs = null;

try{
	fis = context.getResources().openRawResource(R.raw.cabundle);
	cf = CertificateFactory.getInstance("X.509");
	certs = (ArrayList<X509Certificate>) cf.generateCertificates(fis);
	for(X509Certificate c : certs) {
        String name = c.getSubjectDN().getName();
        String cn = "";
        Matcher m = Pattern.compile("CN=[A-Za-z]*[, ]*[ A-Za-z]*[0-9]*").matcher(name);
        if (m.find())
        {
         cn = m.group();
        }
        cn = cn.replace("CN=","");
        cn = cn.trim();
        if (cn.length()>1)
        {
        	certificates.put("cn", c);
        }
     }
}
catch(Exception e) {	
}
finally
{}
}

public X509Certificate getCertificate(String certName) {
	return certificates.get(certName);
}

public Set<String> getAllCertNames() {
	return certificates.keySet();
}

}



