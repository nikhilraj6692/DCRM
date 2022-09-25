package com.smp.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class FileToReturnSaveInputTO {


    private Integer selectedProjectId;
    private List<String> selectedRuleIdList = new ArrayList<String>();
    private String startDate;
    private String endDate;

    public Integer getSelectedProjectId() {
        return selectedProjectId;
    }

    public void setSelectedProjectId(Integer selectedProjectId) {
        this.selectedProjectId = selectedProjectId;
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

    public List<String> getSelectedRuleIdList() {
        return selectedRuleIdList;
    }

    public void setSelectedRuleIdList(List<String> selectedRuleIdList) {
        this.selectedRuleIdList = selectedRuleIdList;
    }


}
