package org.sid.web;
import java.util.List;

import org.sid.entities.Module;

public class InProgressModuleForm {
 
	private Module Module;
	private int questionsLeft;
	
	
	public Module getModule() {
		return Module;
	}
	public void setModule(Module Module) {
		this.Module = Module;
	}
	public int getQuestionsLeft() {
		return questionsLeft;
	}
	public void setQuestionsLeft(int questionsLeft) {
		this.questionsLeft = questionsLeft;
	}
	
	
}
