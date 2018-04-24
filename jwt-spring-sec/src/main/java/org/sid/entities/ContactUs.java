package org.sid.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ContactUs {
	@Id
	@GeneratedValue
	private Long id;
	private String Message;
	private String documentPath;
	@ManyToOne (fetch=FetchType.EAGER)
	private AppUser user;
	

	public ContactUs() {
		super();
	}
	public ContactUs(Long id, String message, String documentPath, AppUser user) {
		super();
		this.id = id;
		Message = message;
		this.documentPath = documentPath;
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public AppUser getUser() {
		return user;
	}
	public void setUser(AppUser user) {
		this.user = user;
	}
	
	
	
}
