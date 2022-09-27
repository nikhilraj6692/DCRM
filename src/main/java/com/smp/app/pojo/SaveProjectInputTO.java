package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SaveProjectInputTO {

    private String companyName;
    private String projectName;
    private String projectDescr;
    private String projectId;
    private Integer selectedCompanyId;

    @Valid
    private List<ManagementInputTO> managementDetail = new ArrayList<>();
    private List<Integer> selectedRuleIds = new ArrayList<Integer>();


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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public List<ManagementInputTO> getManagementDetail() {
        return managementDetail;
    }

    public void setManagementDetail(List<ManagementInputTO> managementDetail) {
        this.managementDetail = managementDetail;
    }

    public List<Integer> getSelectedRuleIds() {
        return selectedRuleIds;
    }

    public void setSelectedRuleIds(List<Integer> selectedRuleIds) {
        this.selectedRuleIds = selectedRuleIds;
    }
}
