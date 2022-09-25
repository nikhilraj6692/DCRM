package com.smp.app.dao.impl;

import com.smp.app.dao.NotificationDao;
import com.smp.app.pojo.NotificationDetailResponseTO;
import com.smp.app.entity.NotificationDetail;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotificationDaoImpl extends GenericDaoImpl<NotificationDetail, Integer> implements NotificationDao {

    public NotificationDaoImpl() {
        super(NotificationDetail.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void deleteNotificationRecord(String lastRecordDate) {
        Session session = this.getSession();
        String nativeSql = "delete from notification_detail " + "where notified_date < '" + lastRecordDate + "'";
        Query query = session.createSQLQuery(nativeSql);
        query.executeUpdate();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<NotificationDetailResponseTO> getNotificationRecords(Integer UserId) {
        Session session = this.getSession();
        String nativeSql = "SELECT notificationDetail.id as notificationId,  "
            + "notificationDetail.notification_message as notificationMessage, "
            + "DATE_FORMAT(notificationDetail.notified_date, '%Y-%m-%d %T.%f') as notifiedDate, "
            + "projectDet.id as projectId " + "FROM notification_detail as notificationDetail "
            + "inner join project_detail as projectDet on projectDet.id = notificationDetail.project_id "
            + "where notification_send_to LIKE '%," + UserId + ",%' "
            + "order by notificationDetail.notified_date desc limit 10";
        Query query = session.createSQLQuery(nativeSql)
            .setResultTransformer(Transformers.aliasToBean(NotificationDetailResponseTO.class));
        return query.list();
    }
}
