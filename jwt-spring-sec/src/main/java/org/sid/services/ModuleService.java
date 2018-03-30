package org.sid.services;

public interface ModuleService {
	void addQuestionToModule(Long id_module, String questionName);
	void addQuestionToModuleUpdate (Long idModule , Long idQuestion);
	boolean deleteQuestionFromModule (Long id_question);
	
}
