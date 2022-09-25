package com.smp.app.dao.impl;

import com.smp.app.dao.ProjectRuleDao;
import com.smp.app.pojo.BookListResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.entity.ProjectRuleDetail;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProjectRuleDaoImpl extends GenericDaoImpl<ProjectRuleDetail, Integer> implements ProjectRuleDao {


    public ProjectRuleDaoImpl() {
        super(ProjectRuleDetail.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<RuleListResponseTO> getRuleList() {
        Session session = this.getSession();
        String hql = "select ruleDetail.ruleId as ruleId, ruleDetail.ruleTitle as ruleTitle "
            + "from ProjectRuleDetail as ruleDetail";
        Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(RuleListResponseTO.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<BookListResponseTO> getBookList() {
        Session session = this.getSession();
        String hql = "select bookDetail.bookId as bookId, bookDetail.bookName as bookName "
            + "from ProvisionBookDetail as bookDetail";
        Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(BookListResponseTO.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Object[]> getRuleListBasedOnProjectId(Integer projectId) {
        Session session = this.getSession();
        String hql = "select bookDetail.bookName , ruleDetail.ruleId , ruleDetail.ruleTitle "
            + "from ProjectRuleRelation as ruleRelation " + "inner join ruleRelation.projectRuleDetail as ruleDetail "
            + "inner join ruleDetail.provisionBookDetail as bookDetail "
            + "where ruleRelation.projectDetail.projectId =:selectedProjectId";
        Query query = session.createQuery(hql);
        query.setParameter("selectedProjectId", projectId);
        return query.list();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Object[]> getRuleListBasedOnRuleIds(List<Integer> ruleIdList) {
        Session session = this.getSession();
        String hql = "select bookDetail.bookName , ruleDetail.ruleSubclauseNum " + "from ProjectRuleDetail as ruleDetail "
            + "inner join ruleDetail.provisionBookDetail as bookDetail " + "where ruleDetail.ruleId IN (:selectedRuleId)";
        Query query = session.createQuery(hql);
        query.setParameterList("selectedRuleId", ruleIdList);
        return query.list();
    }
}
