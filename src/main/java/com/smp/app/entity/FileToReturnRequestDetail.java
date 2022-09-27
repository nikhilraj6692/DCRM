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
@Table(name = "file_to_return_requested_detail")
public class FileToReturnRequestDetail {

    private Integer id;
    private String startDate;
    private String endDate;
    private Date createdDate;
    private Integer fileReturnStatus;
    private ProjectDetail projectDetail;
    private List<FileReturnRuleAssociatedDetail> fileReturnRuleAssociationList =
        new ArrayList<FileReturnRuleAssociatedDetail>();
    private List<FileReturnNotificationDetail> fileReturnNotificationList = new ArrayList<FileReturnNotificationDetail>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "start_date")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    @OneToMany(mappedBy = "fileToReturnRequestDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<FileReturnRuleAssociatedDetail> getFileReturnRuleAssociationList() {
        return fileReturnRuleAssociationList;
    }

    public void setFileReturnRuleAssociationList(List<FileReturnRuleAssociatedDetail> fileReturnRuleAssociationList) {
        this.fileReturnRuleAssociationList = fileReturnRuleAssociationList;
    }

    @OneToMany(mappedBy = "fileToReturnRequestDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<FileReturnNotificationDetail> getFileReturnNotificationList() {
        return fileReturnNotificationList;
    }

    public void setFileReturnNotificationList(List<FileReturnNotificationDetail> fileReturnNotificationList) {
        this.fileReturnNotificationList = fileReturnNotificationList;
    }

    @Column(name = "file_return_status")
    public Integer getFileReturnStatus() {
        return fileReturnStatus;
    }

    public void setFileReturnStatus(Integer fileReturnStatus) {
        this.fileReturnStatus = fileReturnStatus;
    }


}
