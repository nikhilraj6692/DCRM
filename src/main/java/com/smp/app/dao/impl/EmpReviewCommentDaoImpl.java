package com.smp.app.dao.impl;

import com.smp.app.dao.EmpReviewCommentDao;
import com.smp.app.util.UserRuleEnum;
import com.smp.app.entity.ProjectDetail;
import com.smp.app.entity.UserReviewComments;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EmpReviewCommentDaoImpl extends GenericDaoImpl<UserReviewComments, Integer> implements EmpReviewCommentDao {


    public EmpReviewCommentDaoImpl() {
        super(UserReviewComments.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Map<Integer, String> getDeviceTokenList(Integer notificationTypeId, ProjectDetail projectDetail) {
        Session session = this.getSession();
        String nativeSql = "";
        if (notificationTypeId == UserRuleEnum.MANAGEMENT.getId()) {
            /*nativeSql = "SELECT empDetail.id, empDetail.device_token_value " + "FROM employee_detail  as empDetail "
                + "where (empDetail.user_rule_id = " + UserRuleEnum.LEAD_REVIEWER.getId() + " OR empDetail.id = "
                + projectDetail.getReviewerId() + ") AND empDetail.device_token_value IS not null "
                + " AND device_token_value != ''";*/
        } else if (notificationTypeId == UserRuleEnum.REVIEWER.getId()) {
            nativeSql = "SELECT empDetail.id, empDetail.device_token_value " + "FROM employee_detail  as empDetail "
                + "where empDetail.user_rule_id = " + UserRuleEnum.LEAD_REVIEWER.getId()
                + "  AND empDetail.device_token_value IS not null " + "AND device_token_value != ''";
        } else {
            /*nativeSql = "SELECT empDetail.id, empDetail.device_token_value " + "FROM employee_detail  as empDetail "
                + "where (empDetail.id = " + projectDetail.getManagementDetail().getUserId() + " OR empDetail.id = "
                + projectDetail.getReviewerId() + ")  AND empDetail.device_token_value IS not null "
                + "AND device_token_value != ''";*/
        }

        Query query = session.createSQLQuery(nativeSql);
        List<Object[]> dbRecord = query.list();

        Map<Integer, String> resultList = new LinkedHashMap<Integer, String>();
        for (Object[] detail : dbRecord) {
            resultList.put(Integer.parseInt(detail[0].toString()), detail[1].toString());
        }
        return resultList;
    }

}
