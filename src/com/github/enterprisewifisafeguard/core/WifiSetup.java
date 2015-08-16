package com.github.enterprisewifisafeguard.core;

import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public class WifiSetup {
private boolean error = false;	
private WifiManager wifiObj = null;

public WifiSetup(Context context) {
	wifiObj = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
}

//TODO add arguments to the function!!!! this function handles user input and will be called from the ui
public boolean createConnection() {
	//TODO enable wifi
	//TODO remove
	//TODO create
	//TODO add
	//TODO save
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
}
