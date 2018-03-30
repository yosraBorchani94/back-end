package org.sid.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.sid.dao.AnswerRepository;
import org.sid.dao.ModuleRepository;
import org.sid.dao.QuestionRepository;
import org.sid.entities.Answer;
import org.sid.entities.Module;
import org.sid.entities.Question;
import org.sid.services.ModuleService;
import org.sid.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private ModuleRepository moduleRepository;

	@Autowired
	private QuestionService questionService;

	@PostMapping("/question/{id}")
	public Question save(@RequestBody Question question, @PathVariable Long id) {
		questionRepository.save(question);
		moduleService.addQuestionToModule(id, question.getQuestionName());
		return question;
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

	@PutMapping("/updateQuestion/{id}")
	public void updateQuestion(@PathVariable Long id, @RequestBody UpdateQuestionForm form) {
		System.out.println("QuestionName: " + form.getQuestionName());
		System.out.println("idModule: " + form.getModuleId());
		System.out.println("idQuestion: " + id);

		Module m =moduleRepository.findOne(form.getModuleId());
		Question q =new Question();
		q.setId(id);
		q.setQuestionName(form.getQuestionName());
		if (questionRepository.save(q) != null) {
			if (moduleService.deleteQuestionFromModule(id)) {
				moduleService.addQuestionToModuleUpdate(form.getModuleId(), id);
				
				Answer a = new Answer();
				a.setId(form.getTab().get(0).getId());
				a.setAnswerName(form.getTab().get(0).getAnswerName());
				a.setCorrect(form.getTab().get(0).isCorrect());
				System.out.println ("answer 1 : " + a.getId() + " " + a.getAnswerName()+ " " + a.isCorrect());
				
				Answer b = new Answer();
				b.setId(form.getTab().get(1).getId());
				b.setAnswerName(form.getTab().get(1).getAnswerName());
				b.setCorrect(form.getTab().get(1).isCorrect());
				System.out.println ("answer 2 : " + b.getId() + " " + b.getAnswerName()+ " " + b.isCorrect());
				
				Answer c = new Answer();
				c.setId(form.getTab().get(2).getId());
				c.setAnswerName(form.getTab().get(2).getAnswerName());
				c.setCorrect(form.getTab().get(2).isCorrect());
				System.out.println ("answer 3 : " + c.getId() + " " + c.getAnswerName()+ " " + c.isCorrect());
				
				Answer d = new Answer();
				d.setId(form.getTab().get(3).getId());
				d.setAnswerName(form.getTab().get(3).getAnswerName());
				d.setCorrect(form.getTab().get(3).isCorrect());
				System.out.println ("answer 4 : " + d.getId() + " " + d.getAnswerName()+ " " + d.isCorrect());

				if ((answerRepository.save(a) != null) && (answerRepository.save(b) != null)
						&& (answerRepository.save(c) != null) && (answerRepository.save(d) != null)) {
					questionService.addAnswerToQuestion(id, a.getAnswerName());
					questionService.addAnswerToQuestion(id, b.getAnswerName());
					questionService.addAnswerToQuestion(id, c.getAnswerName());
					questionService.addAnswerToQuestion(id, d.getAnswerName());
				}
			}
			
		}
	
	}

}
