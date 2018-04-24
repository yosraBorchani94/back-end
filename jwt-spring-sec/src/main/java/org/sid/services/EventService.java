package org.sid.services;

public interface EventService {

	public void addUserToEvent (String username,Long idEvent);
	public void deleteUserFromEvent(String username,Long idEvent);
}
