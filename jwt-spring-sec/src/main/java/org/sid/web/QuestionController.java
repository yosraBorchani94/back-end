package org.sid.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sid.dao.AnswerRepository;
import org.sid.dao.QuestionRepository;
import org.sid.entities.Answer;
import org.sid.entities.AppRole;
import org.sid.entities.Question;
import org.sid.services.ModuleService;
import org.sid.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	public void save(@RequestBody QuestionForm form, @PathVariable Long id) {

		/*
		 * System.out.println(form.getQuestionName());
		 * System.out.println(form.getResponse1());
		 * System.out.println(form.getResponse2());
		 * System.out.println(form.getResponse3());
		 * System.out.println(form.getResponse4());
		 * System.out.println(form.isCheckbox1());
		 * System.out.println(form.isCheckbox2());
		 * System.out.println(form.isCheckbox3());
		 * System.out.println(form.isCheckbox4());
		 */

		/* add question */
		Question ques = new Question();
		ques.setQuestionName(form.getQuestionName());
		questionRepository.save(ques);
		moduleService.addQuestionToModule(id, form.getQuestionName());

		/* add 4 answers */
		Answer a1 = new Answer();
		a1.setAnswerName(form.getResponse1());
		a1.setCorrect(form.isCheckbox1());
		Question q1 = questionRepository.findByQuestionName(form.getQuestionName());
		System.out.println(q1.getId());
		answerRepository.save(a1);
		questionService.addAnswerToQuestion(q1.getId(), form.getResponse1());

		Answer a2 = new Answer();
		a2.setAnswerName(form.getResponse2());
		a2.setCorrect(form.isCheckbox2());
		Question q2 = questionRepository.findByQuestionName(form.getQuestionName());
		answerRepository.save(a2);
		questionService.addAnswerToQuestion(q2.getId(), form.getResponse2());

		Answer a3 = new Answer();
		a3.setAnswerName(form.getResponse3());
		a3.setCorrect(form.isCheckbox3());
		Question q3 = questionRepository.findByQuestionName(form.getQuestionName());
		answerRepository.save(a3);
		questionService.addAnswerToQuestion(q3.getId(), form.getResponse3());

		Answer a4 = new Answer();
		a4.setAnswerName(form.getResponse4());
		a4.setCorrect(form.isCheckbox4());
		Question q4 = questionRepository.findByQuestionName(form.getQuestionName());
		answerRepository.save(a4);
		questionService.addAnswerToQuestion(q4.getId(), form.getResponse4());

	}

	@GetMapping("/question")
	public List<Question> listQuestion() {
		return questionRepository.findAll();
	}
	
	@GetMapping("/question/{id}")
	public Question getQuestion(@PathVariable Long id) {
		return questionRepository.findOne(id);
	}

	@GetMapping("/answersOfQuestion/{id}")
	public List<Answer> listAnswersOfQuestion(@PathVariable Long id) {
		Question ques = questionRepository.findOne(id);
		List<Answer> listAnswer = new ArrayList<>();
		if (ques.getAnswers().size() > 0) {
			for (Iterator<Answer> iterator = ques.getAnswers().iterator(); iterator.hasNext();) {
				Answer a = answerRepository.findOne(iterator.next().getId());
				listAnswer.add(a);

			}
		}
		return listAnswer;
	}

	@DeleteMapping("/question/{id}")
	public boolean delete(@PathVariable Long id) {
		moduleService.deleteQuestionFromModule(id);
		questionRepository.delete(id);

		return true;
	}

	@GetMapping("/duplicateQuestion/{questionName}")
	public boolean duplicateQuestion(@PathVariable String questionName) {
		List<Question> listquest = questionRepository.findAll();
		if (listquest.size() > 0) {
			for (int i = 0; i < listquest.size(); i++) {
				if (listquest.get(i).getQuestionName().equals(questionName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@GetMapping("/updateQuestion/{id}")
	public void updateQuestion (@PathVariable Long id , @RequestBody QuestionForm form) {
		System.out.println("**********" +id);
	}

}
