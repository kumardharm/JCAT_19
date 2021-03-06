package com.cg.jcat.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.jcat.api.dao.ApplicationModel;
import com.cg.jcat.api.entity.ApplicationStaging;
import com.cg.jcat.api.exception.ApplicationIdNotFoundException;
import com.cg.jcat.api.exception.SystemExceptions;

@Service
public interface IApplicationService {

	public List<ApplicationModel> getApplications();
	
	public boolean save(ApplicationModel application) throws SystemExceptions;
	
	public ApplicationModel getApplicationByApplicationId(String aid) throws ApplicationIdNotFoundException;
	
	public boolean deleteApplicationById(int aid)  throws ApplicationIdNotFoundException, SystemExceptions;

	public boolean deactivateApplicationById(int aid) throws ApplicationIdNotFoundException, SystemExceptions;

	public boolean updateApplication(ApplicationModel application) throws ApplicationIdNotFoundException, SystemExceptions;

//	public void create(List<ApplicationModel> users);

	public void importfile(List<ApplicationStaging> applicationStaging);


}
