package com.microservice.notificationservice.sms;

import java.util.Map;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;

public interface SmsClient {
	
	//To sync up the status sent in case of multiple sms clients used.
	//Hence we are translating them into common status only.
    public enum SmsStatus {
        QUEUED,
//        SENDING,
        SENT,
        FAILED,
        DELIVERED,
        UNDELIVERED,
//        RECEIVING,
//        RECEIVED,
//        ACCEPTED,
//        SCHEDULED,
        READ;
    	
    }
	boolean sendSms(String string, String string2);
	Map<String, Status> getStatus();
}
