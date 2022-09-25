package com.smp.app.util;

import java.util.List;

public class VerificationCodeMail implements Runnable {


    private final List<String> to;
    private final String from;
    private String mailBody;
    private final String subject;
    private final MailUtils appUtil = new MailUtils();


    public VerificationCodeMail(List<String> recipientEmails, String from, String mailBody, String subject) {
        super();
        this.to = recipientEmails;
        this.from = from;
        this.mailBody = mailBody;
        this.subject = subject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }


    //Setting email disclimer1
    public void run() {
        appUtil.sendMail(from, to, subject, mailBody);
    }


}
