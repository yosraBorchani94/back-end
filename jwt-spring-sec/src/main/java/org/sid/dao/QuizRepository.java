package org.sid.dao;

import org.sid.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends  JpaRepository<Quiz, Long>,CrudRepository<Quiz, Long>{
	public Quiz findByQuestionName (String questionName);
}
