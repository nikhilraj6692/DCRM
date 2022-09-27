package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RelevantCircularResponseTO {

    private Integer circularId;
    private String circularName;
    private String circularDescription;


    public Integer getCircularId() {
        return circularId;
    }

    public void setCircularId(Integer circularId) {
        this.circularId = circularId;
    }

    public String getCircularName() {
        return circularName;
    }

    public void setCircularName(String circularName) {
        this.circularName = circularName;
    }

    public String getCircularDescription() {
        return circularDescription;
    }

    public void setCircularDescription(String circularDescription) {
        this.circularDescription = circularDescription;
    }

}
