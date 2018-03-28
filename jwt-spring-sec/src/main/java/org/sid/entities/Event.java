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

	public Event() {
		super();
	}
	public Event(Long id, String title, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
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
}