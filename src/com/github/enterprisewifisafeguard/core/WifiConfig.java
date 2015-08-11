package com.github.enterprisewifisafeguard.core;

import java.security.cert.X509Certificate;

import com.github.enterprisewifisafegueard.exception.EnterpriseConfigurationException;
import com.github.enterprisewifisafegueard.exception.EnterpriseConfigurationMissingPasswordException;
import com.github.enterprisewifisafegueard.exception.EnterpriseConfigurationMissingUsernameException;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.util.Log;

public class WifiConfig {
	//Attributes
	private int eap_method;
	private int phase2_method;
	private String ssid;
	private String username;
	private String password;
	private String anonymous_ident;
	private X509Certificate ca_certificate;
	private String subject_match;
	
	//Setters
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public void setEap_method(int eap_method) {
		this.eap_method = eap_method;
	}

	public void setPhase2_method(int phase2_method) {
		this.phase2_method = phase2_method;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAnonymous_ident(String anonymous_ident) {
		this.anonymous_ident = anonymous_ident;
	}

	public void setCa_certificate(X509Certificate ca_certificate) {
		this.ca_certificate = ca_certificate;
	}

	public void setSubject_match(String subject_match) {
		this.subject_match = subject_match;
	}
	
	
	//Create Wifi Profile
	public WifiConfiguration createNewWifiProfile() throws EnterpriseConfigurationException {
		WifiConfiguration profile = new WifiConfiguration();
		
		//Security Settings for WPA/WPA2
        profile.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);						
        profile.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);   	
        profile.allowedProtocols.set(WifiConfiguration.Protocol.RSN);         			
        profile.allowedProtocols.set(WifiConfiguration.Protocol.WPA);         			
        profile.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);   			
        profile.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);   			
        profile.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);  			
        profile.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);	 		
        
        //Set SSID
		profile.SSID = this.ssid;
		
		//Enterprise Configuration
		WifiEnterpriseConfig enterprise = new WifiEnterpriseConfig();
		//Set Username
		if(username != null && username.length() > 0) {
			enterprise.setIdentity(this.username);
		}
		else{
			throw new EnterpriseConfigurationMissingUsernameException();
		}
		//Set Password
		if(password != null && password.length() > 0) {
			enterprise.setPassword(this.password);
		}
		else {
			throw new EnterpriseConfigurationMissingPasswordException();
		}
		//Set eap method
		enterprise.setEapMethod(this.eap_method);
		//Set Phase2 methos
		enterprise.setPhase2Method(this.phase2_method);
		//Set Anonymous Identity
		enterprise.setAnonymousIdentity(this.anonymous_ident);
		//Set CA_Certificate
		enterprise.setCaCertificate(this.ca_certificate);
		//Set Subject_match
		enterprise.setSubjectMatch(this.subject_match);
		//Add enterprise config to profile
		profile.enterpriseConfig=enterprise;
		
		//return the new profile
		return profile;
	}

}
