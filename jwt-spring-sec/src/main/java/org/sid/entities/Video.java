package org.sid.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Video {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String url;
	@Lob
	private String description;
	
	
	public Video() {
		super();
	}
	
	public Video(Long id, String name, String url, String description) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.description = description;
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
	
	

}
