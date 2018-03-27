package org.sid.web;


import java.util.List;

import org.sid.dao.AnswerRepository;
import org.sid.dao.QuestionRepository;
import org.sid.entities.Answer;
import org.sid.entities.Question;
import org.sid.services.ModuleService;
import org.sid.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")


public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	
	@Autowired
	private QuestionService questionService;
	
	@PostMapping("/question/{id}")
	public void save(@RequestBody QuestionForm form ,@PathVariable Long id){
		
		/*System.out.println(form.getResponse1());
		System.out.println(form.getResponse2());
		System.out.println(form.getResponse3());
		System.out.println(form.getResponse4());
		
		System.out.println(form.isCheckbox1());
		System.out.println(form.isCheckbox2());
		System.out.println(form.isCheckbox3());
		System.out.println(form.isCheckbox4());*/
		
	
		/***************** add question ***********************/
		Question ques = new Question();
		System.out.println(form.getQuestion_name());
		ques.setQuestionName(form.getQuestion_name());
		questionRepository.save(ques);
		moduleService.addQuestionToModule(id, form.getQuestion_name());
		/***************** add 4 answers ***********************/
		Answer a1 = new Answer();
		a1.setAnswerName(form.getResponse1());
		a1.setCorrect(form.isCheckbox1());
		Question q1 = questionRepository.findByQuestionName(form.getQuestion_name());
		System.out.println(q1.getId());
		answerRepository.save(a1);
		questionService.addAnswerToQuestion(q1.getId(), form.getResponse1());
		
		/*Answer a2 = new Answer();
		a2.setAnswerName(form.getResponse2());
		a2.setCorrect(form.isCheckbox2());
		Question q2 = questionRepository.findByQuestionName(form.getQuestion_name());
		answerRepository.save(a2);
		questionService.addAnswerToQuestion(q2.getId(), form.getResponse2());
		
		Answer a3 = new Answer();
		a3.setAnswerName(form.getResponse3());
		a3.setCorrect(form.isCheckbox3());
		Question q3= questionRepository.findByQuestionName(form.getQuestion_name());
		answerRepository.save(a3);
		questionService.addAnswerToQuestion(q3.getId(), form.getResponse3());
		
		Answer a4 = new Answer();
		a1.setAnswerName(form.getResponse4());
		a1.setCorrect(form.isCheckbox4());
		Question q4 = questionRepository.findByQuestionName(form.getQuestion_name());
		answerRepository.save(a4);
		questionService.addAnswerToQuestion(q4.getId(), form.getResponse4());
		*/
	}
}
