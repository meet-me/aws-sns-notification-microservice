package com.microservice.notificationservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:admin-phone-numbers.properties")
public class AdminListService {

	Logger logger = LoggerFactory.getLogger(AdminListService.class);
	
	@Value( "${admin.phone.numbers}" )
	private String adminPhoneNumbers;
	
	public String getAdminPhoneNumbers() {
		return adminPhoneNumbers;
	}

	public ArrayList<String> getAdminUserListbyStatic() {
		ArrayList<String> admins = new ArrayList<>();
		admins.add("+919834942465");
//		admins.add("+919834954287");
		return admins;
	}
	
	public List<String> getAdminUserListFromPropertyFile() {
		List<String> admins = Arrays.asList(this.getAdminPhoneNumbers().split(","));
		
		logger.info(admins.toString());
		
		return admins;
	}
}
