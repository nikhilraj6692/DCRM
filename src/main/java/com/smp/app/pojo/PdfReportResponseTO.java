package com.smp.app.pojo;

public class PdfReportResponseTO {

    private Integer projectId;
    private String projectReportURL;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectReportURL() {
        return projectReportURL;
    }

    public void setProjectReportURL(String projectReportURL) {
        this.projectReportURL = projectReportURL;
    }
}
