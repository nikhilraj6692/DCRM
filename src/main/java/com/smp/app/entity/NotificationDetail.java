package com.smp.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification_detail")
public class NotificationDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer notifyId;
    private String notifyMessage;
    private Date notifiedDate;
    private String notificationSendToDetail;
    private ProjectDetail projectDetail;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(Integer notifyId) {
        this.notifyId = notifyId;
    }

    @Column(name = "notification_message")
    public String getNotifyMessage() {
        return notifyMessage;
    }

    public void setNotifyMessage(String notifyMessage) {
        this.notifyMessage = notifyMessage;
    }

    @Column(name = "notified_date")
    public Date getNotifiedDate() {
        return notifiedDate;
    }

    public void setNotifiedDate(Date notifiedDate) {
        this.notifiedDate = notifiedDate;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public ProjectDetail getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(ProjectDetail projectDetail) {
        this.projectDetail = projectDetail;
    }

    @Column(name = "notification_send_to")
    public String getNotificationSendToDetail() {
        return notificationSendToDetail;
    }

    public void setNotificationSendToDetail(String notificationSendToDetail) {
        this.notificationSendToDetail = notificationSendToDetail;
    }

}
