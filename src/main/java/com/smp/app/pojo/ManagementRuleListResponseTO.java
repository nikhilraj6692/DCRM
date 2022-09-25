package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class ManagementRuleListResponseTO {

    private Integer projectId;
    private String projectName;
    private Integer projectStatus;
    private List<RuleDetailResponseTO> projectRuleList = new ArrayList<RuleDetailResponseTO>();


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

    public List<RuleDetailResponseTO> getProjectRuleList() {
        return projectRuleList;
    }

    public void setProjectRuleList(List<RuleDetailResponseTO> projectRuleList) {
        this.projectRuleList = projectRuleList;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }
}
