package com.smp.app.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee_detail")
public class UserDetail {

    private Integer userId;
    private String userName;
    private String userEmailId;
    private String password;
    private String deviceTokenValue;
    private String token;
    private String refreshToken;
    private UserRule userRule;
    private List<ProjectDetail> projectList = new ArrayList<ProjectDetail>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "emp_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "emp_email_id")
    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    @Column(name = "emp_pwd")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "user_rule_id")
    public UserRule getUserRule() {
        return userRule;
    }

    public void setUserRule(UserRule userRule) {
        this.userRule = userRule;
    }

    @ManyToMany(mappedBy = "employeeDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ProjectDetail> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectDetail> projectList) {
        this.projectList = projectList;
    }

    @Column(name = "device_token_value")
    public String getDeviceTokenValue() {
        return deviceTokenValue;
    }

    public void setDeviceTokenValue(String deviceTokenValue) {
        this.deviceTokenValue = deviceTokenValue;
    }

    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
