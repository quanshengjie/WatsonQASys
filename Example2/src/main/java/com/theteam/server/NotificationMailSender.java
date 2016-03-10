package com.theteam.server;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificationMailSender
{
	public static NotificationMailSender Instance = new NotificationMailSender();
	
	private String[] receiverList = {"quan.48@osu.edu"};
	private String from = "webserver@theteamtoday.com";
	private String host = "localhost";
	
	private NotificationMailSender() {	}
	
	public void simpleSendDown()
	{
		String subject = "WebServer thinks that Watson is Down";
		String text = "This is an automatically sent email from The Team WebServer\n" +
					  "Please do not reply.\n\n" +
					  "The WebServer noticed that Watson may be down. Please check the system status by " + 
					  "logging into Watson Experience Management or do some inquiry to Watson manually\n\n" + 
					  "WebServer\n";
		sendToAll(subject, text);
	}
	
	public void simpleSendStrange()
	{
		String subject = "WebServer thinks that Watson is Strange";
		String text = "This is an automatically sent email from The Team WebServer\n" +
					  "Please do not reply.\n\n" +
					  "The WebServer noticed that Watson behave strangely. Please check the system status by " + 
					  "logging into Watson Experience Management or do some inquiry to Watson manually\n\n" + 
					  "WebServer\n";
		sendToAll(subject, text);
	}
	
	public void simpleSendUp()
	{
		String subject = "WebServer thinks that Watson is Up";
		String text = "This is an automatically sent email from The Team WebServer\n" +
					  "Please do not reply.\n\n" +
					  "The WebServer noticed that Watson may be up now. Please verify manually by trying to " +
					  "ask question to Watson.\n\n" + 
					  "WebServer\n";
		sendToAll(subject, text);
	}
	
	private void sendToAll(String subject, String text)
	{
		for(String address : receiverList)
		{
			sendTo(address, subject, text);
		}
	}
	
	private void sendTo(String recp, String subject, String text)
	{
	     String to = recp;

	     // Create properties, get Session
	     Properties props = new Properties();

	     //http://docs.oracle.com/javaee/6/api/javax/mail/Session.html
	     // If using static Transport.send(),
	     // need to specify which host to send it to
	     props.put("mail.smtp.host", host);
	     // To see what is going on behind the scene
	     props.put("mail.debug", "true");
	     Session session = Session.getInstance(props);

	     try {
	         // Instantiate a message
	         Message msg = new MimeMessage(session);

	         //Set message attributes
	         msg.setFrom(new InternetAddress(from));
	         InternetAddress[] address = {new InternetAddress(to)};
	         msg.setRecipients(Message.RecipientType.TO, address);
	         msg.setSubject(subject);
	         msg.setSentDate(new Date());

	         // Set message content
	         msg.setText(text);

	         //Send the message
	         Transport.send(msg);
	     }
	     catch (MessagingException mex) {
	         // Prints all nested (chained) exceptions as well
	         mex.printStackTrace();
	     }
	}
}
