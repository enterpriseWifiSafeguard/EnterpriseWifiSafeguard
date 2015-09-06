package com.github.enterprisewifisafeguard.core;

import java.security.cert.X509Certificate;
import java.util.List;

import com.github.enterprisewifisafeguard.exception.EnterpriseConfigurationException;
import com.github.enterprisewifisafeguard.utils.CertificateManager;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiSetup {
	private WifiManager wifiObj = null;
	CertificateManager certMan = null;

	public WifiSetup(Context context) {
		wifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		certMan = CertificateManager.getInstance(context);
	}

	public boolean createConnection(String ssid, String username, String password, String anonymous, String cn,
			int eap_meth, int phase2, String caName) {
		Log.d("ews-debug", ssid);
		WifiConfig entConfig = new WifiConfig();
		// enable Wifi
		if (!wifiObj.isWifiEnabled()) {
			wifiObj.setWifiEnabled(true);
		}

		// remove if wifi exists
		final WifiConfiguration oldWifi = this.getWifiConfiguration(ssid);
		if (oldWifi != null) {
			this.removeWifi(oldWifi.networkId);
		}

		// create new Wifi Configuration
		entConfig.setSsid(ssid);
		entConfig.setAnonymous_ident(anonymous);
		entConfig.setUsername(username);
		entConfig.setPassword(password);
		entConfig.setSubject_match(cn);
		entConfig.setEap_method(eap_meth);
		entConfig.setPhase2_method(phase2);

		entConfig.setCa_certificate(this.getRootCA(caName));
		Log.d("ews-debug", caName);

		final WifiConfiguration wifi;
		try {
			wifi = entConfig.createNewWifiProfile();
		} catch (EnterpriseConfigurationException e) {
			Log.d("ews-debug", e.getMessage());
			return true;
		}

		// add wifi network
		final int id = wifiObj.addNetwork(wifi);
		Log.d("ews-debug", "" + id);
		if (id == -1) {
			Log.d("ews-debug", "Could not add network.");
			return true;
		}
		
		// save network configurations
		if (!wifiObj.saveConfiguration()) {
			Log.d("ews-debug", "Could not save WIFI configuration.");
			return true;
		}
		return false;
	}

	public WifiConfiguration getWifiConfiguration(String ssid) {
		List<WifiConfiguration> wifis = wifiObj.getConfiguredNetworks();
		if (wifis != null) {
			for (WifiConfiguration curr : wifis) {
				if (curr.SSID.equals(ssid)) {
					return curr;
				}
			}
		}
		return null;
	}

	public boolean removeWifi(int netId) {
		return wifiObj.removeNetwork(netId);
	}

	public int addWifi(WifiConfiguration config) {
		return wifiObj.addNetwork(config);
	}

	private X509Certificate getRootCA(String caName) {
		return certMan.getCertificate(caName);
	}
}
