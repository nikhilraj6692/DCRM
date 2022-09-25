package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class ReviewerProjectDetailResponseTO {

    List<Integer> ruleIdsList = new ArrayList<Integer>();
    List<ConformityLevelResponseTO> conformityLevelList = new ArrayList<ConformityLevelResponseTO>();
    private Integer projectId;
    private String projectName;
    private Integer projectStatus;
    private String projectLogoUrl;
    private List<ReviewerRuleDetailResponseTO> ruleDetailList = new ArrayList<ReviewerRuleDetailResponseTO>();
    private List<Integer> reviewerSavedRecordList = new ArrayList<Integer>();
    private List<Integer> leadReviewerSavedRecordList = new ArrayList<Integer>();


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

    public List<ReviewerRuleDetailResponseTO> getRuleDetailList() {
        return ruleDetailList;
    }

    public void setRuleDetailList(List<ReviewerRuleDetailResponseTO> ruleDetailList) {
        this.ruleDetailList = ruleDetailList;
    }

    public List<Integer> getRuleIdsList() {
        return ruleIdsList;
    }

    public void setRuleIdsList(List<Integer> ruleIdsList) {
        this.ruleIdsList = ruleIdsList;
    }

    public List<ConformityLevelResponseTO> getConformityLevelList() {
        return conformityLevelList;
    }

    public void setConformityLevelList(List<ConformityLevelResponseTO> conformityLevelList) {
        this.conformityLevelList = conformityLevelList;
    }

    public String getProjectLogoUrl() {
        return projectLogoUrl;
    }

    public void setProjectLogoUrl(String projectLogoUrl) {
        this.projectLogoUrl = projectLogoUrl;
    }

    public List<Integer> getReviewerSavedRecordList() {
        return reviewerSavedRecordList;
    }

    public void setReviewerSavedRecordList(List<Integer> reviewerSavedRecordList) {
        this.reviewerSavedRecordList = reviewerSavedRecordList;
    }

    public List<Integer> getLeadReviewerSavedRecordList() {
        return leadReviewerSavedRecordList;
    }

    public void setLeadReviewerSavedRecordList(List<Integer> leadReviewerSavedRecordList) {
        this.leadReviewerSavedRecordList = leadReviewerSavedRecordList;
    }

}
