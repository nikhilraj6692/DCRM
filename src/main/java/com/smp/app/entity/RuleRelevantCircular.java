package com.smp.app.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rule_relevant_circular_details")
public class RuleRelevantCircular implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer circularId;
    private String circularName;
    private String circularDescription;
    private ProjectRuleDetail projectRuleDetail;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circular_id", nullable = false, unique = true)
    public Integer getCircularId() {
        return circularId;
    }

    public void setCircularId(Integer circularId) {
        this.circularId = circularId;
    }

    @Column(name = "circular_name")
    public String getCircularName() {
        return circularName;
    }

    public void setCircularName(String circularName) {
        this.circularName = circularName;
    }

    @Column(name = "circular_description")
    public String getCircularDescription() {
        return circularDescription;
    }

    public void setCircularDescription(String circularDescription) {
        this.circularDescription = circularDescription;
    }

    @ManyToOne
    @JoinColumn(name = "project_rule_id")
    public ProjectRuleDetail getProjectRuleDetail() {
        return projectRuleDetail;
    }

    public void setProjectRuleDetail(ProjectRuleDetail projectRuleDetail) {
        this.projectRuleDetail = projectRuleDetail;
    }


}
