package org.sid.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Document {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String path;
	@Column(unique = true)
	private String documentName;
	
	@ManyToOne (fetch=FetchType.EAGER)

	private Module module;

	
	public Document() {
		super();
	}

	public Document(Long id, String username, String path, Module module , String documentName) {
		super();
		this.id = id;
		this.username = username;
		this.path = path;
		this.module = module;
		this.documentName =documentName;
		
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}	
	
	
}
