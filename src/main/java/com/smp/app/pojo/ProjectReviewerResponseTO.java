package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class ProjectReviewerResponseTO {

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
}
