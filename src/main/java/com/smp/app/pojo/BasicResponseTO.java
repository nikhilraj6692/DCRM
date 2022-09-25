package com.smp.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.smp.app.util.MessageResourceResolver;
import java.util.Locale;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class BasicResponseTO {

    @JsonIgnore
    private transient static final MessageResourceResolver messageResourceResolver;

    static {
        messageResourceResolver = MessageResourceResolver.getInstance();
    }

    private String code;
    private String message;
    @JsonIgnore
    private transient Object[] args;

    public BasicResponseTO(String code) {
        this.code = code;
        this.message = messageResourceResolver.getNormalizedMessage(code, Locale.getDefault(), null);
    }

    public BasicResponseTO(String code, Object[] args) {
        this.code = code;
        this.message = messageResourceResolver.getNormalizedMessage(code, Locale.getDefault(), args);
    }

    public void setResponseStatus(boolean parseBoolean) {
    }

    public void setResponseMessage(String readUserDefinedMessages) {
    }
}
