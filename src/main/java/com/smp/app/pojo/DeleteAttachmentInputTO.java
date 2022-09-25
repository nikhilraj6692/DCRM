package com.smp.app.pojo;

public class DeleteAttachmentInputTO {

    private Integer ruleRelationId;
    private String imageName;
    private String fullImageURL;

    public Integer getRuleRelationId() {
        return ruleRelationId;
    }

    public void setRuleRelationId(Integer ruleRelationId) {
        this.ruleRelationId = ruleRelationId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFullImageURL() {
        return fullImageURL;
    }

    public void setFullImageURL(String fullImageURL) {
        this.fullImageURL = fullImageURL;
    }

}
