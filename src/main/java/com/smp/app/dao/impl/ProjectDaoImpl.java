package com.smp.app.dao.impl;

import com.smp.app.dao.ProjectDao;
import com.smp.app.pojo.ReviewerProjectListInputTO;
import com.smp.app.pojo.ConformityLevelResponseTO;
import com.smp.app.pojo.ManagementPreviewDetailsResponseTO;
import com.smp.app.pojo.MangtWelComeResponseTO;
import com.smp.app.pojo.ProjectDetailReviewerRelation;
import com.smp.app.pojo.ProjectListResponseTO;
import com.smp.app.pojo.ProjectRulesDaoTO;
import com.smp.app.pojo.RelevantCircularResponseTO;
import com.smp.app.pojo.ReviewerPreviewDetailsResponseTO;
import com.smp.app.pojo.ReviewerProjectListResponseTO;
import com.smp.app.pojo.ReviewerProjectRulesDao;
import com.smp.app.util.ProjectStatusEnum;
import com.smp.app.util.UserRuleEnum;
import com.smp.app.entity.ProjectDetail;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProjectDaoImpl extends GenericDaoImpl<ProjectDetail, Integer> implements ProjectDao {

    public ProjectDaoImpl() {
        super(ProjectDetail.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ProjectRulesDaoTO> getProjectRules(Integer managementId) {
        Session session = this.getSession();
        String nativeSql = "Select projectDetail.id as projectId, projectDetail.project_name as projectName, "
            + "projectDetail.project_status as projectStatus, ruleRelation.id as ruleRelationId, ruleDetail.id as ruleId, "
            + "ruleDetail.rule_subclause_num as ruleSubClauseNum, " + "ruleDetail.rule_title as ruleTitle, "
            + "ruleDetail.rule_responsibilty as ruleResponsibilty, " + "ruleDetail.rule_description as ruleDescription, "
            /*	+ "ruleDetail.relevant_circular_name as ruleRelevantCircular, "*/
            + "ruleDetail.circular_attachment_name as circularAttachName, " + " empComment.id as empCommentId, "
            + "empComment.management_remark as managementRemark " + "from project_detail as projectDetail "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "inner join  project_rule_detail as ruleDetail on ruleDetail.id = ruleRelation.project_rule_id "
            + "left join  employee_review_comment as empComment on empComment.project_rule_relation_id = ruleRelation.id "
            + "where projectDetail.management_id =:management_id ";

        Query query = session.createSQLQuery(nativeSql).setParameter("management_id", managementId)
            .setResultTransformer(Transformers.aliasToBean(ProjectRulesDaoTO.class));
        List<ProjectRulesDaoTO> resultList = query.list();
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ReviewerProjectListResponseTO> getReviewerProjectList(ReviewerProjectListInputTO userInput,
        String projectImgBaseUrl) {
        Session session = this.getSession();
        String hql = "select companyDetail.companyId as companyId, companyDetail.companyName as comapnyName, "
            + "projectDetail.projectId as projectId, projectDetail.projectLogo as projectLogoPath, "
            + "projectDetail.projectName as projectName, projectDetail.projectDescription as projectDescr, "
            + "projectDetail.projectStatus as projectStatus, projectDetail.projectUniqueId as projectUniqueId, "
            + "DATE_FORMAT(projectDetail.createdDate, '%b %d, %Y') as projectCreatedDate, '" + projectImgBaseUrl
            + "' as projectImgBaseUrl " + "from ProjectDetail as projectDetail "
            + "inner join projectDetail.companyDetail as companyDetail "
            + "inner join projectDetail.employeeDetail as employeeDetail "
            + "where projectDetail.projectStatus >=:projectStatus ";

        if (userInput.getUserRuleType() == UserRuleEnum.REVIEWER.getId()) {
            hql = hql + " AND employeeDetail.id =:reviewerId ";
        }

        Query query = session.createQuery(hql);
        if (userInput.getUserRuleType() == UserRuleEnum.REVIEWER.getId()) {
            query.setParameter("projectStatus", ProjectStatusEnum.MANAGEMENT_COMPLETED.getId());
            query.setParameter("reviewerId", userInput.getUserId());
        } else if (userInput.getUserRuleType() == UserRuleEnum.LEAD_REVIEWER.getId()) {
            query.setParameter("projectStatus", ProjectStatusEnum.REVIEWER_COMPLETED.getId());
        }
        query.setResultTransformer(Transformers.aliasToBean(ReviewerProjectListResponseTO.class));

        List<ReviewerProjectListResponseTO> resultList = query.list();
        return resultList;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ReviewerProjectRulesDao> getReviewerProjectRules(Integer projectId, Integer ruleId) {
        Session session = this.getSession();
        String nativeSql = "Select projectDetail.id as projectId, projectDetail.project_name as projectName, "
            + "projectDetail.project_status as projectStatus,  ruleRelation.id as relationId, "
            + "ruleDetail.id as ruleId, ruleDetail.rule_title as ruleTitle, "
            + "ruleDetail.rule_subclause_num as ruleSubclauseNum, " + "ruleDetail.rule_description as ruleDescription, "
            + "ruleDetail.rule_responsibilty as ruleResponsibilty, "
            /*+ "ruleDetail.relevant_circular_name as ruleRelevantCircular, "*/
            + "ruleDetail.circular_attachment_name as circularAttachmentName, "
            + "empComment.reviewer_na_status as reviewerRemarkNAstatus, "
            + "empComment.id as employeeCommentId, projectDetail.project_logo as projectLogoName, "
            + "empComment.management_remark as managementComment, "
            + "DATE_FORMAT(empComment.manager_remarked_date, '%b %d, %Y') as managementCommentedDate, "
            + "empComment.reviewer_conformity_status as reviewerConformaityStatus, "
            + "empComment.reviewer_conformity_level as reviewerConformaityLevel, "
            + "empComment.reviewer_remark as reviewerRemark, "
            + "DATE_FORMAT(empComment.reviewer_remarked_date, '%b %d, %Y') as reviewerRemarkedDate, "
            + "empComment.lead_reviewer_remark as leadReviewerRemark, "
            + "DATE_FORMAT(empComment.lead_reviewer_remarked_date, '%b %d, %Y') as leadReviewerRemarkedDate "
            + "from project_detail as projectDetail "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "inner join  project_rule_detail as ruleDetail on ruleDetail.id = ruleRelation.project_rule_id "
            + "left join  employee_review_comment as empComment on empComment.project_rule_relation_id = ruleRelation.id "
            + "where projectDetail.id =:projectId AND ruleDetail.id =:ruleId " + "order by ruleDetail.id asc limit 1";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId).setParameter("ruleId", ruleId)
            .setResultTransformer(Transformers.aliasToBean(ReviewerProjectRulesDao.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Boolean checkProjectRemarkStatus(Integer projectId) {
        Session session = this.getSession();
        String nativeSql =
            "select count(*) as commentCnt, " + "(select count(*) " + "from project_and_rule_relation as subQueryRel "
                + "where subQueryRel.project_id = " + projectId + ") as relationCnt " + "from project_detail as projDetail "
                + "inner join project_and_rule_relation as relation on relation.project_id = projDetail.id "
                + "inner join employee_review_comment as reviewComment on reviewComment.project_rule_relation_id = "
                + "relation.id "
                + "where projDetail.id = " + projectId;

        Query query = session.createSQLQuery(nativeSql);
        List<Object[]> resultList = query.list();
        Object[] resultArray = resultList.get(0);
        if (resultArray != null) {
            if (Integer.parseInt(resultArray[0].toString()) == 0 && Integer.parseInt(resultArray[1].toString()) == 0) {
                return false;
            } else if (Integer.parseInt(resultArray[0].toString()) != Integer.parseInt(resultArray[1].toString())) {
                return false;
            } else
                return Integer.parseInt(resultArray[0].toString()) == Integer.parseInt(resultArray[1].toString());
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<Integer> getRuleIdBasedProjectId(Integer projectId) {
        Session session = this.getSession();
        String nativeSql = "Select ruleRelation.project_rule_id " + "from project_detail as projectDetail "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "where projectDetail.id =:projectId " + "order by ruleRelation.project_rule_id asc";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId);
        return query.list();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Boolean checkReviewerProjectRemarkStatus(Integer projectId) {
        Session session = this.getSession();
        String remarkNAsubQuery = "select count(*) " + "from project_detail as sq01projDetail "
            + "inner join project_and_rule_relation as sq01relation on sq01relation.project_id = sq01projDetail.id "
            + "inner join employee_review_comment as sq01reviewComment on sq01reviewComment.project_rule_relation_id = "
            + "sq01relation.id "
            + "where sq01projDetail.id = " + projectId + " AND sq01reviewComment.reviewer_na_status=true";

        String nativeSql =
            "select count(*) as commentCnt, " + "(select count(*) " + "from project_and_rule_relation as subQueryRel "
                + "where subQueryRel.project_id = " + projectId + ") as relationCnt, " + "(" + remarkNAsubQuery
                + ") as naStatusCount " + "from project_detail as projDetail "
                + "inner join project_and_rule_relation as relation on relation.project_id = projDetail.id "
                + "inner join employee_review_comment as reviewComment on reviewComment.project_rule_relation_id = "
                + "relation.id "
                + "where projDetail.id = " + projectId + " AND reviewComment.reviewer_remark IS NOT NULL "
                + "AND reviewComment.reviewer_remark != ''";

        Query query = session.createSQLQuery(nativeSql);
        List<Object[]> resultList = query.list();
        Object[] resultArray = resultList.get(0);
        if (resultArray != null) {
            if (Integer.parseInt(resultArray[0].toString()) == 0 && Integer.parseInt(resultArray[1].toString()) == 0) {
                return false;
            } else if (Integer.parseInt(resultArray[0].toString()) != (Integer.parseInt(resultArray[1].toString())
                - Integer.parseInt(resultArray[2].toString()))) {
                return false;
            } else
                return Integer.parseInt(resultArray[0].toString()) == (Integer.parseInt(resultArray[1].toString())
                    - Integer.parseInt(resultArray[2].toString()));
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Boolean checkLeadReviewerProjectStatus(Integer projectId) {
        Session session = this.getSession();
        String nativeSql =
            "select count(*) as commentCnt, " + "(select count(*) " + "from project_and_rule_relation as subQueryRel "
                + "where subQueryRel.project_id = " + projectId + ") as relationCnt " + "from project_detail as projDetail "
                + "inner join project_and_rule_relation as relation on relation.project_id = projDetail.id "
                + "inner join employee_review_comment as reviewComment on reviewComment.project_rule_relation_id = "
                + "relation.id "
                + "where projDetail.id = " + projectId + " AND reviewComment.lead_reviewer_remark IS NOT NULL "
                + "AND reviewComment.lead_reviewer_remark != ''";

        Query query = session.createSQLQuery(nativeSql);
        List<Object[]> resultList = query.list();
        Object[] resultArray = resultList.get(0);
        if (resultArray != null) {
            if (Integer.parseInt(resultArray[0].toString()) == 0 && Integer.parseInt(resultArray[1].toString()) == 0) {
                return false;
            } else if (Integer.parseInt(resultArray[0].toString()) != Integer.parseInt(resultArray[1].toString())) {
                return false;
            } else
                return Integer.parseInt(resultArray[0].toString()) == Integer.parseInt(resultArray[1].toString());
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ConformityLevelResponseTO> getReviewerConformityLevel() {
        Session session = this.getSession();
        String nativeSql = "select conformityLevel.id as levelId, " + "conformityLevel.conformity_level as conformityLevel "
            + "from reviewer_conformity_level as conformityLevel";
        Query query = session.createSQLQuery(nativeSql)
            .setResultTransformer(Transformers.aliasToBean(ConformityLevelResponseTO.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ManagementPreviewDetailsResponseTO> managementPreviewDetails(Integer projectId) {
        Session session = this.getSession();
        String nativeSql = "Select projectDetail.id as projectId, " + "projectDetail.project_name as projectName, "
            + "bookDetail.id as bookId, bookDetail.book_name as bookName, "
            + "ruleDetail.id as ruleId, ruleDetail.rule_title as ruleTitle, "
            + "ruleDetail.rule_subclause_num as ruleSubclauseNum, " + "ruleDetail.rule_description as ruleDescription, "
            + "ruleDetail.rule_responsibilty as ruleResponsibilty, "
            /*+ "ruleDetail.relevant_circular_name as ruleRelevantCircular, "*/
            + "ruleDetail.circular_attachment_name as circularAttachmentName, " + "empComment.id as employeeCommentId, "
            + "empComment.management_remark as managementComment, "
            + "DATE_FORMAT(empComment.manager_remarked_date, '%b %d, %Y') as managementCommentedDate "
            + "from project_detail as projectDetail "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "inner join  project_rule_detail as ruleDetail on ruleDetail.id = ruleRelation.project_rule_id "
            + "inner join  provision_book_detail as bookDetail on bookDetail.id = ruleDetail.provision_book_id "
            + "left join  employee_review_comment as empComment on empComment.project_rule_relation_id = ruleRelation.id "
            + "where projectDetail.id =:projectId " + "order by bookDetail.book_name asc";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId)
            .setResultTransformer(Transformers.aliasToBean(ManagementPreviewDetailsResponseTO.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ReviewerPreviewDetailsResponseTO> reviewerPreviewerDetail(Integer projectId) {
        Session session = this.getSession();
        String nativeSql = "Select projectDetail.id as projectId, projectDetail.project_name as projectName, "
            + "projectDetail.project_submitted_date as projectSubmittedDate, projectDetail.proect_unique_id as "
            + "projectUniqueId, "
            + "projectDetail.proj_version_num as projectVersionNum, projectDetail.project_logo as projectLogoName, "
            + "bookDetail.id as bookId, bookDetail.book_name as bookName, "
            + "ruleDetail.id as ruleId, ruleDetail.rule_title as ruleTitle, "
            + "ruleDetail.rule_subclause_num as ruleSubclauseNum, " + "ruleDetail.rule_description as ruleDescription, "
            + "ruleDetail.rule_responsibilty as ruleResponsibilty, "
            /*+ "ruleDetail.relevant_circular_name as ruleRelevantCircular, "*/
            + "ruleDetail.circular_attachment_name as circularAttacmentName, " + "empComment.id as employeeCommentId, "
            + "empComment.management_remark as managementComment, "
            + "DATE_FORMAT(empComment.manager_remarked_date, '%b %d, %Y') as managementCommentedDate, "
            + "empComment.reviewer_conformity_status as reviewerConformityStatus, "
            + "empComment.reviewer_conformity_level as reviewerConformityLevel, "
            + "empComment.reviewer_remark as reviewerRemark, "
            + "DATE_FORMAT(empComment.reviewer_remarked_date, '%b %d, %Y') as reviewerRemarkedDate, "
            + "empComment.lead_reviewer_remark as leadReviewerRemark, "
            + "DATE_FORMAT(empComment.lead_reviewer_remarked_date, '%b %d, %Y') as leadReviewerRemarkedDate, "
            + "companyDetail.company_name as comapnyName, " + "managementDetail.emp_name as managementName, "
            + "reviewerDetail.emp_name as reviewerName " + "from company_detail as companyDetail "
            + "inner join project_detail as projectDetail on projectDetail.company_id = companyDetail.id "
            + "inner join employee_detail as managementDetail on managementDetail.id = projectDetail.management_id "
            + "inner join employee_detail as reviewerDetail on reviewerDetail.id = projectDetail.reviewer_id "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "inner join  project_rule_detail as ruleDetail on ruleDetail.id = ruleRelation.project_rule_id "
            + "inner join  provision_book_detail as bookDetail on bookDetail.id = ruleDetail.provision_book_id "
            + "left join  employee_review_comment as empComment on empComment.project_rule_relation_id = ruleRelation.id "
            + "where projectDetail.id =:projectId " + "order by bookDetail.book_name asc ";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId)
            .setResultTransformer(Transformers.aliasToBean(ReviewerPreviewDetailsResponseTO.class));
        return query.list();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ReviewerPreviewDetailsResponseTO> pdfReportReviewerPreviewerDetail(Integer projectId) {
        Session session = this.getSession();
        String nativeSql = "Select projectDetail.id as projectId, projectDetail.project_name as projectName, "
            + "projectDetail.project_submitted_date as projectSubmittedDate, projectDetail.proect_unique_id as "
            + "projectUniqueId, "
            + "projectDetail.proj_version_num as projectVersionNum, projectDetail.project_logo as projectLogoName, "
            + "bookDetail.id as bookId, bookDetail.book_name as bookName, "
            + "ruleDetail.id as ruleId, ruleDetail.rule_title as ruleTitle, "
            + "ruleDetail.rule_subclause_num as ruleSubclauseNum, " + "ruleDetail.rule_description as ruleDescription, "
            + "ruleDetail.rule_responsibilty as ruleResponsibilty, "
            /*+ "ruleDetail.relevant_circular_name as ruleRelevantCircular, "*/
            + "ruleDetail.circular_attachment_name as circularAttacmentName, " + "empComment.id as employeeCommentId, "
            + "empComment.management_remark as managementComment, "
            + "DATE_FORMAT(empComment.manager_remarked_date, '%b %d, %Y') as managementCommentedDate, "
            + "empComment.reviewer_conformity_status as reviewerConformityStatus, "
            + "empComment.reviewer_conformity_level as reviewerConformityLevel, "
            + "empComment.reviewer_remark as reviewerRemark, "
            + "DATE_FORMAT(empComment.reviewer_remarked_date, '%b %d, %Y') as reviewerRemarkedDate, "
            + "empComment.lead_reviewer_remark as leadReviewerRemark, "
            + "DATE_FORMAT(empComment.lead_reviewer_remarked_date, '%b %d, %Y') as leadReviewerRemarkedDate, "
            + "companyDetail.company_name as comapnyName, " + "managementDetail.emp_name as managementName, "
            + "reviewerDetail.emp_name as reviewerName " + "from company_detail as companyDetail "
            + "inner join project_detail as projectDetail on projectDetail.company_id = companyDetail.id "
            + "inner join employee_detail as managementDetail on managementDetail.id = projectDetail.management_id "
            + "inner join employee_detail as reviewerDetail on reviewerDetail.id = projectDetail.reviewer_id "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "inner join  project_rule_detail as ruleDetail on ruleDetail.id = ruleRelation.project_rule_id "
            + "inner join  provision_book_detail as bookDetail on bookDetail.id = ruleDetail.provision_book_id "
            + "left join  employee_review_comment as empComment on empComment.project_rule_relation_id = ruleRelation.id "
            + "where projectDetail.id =:projectId AND empComment.reviewer_na_status =:reviewerNAStatus "
            + "order by bookDetail.book_name asc ";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId)
            .setParameter("reviewerNAStatus", false)
            .setResultTransformer(Transformers.aliasToBean(ReviewerPreviewDetailsResponseTO.class));
        return query.list();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<Integer> savedReviewerLeadReviewerRecord(Integer projectId, boolean isReviewer) {
        Session session = this.getSession();
        String nativeSql = "Select ruleDetail.id as ruleId " + "from project_detail as projectDetail "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "inner join  project_rule_detail as ruleDetail on ruleDetail.id = ruleRelation.project_rule_id "
            + "inner join  employee_review_comment as empComment on empComment.project_rule_relation_id = ruleRelation.id "
            + "where projectDetail.id =:projectId ";

        if (isReviewer) {
            nativeSql = nativeSql + " AND empComment.reviewer_remarked_date IS NOT NULL";
        } else {
            nativeSql = nativeSql + " AND empComment.lead_reviewer_remarked_date IS NOT NULL";
        }

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId);
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ProjectListResponseTO> getProjectListBasedOnStatus(Integer targetStatusId) {
        Session session = this.getSession();
        String hqlSql = "select projectDetail.projectId as projectId, " + "projectDetail.projectName as projectName "
            + "from ProjectDetail as projectDetail ";

        if(null!=targetStatusId){
            hqlSql+= "where projectDetail.projectStatus =:projectStatusId";
        }

        Query query = session.createQuery(hqlSql);
        if(null!=targetStatusId){
            query.setParameter("projectStatusId", targetStatusId);

        }
        query.setResultTransformer(Transformers.aliasToBean(ProjectListResponseTO.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public MangtWelComeResponseTO getMangtWelComeContent(Integer managementId) {
        Session session = this.getSession();
        String nativeSql = "select companyDetail.companyId as companyId, companyDetail.companyName as companyName,"
            + " projectDetail.projectId as projectId, " + "projectDetail.projectName as projectName, "
            + "projectDetail.projectLogo as projectLogoPath " + "from ProjectDetail as projectDetail "
            + "inner join projectDetail.companyDetail as companyDetail "
            + "inner join projectDetail.employeeDetail as managementDetail "
            + "where projectDetail.employeeDetail.userId =:userId";
        Query query = session.createQuery(nativeSql).setParameter("userId", managementId)
            .setResultTransformer(Transformers.aliasToBean(MangtWelComeResponseTO.class));
        List<MangtWelComeResponseTO> resultList = query.list();
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        }
        return new MangtWelComeResponseTO();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ProjectListResponseTO> getProjectList(Integer projectId) {
        Session session = this.getSession();
        String hqlSql = "select projectDetail.projectId as projectId, " + "projectDetail.projectName as projectName "
            + "from ProjectDetail as projectDetail ";

        if(null!=projectId){
            hqlSql+="where projectDetail.projectId=:projectId";
        }

        Query query = session.createQuery(hqlSql);
        if(null!=projectId){
            query.setParameter("projectId", projectId);
        }
        query.setResultTransformer(Transformers.aliasToBean(ProjectListResponseTO.class));
        return query.list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<ReviewerProjectListResponseTO> getManagementUserProjectList(ReviewerProjectListInputTO userInput,
        String projectImgBaseUrl) {
        Session session = this.getSession();
        String hql = "select companyDetail.companyId as companyId, companyDetail.companyName as comapnyName, "
            + "projectDetail.projectId as projectId, projectDetail.projectLogo as projectLogoPath, "
            + "projectDetail.projectName as projectName, projectDetail.projectDescription as projectDescr, "
            + "projectDetail.projectStatus as projectStatus, projectDetail.projectUniqueId as projectUniqueId, "
            + "DATE_FORMAT(projectDetail.createdDate, '%b %d, %Y') as projectCreatedDate, '" + projectImgBaseUrl
            + "' as projectImgBaseUrl " + "from ProjectDetail as projectDetail "
            + "inner join projectDetail.companyDetail as companyDetail "
            + "where projectDetail.projectStatus >=:projectStatus AND projectDetail.employeeDetail.userId =:userId";

        Query query = session.createQuery(hql);
        query.setParameter("projectStatus", ProjectStatusEnum.OPEN.getId());
        query.setParameter("userId", userInput.getUserId());
        query.setResultTransformer(Transformers.aliasToBean(ReviewerProjectListResponseTO.class));

        List<ReviewerProjectListResponseTO> resultList = query.list();
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public String getManagementUserEmailBasedOnProjectId(Integer projectId) {
        Session session = this.getSession();
        String hql = "select managementUser.userEmailId " + "from ProjectDetail as projectDetail "
            + "inner join projectDetail.employeeDetail as managementUser " + "where projectDetail.projectId =:projectId";

        Query query = session.createQuery(hql);
        query.setParameter("projectId", projectId);
        List<String> resultList = query.list();
        return resultList.get(0);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<RelevantCircularResponseTO> getRulesRelevantCircularList(Integer projectRuleId) {
        Session session = this.getSession();
        String nativeSql = "Select circularDetail.circular_id as circularId, circularDetail.circular_name as circularName, "
            + "circularDetail.circular_description as circularDescription "
            + "from rule_relevant_circular_details as circularDetail "
            + "inner join project_rule_detail as ruleDetail ON ruleDetail.id = circularDetail.project_rule_id "
            + "where ruleDetail.id =:projectRuleId " + "order by circularDetail.circular_id asc";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectRuleId", projectRuleId)
            .setResultTransformer(Transformers.aliasToBean(RelevantCircularResponseTO.class));
        return query.list();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<Object[]> circularNameBasedonProjectId(Integer projectId) {
        Session session = this.getSession();
        String nativeSql = "Select ruleDetail.id as ruleId, circularDetail.circular_name as circularName  "
            + "from project_detail as projectDetail "
            + "inner join project_and_rule_relation as ruleRelation on ruleRelation.project_id = projectDetail.id "
            + "inner join  project_rule_detail as ruleDetail on ruleDetail.id = ruleRelation.project_rule_id "
            + "inner join  rule_relevant_circular_details as circularDetail on circularDetail.project_rule_id = ruleDetail.id "
            + "where projectDetail.id =:projectId ";

        Query query = session.createSQLQuery(nativeSql).setParameter("projectId", projectId);
        return query.list();
    }
}
