package com.microservice.notificationservice.sms.aws;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.microservice.notificationservice.sms.SmsClient;
import com.microservice.notificationservice.sms.twilio.TwilioClient;
import com.twilio.rest.api.v2010.account.Message.Status;

@Component
@Scope("singleton")
@PropertySource("classpath:aws-application.properties")
@ConditionalOnProperty(name = "application.sms.client.vendor", havingValue = "aws")
public class AwsSnsClient implements SmsClient {
//	@Value("${aws.accesskeyid}")
	private static String accessKeyId;//= "AKIAJI7XAVALD7N3Z2WQ";

//	@Value("${aws.secretaccesskey}")
	private static String secretAccessKey;//= "MhgpAzN+Rri9Dv+GqGirTmrQ0i3DVE81q8Ey80a9";

//	@Value("${aws.region}")
	private static String region;// = "us-east-1";
    
//	@Value("${application.sender.phone}")
//    private static String SENDER_PHONE = "+17042597031";
	
//    private static AwsSnsClient awsSnsClient;
    
    private static AmazonSNS amazonSNS;

	private static final Logger logger = LoggerFactory.getLogger(TwilioClient.class);
	    
//	amazonSNS = AmazonSNSClientBuilder.standard()
//    		.withCredentials(awsCredentialsProvider)
//            .withRegion(region)
//            .build();
//	
    private AwsSnsClient(@Value("${aws.accesskeyid}") String accessKeyId, @Value("${aws.secretaccesskey}") String secretAccessKey, @Value("${aws.region}") String region) { 
    	AwsSnsClient.accessKeyId = accessKeyId;
    	AwsSnsClient.secretAccessKey = secretAccessKey;
    	AwsSnsClient.region = region;
    	
    	AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
		AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);
		AwsSnsClient.amazonSNS =  AmazonSNSClientBuilder.standard()
			    	    		.withCredentials(awsStaticCredentialsProvider)
			    	            .withRegion(region)
			    	            .build();
    }
    
/*    synchronized public static AwsSnsClient getInstance() {
    	if(awsSnsClient == null) {
    		AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
    		AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);
//    		awsSnsClient = new AwsSnsClient();
    		awsSnsClient.amazonSNS =  AmazonSNSClientBuilder.standard()
				    	    		.withCredentials(awsStaticCredentialsProvider)
				    	            .withRegion(region)
				    	            .build();
    	}
    	return awsSnsClient;
    }*/

	@Override
	public boolean sendSms(String phoneNumber, String message) {
		String topicName = "testTopic";
        String topicArn = createSNSTopic(topicName);
        subscribeToTopic(topicArn, "sms", phoneNumber);
        String messageId = sendSMSMessageToTopic(topicArn, message);
        
        logger.info("----------------------"+messageId+"----------------------");
        if(messageId!=null) return true;
        else return false;
	}

	
	public static String createSNSTopic(String topicName) {
		CreateTopicRequest createTopic = new CreateTopicRequest(topicName);
		CreateTopicResult result = amazonSNS.createTopic(createTopic);
		return result.getTopicArn();
	}
	public static void subscribeToTopic(String topicArn, String protocol, String phoneNumber) {
		SubscribeRequest subscribe = new SubscribeRequest(topicArn, protocol, phoneNumber);
		amazonSNS.subscribe(subscribe);
	}
	public static String sendSMSMessageToTopic(String topicArn, String message) {
		PublishResult result = amazonSNS.publish(new PublishRequest()
		  .withTopicArn(topicArn)
		  .withMessage(message));
		
		logger.info("message id created-------------"+result.toString());
		
		return result.getMessageId();
	}

	@Override
	public Map<String, Status> getStatus() {
		return null;
	}

}
