package org.sid.services;

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
		m.getQuestions().add(quest);
	}

	@Override
	public void deleteQuestionFromModule(Long id_question) {
		Question quest = questionRepository.findOne(id_question);
		System.out.println("quest.getId()  : " + quest.getId());
		List<Module> listModule = moduleRepository.findAll();
		for (int i = 0; i < listModule.size(); i++) {
			System.out.println("id question in module : " + listModule.get(i).getQuestions().iterator().next().getId()
					+ "module id :" + listModule.get(i).getId());
			if (listModule.get(i).getQuestions().iterator().next().getId() == id_question) {
				listModule.get(i).getQuestions().remove(quest);
			}

		}

	}

}
