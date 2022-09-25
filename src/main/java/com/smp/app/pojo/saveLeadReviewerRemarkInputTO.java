package com.smp.app.pojo;

public class saveLeadReviewerRemarkInputTO {

    private Integer userReviewCommentId;
    private String leadReviewerRemark;

    public Integer getUserReviewCommentId() {
        return userReviewCommentId;
    }

    public void setUserReviewCommentId(Integer userReviewCommentId) {
        this.userReviewCommentId = userReviewCommentId;
    }

    public String getLeadReviewerRemark() {
        return leadReviewerRemark;
    }

    public void setLeadReviewerRemark(String leadReviewerRemark) {
        this.leadReviewerRemark = leadReviewerRemark;
    }
}
