package com.smp.app.pojo;

public class UserLoginInputTO {

    private Integer loginUserType;
    private String username;
    private String userEmailId;
    private String password;


    public Integer getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(Integer loginUserType) {
        this.loginUserType = loginUserType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginInputTO{" + "loginUserType=" + loginUserType + ", username='" + username + '\'' + ", userEmailId='"
            + userEmailId + '\'' + ", password='" + password + '\'' + '}';
    }
}
