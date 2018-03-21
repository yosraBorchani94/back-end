package org.sid.web;

import java.util.List;
import org.sid.dao.AcceptedUserRepository;
import org.sid.entities.AcceptedUsers;
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

	@PostMapping("/acceptedUsers")
	public AcceptedUsers save(@RequestBody AcceptedUsers au) {
		return acceptedUserRepository.save(au);
	}

	@PostMapping("/accpetedEvent")
	public boolean accpetedEvent(@RequestBody AcceptedUsers au) {
		List<AcceptedUsers> acceptedEventList = acceptedUserRepository.findAll();
		for (AcceptedUsers ael : acceptedEventList) {
			if ((au.getId_event().equals(ael.getId_event())) && (au.getUsername().equals(ael.getUsername()))) {
				return true;
			}
		}
		return false;
	}

	@GetMapping("/accpetedEvent/{id}")
	public AcceptedUsers getAcceptedUsers(@PathVariable Long id) {
		return acceptedUserRepository.findOne(id);
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

}
