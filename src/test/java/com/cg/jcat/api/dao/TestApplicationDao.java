package com.cg.jcat.api.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.cg.jcat.api.entity.Application;
import com.cg.jcat.api.exception.ApplicationIdNotFoundException;
import com.cg.jcat.api.exception.SystemExceptions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TestApplicationDao {
	@Autowired
	private ApplicationDao applicationdao;

	@Test
	// @Ignore
	public void tesBGetApplication() throws SystemExceptions {

		// ApplicationModel applicationModel = getApplicationModel();
		// assertEquals(true,applicationdao.save(applicationModel));
		assertEquals(1, applicationdao.getApplications().size());

	}

	@Test
	// @Ignore
	public void testASave() throws SystemExceptions {
		ApplicationModel applicationModel = getApplicationModel();
		assertEquals(true, applicationdao.save(applicationModel));
	}

	private ApplicationModel getApplicationModel() {
		ApplicationModel appLicationModel = new ApplicationModel();
		appLicationModel.setApplicationId("App1");
		appLicationModel.setApplicationName("Application1");
		appLicationModel.setApplicationDescription("To check cloudable or not");
		appLicationModel.setApplicationDepartment("vg");
		appLicationModel.setApplicationUser(1);
		appLicationModel.setDtCloudProvider("GITC");
		appLicationModel.setDtMigrationPattern("Rehost");
		appLicationModel.setPriority(5);
		appLicationModel.setReCloudProvider("AWS");
		appLicationModel.setAssessmentStage(1);

		return appLicationModel;
	}

	@Test
	// @Ignore
	public void testCGetApplicationById() throws SystemExceptions, ApplicationIdNotFoundException {
		ApplicationModel applicationModel = getApplicationModel();
		// assertEquals(true,applicationdao.save(applicationModel));
		ApplicationModel applicationModel2 = applicationdao.getApplicationByApplicationId("App1");
		assertEquals(applicationModel.getApplicationName(), applicationModel2.getApplicationName());
		assertEquals(applicationModel.getApplicationDepartment(), applicationModel2.getApplicationDepartment());

	}

	@Test
	// @Ignore
	public void testDDeleteApplicationById() throws SystemExceptions, ApplicationIdNotFoundException {
		ApplicationModel applicationModel = getApplicationModel();
		// assertEquals(true,applicationdao.save(applicationModel));
		assertEquals(true, applicationdao.deleteApplicationById(1));
		Application application = applicationdao.findApplicationById(1);
		assertEquals(true, application.isDeleted());
		assertTrue(application.isDeleted());
	}

	@Test
	// @Ignore
	public void testEDeactivateApplicationById() throws SystemExceptions, ApplicationIdNotFoundException {
		ApplicationModel applicationModel = getApplicationModel();
		// assertEquals(true,applicationdao.save(applicationModel));
		assertEquals(true, applicationdao.deactivateApplicationById(1));
		Application application = applicationdao.findApplicationById(1);
		assertEquals(false, application.isActivate());
		assertFalse(application.isActivate());
	}

	@Test
	public void testUpdateApplication() throws SystemExceptions, ApplicationIdNotFoundException {
		ApplicationModel applicationModel = getApplicationModel();
		assertEquals(true, applicationdao.save(applicationModel));
		ApplicationModel applicationModel1 = applicationdao.getApplicationByApplicationId("App1");
		System.out.println(applicationModel1);
		applicationModel1.setApplicationId("App2");
		applicationModel1.setApplicationName("App2");
		applicationModel1.setApplicationDepartment("Application2 Description");
		System.out.println(applicationModel1);
		assertEquals(true, applicationdao.updateApplication(applicationModel1));

		ApplicationModel applicationModel2 = applicationdao.getApplicationByApplicationId("App2");
		assertEquals("App2", applicationModel2.getApplicationName());
		assertEquals("Application2 Description", applicationModel2.getApplicationDepartment());

	}

}
