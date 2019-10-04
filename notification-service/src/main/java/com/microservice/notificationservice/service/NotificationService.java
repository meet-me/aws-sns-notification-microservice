package com.microservice.notificationservice.service;

import java.util.Map;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;

public interface NotificationService {

//	Map<String, SmsStatus> sendNotificationToUsers();
//	Message sendNotificationToUsers();
	Map<String, Boolean> sendNotificationToUsers();

	Map<String, Status> getAllNotificationsStatus();

}
