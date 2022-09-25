package com.smp.app.pojo;

public class NotificationDetailResponseTO {

    private Integer notificationId;
    private String notificationMessage;
    private Integer projectId;
    private String notifiedDate;


    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getNotifiedDate() {
        return notifiedDate;
    }

    public void setNotifiedDate(String notifiedDate) {
        this.notifiedDate = notifiedDate;
    }

}
