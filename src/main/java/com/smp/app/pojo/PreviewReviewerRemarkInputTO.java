package com.smp.app.pojo;

public class PreviewReviewerRemarkInputTO {

    private Integer reviewerCommentId;
    private Integer conformityStatus;
    private Integer conformityLevel;
    private String reviewerRemark;
    private boolean remarkNAstatus;

    public Integer getReviewerCommentId() {
        return reviewerCommentId;
    }

    public void setReviewerCommentId(Integer reviewerCommentId) {
        this.reviewerCommentId = reviewerCommentId;
    }

    public Integer getConformityStatus() {
        return conformityStatus;
    }

    public void setConformityStatus(Integer conformityStatus) {
        this.conformityStatus = conformityStatus;
    }

    public Integer getConformityLevel() {
        return conformityLevel;
    }

    public void setConformityLevel(Integer conformityLevel) {
        this.conformityLevel = conformityLevel;
    }

    public String getReviewerRemark() {
        return reviewerRemark;
    }

    public void setReviewerRemark(String reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public boolean isRemarkNAstatus() {
        return remarkNAstatus;
    }

    public void setRemarkNAstatus(boolean remarkNAstatus) {
        this.remarkNAstatus = remarkNAstatus;
    }


}
