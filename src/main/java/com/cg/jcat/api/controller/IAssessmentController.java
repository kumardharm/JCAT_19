package com.cg.jcat.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cg.jcat.api.dao.AnswerModel;
import com.cg.jcat.api.exception.OptionTextNotNullException;
import com.cg.jcat.api.exception.SystemExceptions;

@RestController
@RequestMapping("/assessment")
public interface IAssessmentController {
	
	@GetMapping
	@RequestMapping("answer/get/{applicationId}")
	public List<AnswerModel> getAnswers(@PathVariable int applicationId);
	
	@PostMapping
	@RequestMapping("answer/create/{applicationId}")
	public boolean saveAnswers(@RequestBody List<AnswerModel> answerModels, @PathVariable int applicationId) throws SystemExceptions, OptionTextNotNullException; 
	
	@PostMapping
	@RequestMapping("finalize/{applicationId}/{assessmentStage}")
	public void finalized(@RequestBody List<AnswerModel> answerModels, @PathVariable int applicationId, @PathVariable int assessmentStage) throws SystemExceptions, OptionTextNotNullException;
	
	
}
