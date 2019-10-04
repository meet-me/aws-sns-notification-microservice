package com.microservice.notificationservice.sms.twilio;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.notificationservice.sms.SmsClient;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;
import com.twilio.type.PhoneNumber;

@Component
@Scope("singleton")
@PropertySource("classpath:twilio-application.properties")
@ConditionalOnProperty(name = "application.sms.client.vendor", havingValue = "twilio")
public class TwilioClient implements SmsClient {
	
    public static String ACCOUNT_SID;// = "AC5a534f506e928265e4743b7827f8f9e8";
    public static String AUTH_TOKEN;// = "33755b96ef89045005915e4ff2b72116";
    public static String SENDER_PHONE;// = "+17042597031";
    
//    private static TwilioClient twilioClient;
    
    private static final Logger logger = LoggerFactory.getLogger(TwilioClient.class);
    
    private TwilioClient(@Value("${twilio.account.sid}") String ACCOUNT_SID, @Value("${twilio.auth.token}") String AUTH_TOKEN, @Value("${twilio.sender.phone}") String SENDER_PHONE) { 
    	TwilioClient.ACCOUNT_SID = ACCOUNT_SID;
    	TwilioClient.AUTH_TOKEN = AUTH_TOKEN;
    	TwilioClient.SENDER_PHONE = SENDER_PHONE;
    	
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    
/*    synchronized public static TwilioClient getInstance() {
    	if(twilioClient == null) {
        	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        	twilioClient = new TwilioClient();
    	}
    	return twilioClient;
    }*/
    
    public boolean sendSms(String phoneNumber, String message) {
    	logger.info("called TwilioClient method with Phone:{} and message as '{}'", phoneNumber, message);
    	Message createdMessage = null;
		try {
			createdMessage = Message.creator(new PhoneNumber(phoneNumber), 
			new PhoneNumber(SENDER_PHONE), message).create();
		} catch (TwilioException e) {
			e.printStackTrace();
		} 
		if(createdMessage!=null) return true;
		else return false;
    }

	@Override
	public Map<String, Status> getStatus() {
		ResourceSet<Message> messages = Message.reader().read();
		Map<String, Status> status = new HashMap<>();
		for(Message message: messages) {
			logger.info(message.getSid()+" : "+ message.getTo() + " : " +message.getStatus());
			status.put(message.getSid(), message.getStatus());			 
		}
		return status;
	}
    
    /*public SmsStatus getStatusWithMapper(Status rawStatus) {
    	SmsStatus status;
    	
    	switch(rawStatus) {
	    	case QUEUED : 
	    	case SENDING: 
	    	case SENT: status = SmsStatus.SENT;
	    		break;
	    	case FAILED: status = SmsStatus.FAILED;
	    		break;
	    	case DELIVERED: status = SmsStatus.DELIVERED;
	    		break;
	    	case UNDELIVERED: status = SmsStatus.UNDELIVERED;
	    		break;
	    	case RECEIVING: 
	    	case RECEIVED: 
	    	case ACCEPTED: 
	    	case SCHEDULED : 
	    	case READ: status = SmsStatus.READ;
	    		break;
			default: status = SmsStatus.FAILED;
				break;
    	}
			
		return status;
    }*/
}
