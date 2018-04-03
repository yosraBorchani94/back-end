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

	@GetMapping("/convertMinutes/{duree}")
	public TimeFormat getTime(@PathVariable int duree) {
		int hours = duree / 60;
		int minutes = duree % 60;
		String hoursString = "";
		String minutesString = "";
		System.out.println(hours + ":" + minutes);
		if (((hours >= 0) && (hours <= 9)) && ((minutes >= 0) && (minutes <= 9))) {
			hoursString = "0" + hours;
			minutesString = "0" + minutes;
		} else if ((hours >= 0) && (hours <= 9)) {
			hoursString = "0" + hours;
			minutesString = minutes + "";
			System.out.println("hoursString " + hoursString);
		} else if ((minutes >= 0) && (minutes <= 9)) {
			minutesString = "0" + minutes;
			hoursString = "0" + hours;
			System.out.println("minutesString " + minutesString);
		}else {
			hoursString = hours + "";
			minutesString = minutes + "";
		}
		TimeFormat tm = new TimeFormat();
		tm.setHours(hoursString);
		tm.setMinutes(minutesString);
		return tm;

	}
}
