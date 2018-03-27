package org.sid.web;

public class QuestionForm {

    private Long id;
	private String question_name;
	private String response1,response2,response3,response4 ;
	private boolean checkbox1,checkbox2,checkbox3,checkbox4;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion_name() {
		return question_name;
	}
	public void setQuestion_name(String question_name) {
		this.question_name = question_name;
	}
	public String getResponse1() {
		return response1;
	}
	public void setResponse1(String response1) {
		this.response1 = response1;
	}
	public String getResponse2() {
		return response2;
	}
	public void setResponse2(String response2) {
		this.response2 = response2;
	}
	public String getResponse3() {
		return response3;
	}
	public void setResponse3(String response3) {
		this.response3 = response3;
	}
	public String getResponse4() {
		return response4;
	}
	public void setResponse4(String response4) {
		this.response4 = response4;
	}
	public boolean isCheckbox1() {
		return checkbox1;
	}
	public void setCheckbox1(boolean checkbox1) {
		this.checkbox1 = checkbox1;
	}
	public boolean isCheckbox2() {
		return checkbox2;
	}
	public void setCheckbox2(boolean checkbox2) {
		this.checkbox2 = checkbox2;
	}
	public boolean isCheckbox3() {
		return checkbox3;
	}
	public void setCheckbox3(boolean checkbox3) {
		this.checkbox3 = checkbox3;
	}
	public boolean isCheckbox4() {
		return checkbox4;
	}
	public void setCheckbox4(boolean checkbox4) {
		this.checkbox4 = checkbox4;
	}
	
	
	
}
