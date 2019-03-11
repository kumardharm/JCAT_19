package com.cg.jcat.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.jcat.api.dao.DTMigrationModel;
import com.cg.jcat.api.dao.DTMigrationRuleModel;
import com.cg.jcat.api.exception.JcatExceptions;
import com.cg.jcat.api.exception.SystemExceptions;
import com.cg.jcat.api.service.IDTMigrationRuleService;

@Component
public class DTMigrationRuleController implements IDTMigrationRuleController{
	
	@Autowired
	IDTMigrationRuleService dtMigrationRuleService;
	
	@Override
	public List<DTMigrationRuleModel> getMigrationRule(int migrationId) {
		try {
			return dtMigrationRuleService.getMigrationRule(migrationId);
		} catch (JcatExceptions e) {
			System.out.println("Error in getting rules" + e);
			return null;
		}
	}
	
	@Override
	public void saveMigrationRule(List<DTMigrationRuleModel> dtMigrationRuleModel) throws SystemExceptions{
		try {
			dtMigrationRuleService.saveMigrationRule(dtMigrationRuleModel);
		} catch (JcatExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<DTMigrationModel> getMigration() {
		return dtMigrationRuleService.getMigrationPattern();
	}

	

}
