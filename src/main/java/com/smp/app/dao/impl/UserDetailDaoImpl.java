package com.smp.app.dao.impl;

import com.smp.app.dao.UserDetailDao;
import com.smp.app.pojo.UserDetailResponseTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.pojo.UserDetailReviewerRelation;
import com.smp.app.util.UserRuleEnum;
import com.smp.app.entity.UserDetail;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDetailDaoImpl extends GenericDaoImpl<UserDetail, Integer> implements UserDetailDao {

    public UserDetailDaoImpl() {
        super(UserDetail.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public UserDetail getUserDetailLoginInput(UserLoginInputTO loginDetail) {
        Session session = this.getSession();
        UserDetail resultObj = null;
        String hql = "from UserDetail as userDetail " + "where lower(userDetail.userName) =:userName AND "
            + "lower(userDetail.userEmailId) =:userEmailId AND " + "lower(userDetail.password) =:password ";
        if (loginDetail.getLoginUserType() == UserRuleEnum.REVIEWER.getId()
            || loginDetail.getLoginUserType() == UserRuleEnum.LEAD_REVIEWER.getId()) {
            hql = hql + " AND (userDetail.userRule.ruleId = 2 OR userDetail.userRule.ruleId = 3) ";
        } else if(loginDetail.getLoginUserType() == UserRuleEnum.SUPER_ADMIN.getId()){
            hql = hql + " AND userDetail.userRule.ruleId =4 ";
        }else if(loginDetail.getLoginUserType() == UserRuleEnum.MANAGEMENT.getId()){
            hql = hql + " AND userDetail.userRule.ruleId =1 ";
        }
        Query query = session.createQuery(hql).setParameter("userName", loginDetail.getUsername().trim().toLowerCase())
            .setParameter("userEmailId", loginDetail.getUserEmailId().trim().toLowerCase())
            .setParameter("password", loginDetail.getPassword());

        List<UserDetail> resultList = query.list();
        if (resultList != null && resultList.size() > 0) {
            resultObj = resultList.get(0);
        }
        return resultObj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public UserDetail getUserBasedEmail(String userEmailId) {
        Session session = this.getSession();
        UserDetail resultObj = null;
        String hql = "from UserDetail as userDetail " + "where lower(userDetail.userEmailId) =:userEmailId";
        Query query = session.createQuery(hql).setParameter("userEmailId", userEmailId.trim().toLowerCase());

        List<UserDetail> resultList = query.list();
        if (resultList != null && resultList.size() > 0) {
            resultObj = resultList.get(0);
        }
        return resultObj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<UserDetailReviewerRelation> getUserListReviewerRelation() {
        Session session = this.getSession();
        UserDetail resultObj = null;
        String hql = "select userDetail.userId as userId, userDetail.userName as userName, "
            + "userDetail.userEmailId as userEmailId, userDetail.projectList as projectList " + "from UserDetail as userDetail "
            + "where userDetail.userRule.ruleId =:userRuleId";
        Query query = session.createQuery(hql).setParameter("userRuleId", 2)
            .setResultTransformer(Transformers.aliasToBean(UserDetailReviewerRelation.class));
        List<UserDetailReviewerRelation> resultList = query.list();
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public UserDetail getUserOnBasisOfRole(Integer userRuleId, Integer userId) {
        Session session = this.getSession();
        UserDetail resultObj = null;
        String hql = "from UserDetail as userDetail " + "where userDetail.id =:userId and userDetail.userRule.ruleId =:userRuleId";
        Query query = session.createQuery(hql).setParameter("userId", userId).setParameter("userRuleId", userRuleId);

        List<UserDetail> resultList = query.list();
        if (resultList != null && resultList.size() > 0) {
            resultObj = resultList.get(0);
        }
        return resultObj;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<UserDetail> getUserOnBasisOfRole(Integer userRuleId) {
        Session session = this.getSession();
        String hql = "from UserDetail as userDetail " +
            (null!=userRuleId?"where userDetail.userRule.ruleId =:userRuleId":"");
        Query query = session.createQuery(hql);
        if(null!=userRuleId){
            query.setParameter("userRuleId", userRuleId);
        }
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public UserDetail findByRefreshToken(String refreshToken) {
        Session session = this.getSession();
        UserDetail resultObj = null;
        String hql = "from UserDetail as userDetail " + "where userDetail.refreshToken =:refreshToken";
        Query query = session.createQuery(hql)
            .setParameter("refreshToken", refreshToken);

        List<UserDetail> resultList = query.list();
        if (resultList != null && resultList.size() > 0) {
            resultObj = resultList.get(0);
        }
        return resultObj;
    }

    @Override
    public List<UserDetailResponseTO> getUsersWithAssignedProjectId(Integer userRuleId, Integer projectId) {
        Session session = this.getSession();
        String hql = "select userDetail.userId as userId, userDetail.userName as username from UserDetail as userDetail inner join userDetail.projectList as projectList where projectList.id=:projectId " +
            (null!=userRuleId?"and userDetail.userRule.ruleId =:userRuleId":"");
        Query query = session.createQuery(hql).setParameter("projectId", projectId);
        if(null!=userRuleId){
            query.setParameter("userRuleId", userRuleId);
        }
        query.setResultTransformer(Transformers.aliasToBean(UserDetailResponseTO.class));
        return query.list();
    }

}
