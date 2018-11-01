package com.uninsubria.watchneighbors.util;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Used to validate the email format.
 * @author alessandro, christian, denise, silvia
 *
 */
public class MailUtilities {

	private static final String MAIL_SENDER_USER_NAME = "******";
	private static final String MAIL_SENDER_PASSWORD = "******";
	private String[] mailAddresses;
	private String subject;
	private String body;
	/**
	 * Create and checks if an array of strings is valid mail addresses
	 * @param mailAddresses The string representing the email address.
	 * @throws AddressException The addressException.
	 */
	public MailUtilities(String[] mailAddresses) throws AddressException {
		this.mailAddresses = mailAddresses;
		
		if(mailAddresses.length <= 1) { 
			if(!isValidEmailAddress(mailAddresses[0])) {
				throw new AddressException();
			}
		}
	}
	/**
	 * Send an email to confirm the registration 
	 * @param userID The userID of the user.
	 * @param password The password of the user.
	 * @throws AddressException The address exception.
	 * @throws MessagingException The messaging exception.
	 */
	public void sendConfirmRegistrationByMail(String userID, String password) throws AddressException, MessagingException {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", MAIL_SENDER_USER_NAME);
		props.put("mail.smtp.password", MAIL_SENDER_PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(MAIL_SENDER_USER_NAME));
		InternetAddress[] toAddress = new InternetAddress[mailAddresses.length];

		for(int i = 0; i < mailAddresses.length; i++ ) {
			toAddress[i] = new InternetAddress(mailAddresses[i]);
		}

		for(int i = 0; i < toAddress.length; i++) {
			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		}

		subject = "Watchneighbors";
		body = "You are now registered to Watchneighbors\n" + 
			"Your credentials are: " + userID + " " + password;
		
		message.setSubject(subject);
		message.setText(body);
		Transport transport = session.getTransport("smtp");
		transport.connect(host, MAIL_SENDER_USER_NAME, MAIL_SENDER_PASSWORD);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

	}
	
	private boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = Pattern.compile(ePattern);
		Matcher m = p.matcher(email);
		
		return m.matches();
	}
	
	/**
	 * Get the mailAddress arrays
	 * @return mailAddresses
	 */
	public String[] getMailAddresses() {
		return mailAddresses;
	}
	
	/**
	 * Set the mailAddress arrays
	 * @param mailAddresses The string representing the email address.
	 */
	public void setMailAddresses(String[] mailAddresses) {
		this.mailAddresses = mailAddresses;
	}
	
}