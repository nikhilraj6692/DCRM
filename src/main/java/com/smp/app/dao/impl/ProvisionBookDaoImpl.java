package com.smp.app.dao.impl;

import com.smp.app.dao.ProvisionBookDao;
import com.smp.app.entity.ProvisionBookDetail;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProvisionBookDaoImpl extends GenericDaoImpl<ProvisionBookDetail, Integer> implements ProvisionBookDao {

    public ProvisionBookDaoImpl() {
        super(ProvisionBookDetail.class);
    }

    @Override
    public List<ProvisionBookDetail> getBookList() {
        Session session = this.getSession();
        String hql = "from ProvisionBookDetail as bookDetail order by bookDetail.bookName asc ";
        return session.createQuery(hql).list();
    }

    @Override
    public List<ProvisionBookDetail> getBookList(Integer bookId) {
        Session session = this.getSession();
        String hql = "from ProvisionBookDetail as bookDetail where :bookId is null or bookDetail.bookId =:bookId order by bookDetail.bookName asc ";
        Query query = session.createQuery(hql);
        query.setParameter("bookId", bookId);
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<Object[]> getBookListBasedOnManagementId(Integer managementId) {
        Session session = this.getSession();
        String hql = "select bookDetail.bookName, ruleDetail.ruleId, ruleDetail.ruleSubclauseNum "
            + "from ProjectRuleRelation as projRuleRelation " + "inner join projRuleRelation.projectDetail as projectDetail "
            + "inner join  projRuleRelation.projectRuleDetail as ruleDetail "
            + "inner join ruleDetail.provisionBookDetail as bookDetail   "
            + "where projectDetail.managementDetail.userId =:managementId " + "order by bookDetail.bookName asc ";
        Query query = session.createQuery(hql);
        query.setParameter("managementId", managementId);
        List<Object[]> resultList = query.list();
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<Object[]> getBookListBasedOnProjectId(Integer projectId) {
        Session session = this.getSession();
        String hql = "select bookDetail.bookName, ruleDetail.ruleId, ruleDetail.ruleSubclauseNum "
            + "from ProjectRuleRelation as projRuleRelation " + "inner join projRuleRelation.projectDetail as projectDetail "
            + "inner join  projRuleRelation.projectRuleDetail as ruleDetail "
            + "inner join ruleDetail.provisionBookDetail as bookDetail   " + "where projectDetail.projectId =:projectId "
            + "order by bookDetail.bookName asc ";
        Query query = session.createQuery(hql);
        query.setParameter("projectId", projectId);
        List<Object[]> resultList = query.list();
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Set<Integer> getBookIdsBasedOnProjectId(Integer projectId) {
        Session session = this.getSession();
        String hql = "select bookDetail.bookId " + "from ProjectRuleRelation as projRuleRelation "
            + "inner join projRuleRelation.projectDetail as projectDetail "
            + "inner join  projRuleRelation.projectRuleDetail as ruleDetail "
            + "inner join ruleDetail.provisionBookDetail as bookDetail   " + "where projectDetail.projectId =:projectId ";
        Query query = session.createQuery(hql);
        query.setParameter("projectId", projectId);
        List<Integer> resultList = query.list();
        return new HashSet<Integer>(resultList);
    }

}
