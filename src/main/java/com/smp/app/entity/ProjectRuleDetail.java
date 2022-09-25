package com.smp.app.entity;

import java.io.Serializable;
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
@Table(name = "project_rule_detail")
public class ProjectRuleDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer ruleId;
    private String ruleSubclauseNum;
    private String ruleTitle;
    private String ruleResponsibilty;
    private String ruleDescription;
    /*private String ruleRelevantCircular;
    private String relevantCircularDescription;*/
    private Date createdDate;
    private String circularAttachmentName;
    private ProvisionBookDetail provisionBookDetail;
    private List<ProjectRuleRelation> ruleRelationList = new ArrayList<ProjectRuleRelation>();
    private List<FileReturnRuleAssociatedDetail> fileReturnRuleAssociationList =
        new ArrayList<FileReturnRuleAssociatedDetail>();
    private List<RuleRelevantCircular> relevantCircularList = new ArrayList<RuleRelevantCircular>();


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    @Column(name = "rule_title")
    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }


    @Column(name = "rule_description")
    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    @Column(name = "rule_responsibilty")
    public String getRuleResponsibilty() {
        return ruleResponsibilty;
    }

    public void setRuleResponsibilty(String ruleResponsibilty) {
        this.ruleResponsibilty = ruleResponsibilty;
    }


    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @OneToMany(mappedBy = "projectRuleDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ProjectRuleRelation> getRuleRelationList() {
        return ruleRelationList;
    }

    public void setRuleRelationList(List<ProjectRuleRelation> ruleRelationList) {
        this.ruleRelationList = ruleRelationList;
    }

    @Column(name = "rule_subclause_num")
    public String getRuleSubclauseNum() {
        return ruleSubclauseNum;
    }

    public void setRuleSubclauseNum(String ruleSubclauseNum) {
        this.ruleSubclauseNum = ruleSubclauseNum;
    }

    @ManyToOne
    @JoinColumn(name = "provision_book_id")
    public ProvisionBookDetail getProvisionBookDetail() {
        return provisionBookDetail;
    }

    public void setProvisionBookDetail(ProvisionBookDetail provisionBookDetail) {
        this.provisionBookDetail = provisionBookDetail;
    }

    @Column(name = "circular_attachment_name")
    public String getCircularAttachmentName() {
        return circularAttachmentName;
    }

    public void setCircularAttachmentName(String circularAttachmentName) {
        this.circularAttachmentName = circularAttachmentName;
    }

    @OneToMany(mappedBy = "projectRuleDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<FileReturnRuleAssociatedDetail> getFileReturnRuleAssociationList() {
        return fileReturnRuleAssociationList;
    }

    public void setFileReturnRuleAssociationList(List<FileReturnRuleAssociatedDetail> fileReturnRuleAssociationList) {
        this.fileReturnRuleAssociationList = fileReturnRuleAssociationList;
    }

    @OneToMany(mappedBy = "projectRuleDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<RuleRelevantCircular> getRelevantCircularList() {
        return relevantCircularList;
    }

    public void setRelevantCircularList(List<RuleRelevantCircular> relevantCircularList) {
        this.relevantCircularList = relevantCircularList;
    }


}
