package com.microservice.notificationservice.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.notificationservice.controller.NotificationServiceController;
import com.microservice.notificationservice.repository.NotificationServiceRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static Logger logger = LoggerFactory.getLogger(NotificationServiceController.class);

	@Autowired
	private NotificationServiceRepository notificationRepository;

	@Autowired
	private AdminListService adminListService;
	
	public Map<String, Boolean> sendNotificationToUsers() {
		logger.info("Service method called");
		
		//@Todo: Detach this implementation to connect to JPA handler later
//		ArrayList<String> admins = this.adminListService.getAdminUserList();
		
		List<String> admins = this.adminListService.getAdminUserListFromPropertyFile();
		logger.info("admin phone numbers--"+admins.toString());
		
		//@Todo: move msg to config file later
		String messageStr = "--Hello, message from Pritam's Test App--";
		Map<String, Boolean> sentNotificationStatus = this.notificationRepository.sendNotification(admins, messageStr );
		return sentNotificationStatus;
	}

	@Override
	public Map<String, Status> getAllNotificationsStatus() {
		logger.info("Service method called");
		return this.notificationRepository.getMessagesStatus();
	}

}
