package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ProjectReviewerResponseTO {

    private Integer projectId;
    private String projectName;
    private List<Integer> reviewers = new ArrayList<>();

    private List<ProjectDetailReviewerRelation> projectList = new ArrayList<ProjectDetailReviewerRelation>();
    private List<UserDetailReviewerRelation> reviewerList = new ArrayList<UserDetailReviewerRelation>();

    public List<ProjectDetailReviewerRelation> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectDetailReviewerRelation> projectList) {
        this.projectList = projectList;
    }

    public List<UserDetailReviewerRelation> getReviewerList() {
        return reviewerList;
    }

    public void setReviewerList(List<UserDetailReviewerRelation> reviewerList) {
        this.reviewerList = reviewerList;
    }

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

    public List<Integer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Integer> reviewers) {
        this.reviewers = reviewers;
    }
}
