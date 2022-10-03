package com.smp.app.pojo;

import java.util.List;

public class ProjectReviewerRelationInputTO {

    private Integer projectId;
    private List<Integer> reviewerIds;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getReviewerIds() {
        return reviewerIds;
    }

    public void setReviewerIds(List<Integer> reviewerIds) {
        this.reviewerIds = reviewerIds;
    }
}
