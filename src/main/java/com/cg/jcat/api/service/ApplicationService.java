package com.cg.jcat.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.jcat.api.dao.ApplicationDao;
import com.cg.jcat.api.dao.ApplicationModel;
import com.cg.jcat.api.entity.ApplicationStaging;
import com.cg.jcat.api.exception.ApplicationIdNotFoundException;
import com.cg.jcat.api.exception.SystemExceptions;

@Component
public class ApplicationService implements IApplicationService {

	@Autowired
	ApplicationDao applicationDao;
	
	@Override
	public List<ApplicationModel> getApplications(){
		
		return applicationDao.getApplications();
	}
	
	@Override
	public boolean save(ApplicationModel application) throws SystemExceptions{
		return applicationDao.save(application);
	}

	@Override
	public ApplicationModel getApplicationByApplicationId(String aid) throws ApplicationIdNotFoundException {

		return applicationDao.getApplicationByApplicationId(aid);
	}

	@Override
	public boolean deleteApplicationById(int aid) throws ApplicationIdNotFoundException, SystemExceptions {
		
		return applicationDao.deleteApplicationById(aid);
	}

	@Override
	public boolean deactivateApplicationById(int aid) throws ApplicationIdNotFoundException, SystemExceptions {
		
		return applicationDao.deactivateApplicationById(aid);
	}

	@Override
	public boolean updateApplication(ApplicationModel application) throws ApplicationIdNotFoundException, SystemExceptions {

		return applicationDao.updateApplication(application);
	}

//	@Override
//	public void create(List<ApplicationModel> users) {
//		
//		applicationDao.create(users);
//	}

	@Override
	public void importfile(List<ApplicationStaging> applicationStaging) {
		applicationDao.importfile(applicationStaging);
	}

	

}
