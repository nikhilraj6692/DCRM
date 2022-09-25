package com.smp.app.pojo;

public class ProjectRulesDaoTO {

    private Integer projectId;
    private String projectName;
    private Integer projectStatus;
    private Integer ruleRelationId;
    private Integer ruleId;
    private String ruleSubClauseNum;
    private String ruleTitle;
    private String ruleResponsibilty;
    private String ruleDescription;
    private String ruleRelevantCircular;
    private boolean circularAttachStatus;
    private String circularAttachName;
    private Integer empCommentId;
    private String managementRemark;


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

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getRuleRelationId() {
        return ruleRelationId;
    }

    public void setRuleRelationId(Integer ruleRelationId) {
        this.ruleRelationId = ruleRelationId;
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

    public Integer getEmpCommentId() {
        return empCommentId;
    }

    public void setEmpCommentId(Integer empCommentId) {
        this.empCommentId = empCommentId;
    }

    public String getManagementRemark() {
        return managementRemark;
    }

    public void setManagementRemark(String managementRemark) {
        this.managementRemark = managementRemark;
    }

    public String getRuleSubClauseNum() {
        return ruleSubClauseNum;
    }

    public void setRuleSubClauseNum(String ruleSubClauseNum) {
        this.ruleSubClauseNum = ruleSubClauseNum;
    }

    public String getRuleRelevantCircular() {
        return ruleRelevantCircular;
    }

    public void setRuleRelevantCircular(String ruleRelevantCircular) {
        this.ruleRelevantCircular = ruleRelevantCircular;
    }

    public boolean isCircularAttachStatus() {
        return circularAttachStatus;
    }

    public void setCircularAttachStatus(boolean circularAttachStatus) {
        this.circularAttachStatus = circularAttachStatus;
    }

    public String getCircularAttachName() {
        return circularAttachName;
    }

    public void setCircularAttachName(String circularAttachName) {
        this.circularAttachName = circularAttachName;
    }


}
