package com.github.enterprisewifisafeguard.core;


import java.security.cert.X509Certificate;
import java.util.List;

import com.github.enterprisewifisafeguard.utils.CertificateManager;
import com.github.enterprisewifisafegueard.exception.EnterpriseConfigurationException;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiSetup {
private boolean error = false;	
private WifiManager wifiObj = null;
CertificateManager certMan = null;

public WifiSetup(Context context) {
	wifiObj = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	certMan = CertificateManager.getInstance(context);
}

public boolean createConnection(String ssid, String username, String password, String anonymous, String cn, int eap_meth, int phase2, String caName) {
	Log.d("ews-debug", ssid);
	WifiConfig entConfig = new WifiConfig();
	WifiConfiguration wifi = this.getWifiConfiguration(ssid);
	//enable Wifi
	if(!wifiObj.isWifiEnabled()){
		wifiObj.setWifiEnabled(true);
	}
	//remove if wifi exists
	if(wifi !=null) {
	   this.removeWifi(wifi.networkId);
	}
	//create new Wifi Configuration
	entConfig.setSsid(ssid);
	entConfig.setAnonymous_ident(anonymous);
	entConfig.setUsername(username);
	entConfig.setPassword(password);
	entConfig.setSubject_match(cn);
	entConfig.setEap_method(eap_meth);
	entConfig.setPhase2_method(phase2);
	
	entConfig.setCa_certificate(this.getRootCA(caName));
	Log.d("ews-debug",caName);
	try {
		wifi = entConfig.createNewWifiProfile();
	} catch (EnterpriseConfigurationException e) {
		Log.d("ews-debug",e.getMessage());
	}

	//add wifi network
	int id = wifiObj.addNetwork(wifi);
	Log.d("ews-debug",""+id);
	if (id == -1) {
		error = true;
	}
	//save network configurations
	error = !wifiObj.saveConfiguration();
	Log.d("ews-debug", "Error: "+error);
	return error;
}

public WifiConfiguration getWifiConfiguration(String ssid) {
	WifiConfiguration config = null;
	List<WifiConfiguration> wifis = wifiObj.getConfiguredNetworks();
	if (wifis == null) {
		config = null;
	}
	else {
		for (WifiConfiguration curr : wifis) {
			if(curr.SSID.equals(ssid)){
				config = curr;
				break;
			}
		}
	}
	return config;
}

public boolean removeWifi(int netId) {
	return wifiObj.removeNetwork(netId);
}

public int addWifi (WifiConfiguration config) {
	return wifiObj.addNetwork(config);
}

private X509Certificate getRootCA(String caName){
    X509Certificate cert = null;
    
    cert = certMan.getCertificate(caName);
    return cert;       
}
}
