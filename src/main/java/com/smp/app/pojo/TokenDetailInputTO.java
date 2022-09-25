package com.smp.app.pojo;

public class TokenDetailInputTO {

    private Integer userId;
    private String deviceTokenValue;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceTokenValue() {
        return deviceTokenValue;
    }

    public void setDeviceTokenValue(String deviceTokenValue) {
        this.deviceTokenValue = deviceTokenValue;
    }

}
