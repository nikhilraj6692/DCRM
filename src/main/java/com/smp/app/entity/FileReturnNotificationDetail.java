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
@Table(name = "file_to_return_notification_detail")
public class FileReturnNotificationDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String notificationMessage;
    private boolean notificationStatus;
    private Date createdDate;
    private FileToReturnRequestDetail fileToReturnRequestDetail;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "notification_message")
    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    @Column(name = "notification_status")
    public boolean isNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    @ManyToOne
    @JoinColumn(name = "file_to_return_req_id")
    public FileToReturnRequestDetail getFileToReturnRequestDetail() {
        return fileToReturnRequestDetail;
    }

    public void setFileToReturnRequestDetail(FileToReturnRequestDetail fileToReturnRequestDetail) {
        this.fileToReturnRequestDetail = fileToReturnRequestDetail;
    }

    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


}
