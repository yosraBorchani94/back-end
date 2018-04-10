package org.sid.dao;

import org.sid.entities.QuizInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface QuizInstanceRepository  extends JpaRepository<QuizInstance, Long>,CrudRepository<QuizInstance, Long>{
   
}
