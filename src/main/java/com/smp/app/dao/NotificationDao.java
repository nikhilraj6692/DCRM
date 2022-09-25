package com.smp.app.dao;

import com.smp.app.pojo.NotificationDetailResponseTO;
import com.smp.app.entity.NotificationDetail;
import java.util.List;

public interface NotificationDao extends GenericDao<NotificationDetail, Integer> {


    void deleteNotificationRecord(String lastRecordDate);

    List<NotificationDetailResponseTO> getNotificationRecords(Integer UserId);
}
