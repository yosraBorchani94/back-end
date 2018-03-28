package org.sid.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Answer {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String answerName;
	private boolean isCorrect;

	
	public Answer() {
		super();
	}
	public Answer(Long id, String answerName, boolean isCorrect ) {
		super();
		this.id = id;
		this.answerName = answerName;
		this.isCorrect = isCorrect;

	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnswerName() {
		return answerName;
	}
	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
}
