package org.sid.services;

import org.sid.dao.AnswerRepository;
import org.sid.dao.QuestionRepository;
import org.sid.entities.Answer;
import org.sid.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionServiceImp implements QuestionService{

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Override
	public void addAnswerToQuestion(Long id_question, String AnswerName) {
		Question q =questionRepository.findOne(id_question);
		Answer a = answerRepository.findByAnswerName(AnswerName);
		q.getAnswers().add(a);
	}

}
