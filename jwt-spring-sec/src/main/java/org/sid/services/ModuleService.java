package org.sid.services;

public interface ModuleService {
	void addQuestionToModule(Long id_module, String questionName);
	void deleteQuestionFromModule (Long id_question);
	
}
