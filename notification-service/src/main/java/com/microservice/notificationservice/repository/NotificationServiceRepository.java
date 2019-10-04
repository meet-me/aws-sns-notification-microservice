package com.microservice.notificationservice.repository;

import java.util.List;
import java.util.Map;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;

public interface NotificationServiceRepository {

//	public void sendNotification();
//	public Map<String, SmsStatus> sendNotification(ArrayList<String> adminContactNumbersList);
//	public Message sendNotification(ArrayList<String> adminContactNumbersList);
	public Map<String, Boolean> sendNotification(List<String> admins, String messageStr);

	public Map<String, Status> getMessagesStatus();

}
