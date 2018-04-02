package org.sid.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Quiz {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String questionName;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private boolean checkbox1;
	private boolean checkbox2;
	private boolean checkbox3;
	private boolean checkbox4;
	private String urlPicture;
	
	
	
	public Quiz() {
		super();
	}
	public Quiz(Long id, String questionName, String choice1, String choice2, String choice3, String choice4,
			boolean checkbox1, boolean checkbox2, boolean checkbox3, boolean checkbox4, String urlPicture) {
		super();
		this.id = id;
		this.questionName = questionName;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.checkbox1 = checkbox1;
		this.checkbox2 = checkbox2;
		this.checkbox3 = checkbox3;
		this.checkbox4 = checkbox4;
		this.urlPicture = urlPicture;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public String getChoice1() {
		return choice1;
	}
	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}
	public String getChoice2() {
		return choice2;
	}
	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}
	public String getChoice3() {
		return choice3;
	}
	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}
	public String getChoice4() {
		return choice4;
	}
	public void setChoice4(String choice4) {
		this.choice4 = choice4;
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
	public String getUrlPicture() {
		return urlPicture;
	}
	public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}
	
	
	
}
