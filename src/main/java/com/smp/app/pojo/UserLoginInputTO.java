package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.smp.app.util.EmailConstraintValidator;
import com.smp.app.util.SMPAppConstants;
import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserLoginInputTO {

    private Integer loginUserType;
    private String username;
    @EmailConstraintValidator(message = SMPAppConstants.INVALID_EMAIL_ID)
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
