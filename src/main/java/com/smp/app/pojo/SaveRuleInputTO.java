package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.smp.app.util.SMPAppConstants;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SaveRuleInputTO {
    @NotNull(message = SMPAppConstants.INVALID_PROVISION_BOOK_ID)
    private Integer provisionBookId;
    @NotBlank(message = SMPAppConstants.INVALID_RULE_SUB_CLAUSE)
    private String ruleSubclauseNum;
    @NotBlank(message = SMPAppConstants.INVALID_RULE_TITLE)
    private String ruleTitle;
    @NotBlank(message = SMPAppConstants.INVALID_RULE_RESPONSIBILITY)
    private String ruleResponsibilty;
    @NotBlank(message = SMPAppConstants.INVALID_RULE_DESCRIPTION)
    private String ruleDescription;
    @Valid
    private List<RelevantCircularInputTO> ruleRelevantCirculars;


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

    public List<RelevantCircularInputTO> getRuleRelevantCirculars() {
        return ruleRelevantCirculars;
    }

    public void setRuleRelevantCirculars(List<RelevantCircularInputTO> ruleRelevantCirculars) {
        this.ruleRelevantCirculars = ruleRelevantCirculars;
    }

}
