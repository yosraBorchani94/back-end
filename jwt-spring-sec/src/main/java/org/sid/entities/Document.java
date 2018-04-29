package org.sid.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Document {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER )
	private AppUser user; 
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Module module;
	
	private String path;
	
	@Column(unique = true)
	private String documentName;

	private boolean accpetd;
	
	public Document() {
		super();
	}
	


	public Document(Long id, String path, String documentName, boolean accpetd , AppUser user ,  Module module) {
		super();
		this.id = id;
		this.user= user;
		this.path = path;
		this.documentName = documentName;
	    this.module = module;
		this.accpetd = accpetd;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	public boolean isAccpetd() {
		return accpetd;
	}

	public void setAccpetd(boolean accpetd) {
		this.accpetd = accpetd;
	}


	
	public AppUser getUser() {
		return user;
	}



	public void setUser(AppUser user) {
		this.user = user;
	}



	public Module getModule() {
		return module;
	}



	public void setModule(Module module) {
		this.module = module;
	}
	
	

	
	
	
}
