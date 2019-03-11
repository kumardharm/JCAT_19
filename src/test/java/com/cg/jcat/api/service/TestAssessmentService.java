package com.cg.jcat.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.jcat.api.dao.AnswerModel;
import com.cg.jcat.api.dao.ApplicationModel;
import com.cg.jcat.api.dao.AssessmentDao;
import com.cg.jcat.api.dao.AssessmentQuestionModel;
import com.cg.jcat.api.dao.DTCloudableRuleModel;
import com.cg.jcat.api.dao.DTMigrationRuleModel;
import com.cg.jcat.api.dao.DTProviderRuleModel;
import com.cg.jcat.api.entity.Application;
import com.cg.jcat.api.entity.DTMigration;
import com.cg.jcat.api.entity.DTProviders;
import com.cg.jcat.api.exception.JcatExceptions;
import com.cg.jcat.api.exception.OptionTextNotNullException;
import com.cg.jcat.api.exception.SystemExceptions;
import com.cg.jcat.api.repository.IDTMigrationRepository;
import com.cg.jcat.api.repository.IDTProviderRepository;
import com.cg.jcat.api.utility.QuestionTypeEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TestAssessmentService {

	@Autowired
	AssessmentService assessmentService;

	@Autowired
	ApplicationService applicationService;

	@Autowired
	AssessmentDao assessmentDao;

	@Autowired
	AssessmentQuestionService assessmentQuestionService;

	@Autowired
	DTCloudableRuleService dtCloudableRuleService;

	@Autowired
	DTMigrationRuleService dtMigrationRuleService;

	@Autowired
	IDTMigrationRepository dtMigrationRepository;
	
	@Autowired
	DTProviderRuleService dtProviderRuleService;
	
	@Autowired
	IDTProviderRepository dtProviderRepository;
	
	Date date = new Date();

	@Ignore
	@Test
	public void testAStage1Finalized() throws SystemExceptions, OptionTextNotNullException {

		/*
		 * Saving Assessment Questions
		 */
		boolean result = true;
		result = assessmentQuestionService.saveQuestions(getAssessmentQuestions());
		assertEquals(true, result);

		/*
		 * Saving Cloudable Rule
		 */
		boolean rule = true;
		List<DTCloudableRuleModel> dTCloudableRuleModelList = new ArrayList<DTCloudableRuleModel>();
		dTCloudableRuleModelList = getCloudableRuleModel();
		rule = dtCloudableRuleService.saveCloudableRule(dTCloudableRuleModelList);
		assertEquals(true, rule);

		/*
		 * Saving Application
		 */
		ApplicationModel applicationModel = new ApplicationModel();
		applicationModel.setAid(1);
		applicationModel.setApplicationId("app");
		applicationModel.setAssessmentStage(1);
		applicationService.save(applicationModel);

		/*
		 * Saving Answer
		 */
		AnswerModel answerModel = getAnswerModel(1, 1, false, false, false, "Admin", "1", "a", 1, "q1");
		List<AnswerModel> answerModels = new ArrayList<>();
		answerModels.add(answerModel);
		result = assessmentService.saveAnswers(answerModels, 1);
		assertTrue(result); // check answers are saving in database or not
		assertEquals(1, assessmentService.getAnswers(1).size()); // check size of answers in database
		answerModels = assessmentService.getAnswers(1); // get answers in list
		assertEquals(true, answerModels.get(0).isDtCloudableRuleResult()); // check dt cloudable result

		/*
		 * Call finalize
		 */
		assessmentService.finalized(answerModels, 1, 1);
		Application application = assessmentDao.getApplicationByApplicationId(1);
		assertEquals(1, application.getAssessmentStage()); // check application stage
		assertEquals(true, application.isDTCloudable()); // check dt cloudable result
		
	}
	
	/*
	 * Public Pass
	 * */
 	@Test
	public void testEprovidersGITC() throws SystemExceptions, OptionTextNotNullException {
		boolean result = false;
		saveQuestions();
		
		/*
		 * int providerId, String providerName, String createdBy, Date createdTime, int evaluationOrder,String logicalOperator,
			String modifiedBy,Date modifiedTime
		 * */
		List<DTProviders> dtProvidersList = new ArrayList<>();
		dtProvidersList.add(saveProvider(1,"GITC","Admin",date,1,"OR","Admin",date));
		dtProvidersList.add(saveProvider(2,"AWS","Admin",date,2,"Others","Admin",date));
		dtProviderRepository.saveAll(dtProvidersList);
		
		
		/*
		 * Saving Migration Rule int providerRuleId, int providerId, int evaluationOrder,
			int questionId, String questiontextEN, String ruleOptionIds, String ruleOptionTextEN
		 */
		try {

			List<DTProviderRuleModel> dtProviderRuleModelList = new ArrayList<>();
			dtProviderRuleModelList.add(getCloudProviderRule(1, 1, 1, 1, "q1", "1", "a"));
			dtProviderRuleModelList.add(getCloudProviderRule(2, 2, 2, 2, "q2", "2", "b"));

			Mockito.when(dtProviderRuleService.saveCloudProviderRule(dtProviderRuleModelList)).thenReturn(true);
			assertEquals(true, dtProviderRuleService.saveCloudProviderRule(dtProviderRuleModelList)); // CHECK GIVEN MIGRATION IS
																								// SUCCESSFULLY SAVING
		} catch (Exception e) {
			
		}

		/*
		 * Saving Application
		 */

		saveApplication();

		/*
		 * Saving Answer answerId, applicationId, dtCloudableRuleResult,
		 * dtMigrationRuleResult, dtProviderRuleResult, modifiedBy, optionIds,
		 * optionTextEn, questionId, questionText
		 */

		List<AnswerModel> answerModels = new ArrayList<>();
		answerModels.add(getAnswerModel(1, 1, false, false, false, "Admin", "1", "a", 1, "q1"));
		answerModels.add(getAnswerModel(2, 1, false, false, false, "Admin", "2", "b", 2, "q2"));
		result = assessmentService.saveAnswers(answerModels, 1);
		assertTrue(result); // check answers are saving in database or not
		assertEquals(2, assessmentService.getAnswers(1).size()); // check size of answers in database
		answerModels = assessmentService.getAnswers(1); // get answers in list
		assertEquals(1, answerModels.get(0).getAnswerId()); // check dt cloudable result

		/*
		 * Call finalize for migration rule id 1 public pass
		 */
		Application application = assessmentDao.getApplicationByApplicationId(1);
		try {
			assessmentService.finalized(answerModels, 1, 2);
		} catch (Exception e) {
		}

		application = assessmentDao.getApplicationByApplicationId(1);
		assertEquals(2, application.getAssessmentStage()); // check application stage
		assertEquals("GITC", assessmentDao.getApplicationByApplicationId(1).getDtCloudProvider());
		
	}
	

	

	/*
	 * Public Pass
	 * */
 	@Ignore
 	@Test
	public void testBStage2MigrationRulePublicPass() throws SystemExceptions, OptionTextNotNullException {
		boolean result = false;
		saveQuestions();
		saveMigrationPattern();

		/*
		 * Saving Migration Rule executionOrder, migrationId, questionId,
		 * questiontextEN, ruleOptionIds, optionTextEN
		 */
		try {

			List<DTMigrationRuleModel> migrationRuleList = new ArrayList<>();
			migrationRuleList.add(toGetMigrationRule(1, 1, 1, "q1", "1,2", "java,c"));
			migrationRuleList.add(toGetMigrationRule(2, 2, 2, "q2", "2", "b"));
			migrationRuleList.add(toGetMigrationRule(3, 3, 3, "q3", "3", "c"));

			Mockito.when(dtMigrationRuleService.saveMigrationRule(migrationRuleList)).thenReturn(true);
			assertEquals(true, dtMigrationRuleService.saveMigrationRule(migrationRuleList)); // CHECK GIVEN MIGRATION IS
																								// SUCCESSFULLY SAVING
																								// OR NOT

		} catch (Exception e) {

		}

		/*
		 * Saving Application
		 */

		saveApplication();

		/*
		 * Saving Answer answerId, applicationId, dtCloudableRuleResult,
		 * dtMigrationRuleResult, dtProviderRuleResult, modifiedBy, optionIds,
		 * optionTextEn, questionId, questionText
		 */

		List<AnswerModel> answerModels = new ArrayList<>();
		answerModels.add(getAnswerModel(1, 1, false, false, false, "Admin", "1,2", "java,c", 1, "q1"));
		answerModels.add(getAnswerModel(2, 1, false, false, false, "Admin", "2", "b", 2, "q2"));
		answerModels.add(getAnswerModel(3, 1, false, false, false, "Admin", "3", "c", 3, "q3"));
		result = assessmentService.saveAnswers(answerModels, 1);
		assertTrue(result); // check answers are saving in database or not
		assertEquals(3, assessmentService.getAnswers(1).size()); // check size of answers in database
		answerModels = assessmentService.getAnswers(1); // get answers in list
		assertEquals(1, answerModels.get(0).getAnswerId()); // check dt cloudable result

		/*
		 * Call finalize for migration rule id 1 public pass
		 */
		Application application = assessmentDao.getApplicationByApplicationId(1);
		try {
			assessmentService.finalized(answerModels, 1, 2);
		} catch (Exception e) {
		}

		application = assessmentDao.getApplicationByApplicationId(1);
		assertEquals(2, application.getAssessmentStage()); // check application stage
		assertEquals("Public- Pass", assessmentDao.getApplicationByApplicationId(1).getDtMigrationPattern());
		//System.out.println(assessmentService);
		//assertEquals(true, answerModels.get(0).isDtMigrationRuleResult());
	}
	
	/*
 	 * Rehost
 	 * */
 	
	@Test
	public void testBStage2MigrationRuleRehost() throws JcatExceptions {
		Date date = new Date();
		boolean result = true;
		saveQuestions();
		saveMigrationPattern();
		
		/*
		 * Saving Migration Rule executionOrder, migrationId, questionId,
		 * questiontextEN, ruleOptionIds, optionTextEN
		 */
		try {

			List<DTMigrationRuleModel> migrationRuleList = new ArrayList<>();
			migrationRuleList.add(toGetMigrationRule(1, 1, 1, "q1", "1,2,3", "a,b,c"));
			migrationRuleList.add(toGetMigrationRule(2, 2, 2, "q2", "2", "b"));
			migrationRuleList.add(toGetMigrationRule(3, 2, 3, "q3", "3", "c"));
			Mockito.when(dtMigrationRuleService.saveMigrationRule(migrationRuleList)).thenReturn(true);
			assertEquals(true, dtMigrationRuleService.saveMigrationRule(migrationRuleList)); // CHECK GIVEN MIGRATION IS
				System.out.println(dtMigrationRuleService.getMigrationRule(0));																				// SUCCESSFULLY SAVING
																								// OR NOT
		} catch (Exception e) {

		}
		
		//saveApplication();

		/*
		 * Saving Answer answerId, applicationId, dtCloudableRuleResult,
		 * dtMigrationRuleResult, dtProviderRuleResult, modifiedBy, optionIds,
		 * optionTextEn, questionId, questionText
		 */

		List<AnswerModel> answerModels = new ArrayList<>();
		answerModels.add(getAnswerModel(1, 1, false, false, false, "Admin", "1", "a", 1, "q1"));
		answerModels.add(getAnswerModel(2, 1, false, false, false, "Admin", "2", "b", 2, "q2"));
		answerModels.add(getAnswerModel(3, 1, false, false, false, "Admin", "3", "c", 3, "q3"));
		result = assessmentService.saveAnswers(answerModels, 1);
		assertTrue(result); // check answers are saving in database or not
		assertEquals(3, assessmentService.getAnswers(1).size()); // check size of answers in database
		answerModels = assessmentService.getAnswers(1); // get answers in list
		assertEquals(1, answerModels.get(0).getAnswerId()); // check dt cloudable result
		/*
		 * Call finalize for migration rule id 1 public pass
		 */
		Application application = assessmentDao.getApplicationByApplicationId(1);
		try {
			assessmentService.finalized(answerModels, 1, 2);
		} catch (Exception e) {
		}

		application = assessmentDao.getApplicationByApplicationId(1);
		assertEquals(2, application.getAssessmentStage()); // check application stage
		assertEquals("Rehost", assessmentDao.getApplicationByApplicationId(1).getDtMigrationPattern());
	}
	


	/*
	 * Re-Plateform
	 * */
	
	@Test
	public void testBStage2MigrationRuleRePlateform() throws JcatExceptions {
		boolean result = false;
		Date date = new Date();
		saveQuestions();
		saveMigrationPattern();
		/*
		 * Saving Migration Rule executionOrder, migrationId, questionId,
		 * questiontextEN, ruleOptionIds, optionTextEN
		 */
		try {

			List<DTMigrationRuleModel> migrationRuleList = new ArrayList<>();
			migrationRuleList.add(toGetMigrationRule(1, 1, 1, "q1", "1,2,3", "a,b,c"));
			migrationRuleList.add(toGetMigrationRule(2, 1, 2, "q2", "2", "b"));
			migrationRuleList.add(toGetMigrationRule(3, 2, 3, "q3", "3", "c"));
			Mockito.when(dtMigrationRuleService.saveMigrationRule(migrationRuleList)).thenReturn(true);
			assertEquals(true, dtMigrationRuleService.saveMigrationRule(migrationRuleList)); // CHECK GIVEN MIGRATION IS
				System.out.println(dtMigrationRuleService.getMigrationRule(0));																				// SUCCESSFULLY SAVING
																								// OR NOT
		} catch (Exception e) {

		}
		
		System.out.println(dtMigrationRuleService.getMigrationRule(0));

		/*
		 * Saving Application
		 */
		
		//saveApplication();
		
		/*
		 * Saving Answer answerId, applicationId, dtCloudableRuleResult,
		 * dtMigrationRuleResult, dtProviderRuleResult, modifiedBy, optionIds,
		 * optionTextEn, questionId, questionText
		 */

		List<AnswerModel> answerModels = new ArrayList<>();
		answerModels.add(getAnswerModel(1, 1, false, false, false, "Admin", "1", "a", 1, "q1"));
		answerModels.add(getAnswerModel(2, 1, false, false, false, "Admin", "2", "b", 2, "q2"));
		answerModels.add(getAnswerModel(3, 1, false, false, false, "Admin", "3,4,5", "c,d,e", 3, "q3"));
		result = assessmentService.saveAnswers(answerModels, 1);
		assertTrue(result); // check answers are saving in database or not
		assertEquals(3, assessmentService.getAnswers(1).size()); // check size of answers in database
		answerModels = assessmentService.getAnswers(1); // get answers in list
		assertEquals(1, answerModels.get(0).getAnswerId()); // check dt cloudable result
		

		/*
		 * Call finalize for migration rule id 1 public pass
		 */
		Application application = assessmentDao.getApplicationByApplicationId(1);
		try {
			assessmentService.finalized(answerModels, 1, 2);
		} catch (Exception e) {
		}

		application = assessmentDao.getApplicationByApplicationId(1);
		assertEquals(2, application.getAssessmentStage()); // check application stage
		assertEquals("Re-Plateform", assessmentDao.getApplicationByApplicationId(1).getDtMigrationPattern());
	}
	
	
 	private void saveMigrationPattern() {
		/*
		 * Migration pattern save
		 * createdBy, createdTime, evaluationOrder,logicalOperator,
		 * migrationPattern,migrationId,modifiedBy
		 */
		DTMigration dtMigration = getMigrationPattern("Admin", date, 1, "AND", "Public- Pass", 1, "Admin");
		dtMigrationRepository.save(dtMigration);
		dtMigration = getMigrationPattern("Admin", date, 1, "OR", "Rehost", 2, "Admin");
		dtMigrationRepository.save(dtMigration);
		dtMigration = getMigrationPattern("Admin", date, 1, "Others", "Re-Plateform", 3, "Admin");
		dtMigrationRepository.save(dtMigration);
	}
	
	private void saveApplication() throws SystemExceptions {
		/*
		 * Saving Application
		 */
		ApplicationModel applicationModel = new ApplicationModel();
		applicationModel.setAid(1);
		applicationModel.setApplicationId("App");
		applicationModel.setAssessmentStage(1);
		applicationService.save(applicationModel);
	}
	
	private DTProviders saveProvider(int providerId, String providerName, String createdBy, Date createdTime, int evaluationOrder,String logicalOperator,
			String modifiedBy,Date modifiedTime ) {
			DTProviders dtProviders = new DTProviders();
			dtProviders.setCreatedBy(createdBy);
			dtProviders.setCreatedTime(createdTime);
			dtProviders.setEvaluationOrder(evaluationOrder);
			dtProviders.setLogicalOperator(logicalOperator);
			dtProviders.setModifiedBy(modifiedBy);
			dtProviders.setModifiedTime(modifiedTime);
			dtProviders.setProviderId(providerId);
			dtProviders.setProviderName(providerName);
			return dtProviders;
	}
	
	private void saveQuestions()
	{
		/*
		 * Saving Assessment Questions
		 */
		boolean result = true;
		result = assessmentQuestionService.saveQuestions(getAssessmentQuestions());
		AssessmentQuestionModel assessmentQuestionModel = new AssessmentQuestionModel();
		assessmentQuestionModel = getAssessmentQuestions();
		assessmentQuestionModel.setQuestionId(2);
		assessmentQuestionModel.setQuestionTextEN("q2");
		result = false;
		result = assessmentQuestionService.saveQuestions(assessmentQuestionModel);
		assessmentQuestionModel = getAssessmentQuestions();
		assessmentQuestionModel.setQuestionId(3);
		assessmentQuestionModel.setQuestionTextEN("q3");
		result = false;
		result = assessmentQuestionService.saveQuestions(assessmentQuestionModel);
		assertEquals(true, result);

	}

	/*
	 * Setter for answers
	 */
	private AnswerModel getAnswerModel(int answerId, int applicationId, boolean dtCloudableRuleResult,
			boolean dtMigrationRuleResult, boolean dtProviderRuleResult, String modifiedBy, String optionIds,
			String optionTextEn, int questionId, String questionText) {
		AnswerModel answerModel = new AnswerModel();
		answerModel.setAnswerId(answerId);
		answerModel.setApplicationId(applicationId);
		answerModel.setDtCloudableRuleResult(dtCloudableRuleResult);
		answerModel.setDtMigrationRuleResult(dtMigrationRuleResult);
		answerModel.setDtProviderRuleResult(dtProviderRuleResult);
		answerModel.setModifiedBy(modifiedBy);
		answerModel.setOptionIds(optionIds);
		answerModel.setOptionTextsEN(optionTextEn);
		answerModel.setQuestionId(questionId);
		answerModel.setQuestionTextEN(questionText);
		return answerModel;
	}

	/*
	 * Setter for Assessment Questions
	 */
	AssessmentQuestionModel getAssessmentQuestions() {
		Date date = new Date();
		AssessmentQuestionModel assessmentQuestionModel = new AssessmentQuestionModel();
		assessmentQuestionModel.setAssessmentTypeForCloudable(true);
		assessmentQuestionModel.setAssessmentTypeForCloudProvider(true);
		assessmentQuestionModel.setAssessmentTypeForMigration(true);
		assessmentQuestionModel.setCreatedBy("Admin");
		assessmentQuestionModel.setDeleted(false);
		assessmentQuestionModel.setDisplayOrder(2);
		assessmentQuestionModel.setModifiedBy("Admin");
		assessmentQuestionModel.setQuestionDescriptionEN("e1");
		assessmentQuestionModel.setQuestionDescriptionLang2("e2");
		assessmentQuestionModel.setQuestionId(1);
		assessmentQuestionModel.setQuestionTextEN("q1");
		assessmentQuestionModel.setQuestionTextLang2("e1");
		assessmentQuestionModel.setCreatedTime(date);
		assessmentQuestionModel.setQuestionType(QuestionTypeEnum.LONG_ANSWER);
		assessmentQuestionModel.setNumberOfOptions(2);
		return assessmentQuestionModel;
	}

	/*
	 * List of cloudable rule
	 */
	public List<DTCloudableRuleModel> getCloudableRuleModel() {
		List<DTCloudableRuleModel> dtCloudableRuleModelist = new ArrayList<DTCloudableRuleModel>();
		DTCloudableRuleModel dtCloudableRuleModel = new DTCloudableRuleModel();
		dtCloudableRuleModel.setOptionIds("1");
		dtCloudableRuleModel.setOptionTextsEN("a");
		dtCloudableRuleModel.setQuestionId(1);
		dtCloudableRuleModel.setQuestionTextEN("q1");
		dtCloudableRuleModel.setExecutionOrder(1);
		dtCloudableRuleModelist.add(dtCloudableRuleModel);
		return dtCloudableRuleModelist;
	}

	/*
	 * Get migration rule
	 */
	private DTMigrationRuleModel toGetMigrationRule(int executionOrder, int migrationId, int questionId,
			String questiontextEN, String ruleOptionIds, String optionTextEN) {

		DTMigrationRuleModel dtMigrationRuleModel = new DTMigrationRuleModel();
		dtMigrationRuleModel.setExecutionOrder(executionOrder);
		dtMigrationRuleModel.setMigrationId(migrationId);
		dtMigrationRuleModel.setQuestionId(questionId);
		dtMigrationRuleModel.setQuestiontextEN(questiontextEN);
		dtMigrationRuleModel.setRuleOptionIds(ruleOptionIds);
		dtMigrationRuleModel.setRuleOptionTextEN(optionTextEN);
		return dtMigrationRuleModel;
	}

	private DTMigration getMigrationPattern(String createdBy, Date createdTime, int evaluationOrder,
			String logicalOperator, String migrationPattern, int migrationId, String modifiedBy) {
		Date date = new Date();
		DTMigration dtMigration = new DTMigration();
		dtMigration.setCreatedBy(createdBy);
		dtMigration.setCreatedTtime(createdTime);
		dtMigration.setEvaluationOrder(evaluationOrder);
		dtMigration.setLogicalOperator(logicalOperator);
		dtMigration.setMigration_pattern(migrationPattern);
		dtMigration.setMigrationId(migrationId);
		dtMigration.setModifiedBy(modifiedBy);
		dtMigrationRepository.save(dtMigration);
		return dtMigration;
	}
	
	private DTProviderRuleModel getCloudProviderRule(int providerRuleId, int providerId, int evaluationOrder,
			int questionId, String questiontextEN, String ruleOptionIds, String ruleOptionTextEN)
	{
		DTProviderRuleModel dtProviderRuleModel = new DTProviderRuleModel();
		dtProviderRuleModel.setProviderRuleId(providerRuleId);
		dtProviderRuleModel.setProviderId(providerId);
		dtProviderRuleModel.setEvaluationOrder(evaluationOrder);
		dtProviderRuleModel.setQuestionId(questionId);
		dtProviderRuleModel.setQuestiontextEN(questiontextEN);
		dtProviderRuleModel.setRuleOptionIds(ruleOptionIds);
		dtProviderRuleModel.setRuleOptionTextEN(ruleOptionTextEN);
		return dtProviderRuleModel;
	}

}
