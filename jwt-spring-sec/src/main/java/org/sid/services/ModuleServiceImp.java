package org.sid.services;

import java.util.Iterator;
import java.util.List;

import org.sid.dao.ModuleRepository;
import org.sid.dao.QuestionRepository;
import org.sid.entities.Module;
import org.sid.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModuleServiceImp implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public void addQuestionToModule(Long id_module, String questionName) {
		Module m = moduleRepository.findOne(id_module);
		Question quest = questionRepository.findByQuestionName(questionName);
		// System.out.println("***********************" +m.getId()+ " "+quest.getId());
		// System.out.println("***********************" + m.getQuestions().size());
		m.getQuestions().add(quest);

	}

	@Override
	public boolean deleteQuestionFromModule(Long id_question) {
		System.out.println(" in delete ");
		Question quest = questionRepository.findOne(id_question);
		List<Module> listModule = moduleRepository.findAll();
		System.out.println("size module " + listModule.size());
		System.out.println("id_question  " + id_question);
		if (listModule.size() > 0) {
			for (int i = 0; i < listModule.size(); i++) {
				for (Iterator<Question> iterator = listModule.get(i).getQuestions().iterator(); iterator.hasNext();) {
					if (iterator.next().getId() == id_question) {
						listModule.get(i).getQuestions().remove(quest);
						return true;
					}

				}
			}
			return false;
		}
		return false;
	}

	@Override
	public void addQuestionToModuleUpdate(Long idModule, Long idQuestion) {

		Module m = moduleRepository.findOne(idModule);
		Question quest = questionRepository.findOne(idQuestion);
		m.getQuestions().add(quest);
	}

}
