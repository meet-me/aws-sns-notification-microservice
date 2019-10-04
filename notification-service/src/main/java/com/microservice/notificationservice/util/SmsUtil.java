package com.microservice.notificationservice.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.notificationservice.sms.SmsClient;
import com.twilio.rest.api.v2010.account.Message.Status;

@Component
public class SmsUtil {

	private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	
	@Autowired
	private SmsClient smsClient;
	
	public SmsUtil() {
//		if(smsClientVendor.equalsIgnoreCase("twilio")) {
//			this.smsClient = TwilioClient.getInstance();
//			logger.info(smsClient.toString());
//		}else {
//			this.smsClient = AwsSnsClient.getInstance();
//			logger.info(smsClient.toString());			
//		}
	}
	
	public boolean sendSms(String adminContactNumber, String message) {
		logger.info(adminContactNumber.toString());
		logger.info(" SMS sent");

		if(this.smsClient.sendSms(adminContactNumber, message)) {
			return true;
		}
		else {
			return false;
		}
	}

	public Map<String, Status> getSmsStatus() {
		return this.smsClient.getStatus();
		
	}
}
