package org.sid.services;


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
		System.out.println("quest.getId()"+quest.getId());
		m.getQuestions().add(quest);
	}


}
