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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project_detail")
public class ProjectDetail implements Serializable {

    private Integer projectId;
    private String projectName;
    private String projectUniqueId;
    private String projectDescription;
    private Integer projectStatus;
    private String projectLogo;
    private Date createdDate;
    private Integer versionNum;
    private Date projectSubmittedDate;
    private CompanyDetail companyDetail;
    private List<UserDetail> employeeDetail;
    private List<ProjectRuleRelation> ruleRelationList = new ArrayList<ProjectRuleRelation>();
    private List<NotificationDetail> notificationList = new ArrayList<NotificationDetail>();
    private List<FileToReturnRequestDetail> fileToReturnRequestList = new ArrayList<FileToReturnRequestDetail>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "project_descripton")
    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Column(name = "project_status")
    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public CompanyDetail getCompanyDetail() {
        return companyDetail;
    }

    public void setCompanyDetail(CompanyDetail companyDetail) {
        this.companyDetail = companyDetail;
    }

    @ManyToMany
    @JoinTable(
        name = "employee_project_detail",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    public List<UserDetail> getEmployeeDetail() {
        return employeeDetail;
    }

    public void setEmployeeDetail(List<UserDetail> managementDetail) {
        this.employeeDetail = managementDetail;
    }

    @OneToMany(mappedBy = "projectDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ProjectRuleRelation> getRuleRelationList() {
        return ruleRelationList;
    }

    public void setRuleRelationList(List<ProjectRuleRelation> ruleRelationList) {
        this.ruleRelationList = ruleRelationList;
    }

    @Column(name = "proect_unique_id")
    public String getProjectUniqueId() {
        return projectUniqueId;
    }

    public void setProjectUniqueId(String projectUniqueId) {
        this.projectUniqueId = projectUniqueId;
    }

    @Column(name = "project_logo")
    public String getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(String projectLogo) {
        this.projectLogo = projectLogo;
    }

    @OneToMany(mappedBy = "projectDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<NotificationDetail> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationDetail> notificationList) {
        this.notificationList = notificationList;
    }

    @Column(name = "proj_version_num")
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Column(name = "project_submitted_date")
    public Date getProjectSubmittedDate() {
        return projectSubmittedDate;
    }

    public void setProjectSubmittedDate(Date projectSubmittedDate) {
        this.projectSubmittedDate = projectSubmittedDate;
    }

    @OneToMany(mappedBy = "projectDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<FileToReturnRequestDetail> getFileToReturnRequestList() {
        return fileToReturnRequestList;
    }

    public void setFileToReturnRequestList(List<FileToReturnRequestDetail> fileToReturnRequestList) {
        this.fileToReturnRequestList = fileToReturnRequestList;
    }


}
