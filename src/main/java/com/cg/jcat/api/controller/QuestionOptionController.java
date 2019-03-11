package com.cg.jcat.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.jcat.api.dao.QuestionOptionModel;
import com.cg.jcat.api.exception.JcatExceptions;
import com.cg.jcat.api.service.IQuestionOptionService;

@Component
public class QuestionOptionController implements IQuestionOptionController{
	
	@Autowired
	private IQuestionOptionService questionOptionService;
	
	@Override
	public List<QuestionOptionModel> getQuestionOptions() {
		try {
			return questionOptionService.getQuestionOptions();
		} catch (JcatExceptions e) {
			System.out.println("Error in getting users" + e);
			return null;
		}
	}
	
	@Override
	public void saveQuestionOption(QuestionOptionModel questionOptionModel) {
		try {
				questionOptionService.saveQuestionOption(questionOptionModel);
		} catch (Exception e) {
			System.out.print("Error while saving question option" + e);
		}
	}

	@Override
	public void updateQuestionOption(QuestionOptionModel questionOptionModel) {
		try {
			questionOptionService.updateQuestionOption(questionOptionModel);
		} catch (Exception e) {
			System.out.print("Error while updating question options" + e);
		}
	}

}
