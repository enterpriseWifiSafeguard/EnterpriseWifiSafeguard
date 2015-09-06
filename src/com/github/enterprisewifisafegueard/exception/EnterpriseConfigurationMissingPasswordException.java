package com.github.enterprisewifisafegueard.exception;

public class EnterpriseConfigurationMissingPasswordException extends EnterpriseConfigurationException {
	public EnterpriseConfigurationMissingPasswordException() {
		super("Password is missing");
	}
}
