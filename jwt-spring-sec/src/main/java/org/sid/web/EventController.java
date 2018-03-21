package org.sid.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.sid.dao.AcceptedUserRepository;
import org.sid.dao.EventRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AcceptedUsers;
import org.sid.entities.AppUser;
import org.sid.entities.Event;
import org.sid.entities.Task;
import org.sid.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
public class EventController {
	
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AcceptedUserRepository acceptedUserRepository;
	
	@PostMapping("/event")
	public void save(@RequestBody Event event){
		eventRepository.save(event);
		List<AppUser> listUsers = userRepository.findAll();
		for (AppUser user:listUsers) {
			 try {
				 //mailService.sendNotificationEvent(user.getUsername(),event);
				} catch (MailException e) {
					System.out.println(e);
				}
		}
	}
	
	@GetMapping("/eventNotification")
	public String eventNotification () throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date Todaydate = new Date();
		 List<Event> listEvents = eventRepository.findAll();
		 for (Event event:listEvents) {
			    StringTokenizer st = new StringTokenizer(event.getStartDate());  
			    String EventDateString = st.nextToken("T");
			    Date eventdate = dateFormat.parse(EventDateString);		
			    Calendar c = Calendar.getInstance(); 
			    c.setTime(Todaydate); 
			    c.add(Calendar.DATE, 1);
			    Date tomorrow = c.getTime();
				//System.out.println("EventDateString: "+EventDateString);
				//System.out.println("yesterdayEvent: " + dateFormat.format(yesterdayEvent));
			    if ((EventDateString.equals(dateFormat.format(Todaydate))) ||(EventDateString.equals(dateFormat.format(tomorrow)))) {
					List<AcceptedUsers> listAcceptedUsers = acceptedUserRepository.findAll();
					for (AcceptedUsers accepteduser:listAcceptedUsers) {
						try {
							mailService.sendReminderNotificationEvent(accepteduser.getUsername(),event.getId());
							} catch (MailException e) {
								System.out.println(e);
							}
					}
					return "send notification";
				}
		 }
		 return "Do not send notification";
	}
	
	

	
	@GetMapping("/event")
	public List<Event> listEvents(){
		return eventRepository.findAll();
	}
	
	@GetMapping("/event/{id}")
	public Event getEvent(@PathVariable Long id){
		return eventRepository.findOne(id);
	}
	
	@PutMapping("/event/{id}")
	public Event update (@PathVariable Long id,@RequestBody Event t) {
		t.setId(id);
		return eventRepository.save(t);
	}
	
	@DeleteMapping("/event/{id}")
	public boolean delete(@PathVariable Long id){
		eventRepository.delete(id);
		return true ;
	}
	
	
}
