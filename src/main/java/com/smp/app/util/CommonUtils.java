package com.smp.app.util;

import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");

    public static String setLogData(String methodinfo, StackTraceElement[] elements) {
        return methodinfo.concat(elements[0].getClassName()).concat(":").concat(elements[1].getMethodName());
    }

    public String readUserDefinedMessages(String userDefinedMsg) {
        String Message = bundle.getString(userDefinedMsg);
        return Message;
    }

}
