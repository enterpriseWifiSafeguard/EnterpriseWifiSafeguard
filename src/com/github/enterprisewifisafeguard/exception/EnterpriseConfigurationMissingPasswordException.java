package com.github.enterprisewifisafeguard.exception;

public class EnterpriseConfigurationMissingPasswordException extends EnterpriseConfigurationException {
	public EnterpriseConfigurationMissingPasswordException() {
		super("Password is missing");
	}
}
