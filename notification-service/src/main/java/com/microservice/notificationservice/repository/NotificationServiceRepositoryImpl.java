package com.microservice.notificationservice.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.microservice.notificationservice.controller.NotificationServiceController;
import com.microservice.notificationservice.util.SmsUtil;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;

@Repository
public class NotificationServiceRepositoryImpl implements NotificationServiceRepository {

	private static Logger logger = LoggerFactory.getLogger(NotificationServiceController.class);

	@Autowired
	private SmsUtil smsUtil;
	
	@Override
	public Map<String, Boolean> sendNotification(List<String> adminContactNumbersList, String messageStr) {
		logger.info("Repository method called");
		Map<String, Boolean> statusMap = new HashMap<>();
		for(String adminContactNumber: adminContactNumbersList) {
//			SmsStatus sendStatus = 
					boolean sentSms = smsUtil.sendSms(adminContactNumber, messageStr);
			if(sentSms) {
				statusMap.put(adminContactNumber, true);
			}
		}
		return statusMap;
	}

	@Override
	public Map<String, Status> getMessagesStatus() {
		logger.info("Repository method called");
		return smsUtil.getSmsStatus();
		
	}
}
