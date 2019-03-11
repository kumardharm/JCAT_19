package com.cg.jcat.api.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.jcat.api.exception.SystemExceptions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TestAssessmentDao {

	@Autowired
	AssessmentDao assessmentDao;

	@Autowired
	AssessmentQuestionDao assessmentQuestionDao;

	@Test
	public void getAnswers() {

		assertNotNull(assessmentDao.getAnswers(1));
	}

	@Test
	public void testSaveAnswers() throws SystemExceptions {
		boolean result = false;
		AnswerModel answerModel = getAnswerModel();
		List<AnswerModel> answerModels = new ArrayList<>();
		answerModels.add(answerModel);
		result = assessmentDao.saveAnswers(answerModels, 1);
		assertTrue(result);
		assertEquals(1, assessmentDao.getAnswers(1).size());
		assertEquals(0, assessmentDao.getAnswerHistory().size());

//		answerModel.setAnswerId(1);
//		answerModel.setOptionTextsEN("a,b,c,d,e");
//		answerModel.setOptionIds("1,2,3,4,5");
//		answerModels.add(answerModel);
//		result = assessmentDao.saveAnswers(answerModels, 1);
//		assertTrue(result);
//		assertEquals(1, assessmentDao.getAnswers(1).size());
//		assertEquals(1, assessmentDao.getAnswerHistory().size());
//
//		 answerModel.setAnswerId(2);
//		 answerModels.add(answerModel);
//		 result = assessmentDao.saveAnswers(answerModels,1);
//		 assertEquals(2, assessmentDao.getAnswers(1).size());
//		 assertEquals(1, assessmentDao.getAnswerHistory().size());
	}

	private AnswerModel getAnswerModel() {
		AnswerModel answerModel = new AnswerModel();
		answerModel.setAnswerId(1);
		answerModel.setApplicationId(1);
		answerModel.setDtCloudableRuleResult(true);
		answerModel.setDtMigrationRuleResult(false);
		answerModel.setDtProviderRuleResult(false);
		answerModel.setModifiedBy("Admin");
		answerModel.setOptionIds("1,2,3");
		answerModel.setOptionTextsEN("a,b,c");
		answerModel.setQuestionId(1);
		answerModel.setQuestionTextEN("q1");
		return answerModel;
	}

}
