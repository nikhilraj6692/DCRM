package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class FileReturnListResponseTO {

    private Integer fileToReturnRequestId;
    private Integer projectId;
    private String projectName;
    private String returnStatus;
    private String createdDate;
    private String startDate;
    private String endDate;
    private List<String> returnRequestRuleList = new ArrayList<String>();


    public Integer getFileToReturnRequestId() {
        return fileToReturnRequestId;
    }

    public void setFileToReturnRequestId(Integer fileToReturnRequestId) {
        this.fileToReturnRequestId = fileToReturnRequestId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getReturnRequestRuleList() {
        return returnRequestRuleList;
    }

    public void setReturnRequestRuleList(List<String> returnRequestRuleList) {
        this.returnRequestRuleList = returnRequestRuleList;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
