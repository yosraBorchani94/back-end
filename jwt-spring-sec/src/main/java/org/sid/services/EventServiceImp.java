package org.sid.services;

import org.sid.dao.EventRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
@Service
@Transactional
public class EventServiceImp implements EventService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Override
	public void addUserToEvent(String username, Long idEvent) {
	    AppUser user = userRepository.findByUsername(username);
	    Event e = eventRepository.findOne(idEvent);
		e.getUser().add(user);		
	}

	@Override
	public void deleteUserFromEvent(String username, Long idEvent) {
		 AppUser user = userRepository.findByUsername(username);
		 Event e = eventRepository.findOne(idEvent);
		 if (e.getUser().size() > 0 ) {
			 Iterator<AppUser> iterator = e.getUser().iterator();
			 if( iterator.next().getId() == user.getId()) {
				 e.getUser().remove(user);
			 }
		 }
	}

}
