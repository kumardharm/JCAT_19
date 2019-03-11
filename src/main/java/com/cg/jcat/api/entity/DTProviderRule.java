package com.cg.jcat.api.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="dt_provider_rule")
public class DTProviderRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="provider_rule_id")
	private int providerRuleId;
		
	@ColumnDefault("0")
	@Column(name="execution_order")
	private int executionOrder;
	
	@Lob
	@Column(name="question_text_EN")
	private String questiontextEN;
	
	@Lob
	@Column(name="rule_option_ids")
	private String ruleOptionIds;
	
	@Lob
	@Column(name="rule_option_text_EN")
	private String ruleOptionTextEN;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_time")
	private Date createdTime;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="modified_time")
	private Date modifiedTime;
	
	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="providerId")
	private DTProviders dtProviders;
	
	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="questionId")
	private AssessmentQuestion assessmentQuestion;

	public int getProviderRuleId() {
		return providerRuleId;
	}
	public void setProviderRuleId(int providerRuleId) {
		this.providerRuleId = providerRuleId;
	}
	
	public int getExecutionOrder() {
		return executionOrder;
	}
	public void setExecutionOrder(int executionOrder) {
		this.executionOrder = executionOrder;
	}
	
	public String getQuestiontextEN() {
		return questiontextEN;
	}
	public void setQuestiontextEN(String questiontextEN) {
		this.questiontextEN = questiontextEN;
	}
	public String getRuleOptionIds() {
		return ruleOptionIds;
	}
	public void setRuleOptionIds(String ruleOptionIds) {
		this.ruleOptionIds = ruleOptionIds;
	}
	public String getRuleOptionTextEN() {
		return ruleOptionTextEN;
	}
	public void setRuleOptionTextEN(String ruleOptionTextEN) {
		this.ruleOptionTextEN = ruleOptionTextEN;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public DTProviders getDtProviders() {
		return dtProviders;
	}
	public void setDtProviders(DTProviders dtProviders) {
		this.dtProviders = dtProviders;
	}
	public AssessmentQuestion getAssessmentQuestion() {
		return assessmentQuestion;
	}
	public void setAssessmentQuestion(AssessmentQuestion assessmentQuestion) {
		this.assessmentQuestion = assessmentQuestion;
	}
	@Override
	public String toString() {
		return "DTProviderRule [providerRuleId=" + providerRuleId + ", executionOrder=" + executionOrder
				+ ", questiontextEN=" + questiontextEN + ", ruleOptionIds=" + ruleOptionIds + ", ruleOptionTextEN="
				+ ruleOptionTextEN + ", createdBy=" + createdBy + ", createdTime=" + createdTime + ", modifiedBy="
				+ modifiedBy + ", modifiedTime=" + modifiedTime + ", dtProviders=" + dtProviders
				+ ", assessmentQuestion=" + assessmentQuestion + "]";
	}
	
	
}
