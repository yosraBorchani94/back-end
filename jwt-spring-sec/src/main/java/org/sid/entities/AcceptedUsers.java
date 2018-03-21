package org.sid.entities;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AcceptedUsers {
	@Id
	private Long id_event;
	private String username;
	
	public AcceptedUsers () {
		super();
	}
	
	public AcceptedUsers(Long id_event, String username) {
		super();
		this.id_event = id_event;
		this.username = username;
	}
	public Long getId_event() {
		return id_event;
	}
	public void setId_event(Long id_event) {
		this.id_event = id_event;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
