package com.smp.app.util;

import java.util.Date;
import java.util.List;

public class MailContents {

    public static String notificationMessageText(String endDate) {
        String notificationMessage =
            "Provisions need to be complied before end of general shift of " + endDate + " at priority";
        return notificationMessage;
    }

    public static String notificationMailSubjectLine() {
        String subjectLineText = "Compliance(s) to be attended-Urgent";
        return subjectLineText;
    }


    public static String notificationMailBodyDetail(String endDate, List<String> formatedRuleList) {

        String mailBodyText =
            "<p>Sir,</p>" + "<p>Greetings of the day!!!</p><br/>" + "<p>Hope this mail finds you well.</p><br/>"
                + "<p>It is to invite your kind attention that the Compliance Monitoring Cell observed that the following "
                + "provisions "
                + "need to be complied before end of general shift of " + endDate + " at priority.</p>";

        String provisionDetail = "<p><b>Sl No.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;           Provisions to be "
            + "complied<b></p>";
        int lpCount = 1;
        for (String ruleName : formatedRuleList) {
            provisionDetail = provisionDetail + "<p>" + lpCount
                + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + ruleName + "<p>";
            lpCount++;
        }

        mailBodyText = mailBodyText + provisionDetail
            + "<br/><p>Accordingly, you are requested to follow the process as mentioned herein;</P>"
            + "<p>Login through your login credentials to www.dcrm.in.net;</P>"
            + "<p>Go to the provisions highlighted to be complied;</P>" + "<p>File the compliance status;</P>"
            + "<p>Submit.</P>" + "<p>Attend the same at priority to avoid escalation.</P><br/>" + "<p>Regards,</P>"
            + "<p>Compliance Monitoring Cell,</P>" + "<p>JSW Steel Ltd,</P>" + "<p>Odisha.</P>";

        return mailBodyText;
    }


    public static String fileReturnComlpetionSubjectLine() {
        String subjectLineText = "Compliance(s) detail completed";
        return subjectLineText;
    }

    public static String fileReturnComlpetionMailBodyDetail(String projectName) {
        String mailBodyText =
            "<p><b>Project Name : </b>" + projectName + "</p>" + "<p><b>Completion Date : </b>" + new Date() + "</p>"
                + "<p>Compliance(s) detail completed successfully.</p><br/>" + "<p>Thank you.</p>";
        return mailBodyText;
    }
}
