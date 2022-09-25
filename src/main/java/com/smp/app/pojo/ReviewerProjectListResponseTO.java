package com.smp.app.pojo;

public class ReviewerProjectListResponseTO {

    private Integer companyId;
    private String comapnyName;
    private Integer projectId;
    private String projectUniqueId;
    private String projectName;
    private String projectDescr;
    private Integer projectStatus;
    private String projectCreatedDate;
    private String projectLogoPath;
    private String projectImgBaseUrl;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getComapnyName() {
        return comapnyName;
    }

    public void setComapnyName(String comapnyName) {
        this.comapnyName = comapnyName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescr() {
        return projectDescr;
    }

    public void setProjectDescr(String projectDescr) {
        this.projectDescr = projectDescr;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectCreatedDate() {
        return projectCreatedDate;
    }

    public void setProjectCreatedDate(String projectCreatedDate) {
        this.projectCreatedDate = projectCreatedDate;
    }

    public String getProjectLogoPath() {
        return projectLogoPath;
    }

    public void setProjectLogoPath(String projectLogoPath) {
        this.projectLogoPath = projectLogoPath;
    }

    public String getProjectUniqueId() {
        return projectUniqueId;
    }

    public void setProjectUniqueId(String projectUniqueId) {
        this.projectUniqueId = projectUniqueId;
    }

    public String getProjectImgBaseUrl() {
        return projectImgBaseUrl;
    }

    public void setProjectImgBaseUrl(String projectImgBaseUrl) {
        this.projectImgBaseUrl = projectImgBaseUrl;
    }

}
