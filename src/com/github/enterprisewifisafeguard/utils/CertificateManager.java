package com.github.enterprisewifisafeguard.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;


public class CertificateManager {

	//Some examplecode how to retrive the root certificates from bundle and extract the Common Name
	
/*	FileInputStream fis = null;
	try {
		fis = new FileInputStream("ca-bundle.crt");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 

	 CertificateFactory cf = null;
	try {
		cf = CertificateFactory.getInstance("X.509");
	} catch (CertificateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     ArrayList<X509Certificate> certs = null;
	try {
		certs = (ArrayList) cf.generateCertificates(fis);
	} catch (CertificateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
     for(X509Certificate c : certs) {
        String name = c.getSubjectX500Principal().getName();
        LdapName ln = null;
		try {
			ln = new LdapName(name);
		} catch (InvalidNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(Rdn rdn : ln.getRdns()) {
            if(rdn.getType().equalsIgnoreCase("CN")) {
                System.out.println(rdn.getValue());
                break;
            }
    	 //System.out.println(c);
     }

}*/

}
