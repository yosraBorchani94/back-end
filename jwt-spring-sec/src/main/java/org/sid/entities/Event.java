package org.sid.entities;

import java.util.Date;

import javax.persistence.*;


@Entity
public class Event {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private String broadcastId;

	public Event() {
		super();
	}
	public Event(Long id, String title, Date startDate, Date endDate , String broadcastId) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.broadcastId = broadcastId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getBroadcastId() {
		return broadcastId;
	}
	public void setBroadcastId(String broadcastId) {
		this.broadcastId = broadcastId;
	}
	
	
}