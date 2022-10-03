package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.smp.app.entity.ProjectDetail;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserDetailReviewerRelation {

    private Integer userId;
    private String userName;
    private String userEmailId;
    private List<ProjectDetail> projectList = new ArrayList<ProjectDetail>();


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public List<ProjectDetail> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectDetail> projectList) {
        this.projectList = projectList;
    }
}
