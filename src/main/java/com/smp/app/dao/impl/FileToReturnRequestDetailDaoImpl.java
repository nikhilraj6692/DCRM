package com.smp.app.dao.impl;

import com.smp.app.dao.FileToReturnRequestDetailDao;
import com.smp.app.pojo.FileReturnNotificationResponseTO;
import com.smp.app.entity.FileToReturnRequestDetail;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FileToReturnRequestDetailDaoImpl extends GenericDaoImpl<FileToReturnRequestDetail, Integer> implements
    FileToReturnRequestDetailDao {


    public FileToReturnRequestDetailDaoImpl() {
        super(FileToReturnRequestDetail.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<FileReturnNotificationResponseTO> getNotificationBasedOnProjectId(Integer projectId) {
        Session session = this.getSession();
        String nativeSql = "Select projectDetail.project_name as projectName,  "
            + "notificationDetail.notification_message as notificationMessage, "
            + "DATE_FORMAT(notificationDetail.created_date, '%M, %d %Y') as createdDate "
            + "from project_detail as projectDetail "
            + "inner join file_to_return_requested_detail as returnReqDetail on returnReqDetail.project_id = projectDetail"
			+ ".id "
            + "inner join file_to_return_notification_detail as notificationDetail on notificationDetail"
			+ ".file_to_return_req_id = returnReqDetail.id "
            + "where projectDetail.id =:projectId " + "order by notificationDetail.created_date desc";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId)
            .setResultTransformer(Transformers.aliasToBean(FileReturnNotificationResponseTO.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<FileToReturnRequestDetail> getReturnRequestListBasedOnProjectId(Integer projectId) {
        Session session = this.getSession();
        String hqlQuery =
            "from FileToReturnRequestDetail as returnDetail " + "where returnDetail.projectDetail.id =:projectId ";
        Query query = session.createQuery(hqlQuery);
        query.setParameter("projectId", projectId);
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public FileToReturnRequestDetail getReturnDetailBasedOnRequestId(Integer requestId) {
        Session session = this.getSession();
        String hqlQuery = "from FileToReturnRequestDetail as returnDetail " + "where returnDetail.id =:requestId ";
        Query query = session.createQuery(hqlQuery);
        query.setParameter("requestId", requestId);
        List<FileToReturnRequestDetail> resultList = query.list();
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return new FileToReturnRequestDetail();
        }
    }
}
