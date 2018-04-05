package org.sid.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class ModuleInstance {
	@Id
	@GeneratedValue
	private Long id;
	private Long idModule;
	private Long idUser;
	private int score;
	@OneToMany (fetch = FetchType.EAGER )
	private Collection<QuizInstance> quizInstance = new ArrayList<>();

	
	public ModuleInstance() {
		super();
	}
	
	


	public ModuleInstance(Long id, Long idModule, Long idUser, int score, Collection<QuizInstance> quizInstance) {
		super();
		this.id = id;
		this.idModule = idModule;
		this.idUser = idUser;
		this.score = score;
		this.quizInstance = quizInstance;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getIdModule() {
		return idModule;
	}


	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}


	public Long getIdUser() {
		return idUser;
	}


	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public Collection<QuizInstance> getQuizInstance() {
		return quizInstance;
	}


	public void setQuizInstance(Collection<QuizInstance> quizInstance) {
		this.quizInstance = quizInstance;
	}

	

	
	
}
