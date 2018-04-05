package org.sid.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class QuizInstance {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne (fetch = FetchType.EAGER )
	private ModuleInstance moduleInstance;
	
	@ManyToOne (fetch = FetchType.EAGER )
	private Quiz Quiz;
	
	private String userAnswers;

	
	public QuizInstance() {
		super();
	}

	public QuizInstance(Long id, ModuleInstance moduleInstance, org.sid.entities.Quiz quiz, String userAnswers) {
		super();
		this.id = id;
		this.moduleInstance = moduleInstance;
		Quiz = quiz;
		this.userAnswers = userAnswers;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ModuleInstance getModuleInstance() {
		return moduleInstance;
	}

	public void setModuleInstance(ModuleInstance moduleInstance) {
		this.moduleInstance = moduleInstance;
	}

	public Quiz getQuiz() {
		return Quiz;
	}

	public void setQuiz(Quiz quiz) {
		Quiz = quiz;
	}

	public String getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(String userAnswers) {
		this.userAnswers = userAnswers;
	}
	
	
	
}
