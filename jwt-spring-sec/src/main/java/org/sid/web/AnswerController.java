package org.sid.web;

import java.util.List;

import org.sid.dao.AnswerRepository;
import org.sid.dao.QuestionRepository;
import org.sid.entities.Answer;
import org.sid.entities.Question;
import org.sid.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionService questionService;

	@GetMapping("/answer")
	public List<Answer> listModules() {
		return answerRepository.findAll();
	}

	@PostMapping("/answer/{id}")
	public void save(@RequestBody QuestionForm form, @PathVariable Long id) {
		//System.out.println("answer ====> : "+ form.getResponse1() + " "+ form.isCheckbox1());
		
			Answer a = new Answer();
			a.setAnswerName(form.getResponse1());
			a.setCorrect(form.isCheckbox1());
			
			Answer b= new Answer();
			b.setAnswerName(form.getResponse2());
			b.setCorrect(form.isCheckbox2());
	
			
			Answer c = new Answer();
			c.setAnswerName(form.getResponse3());
			c.setCorrect(form.isCheckbox3());
			
			Answer d = new Answer();
			d.setAnswerName(form.getResponse4());
			d.setCorrect(form.isCheckbox4());
			
			
			if ( (answerRepository.save(a)!= null )&& (answerRepository.save(b) != null) && (answerRepository.save(c)!= null) && (answerRepository.save(d)!=null)) {
				questionService.addAnswerToQuestion(id, a.getAnswerName());
				questionService.addAnswerToQuestion(id, b.getAnswerName());
				questionService.addAnswerToQuestion(id, c.getAnswerName());
				questionService.addAnswerToQuestion(id, d.getAnswerName());
			}
			
		
	}
	
//	@GetMapping("/isAnswers/{id}")
//	public boolean isAnswers(@PathVariable Long id) {
//		Question q =questionRepository.findOne(id);
//		if (q.getAnswers().size()>0) {
//			return true;
//		}
//		return false;
//	}

}