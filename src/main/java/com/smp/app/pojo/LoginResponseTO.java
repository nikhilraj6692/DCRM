package com.smp.app.pojo;

public class LoginResponseTO {

    private Boolean responseStatus;
    private String responseMessage;
    private UserDetailResponseTO userDetail = new UserDetailResponseTO();

    public Boolean getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Boolean responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public UserDetailResponseTO getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailResponseTO userDetail) {
        this.userDetail = userDetail;
    }
}
