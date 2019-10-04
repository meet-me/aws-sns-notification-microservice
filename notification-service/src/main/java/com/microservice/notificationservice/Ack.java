package com.microservice.notificationservice;

import java.io.Serializable;
import java.util.Map;

public class Ack implements Serializable {
	
	private static final long serialVersionUID = -8087948948182699842L;
	
	private Map<String, Boolean> message;
	
	public Map<String, Boolean> getMessage() {
		return message;
	}

	public void setMessage(Map<String, Boolean> message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("MessageWrapper [message=%s]", message);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}
	
}
