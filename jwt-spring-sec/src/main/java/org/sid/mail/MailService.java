package org.sid.mail;
import java.util.List;

import org.sid.dao.EventRepository;
import org.sid.entities.AppUser;
import org.sid.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
 
	private JavaMailSender javaMailSender;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	public void NotificationService (JavaMailSender javaMailSender) throws MailException{
		this.javaMailSender=javaMailSender;
	}
	
	public void sendNotification (AppUser user) {
		SimpleMailMessage mail = new SimpleMailMessage ();
		mail.setTo(user.getUsername());
		mail.setFrom("yosra.borchani.lfi1@gmail.com");
		mail.setSubject("Login informations");
		mail.setText(" Your account information : username: "+user.getUsername()+" ,password: "+user.getPassword());
		javaMailSender.send(mail);
	}
	
	public void sendNotificationEvent (String username , Event e) {
		SimpleMailMessage mail = new SimpleMailMessage ();
			mail.setTo(username);
			mail.setFrom("yosra.borchani.lfi1@gmail.com");
			mail.setSubject("Event informations");
			mail.setText(" Event information : Title: "+e.getTitle()+" ,Start Date: "+e.getStartDate()+" , End Date: "+e.getEndDate());
			javaMailSender.send(mail);
		
	
	}
	
	public void sendReminderNotificationEvent (String username , Long id) {
		Event event = eventRepository.findOne(id);
		SimpleMailMessage mail = new SimpleMailMessage ();
		mail.setTo(username);
		mail.setFrom("yosra.borchani.lfi1@gmail.com");
		mail.setSubject("Reminder Event");
		mail.setText(" Event information : Title: "+event.getTitle()+" ,Start Date: "+event.getStartDate()+" , End Date: "+event.getEndDate());
		javaMailSender.send(mail);
	}
}
