package org.sid.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AcceptedUsers {
	@Id
	@GeneratedValue
	private Long id ;
	private Long idEvent;
	private String username;
	
	public AcceptedUsers () {
		super();
	}
	
	public AcceptedUsers(Long id , Long idEvent, String username) {
		super();
		this.id=id;
		this.idEvent = idEvent;
		this.username = username;
	}

	
	public Long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
