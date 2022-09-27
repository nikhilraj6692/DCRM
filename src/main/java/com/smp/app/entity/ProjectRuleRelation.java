package com.smp.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project_and_rule_relation")
public class ProjectRuleRelation {

    private Integer relationId;
    private Date createdDate;
    private ProjectDetail projectDetail;
    private ProjectRuleDetail projectRuleDetail;
    private List<UserReviewComments> reviewCommentList = new ArrayList<UserReviewComments>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public ProjectDetail getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(ProjectDetail projectDetail) {
        this.projectDetail = projectDetail;
    }

    @ManyToOne
    @JoinColumn(name = "project_rule_id")
    public ProjectRuleDetail getProjectRuleDetail() {
        return projectRuleDetail;
    }

    public void setProjectRuleDetail(ProjectRuleDetail projectRuleDetail) {
        this.projectRuleDetail = projectRuleDetail;
    }

    @OneToMany(mappedBy = "projectRuleRelation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<UserReviewComments> getReviewCommentList() {
        return reviewCommentList;
    }

    public void setReviewCommentList(List<UserReviewComments> reviewCommentList) {
        this.reviewCommentList = reviewCommentList;
    }

}
