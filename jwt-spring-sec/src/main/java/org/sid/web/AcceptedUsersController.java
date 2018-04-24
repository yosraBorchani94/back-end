package org.sid.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.sid.dao.AcceptedUserRepository;
import org.sid.dao.EventRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AcceptedUsers;
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
	private AcceptedUserRepository acceptedUserRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EventService eventService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/acceptedUsers")
	public void save(@RequestParam("idEvent") Long idEvent, @RequestParam("username") String username) {

		eventService.addUserToEvent(username, idEvent);
		// return acceptedUserRepository.save(au);
	}

	@PostMapping("/unparticipate")
	public void unparticipate(@RequestParam("idEvent") Long idEvent, @RequestParam("username") String username) {

		eventService.deleteUserFromEvent(username, idEvent);
		// List<AcceptedUsers> accAll = acceptedUserRepository.findAll();
		// if (accAll.size() > 0) {
		// for (int i = 0; i < accAll.size(); i++) {
		// if (accAll.get(i).getIdEvent() == au.getIdEvent()
		// && accAll.get(i).getUsername().equals(au.getUsername())) {
		// acceptedUserRepository.delete(accAll.get(i));
		// }
		//
		// }
		// }
	}

	@PostMapping("/accpetedEvent")
	public Set <AccpetedUserForm> getAcceptedUsers(@RequestBody String username) {

		Date today = new Date();
		Set <AccpetedUserForm> form = new LinkedHashSet();
		AppUser user = userRepository.findByUsername(username);
		List<Event> events = eventRepository.findAll();
		if (events.size() > 0) {
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i).getStartDate().after(today)) {
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

	@GetMapping("/acceptedUsers")
	public List<AcceptedUsers> listAccepted() {
		return acceptedUserRepository.findAll();
	}

	@DeleteMapping("/acceptedUsers/{id}")
	public boolean delete(@PathVariable Long id) {
		acceptedUserRepository.delete(id);
		return true;
	}

	@PostMapping("/getEventsByUser")
	public List<Event> getEventsByUser(@RequestBody String username) {
		List<AcceptedUsers> accAll = acceptedUserRepository.findAll();
		List<Long> accUserId = new ArrayList<>();
		List<Event> accUser = new ArrayList<Event>();

		if (accAll.size() > 0) {
			for (int i = 0; i < accAll.size(); i++) {
				if (accAll.get(i).getUsername().equals(username)) {
					accUserId.add(accAll.get(i).getIdEvent());
				}
			}

			if (accUserId.size() > 0) {
				for (int i = 0; i < accUserId.size(); i++) {
					accUser.add(eventRepository.findOne(accUserId.get(i)));
				}
			}
		}
		return accUser;

	}

}
