package com.smp.app.pojo;

public class ChechUserExistenceResponseTO {

    private Boolean isUserExisted;
    private String serviceMessage;

    public Boolean getIsUserExisted() {
        return isUserExisted;
    }

    public void setIsUserExisted(Boolean isUserExisted) {
        this.isUserExisted = isUserExisted;
    }

    public String getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(String serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

}
