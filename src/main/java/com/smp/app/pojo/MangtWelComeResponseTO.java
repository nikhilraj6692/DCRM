package com.smp.app.pojo;

public class MangtWelComeResponseTO {

    private Integer companyId;
    private Integer projectId;
    private String companyName;
    private String projectName;
    private String projectLogoPath;


    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLogoPath() {
        return projectLogoPath;
    }

    public void setProjectLogoPath(String projectLogoPath) {
        this.projectLogoPath = projectLogoPath;
    }

}
