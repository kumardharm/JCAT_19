package com.cg.jcat.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.jcat.api.dao.AssessmentQuestionModel;
import com.cg.jcat.api.entity.AssessmentQuestion;
import com.cg.jcat.api.exception.JcatExceptions;
import com.cg.jcat.api.repository.IAssessmentQuestionRepository;
import com.cg.jcat.api.service.IAssessmentQuestionService;

@Component
public class AssessmentQuestionController implements IAssessmentQuestionController{
	
	@Autowired
	private IAssessmentQuestionRepository repository;
	
	@Autowired
	private IAssessmentQuestionService assessmentQuestionService;
	
	@Override
	public List<AssessmentQuestion> getQuestions() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			System.out.println("Error getting assessment questions");
		}
		return repository.findAll();
	}

//	@Override
//	public List<AssessmentQuestionModel> getQuestions() {
//		try {
//			return assessmentQuestionService.getQuestions();
//		} catch (Exception e) {
//			System.out.println("Error getting assessment questions");
//		}
//		return null;
//	}

	@Override
	public void saveQuestions(AssessmentQuestionModel assessmentQuestionModel) {
		assessmentQuestionService.saveQuestions(assessmentQuestionModel);
	}

	@Override
	public void updateQuestion(AssessmentQuestionModel question) {
			assessmentQuestionService.updateQuestion(question);
		
	}

	@Override
	public void deleteQuestion(int questionId) {
		try {
			assessmentQuestionService.deleteQuestion(questionId);
			
		} catch (Exception e) {
			System.out.println("Error deleting assessment questions");
		}
		
	}

	@Override
	public AssessmentQuestionModel getQuestionById(int questionId) {
		// TODO Auto-generated method stub
		return assessmentQuestionService.getQuestionById(questionId);
	}

}
