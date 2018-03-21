package org.sid.web;

import org.sid.entities.AppRole;

public class RegisterForm {
    
	private String id;
	private String username;
	private AppRole role;
	private String password;
	private String repassword;
	
	public AppRole getRole() {
		return role;
	}
	public void setRole (AppRole role) {
		this.role = role;
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
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
