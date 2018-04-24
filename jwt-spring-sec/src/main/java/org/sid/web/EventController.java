package org.sid.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.sid.dao.EventRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppUser;
import org.sid.entities.Event;
import org.sid.liveBroadcast.Auth;
import org.sid.liveBroadcast.CreateBroadcast;
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
	private CreateBroadcast createBroadcast;

	// send the first notification to all users
	@PostMapping("/event")
	public int save(@RequestBody Event event) {
		String id = createBroadcast.insert(event);
		if (id.equals("0"))
			return 0;
		else {
			Event ev = new Event();
			ev.setTitle(event.getTitle());
			ev.setBroadcastId("https://www.youtube.com/watch?v=" + id);
			ev.setStartDate(event.getStartDate());
			ev.setEndDate(event.getEndDate());
			if (eventRepository.save(ev) != null) {
				return 1;
			}
			/*if (eventRepository.save(ev) != null) {
				List<AppUser> listUsers = userRepository.findAll();
				for (AppUser user : listUsers) {
					try {
						mailService.sendNotificationEvent(user.getUsername(), event);
					} catch (MailException e) {
						System.out.println(e);
					}
				}
				return 1;
			}*/
		}
		return 0;
	}

	

	@PutMapping("/event/{id}")
	public Event update(@PathVariable Long id, @RequestBody Event e) {
		e.setId(id);
		return eventRepository.save(e);
	}

	// send the second notification to accepted event with specific users
//	@GetMapping("/eventNotification")
//	public void eventNotification() throws ParseException {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date Todaydate = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(Todaydate);
//		c.add(Calendar.DATE, 1);
//		Date tomorrow = c.getTime();
//		List<AcceptedUsers> listAcceptedUsers = acceptedUserRepository.findAll();
//		if (listAcceptedUsers.size() > 0) {
//			for (AcceptedUsers accepteduser : listAcceptedUsers) {
//				Event e = eventRepository.findOne(accepteduser.getIdEvent());
//				String eventDateString = dateFormat.format(e.getStartDate());
//				if ((eventDateString.equals(dateFormat.format(Todaydate)))
//						|| (eventDateString.equals(dateFormat.format(tomorrow)))) {
//
//					mailService.sendReminderNotificationEvent(accepteduser.getUsername(), e);
//				}
//			}
//		}
//	}

	@GetMapping("/event")
	public List<Event> listEvents() {
		return eventRepository.findAll();
	}

	@GetMapping("/event/{id}")
	public Event getEvent(@PathVariable Long id) {
		return eventRepository.findOne(id);
	}

	@DeleteMapping("/event/{id}")
	public void delete(@PathVariable Long id) {
		Event e = eventRepository.findOne(id);
		if (e.getUser().size() > 0 ) {
			 Iterator<AppUser> iterator = e.getUser().iterator();
                     e.getUser().clear();
			 }
		 eventRepository.delete(id);
	}

	@GetMapping("getActualEvents")
	public List<Event> getActualEvents() {
		List<Event> getActualEvents = new ArrayList<>();
		Date today = new Date();
		List<Event> AllEvents = eventRepository.findAll();
		for (Event event : AllEvents) {
			if (event.getStartDate().after(today)  || event.getStartDate() == today) {
				getActualEvents.add(event);
			}
		}
		return getActualEvents;
	}

}
