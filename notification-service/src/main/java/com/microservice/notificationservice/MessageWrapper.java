package com.microservice.notificationservice;

import java.io.Serializable;
import java.util.Map;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;

public class MessageWrapper implements Serializable {
	
	private static final long serialVersionUID = -8087948948182699842L;
	
	private Map<String, Message> message;
	
	private Map<String, Status> status;
	
	public Map<String, Message> getMessage() {
		return message;
	}
	public void setMessage(Map<String, Message> message) {
		this.message = message;
	}
	
	public Map<String, Status> getStatus() {
		return status;
	}
	public void setStatus(Map<String, Status> status) {
		this.status = status;
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageWrapper other = (MessageWrapper) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	
}
