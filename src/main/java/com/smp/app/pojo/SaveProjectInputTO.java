package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class SaveProjectInputTO {

    private String projectName;
    private String projectDescr;
    private Integer selectedCompanyId;
    private ManagementInputTO managementDetail = new ManagementInputTO();
    private List<Integer> selectedRuleIds = new ArrayList<Integer>();


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

    public Integer getSelectedCompanyId() {
        return selectedCompanyId;
    }

    public void setSelectedCompanyId(Integer selectedCompanyId) {
        this.selectedCompanyId = selectedCompanyId;
    }

    public ManagementInputTO getManagementDetail() {
        return managementDetail;
    }

    public void setManagementDetail(ManagementInputTO managementDetail) {
        this.managementDetail = managementDetail;
    }

    public List<Integer> getSelectedRuleIds() {
        return selectedRuleIds;
    }

    public void setSelectedRuleIds(List<Integer> selectedRuleIds) {
        this.selectedRuleIds = selectedRuleIds;
    }
}
