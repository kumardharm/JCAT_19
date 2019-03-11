package com.cg.jcat.api.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.jcat.api.entity.Application;
import com.cg.jcat.api.entity.ApplicationStaging;
import com.cg.jcat.api.entity.ApplicationsHistory;
import com.cg.jcat.api.entity.User;
import com.cg.jcat.api.exception.ApplicationIdNotFoundException;
import com.cg.jcat.api.exception.SystemExceptions;
import com.cg.jcat.api.repository.IApplicationRepository;
import com.cg.jcat.api.repository.IApplicationsHistoryRepository;
import com.cg.jcat.api.repository.IUserRepository;

@Component
public class ApplicationDao {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationDao.class);
	
	@Autowired
	IApplicationRepository applicationRepository;
	
	@Autowired
	IApplicationsHistoryRepository applicationHistoryRepository;
	
	@Autowired
	IUserRepository iUserRepository;
	
	@Autowired
	UserDao userDao;
	
	Date date = new Date();
	private boolean isActivate = true;
	private boolean isDeleted = false;
	
	public List<ApplicationModel> getApplications() {
		
		List<Application> applicationList=applicationRepository.findAllByIsActivateOrderByApplicationName(isActivate);
		List<ApplicationModel> applicationDaoList=new ArrayList<ApplicationModel>();
		return getApplication(applicationList,applicationDaoList);
	}
	private List<ApplicationModel> getApplication(List<Application> applicationList,List<ApplicationModel> applicationDaoList) {
		for(Application application :applicationList) {
			applicationDaoList.add(toGetApplication(application));
		}
		return applicationDaoList;
	}
	private ApplicationModel toGetApplication(Application application)
	{
		ApplicationModel appLicationModel=new ApplicationModel();
		appLicationModel.setAid(application.getAid());
		appLicationModel.setApplicationId(application.getApplicationId());
		appLicationModel.setApplicationName(application.getApplicationName());
		appLicationModel.setApplicationDescription(application.getApplicationDescription());
		appLicationModel.setApplicationDepartment(application.getApplicationDepartment());
		appLicationModel.setApplicationUser(application.getApplicationUser());
		appLicationModel.setDtCloudProvider(application.getDtCloudProvider());
		appLicationModel.setDtMigrationPattern(application.getDtMigrationPattern());
		appLicationModel.setPriority(application.getPriority());
		appLicationModel.setReCloudProvider(application.getReCloudProvider());
		appLicationModel.setReMigrationPattern(application.getReMigrationPattern());
		appLicationModel.setAssessmentStage(application.getAssessmentStage());
		
		return appLicationModel;
	}
	
	public boolean save(ApplicationModel applicationModel) throws SystemExceptions {
		Application application = new Application();
		boolean afterSave = false;
		try {
			application = saveApplication(applicationModel);
			if(application!=null)
			{
				afterSave = true;
			}
				return afterSave;
		}catch (Exception e) {
			logger.error("Error in Saving Applications ",e.getMessage());
			throw new SystemExceptions("Error in Application save()");
		}
	}
	
	private Application saveApplication(ApplicationModel applicationModel) {
		 return applicationRepository.save(toApplication(applicationModel));
	}
	
    private Application toApplication(ApplicationModel applicationModel) {
    	Application application=new Application();
    	application.setApplicationId(applicationModel.getApplicationId());
    	application.setApplicationName(applicationModel.getApplicationName());
    	application.setApplicationDescription(applicationModel.getApplicationDescription());
    	application.setApplicationDepartment(applicationModel.getApplicationDepartment());
    	application.setPriority(applicationModel.getPriority());
    	application.setApplicationUser(applicationModel.getApplicationUser());
    	application.setAssessmentStage(applicationModel.getAssessmentStage());
    	application.setReCloudable(applicationModel.isRECloudable());
    	application.setReCloudProvider(applicationModel.getReCloudProvider());
    	application.setReMigrationPattern(applicationModel.getReMigrationPattern());
    	application.setCreatedBy("Admin");
    	application.setCreatedTime(date);
    	return application;
    }
	public ApplicationModel getApplicationByApplicationId(String aid) throws ApplicationIdNotFoundException{
		try {
		Application application = applicationRepository.findByApplicationId(aid);
		ApplicationModel applicationModel = toGetApplication(application);
		return applicationModel;
		}catch (Exception e) {
			logger.error("Error in getApplicationByApplicationId() application id-"+"application id doesn't exists",e.getMessage());
			throw new ApplicationIdNotFoundException("Error in getApplicationByApplicationId()");
		}
	}
	
	public Application findApplicationById(int applicationId) throws ApplicationIdNotFoundException
	{
		Application application = new Application();
		try {
	    Optional<Application> optApplication = applicationRepository.findById(applicationId);
	    application = optApplication.get();
	    return application;
		}catch (Exception e) {
			logger.error("Error in findApplicationById() application id-"+"aid doesn't exists",e.getMessage());
			throw new ApplicationIdNotFoundException("Error in findApplicationById()");
		}
		
	}
	
	public boolean deleteApplicationById(int aid) throws ApplicationIdNotFoundException, SystemExceptions {
		Application application = findApplicationById(aid);
		boolean deleteApplication = false;
		try
		{
		application.setDeleted(true);
		application.setModifiedBy("Admin");
		application.setModifiedTime(date);
		Application saveApplication =  applicationRepository.save(application);
		if(saveApplication!=null)
		{
			deleteApplication = true;
		}
		return deleteApplication;
		}catch (Exception e) {
			System.out.println(e);
			logger.error("Error in deleting application by id- "+ aid +", deleteApplicationById()",e.getMessage());
			throw new SystemExceptions("Error in deleting application by id- "+aid+", deleteApplicationById()");
		}
	}
	public boolean deactivateApplicationById(int aid) throws ApplicationIdNotFoundException, SystemExceptions {
		Application application = findApplicationById(aid);
		boolean deactivateApplication = false;
//		ApplicationsHistory applicationsHistory = new ApplicationsHistory();
		try
		{
			application.setActivate(false);
			application.setModifiedBy("Admin");
			application.setModifiedTime(date);
//			applicationsHistory = applicationHistoryRepository.save(toApplicationHistory(application));
			Application saveApplication =  applicationRepository.save(application);
			if(saveApplication!=null)
			{
				deactivateApplication = true;
			}
			return deactivateApplication;
		}catch (Exception e) {
			logger.error("Error in deactivate application by id- "+ aid +", deactivateApplicationById()",e.getMessage());
			throw new SystemExceptions("Error in deactivate application by id - "+aid+", deleteApplicationById()");
		}
		
	}
	public boolean updateApplication(ApplicationModel applicationModel) throws SystemExceptions {
		Application application = toApplication(applicationModel);
		boolean afterSave = false;
		ApplicationsHistory applicationsHistory = new ApplicationsHistory();
		try
		{
			application.setAid(applicationModel.getAid());
			application.setApplicationId(applicationModel.getApplicationId());
			application.setModifiedBy("Admin");
			application.setModifiedTime(date);
			applicationsHistory = applicationHistoryRepository.save(toApplicationHistory(application));
			Application saveApplication =  applicationRepository.saveAndFlush(application);
			if(saveApplication!=null && applicationsHistory!=null)
			{
				afterSave = true;
			}
		}catch (Exception e) {
			System.out.println(e);
			logger.error("Error in updating application, updateApplication()",e.getMessage());
			throw new SystemExceptions("Error in updating application, updateApplication()");
		}
		return afterSave;
	}
	private ApplicationsHistory toApplicationHistory(Application application) {
		ApplicationsHistory applicationsHistory = new ApplicationsHistory();
		applicationsHistory.setAid(application.getAid());
		applicationsHistory.setApplicationId(application.getApplicationId());
		applicationsHistory.setApplicationName(application.getApplicationName());
		applicationsHistory.setApplicationDescription(application.getApplicationDescription());
		applicationsHistory.setApplicationDepartment(application.getApplicationDepartment());
		applicationsHistory.setPriority(application.getPriority());
		applicationsHistory.setApplicationUser(application.getApplicationUser());
		applicationsHistory.setDeleted(application.isDeleted());
		applicationsHistory.setActivate(application.isActivate());
		applicationsHistory.setAssessmentStage(application.getAssessmentStage());
		applicationsHistory.setAssessmentCompleted(application.isAssessmentCompleted());
		applicationsHistory.setAssessmentDoneTime(application.getAssessmentCompletionTime());
		applicationsHistory.setDtCloudable(application.isDTCloudable());
		applicationsHistory.setReCloudable(application.isDTCloudable());
		applicationsHistory.setDtCloudProvider(application.getDtCloudProvider());
		applicationsHistory.setReCloudProvider(application.getReCloudProvider());
		applicationsHistory.setDtMigrationPattern(application.getDtMigrationPattern());
		applicationsHistory.setReMigrationPattern(application.getReMigrationPattern());
		applicationsHistory.setCreatedBy(application.getCreatedBy());
		applicationsHistory.setCreatedTime(application.getCreatedTime());
		applicationsHistory.setModifiedBy(application.getModifiedBy());
		applicationsHistory.setModifiedTime(application.getModifiedTime());
		
		return applicationsHistory;
	}
//	public void create(List<ApplicationModel> applicationModel) {
//		for(ApplicationModel app: applicationModel)
//		{
//			try {
//				save(app);
//			} catch (SystemExceptions e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("************");
//	}
	
	public void importfile(List<ApplicationStaging> applicationStaging) {
 
		User user = new User();
		Application application = new Application();
		for(ApplicationStaging applications:applicationStaging)
		{
			user = iUserRepository.findByUsername(applications.getUserName());
			if(user!=null)
			{
				application=applicationRepository.save(toapplication(applications,user));
			}
			else
			{
				user = userDao.create(applications.getUserName());
				application=applicationRepository.save(toapplication(applications,user));
			}
			if(application!=null)
			{
				System.out.println("&&&&&&&&&&&&&&&&&&&&");
				System.out.println(applicationStaging);
			}
		}
		
	}
private Application toapplication(ApplicationStaging applications, User user) {
	Application application=new Application();
	application.setApplicationId(applications.getApplicationId());
	application.setApplicationName(applications.getApplicationName());
	application.setApplicationDescription(applications.getApplicationDescription());
	application.setApplicationDepartment(applications.getApplicationDepartment());
	application.setPriority(applications.getPriority());
	application.setApplicationUser(user.getUserId());
	application.setDTCloudable(applications.isDTCloudable());
	application.setDtCloudProvider(applications.getDtCloudProvider());
	application.setDtMigrationPattern(applications.getDtMigrationPattern());
	application.setCreatedBy("Admin");
	application.setCreatedTime(date);
	return application;
}
}
