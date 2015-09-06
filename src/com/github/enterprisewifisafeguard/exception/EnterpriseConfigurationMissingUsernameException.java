package com.github.enterprisewifisafeguard.exception;

public class EnterpriseConfigurationMissingUsernameException extends EnterpriseConfigurationException {
	public EnterpriseConfigurationMissingUsernameException() {
		super("Username is missing");
	}
}
