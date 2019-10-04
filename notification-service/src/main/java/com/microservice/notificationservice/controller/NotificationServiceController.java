package com.microservice.notificationservice.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.notificationservice.Ack;
import com.microservice.notificationservice.MessageWrapper;
import com.microservice.notificationservice.service.NotificationService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;

@RestController
@CrossOrigin(origins="*/*")
public class NotificationServiceController {

	private static Logger logger = LoggerFactory.getLogger(NotificationServiceController.class);
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping(path="/notify", produces="Application/json")
	@ResponseBody
	public ResponseEntity<Ack> sendNotificationToAdmins() {
		logger.info("Controller called");
		
		Ack ack = new Ack();
		Map<String, Boolean> sentNotificationToUsers = this.notificationService.sendNotificationToUsers();
		ack.setMessage(sentNotificationToUsers); 
		
		logger.info(sentNotificationToUsers.toString());
		logger.info(ack.toString());
		return new ResponseEntity<Ack>(ack, HttpStatus.OK);
	}
	
	@GetMapping(path="/status", produces="Application/json")
	@ResponseBody
	public ResponseEntity<MessageWrapper> getNotificationStatus() {
		logger.info("controller - getNotificationStatus() called");
		
		MessageWrapper messageWrapper = new MessageWrapper();
		Map<String, Status> notificationsStatus = this.notificationService.getAllNotificationsStatus();
		messageWrapper.setStatus(notificationsStatus);
		return new ResponseEntity<MessageWrapper>(messageWrapper, HttpStatus.OK);
	}
	
}
