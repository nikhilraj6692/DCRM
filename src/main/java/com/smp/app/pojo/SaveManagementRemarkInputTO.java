package com.smp.app.pojo;

public class SaveManagementRemarkInputTO {

    private Integer ruleId;
    private Integer projectRuleRelationId;
    private Integer employeeCommentId;
    private String remarkValue;

    public Integer getProjectRuleRelationId() {
        return projectRuleRelationId;
    }

    public void setProjectRuleRelationId(Integer projectRuleRelationId) {
        this.projectRuleRelationId = projectRuleRelationId;
    }

    public Integer getEmployeeCommentId() {
        return employeeCommentId;
    }

    public void setEmployeeCommentId(Integer employeeCommentId) {
        this.employeeCommentId = employeeCommentId;
    }

    public String getRemarkValue() {
        return remarkValue;
    }

    public void setRemarkValue(String remarkValue) {
        this.remarkValue = remarkValue;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

}
