package org.sid.services;

public interface ModuleService {
	void addQuizToModule(Long id_module, String questionName);
	void addQuizToModuleUpdate (Long idModule , Long idQuestion);
	boolean deleteQuizFromModule (Long id_question);
	void deleteModule (Long idModule);
	
}
