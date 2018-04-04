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

	
	
	public QuizInstance() {
		super();
	}

	public QuizInstance(Long id, ModuleInstance moduleInstance, org.sid.entities.Quiz quiz) {
		super();
		this.id = id;
		this.moduleInstance = moduleInstance;
		Quiz = quiz;
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
	
	
}
