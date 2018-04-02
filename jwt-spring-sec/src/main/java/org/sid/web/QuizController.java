package org.sid.web;

import java.util.List;

import org.sid.dao.QuizRepository;
import org.sid.entities.Module;
import org.sid.entities.Quiz;
import org.sid.services.ModuleService;
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
public class QuizController {

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private QuizRepository quizRepository;

	@PostMapping("/quiz/{id}")
	public Quiz addModule(@PathVariable Long id, @RequestBody Quiz quiz) {
		quizRepository.save(quiz);
		moduleService.addQuizToModule(id, quiz.getQuestionName());
		return quiz;
	}

	@GetMapping("/quiz")
	public List<Quiz> listQuiz() {
		return quizRepository.findAll();
	}

	@GetMapping("/quiz/{id}")
	public Quiz getQuiz(@PathVariable Long id) {
		return quizRepository.findOne(id);
	}

	@PutMapping("/quiz/{idModule}")
	public Quiz updateModule(@PathVariable Long idModule, @RequestBody Quiz q) {
		q.setId(q.getId());
		quizRepository.save(q);
		moduleService.deleteQuizFromModule(q.getId());
		moduleService.addQuizToModuleUpdate(idModule, q.getId());
		return q;
	}

	@DeleteMapping("/quiz/{id}")
	public boolean delete(@PathVariable Long id) {
		moduleService.deleteQuizFromModule(id);
		quizRepository.delete(id);
		return true;
	}
}
