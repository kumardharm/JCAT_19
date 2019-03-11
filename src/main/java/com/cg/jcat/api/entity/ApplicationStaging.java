package com.cg.jcat.api.entity;

public class ApplicationStaging {
	
	private String applicationId;
	private String applicationName;
	private String applicationDescription;
	private String applicationDepartment;
	private int priority;
	private String userName;
	private boolean isDTCloudable;
	private String dtMigrationPattern;
	private String dtCloudProvider;
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getApplicationDescription() {
		return applicationDescription;
	}
	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}
	public String getApplicationDepartment() {
		return applicationDepartment;
	}
	public void setApplicationDepartment(String applicationDepartment) {
		this.applicationDepartment = applicationDepartment;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isDTCloudable() {
		return isDTCloudable;
	}
	public void setDTCloudable(boolean isDTCloudable) {
		this.isDTCloudable = isDTCloudable;
	}
	public String getDtMigrationPattern() {
		return dtMigrationPattern;
	}
	public void setDtMigrationPattern(String dtMigrationPattern) {
		this.dtMigrationPattern = dtMigrationPattern;
	}
	public String getDtCloudProvider() {
		return dtCloudProvider;
	}
	public void setDtCloudProvider(String dtCloudProvider) {
		this.dtCloudProvider = dtCloudProvider;
	}
	@Override
	public String toString() {
		return "ApplicationStaging [applicationId=" + applicationId + ", applicationName=" + applicationName
				+ ", applicationDescription=" + applicationDescription + ", applicationDepartment="
				+ applicationDepartment + ", priority=" + priority + ", userName=" + userName + ", isDTCloudable="
				+ isDTCloudable + ", dtMigrationPattern=" + dtMigrationPattern + ", dtCloudProvider=" + dtCloudProvider
				+ "]";
	}
	
}
