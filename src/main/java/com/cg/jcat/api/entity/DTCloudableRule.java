package com.cg.jcat.api.entity;

import java.util.Date;

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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dt_cloudable_rule")
public class DTCloudableRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cloudableRuleId;
	private int executionOrder;

	@Lob
	@Column(name = "question_text_EN")
	private String questionTextEN;

	@Lob
	@NotNull
	private String optionIds;

	@Lob
	@NotNull
	@Column(name = "option_texts_EN")
	private String optionTextsEN;

	@NotNull
	private String createdBy;

	@NotNull
	private Date createdTime;

	private String modifiedBy;

	private Date modifiedTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionId")
	private AssessmentQuestion assessmentQuestion;

	public int getCloudableRuleId() {
		return cloudableRuleId;
	}

	public void setCloudableRuleId(int cloudableRuleId) {
		this.cloudableRuleId = cloudableRuleId;
	}

	public int getExecutionOrder() {
		return executionOrder;
	}

	public void setExecutionOrder(int executionOrder) {
		this.executionOrder = executionOrder;
	}

	public String getQuestionTextEN() {
		return questionTextEN;
	}

	public void setQuestionTextEN(String questionTextEN) {
		this.questionTextEN = questionTextEN;
	}

	public String getOptionIds() {
		return optionIds;
	}

	public void setOptionIds(String optionIds) {
		this.optionIds = optionIds;
	}

	public String getOptionTextsEN() {
		return optionTextsEN;
	}

	public void setOptionTextsEN(String optionTextsEN) {
		this.optionTextsEN = optionTextsEN;
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

	public AssessmentQuestion getAssessmentQuestion() {
		return assessmentQuestion;
	}

	public void setAssessmentQuestion(AssessmentQuestion assessmentQuestion) {
		this.assessmentQuestion = assessmentQuestion;
	}

	@Override
	public String toString() {
		return "DTCloudableRule [cloudableRuleId=" + cloudableRuleId + ", executionOrder=" + executionOrder
				+ ", questionTextEN=" + questionTextEN + ", optionIds=" + optionIds + ", optionTextsEN=" + optionTextsEN
				+ ", createdBy=" + createdBy + ", createdTime=" + createdTime + ", modifiedBy=" + modifiedBy
				+ ", modifiedTime=" + modifiedTime + "]";
	}

}
