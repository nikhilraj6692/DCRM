package com.smp.app.pojo;

public class SaveRuleInputTO {

    private Integer ruleId;
    private Integer provisionBookId;
    private String ruleSubclauseNum;
    private String ruleTitle;
    private String ruleResponsibilty;
    private String ruleDescription;
    private String ruleRelevantCircular;


    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getProvisionBookId() {
        return provisionBookId;
    }

    public void setProvisionBookId(Integer provisionBookId) {
        this.provisionBookId = provisionBookId;
    }

    public String getRuleSubclauseNum() {
        return ruleSubclauseNum;
    }

    public void setRuleSubclauseNum(String ruleSubclauseNum) {
        this.ruleSubclauseNum = ruleSubclauseNum;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleResponsibilty() {
        return ruleResponsibilty;
    }

    public void setRuleResponsibilty(String ruleResponsibilty) {
        this.ruleResponsibilty = ruleResponsibilty;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String getRuleRelevantCircular() {
        return ruleRelevantCircular;
    }

    public void setRuleRelevantCircular(String ruleRelevantCircular) {
        this.ruleRelevantCircular = ruleRelevantCircular;
    }

}
