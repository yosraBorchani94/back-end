package org.sid.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.sid.dao.ModuleInstanceRepository;
import org.sid.dao.QuizInstanceRepository;
import org.sid.dao.QuizRepository;
import org.sid.entities.ModuleInstance;
import org.sid.entities.Quiz;
import org.sid.entities.QuizInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class QuizInstanceController {

	HashMap<String, String> hmapQuestion = new HashMap<String, String>();
	
	@Autowired
	private ModuleInstanceRepository moduleInstanceRepository;
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuizInstanceRepository quizInstanceRepository;
	
	@PostMapping("/addQuizInstance/{idModuleInstance}")
	public void  addQuizInstance(@PathVariable Long idModuleInstance, @RequestBody Collection<String> MyArrayAnswers){
		hmapQuestion.clear();
		System.out.println(" ---------------- in add QuizInstance ---------------- ");
		
		ModuleInstance mi = moduleInstanceRepository.findOne(idModuleInstance);
		
		for (Iterator<String> iterator = MyArrayAnswers.iterator(); iterator.hasNext();) {
			StringTokenizer st2 = new StringTokenizer(iterator.next(), ":");
			String idQues = st2.nextElement().toString();
			String answer = st2.nextElement().toString();
			if (hmapQuestion.containsKey(idQues)) {
				String oldAnswer = hmapQuestion.get(idQues);
				String newAnswer = oldAnswer +","+ answer;
				System.out.println("oldAnswer : " + oldAnswer + "  " + "newAnswer : " + newAnswer);
				hmapQuestion.put(idQues, newAnswer);
			} else {
				hmapQuestion.put(idQues, answer);
			}
		}
		
		System.out.println(" ------------------------------------------ ");
		Set set = hmapQuestion.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			QuizInstance qi = new QuizInstance();
			Map.Entry mentry = (Map.Entry) iterator.next();
			System.out.println ("key is: " + mentry.getKey() + " & Value is: "+mentry.getValue());
			Quiz quiz = quizRepository.findOne(Long.parseLong(mentry.getKey().toString()));
			qi.setModuleInstance(mi);
			qi.setQuiz(quiz);
			qi.setUserAnswers(mentry.getValue().toString());
			quizInstanceRepository.save(qi);
		}
	}
}
