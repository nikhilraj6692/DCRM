package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewerPreviewDetailsResponseTO {

    private Integer projectId;
    private String comapnyName;
    private String projectName;
    private Integer bookId;
    private String bookName;
    private Integer ruleId;
    private String ruleTitle;
    private String ruleSubclauseNum;
    private String ruleDescription;
    private String ruleResponsibilty;
    private String ruleRelevantCircular;
    private String circularAttacmentName;
    private String circularAttacmentBaseURL;
    private Integer employeeCommentId;
    private String managementComment;
    private String managementCommentedDate;
    private Integer reviewerConformityStatus;
    private Integer reviewerConformityLevel;
    private String reviewerRemark;
    private List<String> reviewerImageList = new ArrayList<String>();
    private String reviewerRemarkedDate;
    private String leadReviewerRemark;
    private String leadReviewerRemarkedDate;
    private String managementName;
    private String reviewerName;
    private Date projectSubmittedDate;
    private String projectUniqueId;
    private Integer projectVersionNum;
    private String projectLogoName;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleSubclauseNum() {
        return ruleSubclauseNum;
    }

    public void setRuleSubclauseNum(String ruleSubclauseNum) {
        this.ruleSubclauseNum = ruleSubclauseNum;
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

    public Integer getEmployeeCommentId() {
        return employeeCommentId;
    }

    public void setEmployeeCommentId(Integer employeeCommentId) {
        this.employeeCommentId = employeeCommentId;
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

    public Integer getReviewerConformityStatus() {
        return reviewerConformityStatus;
    }

    public void setReviewerConformityStatus(Integer reviewerConformityStatus) {
        this.reviewerConformityStatus = reviewerConformityStatus;
    }

    public Integer getReviewerConformityLevel() {
        return reviewerConformityLevel;
    }

    public void setReviewerConformityLevel(Integer reviewerConformityLevel) {
        this.reviewerConformityLevel = reviewerConformityLevel;
    }

    public String getReviewerRemark() {
        return reviewerRemark;
    }

    public void setReviewerRemark(String reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public List<String> getReviewerImageList() {
        return reviewerImageList;
    }

    public void setReviewerImageList(List<String> reviewerImageList) {
        this.reviewerImageList = reviewerImageList;
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

    public String getComapnyName() {
        return comapnyName;
    }

    public void setComapnyName(String comapnyName) {
        this.comapnyName = comapnyName;
    }

    public String getManagementName() {
        return managementName;
    }

    public void setManagementName(String managementName) {
        this.managementName = managementName;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Date getProjectSubmittedDate() {
        return projectSubmittedDate;
    }

    public void setProjectSubmittedDate(Date projectSubmittedDate) {
        this.projectSubmittedDate = projectSubmittedDate;
    }

    public String getProjectUniqueId() {
        return projectUniqueId;
    }

    public void setProjectUniqueId(String projectUniqueId) {
        this.projectUniqueId = projectUniqueId;
    }

    public Integer getProjectVersionNum() {
        return projectVersionNum;
    }

    public void setProjectVersionNum(Integer projectVersionNum) {
        this.projectVersionNum = projectVersionNum;
    }

    public String getProjectLogoName() {
        return projectLogoName;
    }

    public void setProjectLogoName(String projectLogoName) {
        this.projectLogoName = projectLogoName;
    }

    public String getRuleRelevantCircular() {
        return ruleRelevantCircular;
    }

    public void setRuleRelevantCircular(String ruleRelevantCircular) {
        this.ruleRelevantCircular = ruleRelevantCircular;
    }

    public String getCircularAttacmentName() {
        return circularAttacmentName;
    }

    public void setCircularAttacmentName(String circularAttacmentName) {
        this.circularAttacmentName = circularAttacmentName;
    }

    public String getCircularAttacmentBaseURL() {
        return circularAttacmentBaseURL;
    }

    public void setCircularAttacmentBaseURL(String circularAttacmentBaseURL) {
        this.circularAttacmentBaseURL = circularAttacmentBaseURL;
    }

}
