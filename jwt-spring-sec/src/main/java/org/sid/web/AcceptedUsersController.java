package org.sid.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import org.sid.dao.EventRepository;
import org.sid.dao.UserRepository;

import org.sid.entities.AppUser;
import org.sid.entities.Event;
import org.sid.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AcceptedUsersController {



	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EventService eventService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/acceptedUsers")
	public void save(@RequestParam("idEvent") Long idEvent, @RequestParam("username") String username) {

		eventService.addUserToEvent(username, idEvent);
	}

	@PostMapping("/unparticipate")
	public void unparticipate(@RequestParam("idEvent") Long idEvent, @RequestParam("username") String username) {

		eventService.deleteUserFromEvent(username, idEvent);
	}

	@PostMapping("/accpetedEvent")
	public Set<AccpetedUserForm> getAcceptedUsers(@RequestBody String username) {

		Date today = new Date();
		Set<AccpetedUserForm> form = new LinkedHashSet();
		AppUser user = userRepository.findByUsername(username);
		List<Event> events = eventRepository.findAll();
		if (events.size() > 0) {
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i).getStartDate().after(today) || events.get(i).getStartDate() == today) {
					if (events.get(i).getUser().size() > 0) {
						Iterator<AppUser> iterator = events.get(i).getUser().iterator();
						if (iterator.next().getId() == user.getId()) {
							AccpetedUserForm accpetedUserForm = new AccpetedUserForm();
							accpetedUserForm.setId(events.get(i).getId());
							accpetedUserForm.setTitle(events.get(i).getTitle());
							accpetedUserForm.setStartDate(events.get(i).getStartDate());
							accpetedUserForm.setEndDate(events.get(i).getEndDate());
							accpetedUserForm.setStatus(true);
							form.add(accpetedUserForm);
						} else {
							AccpetedUserForm accpetedUserForm = new AccpetedUserForm();
							accpetedUserForm.setId(events.get(i).getId());
							accpetedUserForm.setTitle(events.get(i).getTitle());
							accpetedUserForm.setStartDate(events.get(i).getStartDate());
							accpetedUserForm.setEndDate(events.get(i).getEndDate());
							accpetedUserForm.setStatus(true);
							form.add(accpetedUserForm);
						}
					} else {
						AccpetedUserForm accpetedUserForm = new AccpetedUserForm();
						accpetedUserForm.setId(events.get(i).getId());
						accpetedUserForm.setTitle(events.get(i).getTitle());
						accpetedUserForm.setStartDate(events.get(i).getStartDate());
						accpetedUserForm.setEndDate(events.get(i).getEndDate());
						accpetedUserForm.setStatus(false);
						form.add(accpetedUserForm);
					}

				}
			}
			return form;

		}
		return null;
	}

	@PostMapping("/getEventsByUser")
	public List<Event> getEventsByUser(@RequestBody String username) {
		AppUser user = userRepository.findByUsername(username);
		List<Event> accUser = new ArrayList<Event>();	
		List<Event> events = eventRepository.findAll();
		if (events.size() > 0) {
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i).getUser().size() > 0) {
					Iterator<AppUser> iterator = events.get(i).getUser().iterator();
					if (iterator.next().getId() == user.getId()) {
						accUser.add(events.get(i));
					}
				}
			}
			
		}
		return accUser;
	}
}