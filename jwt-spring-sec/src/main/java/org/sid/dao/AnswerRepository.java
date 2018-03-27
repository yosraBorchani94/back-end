package org.sid.dao;

import org.sid.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>,CrudRepository<Answer, Long>{

	public Answer findByAnswerName (String answer );
}
