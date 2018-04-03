package org.sid.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Module {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String nom;
	private String duree;
	private int nbr_questions;
	@ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL )
	private List<Quiz> quiz = new ArrayList<>();
	
	
	public Module() {
		super();
	}
	public Module(Long id, String nom, String duree, int nbr_questions, List<Quiz> quiz) {
		super();
		this.id = id;
		this.nom = nom;
		this.duree = duree;
		this.nbr_questions = nbr_questions;
		this.quiz = quiz;
	}
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
	public List<Quiz> getQuiz() {
		return quiz;
	}
	public void setQuiz(List<Quiz> quiz) {
		this.quiz = quiz;
	}
	
	
}