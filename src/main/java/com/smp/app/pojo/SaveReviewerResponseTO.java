package com.smp.app.pojo;

import java.util.List;

public class SaveReviewerResponseTO {

    private Boolean responseStatus;
    private String responseMessage;
    private List<String> reviewerImageList;


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

    public List<String> getReviewerImageList() {
        return reviewerImageList;
    }

    public void setReviewerImageList(List<String> reviewerImageList) {
        this.reviewerImageList = reviewerImageList;
    }

}
