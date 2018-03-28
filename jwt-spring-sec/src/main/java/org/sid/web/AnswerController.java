package org.sid.web;

import java.util.List;

import org.sid.dao.AnswerRepository;
import org.sid.entities.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("/answer")
	public List<Answer> listModules() {
		return answerRepository.findAll();
	}
	
	@GetMapping("/checkedAnwer/{answer}")
	public boolean checkedAnwer(@PathVariable String answer) {
		Answer a = answerRepository.findByAnswerName(answer);
		return a.isCorrect();
	}
	

}