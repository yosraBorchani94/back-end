package org.sid.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
public class Event {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String title;
	private String startDate;
	private String endDate;

	public Event() {
		super();
	}
	public Event(Long id, String title, String startDate, String endDate) {
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
