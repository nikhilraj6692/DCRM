package com.smp.app.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee_review_comment")
public class UserReviewComments {

    private Integer reviewCommentId;
    private String managementRemark;
    private Date managementRemarkedDate;
    private Integer reviewerConformityStatus;
    private Integer reviewerConformityLevel;
    private String reviewerRemark;
    private boolean reviewerNAStatus;
    private Date reviewerRemarkedDate;
    private String leadReviewerRemark;
    private Date leadReviewerRemarkedDate;
    private ProjectRuleRelation projectRuleRelation;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getReviewCommentId() {
        return reviewCommentId;
    }

    public void setReviewCommentId(Integer reviewCommentId) {
        this.reviewCommentId = reviewCommentId;
    }

    @Column(name = "management_remark")
    public String getManagementRemark() {
        return managementRemark;
    }

    public void setManagementRemark(String managementRemark) {
        this.managementRemark = managementRemark;
    }

    @Column(name = "manager_remarked_date")
    public Date getManagementRemarkedDate() {
        return managementRemarkedDate;
    }

    public void setManagementRemarkedDate(Date managementRemarkedDate) {
        this.managementRemarkedDate = managementRemarkedDate;
    }

    @Column(name = "reviewer_conformity_level")
    public Integer getReviewerConformityLevel() {
        return reviewerConformityLevel;
    }

    public void setReviewerConformityLevel(Integer reviewerConformityLevel) {
        this.reviewerConformityLevel = reviewerConformityLevel;
    }

    @Column(name = "reviewer_remark")
    public String getReviewerRemark() {
        return reviewerRemark;
    }

    public void setReviewerRemark(String reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    @Column(name = "reviewer_remarked_date")
    public Date getReviewerRemarkedDate() {
        return reviewerRemarkedDate;
    }

    public void setReviewerRemarkedDate(Date reviewerRemarkedDate) {
        this.reviewerRemarkedDate = reviewerRemarkedDate;
    }

    @Column(name = "lead_reviewer_remark")
    public String getLeadReviewerRemark() {
        return leadReviewerRemark;
    }

    public void setLeadReviewerRemark(String leadReviewerRemark) {
        this.leadReviewerRemark = leadReviewerRemark;
    }

    @Column(name = "lead_reviewer_remarked_date")
    public Date getLeadReviewerRemarkedDate() {
        return leadReviewerRemarkedDate;
    }

    public void setLeadReviewerRemarkedDate(Date leadReviewerRemarkedDate) {
        this.leadReviewerRemarkedDate = leadReviewerRemarkedDate;
    }

    @ManyToOne
    @JoinColumn(name = "project_rule_relation_id")
    public ProjectRuleRelation getProjectRuleRelation() {
        return projectRuleRelation;
    }

    public void setProjectRuleRelation(ProjectRuleRelation projectRuleRelation) {
        this.projectRuleRelation = projectRuleRelation;
    }

    @Column(name = "reviewer_conformity_status")
    public Integer getReviewerConformityStatus() {
        return reviewerConformityStatus;
    }

    public void setReviewerConformityStatus(Integer reviewerConformityStatus) {
        this.reviewerConformityStatus = reviewerConformityStatus;
    }

    @Column(name = "reviewer_na_status")
    public boolean isReviewerNAStatus() {
        return reviewerNAStatus;
    }

    public void setReviewerNAStatus(boolean reviewerNAStatus) {
        this.reviewerNAStatus = reviewerNAStatus;
    }


}
