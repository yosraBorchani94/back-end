package org.sid.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Answer {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String answerName;
	private boolean isCorrect;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Question question;
	
	public Answer() {
		super();
	}
	
	

	public Answer(Long id, String answerName, boolean isCorrect, Question question) {
		super();
		this.id = id;
		this.answerName = answerName;
		this.isCorrect = isCorrect;
		this.question = question;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
}
