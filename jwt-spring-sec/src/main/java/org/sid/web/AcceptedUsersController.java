package org.sid.web;

import java.util.ArrayList;
import java.util.List;
import org.sid.dao.AcceptedUserRepository;
import org.sid.dao.EventRepository;
import org.sid.entities.AcceptedUsers;
import org.sid.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AcceptedUsersController {

	@Autowired
	private AcceptedUserRepository acceptedUserRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@PostMapping("/acceptedUsers")
	public AcceptedUsers save(@RequestBody AcceptedUsers au) {
		return acceptedUserRepository.save(au);
	}
	
	@PostMapping("/unparticipate")
	public void unparticipate(@RequestBody AcceptedUsers au) {
		List <AcceptedUsers> accAll = acceptedUserRepository.findAll();
		if (accAll.size() >0) {
			for (int i = 0; i < accAll.size(); i++) {
				if (accAll.get(i).getIdEvent() == au.getIdEvent() && accAll.get(i).getUsername().equals(au.getUsername())) {
					 acceptedUserRepository.delete(accAll.get(i));
				}
					
			}	
		}
	}

	@PostMapping("/accpetedEvent")
	public boolean accpetedEvent(@RequestBody AcceptedUsers au) {
		List<AcceptedUsers> acceptedEventList = acceptedUserRepository.findAll();
		for (AcceptedUsers ael : acceptedEventList) {
			if ((au.getIdEvent().equals(ael.getIdEvent())) && (au.getUsername().equals(ael.getUsername()))) {
				return true;
			}
		}
		return false;
	}

	@PostMapping("/accpetedEvent/{id}")
	public AcceptedUsers getAcceptedUsers(@PathVariable Long id ,@RequestBody String username ) {
		List <AcceptedUsers> accAll = acceptedUserRepository.findAll();
		if (accAll.size() >0) {
			for (int i = 0; i < accAll.size(); i++) {
				if (accAll.get(i).getIdEvent() == id && accAll.get(i).getUsername().equals(username)) {
					return accAll.get(i);
				}
					
			}	
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
	public List<Event> getEventsByUser (@RequestBody String username) {
		List<AcceptedUsers> accAll = acceptedUserRepository.findAll();
		List<Long> accUserId =  new ArrayList<>();
		List<Event> accUser =  new ArrayList<Event>();
		
		if (accAll.size()>0) {
			for (int i = 0; i < accAll.size(); i++) {
				if (accAll.get(i).getUsername().equals(username)) {
					accUserId.add(accAll.get(i).getIdEvent());
				}
			}
			
			if (accUserId.size()>0) {
				for (int i = 0; i < accUserId.size(); i++) {
					accUser.add(eventRepository.findOne(accUserId.get(i)));
				}
			}
		}
		return accUser;
		
	}

}
