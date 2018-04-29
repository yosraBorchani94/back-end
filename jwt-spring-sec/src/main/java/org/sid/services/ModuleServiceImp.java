package org.sid.services;

import java.util.Iterator;
import java.util.List;
import org.sid.dao.ModuleRepository;
import org.sid.dao.QuizRepository;
import org.sid.entities.AppUser;
import org.sid.entities.Module;
import org.sid.entities.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModuleServiceImp implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public void addQuizToModule(Long id_module, String questionName) {
		Module m = moduleRepository.findOne(id_module);
		Quiz quiz = quizRepository.findByQuestionName(questionName);
		m.getQuiz().add(quiz);
	}

	@Override
	public void addQuizToModuleUpdate(Long idModule, Long idQuestion) {
		Module m = moduleRepository.findOne(idModule);
		Quiz quiz = quizRepository.findOne(idQuestion);
		m.getQuiz().add(quiz);

	}

	@Override
	public boolean deleteQuizFromModule(Long id_question) {
		System.out.println(" in delete ");
		Quiz quiz = quizRepository.findOne(id_question);
		List<Module> listModule = moduleRepository.findAll();
		System.out.println("size module " + listModule.size());
		System.out.println("id_question  " + id_question);
		if (listModule.size() > 0) {
			for (int i = 0; i < listModule.size(); i++) {
				for (Iterator<Quiz> iterator = listModule.get(i).getQuiz().iterator(); iterator.hasNext();) {
					if (iterator.next().getId() == id_question) {
						listModule.get(i).getQuiz().remove(quiz);
						return true;
					}

				}
			}
			return false;
		}
		return false;
	}

	@Override
	public void deleteModule(Long idModule) {
		Module module = moduleRepository.findOne(idModule);
		if (module.getQuiz().size() > 0) {
			for (Iterator<Quiz> iterator = module.getQuiz().iterator(); iterator.hasNext();) {
			 quizRepository.delete(iterator.next().getId());
			 
			}
			module.getQuiz().clear();
		}

	}
}
