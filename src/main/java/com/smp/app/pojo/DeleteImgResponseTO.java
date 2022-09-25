package com.smp.app.pojo;

public class DeleteImgResponseTO {

    private Boolean responseStatus;
    private String responseMessage;
    private String imgFullURL;

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

    public String getImgFullURL() {
        return imgFullURL;
    }

    public void setImgFullURL(String imgFullURL) {
        this.imgFullURL = imgFullURL;
    }

}
