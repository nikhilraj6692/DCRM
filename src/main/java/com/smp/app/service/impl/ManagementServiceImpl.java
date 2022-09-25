package com.smp.app.service.impl;

import com.smp.app.dao.EmpReviewCommentDao;
import com.smp.app.dao.ProjectDao;
import com.smp.app.dao.ProjectRuleRelationDao;
import com.smp.app.dao.ProvisionBookDao;
import com.smp.app.dao.UserDetailDao;
import com.smp.app.entity.UserReviewComments;
import com.smp.app.pojo.ManagementPreviewDetailsResponseTO;
import com.smp.app.pojo.ManagementRuleListResponseTO;
import com.smp.app.pojo.MangtWelComeResponseTO;
import com.smp.app.pojo.PreviewResponseListTO;
import com.smp.app.pojo.ProjectRemarkStatusResponseTO;
import com.smp.app.pojo.ProjectRulesDaoTO;
import com.smp.app.pojo.RuleDetailResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.pojo.SaveManagementRemarkInputTO;
import com.smp.app.pojo.SaveManagementRemarkResponseTO;
import com.smp.app.service.ManagementService;
import com.smp.app.util.CommonUtils;
import com.smp.app.util.ManagementPreviewSortByRuleTitle;
import com.smp.app.util.SMPAppConstants;
import com.smp.app.util.TestComp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManagementServiceImpl implements ManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagementServiceImpl.class);

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EmpReviewCommentDao empReviewCommentDao;

    @Autowired
    private ProjectRuleRelationDao projectRuleRelationDao;

    @Autowired
    private ProvisionBookDao provisionBookDao;

    @Override
    public ManagementRuleListResponseTO getProjectRules(Integer managementId) {
        List<ProjectRulesDaoTO> resultList = this.projectDao.getProjectRules(managementId);

        ManagementRuleListResponseTO projectRuleDetail = new ManagementRuleListResponseTO();

        for (ProjectRulesDaoTO dbRecord : resultList) {
            if (projectRuleDetail.getProjectId() == null) {
                projectRuleDetail.setProjectId(dbRecord.getProjectId());
                projectRuleDetail.setProjectName(dbRecord.getProjectName());
                projectRuleDetail.setProjectStatus(dbRecord.getProjectStatus());
            }
            RuleDetailResponseTO ruleObject = new RuleDetailResponseTO();
            ruleObject.setRuleId(dbRecord.getRuleId());
            ruleObject.setRelationId(dbRecord.getRuleRelationId());
            ruleObject.setEmployeeCommentId(dbRecord.getEmpCommentId());
            ruleObject.setRuleSubClauseNum(dbRecord.getRuleSubClauseNum());
            ruleObject.setRuleTitle(dbRecord.getRuleTitle());
            ruleObject.setRuleDescription(dbRecord.getRuleDescription());
            ruleObject.setRuleResponsibilty(dbRecord.getRuleResponsibilty());
            ruleObject.setRuleRelevantCircular(dbRecord.getRuleRelevantCircular());
            if (dbRecord.getCircularAttachName() != null && !dbRecord.getCircularAttachName().isEmpty()) {
                ruleObject.setCircularAttachName(
                    this.commonUtils.readUserDefinedMessages("smp.cloud.circular_attachment.read_image_base_url")
                        + dbRecord.getRuleId() + "_" + dbRecord.getCircularAttachName());
            }
            ruleObject.setManagementComment(dbRecord.getManagementRemark());
            ruleObject.setRelevantCircularList(this.projectDao.getRulesRelevantCircularList(ruleObject.getRuleId()));

            projectRuleDetail.getProjectRuleList().add(ruleObject);
        }
        return projectRuleDetail;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public SaveManagementRemarkResponseTO saveManagementRemark(SaveManagementRemarkInputTO remarkInputTo) {
        SaveManagementRemarkResponseTO responseDetail = new SaveManagementRemarkResponseTO();
        responseDetail.setProjectRuleRelationId(remarkInputTo.getProjectRuleRelationId());
        responseDetail.setRemarkValue(remarkInputTo.getRemarkValue());
        responseDetail.setRuleId(remarkInputTo.getRuleId());

        try {
            UserReviewComments reviewComment = null;
            if (remarkInputTo.getEmployeeCommentId() != null && remarkInputTo.getEmployeeCommentId() != 0) {
                reviewComment = this.empReviewCommentDao.read(remarkInputTo.getEmployeeCommentId());
            } else {
                reviewComment = new UserReviewComments();
                reviewComment.setProjectRuleRelation(
                    this.projectRuleRelationDao.read(remarkInputTo.getProjectRuleRelationId()));
            }
            reviewComment.setManagementRemark(remarkInputTo.getRemarkValue());
            reviewComment.setManagementRemarkedDate(new Date());

            this.empReviewCommentDao.saveOrUpdate(reviewComment);
            responseDetail.setEmployeeCommentId(reviewComment.getReviewCommentId());
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return responseDetail;
    }


    @Override
    public ProjectRemarkStatusResponseTO checkProjectRemarkStatus(Integer projectId) {
        ProjectRemarkStatusResponseTO remarkStatusResponseTO = new ProjectRemarkStatusResponseTO();
        Boolean projectRemarkStatus = this.projectDao.checkProjectRemarkStatus(projectId);
        remarkStatusResponseTO.setProjectRemarkStatus(projectRemarkStatus);
        if (projectRemarkStatus) {
            remarkStatusResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.MANAG_VALIDATE_PROJECT_REMARK_SUCCESS_MSG));
        } else {
            remarkStatusResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.MANAG_VALIDATE_PROJECT_REMARK_FAILURE_MSG));
        }
        return remarkStatusResponseTO;
    }


    @Override
    public Map<String, List<RuleListResponseTO>> getBookListBasedOnManagementId(Integer managementId) {
        List<Object[]> dbBookRecord = this.provisionBookDao.getBookListBasedOnManagementId(managementId);
        return this.prepareBookListJsonDetail(dbBookRecord);
    }

    @Override
    public Map<String, List<RuleListResponseTO>> getBookListBasedOnProjectId(Integer projectId) {
        List<Object[]> dbBookRecord = this.provisionBookDao.getBookListBasedOnProjectId(projectId);
        return this.prepareBookListJsonDetail(dbBookRecord);
    }

    private Map<String, List<RuleListResponseTO>> prepareBookListJsonDetail(List<Object[]> dbBookRecord) {
        Map<String, List<RuleListResponseTO>> resultMapObject = new TreeMap<String, List<RuleListResponseTO>>();

        for (Object[] dbRecord : dbBookRecord) {
            if (resultMapObject.containsKey(dbRecord[0].toString())) {
                RuleListResponseTO ruleResponseObj = new RuleListResponseTO();
                ruleResponseObj.setRuleId(Integer.parseInt(dbRecord[1].toString()));
                ruleResponseObj.setRuleTitle(dbRecord[2].toString());
                resultMapObject.get(dbRecord[0].toString()).add(ruleResponseObj);
            } else {
                List<RuleListResponseTO> ruleResponseList = new LinkedList<RuleListResponseTO>();
                RuleListResponseTO ruleResponseObj = new RuleListResponseTO();
                ruleResponseObj.setRuleId(Integer.parseInt(dbRecord[1].toString()));
                ruleResponseObj.setRuleTitle(dbRecord[2].toString());
                ruleResponseList.add(ruleResponseObj);
                resultMapObject.put(dbRecord[0].toString(), ruleResponseList);
            }
        }

        // Sort Rule Object
        for (String bookTitle : resultMapObject.keySet()) {
            List<RuleListResponseTO> ruleList = resultMapObject.get(bookTitle);
            //Collections.sort(ruleList, new SortByRuleTitleComparactor());
            Collections.sort(ruleList, new TestComp());
        }
        return resultMapObject;
    }


    @Override
    public PreviewResponseListTO managementPreviewDetails(Integer projectId) {
        List<ManagementPreviewDetailsResponseTO> resultList = this.projectDao.managementPreviewDetails(projectId);
        Collections.sort(resultList, new ManagementPreviewSortByRuleTitle());
        if (resultList != null && resultList.size() > 0) {
            resultList.get(0).setCircularAttachmentBaseURL(
                this.commonUtils.readUserDefinedMessages("smp.cloud.circular_attachment.read_image_base_url"));
        }

        List<Object[]> circularNameArray = this.projectDao.circularNameBasedonProjectId(projectId);
        Map<Integer, List<String>> circularNameMapObject = new HashMap<>();
        circularNameArray.stream().forEach(actionObj -> {
            Integer ruleId = Integer.valueOf(actionObj[0].toString());
            if (circularNameMapObject.containsKey(ruleId)) {
                circularNameMapObject.get(ruleId).add(actionObj[1].toString());
            } else {
                List<String> circularNameList = new ArrayList<String>();
                circularNameList.add(actionObj[1].toString());
                circularNameMapObject.put(ruleId, circularNameList);
            }
        });

        PreviewResponseListTO previewResponse = new PreviewResponseListTO();
        previewResponse.setManagementPreviewList(resultList);
        previewResponse.setCircularNameMapObject(circularNameMapObject);
        return previewResponse;
    }


    @Override
    public MangtWelComeResponseTO getMangtWelComeContent(Integer managementId) {
        MangtWelComeResponseTO mangtWelComeResponseTO = this.projectDao.getMangtWelComeContent(managementId);
        if (mangtWelComeResponseTO.getProjectLogoPath() != null) {
            mangtWelComeResponseTO.setProjectLogoPath(
                this.commonUtils.readUserDefinedMessages("smp.cloud.project_logo.read_image_base_url")
                    + mangtWelComeResponseTO.getProjectLogoPath());
        }
        return mangtWelComeResponseTO;
    }


}
