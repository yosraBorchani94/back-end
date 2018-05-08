package org.sid.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class ModuleInstance {
	@Id
	@GeneratedValue
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Module module;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private AppUser user; 
	
	private int score;

//	@OneToMany (fetch = FetchType.EAGER )
//	private Collection<QuizInstance> quizInstance = new ArrayList<>();

	
	public ModuleInstance() {
		super();
	}
	
	


	public ModuleInstance(Long id, Module module, AppUser user, int scor) {
		super();
		this.id = id;
		this.module = module;
		this.user = user;
		this.score = score;
		//this.quizInstance = quizInstance;
		
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Module getModule() {
		return module;
	}


	public void setModule(Module module) {
		this.module = module;
	}




	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

//	public Collection<QuizInstance> getQuizInstance() {
//		return quizInstance;
//	}
//
//	public void setQuizInstance(Collection<QuizInstance> quizInstance) {
//		this.quizInstance = quizInstance;
//	}

}
