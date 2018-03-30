package org.sid.web;

import java.util.List;

import org.sid.entities.Answer;

public class UpdateQuestionForm {

	private String questionName;
	private Long moduleId;
	private List<Answer> tab;
	
	
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public List<Answer> getAnswers() {
		return tab;
	}
	public void setAnswers(List<Answer> tab) {
		this.tab = tab;
	}
	public List<Answer> getTab() {
		return tab;
	}
	public void setTab(List<Answer> tab) {
		this.tab = tab;
	}
	
	
	
	
	
}
