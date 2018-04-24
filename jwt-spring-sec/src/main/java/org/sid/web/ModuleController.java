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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.sid.dao.DocumentRepository;
import org.sid.dao.ModuleInstanceRepository;
import org.sid.dao.ModuleRepository;
import org.sid.dao.QuizRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.AppUser;
import org.sid.entities.Document;
import org.sid.entities.Module;
import org.sid.entities.ModuleInstance;
import org.sid.entities.Quiz;
import org.sid.services.ModuleService;
import org.sid.uploadfile.RestUploadController;

@RestController
@CrossOrigin("*")
public class ModuleController {

	HashMap<String, Integer> hmapQuestion = new HashMap<String, Integer>();

	@Autowired
	private ModuleService moduleService;
	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private ModuleInstanceRepository moduleInstanceRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	

	@Autowired
	private 	RestUploadController uploadCtrl ;
	 
	@PostMapping("/module")
	public Module addModule(@RequestBody Module module) {
		int minScore = (module.getNbr_questions() * 60)/100;
		module.setMinScore(minScore);
		return moduleRepository.save(module);
	}

	@GetMapping("/module")
	public List<Module> listModules() {
		return moduleRepository.findAll();
	}
	
	@GetMapping("/getModulesSorted")
	public List<Module> getModulesSorted () {
		List<Module> listModule = moduleRepository.findAll();
		listModule.sort(Comparator.comparing(Module:: getLevel));
		return listModule;
	}

	@DeleteMapping("/module/{id}")
	public boolean delete(@PathVariable Long id) {

		List <Document> listDocument = documentRepository.findAll();
		for (int i = 0; i < listDocument.size(); i++) {
			if (listDocument.get(i).getModule().getId() == id) {
				System.out.println("trouve =>  delete document id " + listDocument.get(i).getId());
				uploadCtrl.deleteUserFile (listDocument.get(i).getId());
			}
		}
		moduleRepository.delete(id);
		return true;
	}

	@GetMapping("/module/{id}")
	public Module getModule(@PathVariable Long id) {
		return moduleRepository.findOne(id);
	}
	
	@GetMapping("/getModuleByName/{nom}")
	public Module getModuleName(@PathVariable String nom) {
		return moduleRepository.findByNom(nom);
	}

	@PutMapping("/module/{id}")
	public Module updateModule(@PathVariable Long id, @RequestBody Module m) {
		m.setId(id);
		int minScore = (m.getNbr_questions() * 60)/100;
		m.setMinScore(minScore);
		return moduleRepository.save(m);
	}
	
	@GetMapping("/actifModule")
	public List<Module> getActifModule() {
		List<Module> listModule = moduleRepository.findAll();
		List<Module> listModuleActif = new ArrayList<>();
		for (int i = 0; i < listModule.size(); i++) {
			if(listModule.get(i).isTotalQuestions()) {
				listModuleActif.add(listModule.get(i));
			}
		}
		return listModuleActif;
	}
	
	@GetMapping("/inProgressModule")
	public List<InProgressModuleForm> getInProgressModule() {
		List<Module> listModule = moduleRepository.findAll();
		List<InProgressModuleForm> inProgress  = new ArrayList<>();
		int questionsLeft =0;
		for (int i = 0; i < listModule.size(); i++) {
			if(!listModule.get(i).isTotalQuestions()) {	
				InProgressModuleForm e = new InProgressModuleForm();
				questionsLeft = listModule.get(i).getNbr_questions() - listModule.get(i).getQuiz().size() ;
                e.setModule(listModule.get(i));
                e.setQuestionsLeft(questionsLeft);
				inProgress.add(e);
			}
		}
		return inProgress;
	}

	
	
	
	
	public Long checkprecedantTest (Long idModel){
		Module m = moduleRepository.findOne(idModel);
		System.out.println (" the Module you are about to pass is : " + m.getLevel());
		if ( m.getLevel() > 1 ) {
			int precedentLevel =  m.getLevel()-1;
			System.out.println ("the precedent Module level : " + precedentLevel);
			Module ml = moduleRepository.findByLevel(precedentLevel);
			System.out.println (" the precedent Module id" + ml.getId()); 
			return  ml.getId();
		}
		return (long) 0;
		 
	}

	@PostMapping (value ="/checkPassTheTest/{idModule}")
	public int checkPassTheTest (@PathVariable Long idModule, @RequestBody String username) {
		

	    
		AppUser user = UserRepository.findByUsername(username);
		Module m = moduleRepository.findOne(idModule);
		
		
		List <ModuleInstance> ModuleByUser = moduleInstanceRepository.findAll();
		if (ModuleByUser.size() > 0) {
			for (int i = 0; i < ModuleByUser.size(); i++) {

				if ((ModuleByUser.get(i).getUser().getId() == user.getId())&&(ModuleByUser.get(i).getModule().getId() == idModule) && (ModuleByUser.get(i).getScore() >= m.getMinScore())) {
					System.out.println("you succeded  "+ m.getLevel());
					return 0;
				}
				else if ((ModuleByUser.get(i).getUser().getId() == user.getId()) &&(ModuleByUser.get(i).getModule().getId() == idModule) && (ModuleByUser.get(i).getScore() <  m.getMinScore())){
					System.out.println ("try again this Test");
					return 1;

				}else {
					Long idPrecedentModule = checkprecedantTest(idModule);
					if (idPrecedentModule != 0) {
						if ((ModuleByUser.get(i).getUser().getId() == user.getId())&&(ModuleByUser.get(i).getModule().getId() == idPrecedentModule) && (ModuleByUser.get(i).getScore() >= m.getMinScore())) {
							System.out.println ("u succeded the precedent exam u can take this level now ");
							return 2;
						}else {
							System.out.println ("u need to pass the precedent Module before taking this exam ");
							return 3;
						}
					}
				}
			}
		}
		return 4;
	}
	
	@GetMapping("/checkNumberOfQuestions/{id}")
	public boolean checkNumberOfQuestions ( Long id) {
		Module m = moduleRepository.findOne(id);
		List<Quiz> listQuiz = getAllQuestionFromModule(id);
		if (listQuiz.size() >= m.getNbr_questions()) {
			return true;
		}
		return false;
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

	@GetMapping("/getQuestionFromModuleShuffle/{id}")
	public List<Quiz> getQuestionFromModuleShuffle(@PathVariable Long id) {
		Module m = moduleRepository.findOne(id);
		List<Quiz> listQuestion = getAllQuestionFromModule(id);
		List<Quiz> listQuestionshuffle = new ArrayList<>();
		Collections.shuffle(listQuestion);

		for (int i = 0; i < m.getNbr_questions(); i++) {
			// System.out.print(" Shuffle array: " +listQuestion.get(i).getId());
			listQuestionshuffle.add(listQuestion.get(i));

		}
		return listQuestionshuffle;
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

	public int findTheCorrectAnswer(String idQuestion, String answer) {
		int myCpt = 0;

		System.out.println(idQuestion + " " + answer);
		Quiz q = quizRepository.findOne(Long.parseLong(idQuestion));

		if (q.getChoice1().equals(answer)) {
			if (q.isCheckbox1()) {
				myCpt++;
			} else {
				myCpt--;
			}
		} else if (q.getChoice2().equals(answer)) {
			if (q.isCheckbox2()) {
				myCpt++;
			} else {
				myCpt--;
			}
		} else if (q.getChoice3().equals(answer)) {
			if (q.isCheckbox3()) {
				myCpt++;
			} else {
				myCpt--;
			}
		} else {
			if (q.isCheckbox4()) {
				myCpt++;
			} else {
				myCpt--;
			}
		}

		System.out.println("myCpt: " + myCpt);
		return myCpt;
	}


	@PostMapping("/calculScore")
	public int getScore(@RequestBody Collection<String> MyArrayAnswers) {
		
		// clear hash map
		hmapQuestion.clear();
		int myCpt, cpt;
		int score = 0;
		for (Iterator<String> iterator = MyArrayAnswers.iterator(); iterator.hasNext();) {
			// System.out.println(iterator.next());
			StringTokenizer st2 = new StringTokenizer(iterator.next(), ":");
			String idQues = st2.nextElement().toString();
			String answer = st2.nextElement().toString();
			myCpt = findTheCorrectAnswer(idQues, answer);
			if (hmapQuestion.containsKey(idQues)) {
				int oldCpt = hmapQuestion.get(idQues);
				int newCpt = oldCpt + myCpt;
				System.out.println("oldCpt : " + oldCpt + "  " + "newCpt : " + newCpt);
				hmapQuestion.put(idQues, newCpt);
			} else {
				hmapQuestion.put(idQues, myCpt);
			}
		}

		/* Display content of hmap using Iterator */
		System.out.println(" ------------------------------------------ ");
		Set set = hmapQuestion.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			System.out.print("key is: " + mentry.getKey() + " & Value is: ");
			System.out.println(mentry.getValue());
			cpt = numberOfCorrectAnswers(Long.parseLong(mentry.getKey().toString()));
			System.out.println(" key  " + mentry.getKey() + " cpt : " + cpt);
			if (cpt == Integer.parseInt(mentry.getValue().toString())) {
				score += 1;
			}

		}
		System.out.println("votre scrore final : " + score);
		return score;
	}
	
	

}
