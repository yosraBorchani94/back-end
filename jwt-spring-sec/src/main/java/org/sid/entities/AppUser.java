package org.sid.entities;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class AppUser {

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL )
	private Set<AppRole> roles =new HashSet<AppRole>();

//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	private Set<Document> document = new HashSet<Document>();
//
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	private Set<ModuleInstance> moduleInstance = new HashSet<ModuleInstance>();
//
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	private Set<ContactUs> contactUs = new HashSet<ContactUs>();
	
	public AppUser() {
		super();
	}
	public AppUser(Long id ,String username, String password, Set<AppRole> roles) {
		super();
		this.id=id;
		this.username = username;
		this.password = password;
		this.roles = roles;
		//this.moduleInstance = moduleInstance;
		//this.contactUs = contactUs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Set<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<AppRole> roles) {
		this.roles = roles;
	}
//	public Set<Document> getDocument() {
//		return document;
//	}
//	public void setDocument(Set<Document> document) {
//		this.document = document;
//	}
//	public Set<ModuleInstance> getModuleInstance() {
//		return moduleInstance;
//	}
//	public void setModuleInstance(Set<ModuleInstance> moduleInstance) {
//		this.moduleInstance = moduleInstance;
//	}
//	public Set<ContactUs> getContactUs() {
//		return contactUs;
//	}
//	public void setContactUs(Set<ContactUs> contactUs) {
//		this.contactUs = contactUs;
//	}
	
	

}
