package org.sid.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String questionName;
	@OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
	private Collection<Answer> answers = new ArrayList<>();
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	Module module;
	
	public Question() {
		super();
	}
	public Question(Long id, String questionName, Collection<Answer> answers) {
		super();
		this.id = id;
		this.questionName = questionName;
		this.answers = answers;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public Collection<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}
//	public Module getModule() {
//		return module;
//	}
//	public void setModule(Module module) {
//		this.module = module;
//	}
	
	
	
}
