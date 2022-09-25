package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class RuleDetailResponseTO {

    private Integer relationId;
    private Integer ruleId;
    private Integer employeeCommentId;
    private String ruleSubClauseNum;
    private String ruleTitle;
    private String ruleResponsibilty;
    private String ruleDescription;
    private String ruleRelevantCircular;
    private String circularAttachName;
    private String managementComment;
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

    public String getCircularAttachName() {
        return circularAttachName;
    }

    public void setCircularAttachName(String circularAttachName) {
        this.circularAttachName = circularAttachName;
    }

    public List<RelevantCircularResponseTO> getRelevantCircularList() {
        return relevantCircularList;
    }

    public void setRelevantCircularList(List<RelevantCircularResponseTO> relevantCircularList) {
        this.relevantCircularList = relevantCircularList;
    }

}
