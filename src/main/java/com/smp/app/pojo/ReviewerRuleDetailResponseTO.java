package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class ReviewerRuleDetailResponseTO {

    private Integer relationId;
    private Integer ruleId;
    private Integer employeeCommentId;
    private String ruleTitle;
    private String ruleSubclauseNum;
    private String ruleDescription;
    private String ruleResponsibilty;
    private String ruleRelevantCircular;
    private String circularAttachmentName;
    private boolean reviewerRemarkNAstatus;
    private String managementComment;
    private String managementCommentedDate;
    private Integer reviewerConformaityStatus;
    private String reviewerConformaityLevel;
    private String reviewerRemark;
    private String reviewerRemarkedDate;
    private String leadReviewerRemark;
    private String leadReviewerRemarkedDate;
    private List<String> reviewerImageList = new ArrayList<String>();
    private List<RelevantCircularResponseTO> relevantCircularList = new ArrayList<RelevantCircularResponseTO>();

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getEmployeeCommentId() {
        return employeeCommentId;
    }

    public void setEmployeeCommentId(Integer employeeCommentId) {
        this.employeeCommentId = employeeCommentId;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String getRuleResponsibilty() {
        return ruleResponsibilty;
    }

    public void setRuleResponsibilty(String ruleResponsibilty) {
        this.ruleResponsibilty = ruleResponsibilty;
    }

    public String getManagementComment() {
        return managementComment;
    }

    public void setManagementComment(String managementComment) {
        this.managementComment = managementComment;
    }

    public String getManagementCommentedDate() {
        return managementCommentedDate;
    }

    public void setManagementCommentedDate(String managementCommentedDate) {
        this.managementCommentedDate = managementCommentedDate;
    }

    public Integer getReviewerConformaityStatus() {
        return reviewerConformaityStatus;
    }

    public void setReviewerConformaityStatus(Integer reviewerConformaityStatus) {
        this.reviewerConformaityStatus = reviewerConformaityStatus;
    }

    public String getReviewerConformaityLevel() {
        return reviewerConformaityLevel;
    }

    public void setReviewerConformaityLevel(String reviewerConformaityLevel) {
        this.reviewerConformaityLevel = reviewerConformaityLevel;
    }

    public String getReviewerRemark() {
        return reviewerRemark;
    }

    public void setReviewerRemark(String reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public String getReviewerRemarkedDate() {
        return reviewerRemarkedDate;
    }

    public void setReviewerRemarkedDate(String reviewerRemarkedDate) {
        this.reviewerRemarkedDate = reviewerRemarkedDate;
    }

    public String getLeadReviewerRemark() {
        return leadReviewerRemark;
    }

    public void setLeadReviewerRemark(String leadReviewerRemark) {
        this.leadReviewerRemark = leadReviewerRemark;
    }

    public String getLeadReviewerRemarkedDate() {
        return leadReviewerRemarkedDate;
    }

    public void setLeadReviewerRemarkedDate(String leadReviewerRemarkedDate) {
        this.leadReviewerRemarkedDate = leadReviewerRemarkedDate;
    }

    public List<String> getReviewerImageList() {
        return reviewerImageList;
    }

    public void setReviewerImageList(List<String> reviewerImageList) {
        this.reviewerImageList = reviewerImageList;
    }

    public String getRuleSubclauseNum() {
        return ruleSubclauseNum;
    }

    public void setRuleSubclauseNum(String ruleSubclauseNum) {
        this.ruleSubclauseNum = ruleSubclauseNum;
    }

    public String getRuleRelevantCircular() {
        return ruleRelevantCircular;
    }

    public void setRuleRelevantCircular(String ruleRelevantCircular) {
        this.ruleRelevantCircular = ruleRelevantCircular;
    }

    public String getCircularAttachmentName() {
        return circularAttachmentName;
    }

    public void setCircularAttachmentName(String circularAttachmentName) {
        this.circularAttachmentName = circularAttachmentName;
    }

    public boolean isReviewerRemarkNAstatus() {
        return reviewerRemarkNAstatus;
    }

    public void setReviewerRemarkNAstatus(boolean reviewerRemarkNAstatus) {
        this.reviewerRemarkNAstatus = reviewerRemarkNAstatus;
    }

    public List<RelevantCircularResponseTO> getRelevantCircularList() {
        return relevantCircularList;
    }

    public void setRelevantCircularList(List<RelevantCircularResponseTO> relevantCircularList) {
        this.relevantCircularList = relevantCircularList;
    }

}
