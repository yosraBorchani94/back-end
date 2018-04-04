package org.sid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.sid.dao.ModuleRepository;
import org.sid.dao.QuizRepository;
import org.sid.entities.Module;
import org.sid.entities.Quiz;
import org.sid.services.ModuleService;

@RestController
@CrossOrigin("*")
public class ModuleController {

	HashMap<String,Integer> hmapQuestion = new HashMap<String,Integer>();
	
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private QuizRepository quizRepository;

	@PostMapping("/module")
	public Module addModule(@RequestBody Module module) {
		return moduleRepository.save(module);
	}

	@GetMapping("/module")
	public List<Module> listModules() {
		return moduleRepository.findAll();
	}

	@DeleteMapping("/module/{id}")
	public boolean delete(@PathVariable Long id) {
		moduleRepository.delete(id);
		return true;
	}

	@GetMapping("/module/{id}")
	public Module getModule(@PathVariable Long id) {
		return moduleRepository.findOne(id);
	}

	@PutMapping("/module/{id}")
	public Module updateModule(@PathVariable Long id, @RequestBody Module m) {
		m.setId(id);
		return moduleRepository.save(m);
	}

	@GetMapping("/getAllQuestionFromModule/{id}")
	public List<Quiz> getAllQuestionFromModule(@PathVariable Long id) {
		List idQuestions = new ArrayList<>();
		List<Quiz> listQuestion = new ArrayList<>();
		Quiz quiz;
		List<Module> listModule = moduleRepository.findAll();
		if (listModule.size() > 0) {
			for (int i = 0; i < listModule.size(); i++) {
				if (listModule.get(i).getId() == id) {
					for (Iterator<Quiz> iterator = listModule.get(i).getQuiz().iterator(); iterator.hasNext();) {
						idQuestions.add(iterator.next().getId());
					}
				}
			}

		}
		for (int i = 0; i < idQuestions.size(); i++) {
			quiz = quizRepository.findOne((Long) idQuestions.get(i));
			listQuestion.add(quiz);
		}
		return listQuestion;

	}

	public int numberOfCorrectAnswers(Long id) {
		int cpt = 0;
		Quiz q = quizRepository.findOne(id);
		if (q.isCheckbox1()) {
			cpt++;
		}
		if (q.isCheckbox2()) {
			cpt++;
		}
		if (q.isCheckbox3()) {
			cpt++;
		}
		if (q.isCheckbox4()) {
			cpt++;
		}
		return cpt;
	}

	public int  findTheCorrectAnswer(String idQuestion, String answer) {
		int myCpt = 0;
	
		System.out.println(idQuestion + " " + answer);
		Quiz q = quizRepository.findOne(Long.parseLong(idQuestion));
	
		if (q.getChoice1().equals(answer)) {
			if (q.isCheckbox1()) {
				myCpt++;
			}
		} else if (q.getChoice2().equals(answer)) {
			if (q.isCheckbox2()) {
				myCpt++;
			}
		} else if (q.getChoice3().equals(answer)) {
			if (q.isCheckbox3()) {
				myCpt++;
			}
		} else {
			if (q.isCheckbox4()) {
				myCpt++;
			}
		}
		
		System.out.println ("myCpt: "+myCpt);
		return myCpt;
		
	
	}

	@PostMapping("/calculScore")
	public void getScore(@RequestBody Collection<String> MyArrayAnswers) {
		// clear hash map
		hmapQuestion.clear();
		int myCpt;
		for (Iterator<String> iterator = MyArrayAnswers.iterator(); iterator.hasNext();) {
			// System.out.println(iterator.next());
			StringTokenizer st2 = new StringTokenizer(iterator.next(), ":");
			String idQues = st2.nextElement().toString();
			String answer = st2.nextElement().toString();
			myCpt = findTheCorrectAnswer(idQues,answer);
			if (hmapQuestion.containsKey(idQues)) {
				int oldCpt = hmapQuestion.get(idQues);
				int newCpt = oldCpt+myCpt;
				System.out.println ("oldCpt : "+oldCpt + "  "+ "newCpt : " +newCpt );
				hmapQuestion.put(idQues, newCpt);
			}else {
				hmapQuestion.put(idQues, myCpt);
			}
		}
		
		 /* Display content of hmap using Iterator*/
		System.out.println (" ------------------------------------------ ");
		 Set set = hmapQuestion.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
	         System.out.println(mentry.getValue());
	      }
	}

}
