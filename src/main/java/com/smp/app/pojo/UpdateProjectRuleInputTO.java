package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class UpdateProjectRuleInputTO {

    private Integer projectId;
    private List<Integer> ruleIdList = new ArrayList<Integer>();


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getRuleIdList() {
        return ruleIdList;
    }

    public void setRuleIdList(List<Integer> ruleIdList) {
        this.ruleIdList = ruleIdList;
    }

}
