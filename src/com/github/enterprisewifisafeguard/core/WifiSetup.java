package com.github.enterprisewifisafeguard.core;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiSetup {
private boolean error = false;	
private WifiManager wifiObj = null;
private Context context;

public WifiSetup(Context context) {
	wifiObj = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	this.context = context;
}

public boolean createConnection(String ssid, String username, String password, String anonymous, String cn, int eap_meth, int phase2) {
	WifiConfig entConfig = new WifiConfig();
	WifiConfiguration wifi = this.getWifiConfiguration(ssid);
	//enable Wifi
	if(!wifiObj.isWifiEnabled()){
		wifiObj.setWifiEnabled(true);
	}
	//remove if wifi exists
	if(wifi !=null) {
		error = !this.removeWifi(wifi.networkId);
	}
	//create new Wifi Configuration
	entConfig.setAnonymous_ident(anonymous);
	entConfig.setUsername(username);
	entConfig.setPassword(password);
	entConfig.setSubject_match(cn);
	entConfig.setEap_method(eap_meth);
	entConfig.setPhase2_method(phase2);
	try {
	entConfig.setCa_certificate(this.getRootCA(this.context));
	wifi = entConfig.createNewWifiProfile();
	}
	catch (Exception e) {
		error = true;
		return error;
	}
	//add wifi network
	int id = wifiObj.addNetwork(wifi);
	if (id == -1) {
		error = true;
	}
	//save network configurations
	error = wifiObj.saveConfiguration();
	return error;
}

public WifiConfiguration getWifiConfiguration(String ssid) {
	WifiConfiguration Config = null;
	List<WifiConfiguration> wifis = wifiObj.getConfiguredNetworks();
	if (wifis == null) {
		Config = null;
	}
	else {
		for (WifiConfiguration curr : wifis) {
			if(curr.SSID.equals(ssid)){
				Config = curr;
				break;
			}
		}
	}
	return Config;
}

public boolean removeWifi(int netId) {
	return wifiObj.removeNetwork(netId);
}

public int addWifi (WifiConfiguration config) {
	return wifiObj.addNetwork(config);
}

//TODO Cerfificate stuff

private X509Certificate getRootCA(Context context) throws CertificateException {
    CertificateFactory ca = null;
    X509Certificate cert = null;
    
        ca = CertificateFactory.getInstance("X.509");
      //TODO  ;
      //TODO  cert = (X509Certificate) ca.generateCertificate(certStream);
        cert.getSubjectDN().getName();
      return cert;       
}
}
