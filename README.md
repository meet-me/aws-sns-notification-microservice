# aws-sns-notification-microservice


Eureka & AWS/Twilio SMS Notification Discoverable MicroService
(Auther - Pritam Mugal)

Overview:
The attached project is a group of 3 web services. Its purpose is to send SMS notification to admin phone numbers, which are picked from the properties file. Three microservices are – 
-	Discovery Service
-	Notification Service & 
-	Notification Service Consumer

Technology Stack :
 Spring boot 2.1.6, Netflix Eureka, AWS Java SDK 1.11.163, Twilio SDK 7.41.1
Other dependencies involved you can check in the pom.xml of respective microservices.

You don’t need all 3 of the microservices to send the SMS notification. notification microservice could be run independently by removing the @EnableDiscoveryClient annotation from the NotificationServiceApplication class. 

1.	Discovery Service is an implementation of Netflix Eureka discovery server, which enables the discovery of other microservices.
2.	Notification Service is a microservice to send the SMS alert to the admin phone numbers, which also integrates AWS SNS & Twilio services in it. AWS SNS & Twilio are used as actual clients to send the SMS. The design makes use of the factory design pattern & makes multiple clients to choose for sending sms from the options given or even extended further. 
application.sms.client.vendor configurable property from the application.properties file with accepted values as ‘aws’ & ‘twilio’ controls which client is invoked for sending the SMS.

application.sms.client.vendor=aws 

or 

application.sms.client.vendor=twilio

This switch is used to conditionally initialize TwilioClient or AwsSnsClient instances with @ConditionalOnProperty annotation

AWS configuration properties are picked from src/main/resources/aws-application.properties file, whereas twilio configuration properties are picked from src/main/resources/twilio-application.properties file. Pls update these values correctly from your aws & twilio accounts.

List of admin phone numbers are currently picked from the admin-phone-numbers.properties file by the AdminListService, which is comma separated list of numbers including country code. So multiple admin phone numbers are supported. In case of twilio though, found out that it allows to send SMS only to twilio registered numbers only, does not allow to send SMS to unverified numbers & throws exception in that case.
The SMS message is currently hardcoded in sendNotificationToUsers() in NotificationServiceImpl.java as messageStr;

3.	Notification Service Consumer is just another microservice to test the sms sending capability of notification service microservice. It uses rest template to call it. Its just to demonstrate that 2nd service is callable from 3rd service.

Steps to Run the Project: 
1.	Initially execute the discovery service & browse to http://localhost:1111/ which shows the Eureka service stats as below – 

 

Initially at this stage you wont observe any service registered with Eureka.
2.	Now execute notification service. At this point, you would notice that notification service instance is registered with Eureka now, refresh the above stats page to check the same. 
3.	You can optionally execute notification service consumer

End Points: 
To send SMS (aws/twilio) - http://localhost:2222/notify
To check status for twilio - http://localhost:2222/status
To send Sms from consumer – http://localhost:4444/consumer/test/sendSms
