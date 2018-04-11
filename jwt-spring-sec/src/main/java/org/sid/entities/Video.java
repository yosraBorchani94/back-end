package org.sid.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Video {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String name;
	private String url;
	@Lob
	private String description;
	
	@ManyToOne
	private Module module;

	public Video() {
		super();
	}
	
	public Video(Long id, String name, String url, String description, Module module) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.description = description;
		this.module = module;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
}
