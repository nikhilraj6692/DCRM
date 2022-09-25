package com.smp.app.pojo;

public class ReviewerProjectRulesInputTO {

    private Integer projectId;
    private Integer ruleId;
    private boolean initialRuleStatus;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public boolean isInitialRuleStatus() {
        return initialRuleStatus;
    }

    public void setInitialRuleStatus(boolean initialRuleStatus) {
        this.initialRuleStatus = initialRuleStatus;
    }

}
