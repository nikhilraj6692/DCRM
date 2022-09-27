package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.smp.app.util.SMPAppConstants;
import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RelevantCircularInputTO {
    @NotBlank(message = SMPAppConstants.INVALID_CIRCULAR_NAME)
    private String circularName;
    @NotBlank(message = SMPAppConstants.INVALID_CIRCULAR_DESCRIPTION)
    private String circularDescription;


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
