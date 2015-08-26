package com.github.enterprisewifisafeguard.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
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
import android.util.Log;
import android.util.TypedValue;


public class CertificateManager {
Map<String, X509Certificate> certificates = new HashMap<String, X509Certificate>();
private static CertificateManager instance = null;

public static CertificateManager getInstance(Context context) {
	if(CertificateManager.instance == null) {
		CertificateManager.instance = new CertificateManager(context);
	}
	return CertificateManager.instance;
}

@SuppressWarnings("unchecked")
private CertificateManager (Context context)
{	
    CertificateFactory cf = null;
    Field[] fields = R.raw.class.getFields();

try{
	
	cf = CertificateFactory.getInstance("X.509");
	for(int count=0; count < fields.length; count++) {
		int rid = fields[count].getInt(fields[count]);
		InputStream fis = context.getResources().openRawResource(rid);
		X509Certificate c = (X509Certificate) cf.generateCertificate(fis);
        String name = c.getSubjectDN().getName();
        String cn = "";
        Matcher m = Pattern.compile("CN=[A-Za-z]*[, ]*[ A-Za-z]*[0-9]*").matcher(name);
        if (m.find())
        {
         cn = m.group();
        }
        cn = cn.replace("CN=","");
        cn = cn.trim();
        Log.d("CN", cn);
        if (cn.length()>1)
        {
        	
        	certificates.put(cn, c);
        }
     }
}
catch(Exception e) {	
	Log.d("Cert","Cert error "+e.toString());
}
finally
{}
}

public X509Certificate getCertificate(String certName) {
	return certificates.get(certName);
}

public Set<String> getAllCertNames() {
	Log.d("Cert2", "Number: "+certificates.size());
	return certificates.keySet();
}

}



