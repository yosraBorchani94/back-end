package org.sid.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Module {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String nom;
	private String duree;
	private int nbr_questions;
	@Column(unique = true)
	private int level ;
	private boolean isTotalQuestions;
	private int minScore;
	
	@ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL )
	private Set<Quiz> quiz = new HashSet<>();
	
//	@OneToMany(fetch = FetchType.EAGER  , cascade = CascadeType.ALL )
//	private Set<Video> videos = new HashSet<>();
//
//	
//	@OneToMany (fetch = FetchType.EAGER , cascade = CascadeType.ALL )
//	private Set<Document> document  = new HashSet<Document>();
//	
//	@OneToMany (fetch = FetchType.EAGER , cascade = CascadeType.ALL )
//	private Set<ModuleInstance> moduleInstance  = new HashSet<ModuleInstance>();
	
	
	public Module() {
		super();
	}
	
	
	public Module(Long id, String nom, String duree, int nbr_questions, int level, boolean isTotalQuestions,
			int minScore, Set<Quiz> quiz) {
		super();
		this.id = id;
		this.nom = nom;
		this.duree = duree;
		this.nbr_questions = nbr_questions;
		this.level = level;
		this.isTotalQuestions = isTotalQuestions;
		this.minScore = minScore;
		this.quiz = quiz;
//		this.videos = videos;
//		this.document = document;
//		this.moduleInstance = moduleInstance;
	}

//
//	public Set<Video> getVideos() {
//		return videos;
//	}
//
//
//	public void setVideos(Set<Video> videos) {
//		this.videos = videos;
//	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDuree() {
		return duree;
	}
	public void setDuree(String duree) {
		this.duree = duree;
	}
	public int getNbr_questions() {
		return nbr_questions;
	}
	public void setNbr_questions(int nbr_questions) {
		this.nbr_questions = nbr_questions;
	}
	public Set<Quiz> getQuiz() {
		return quiz;
	}
	public void setQuiz(Set<Quiz> quiz) {
		this.quiz = quiz;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isTotalQuestions() {
		return isTotalQuestions;
	}
	public void setTotalQuestions(boolean isTotalQuestions) {
		this.isTotalQuestions = isTotalQuestions;
	}
	public int getMinScore() {
		return minScore;
	}
	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}

//
//	public Set<Document> getDocument() {
//		return document;
//	}
//
//
//	public void setDocument(Set<Document> document) {
//		this.document = document;
//	}
//
//
//	public Set<ModuleInstance> getModuleInstance() {
//		return moduleInstance;
//	}
//
//
//	public void setModuleInstance(Set<ModuleInstance> moduleInstance) {
//		this.moduleInstance = moduleInstance;
//	}
//	
	
	
	
}
