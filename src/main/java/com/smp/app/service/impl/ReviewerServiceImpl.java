package com.smp.app.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smp.app.dao.EmpReviewCommentDao;
import com.smp.app.dao.ProjectDao;
import com.smp.app.dao.ProjectRuleDao;
import com.smp.app.dao.RelevantCircularDao;
import com.smp.app.entity.ProjectRuleDetail;
import com.smp.app.entity.RuleRelevantCircular;
import com.smp.app.entity.UserReviewComments;
import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.pojo.ConformityLevelResponseTO;
import com.smp.app.pojo.PreviewResponseListTO;
import com.smp.app.pojo.PreviewReviewerRemarkInputTO;
import com.smp.app.pojo.ProjectRemarkStatusResponseTO;
import com.smp.app.pojo.ReviewerPreviewDetailsResponseTO;
import com.smp.app.pojo.ReviewerProjectDetailResponseTO;
import com.smp.app.pojo.ReviewerProjectListInputTO;
import com.smp.app.pojo.ReviewerProjectListResponseTO;
import com.smp.app.pojo.ReviewerProjectRulesDao;
import com.smp.app.pojo.ReviewerProjectRulesInputTO;
import com.smp.app.pojo.ReviewerRuleDetailResponseTO;
import com.smp.app.pojo.SaveReviewerResponseTO;
import com.smp.app.pojo.saveLeadReviewerRemarkInputTO;
import com.smp.app.service.ReviewerService;
import com.smp.app.util.CommonUtils;
import com.smp.app.util.ImageUpload;
import com.smp.app.util.PdfReportRuleSorting;
import com.smp.app.util.ReviewerPreviewSortByRuleTitle;
import com.smp.app.util.SMPAppConstants;
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
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReviewerServiceImpl implements ReviewerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewerServiceImpl.class);

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EmpReviewCommentDao empReviewCommentDao;


    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private ProjectRuleDao projectRuleDao;

    @Autowired
    private RelevantCircularDao relevantCircularDao;


    @Override
    public List<ReviewerProjectListResponseTO> getReviewerProjectList(ReviewerProjectListInputTO userInput) {
        String projectImgBaseUrl = this.commonUtils.readUserDefinedMessages("smp.cloud.project_logo.read_image_base_url");
        return this.projectDao.getReviewerProjectList(userInput, projectImgBaseUrl);
    }

    @Override
    public ReviewerProjectDetailResponseTO getReviewerProjectRules(ReviewerProjectRulesInputTO projectRulesInputTO) {
        List<Integer> ruleIdsList = this.projectDao.getRuleIdBasedProjectId(projectRulesInputTO.getProjectId());
        List<ReviewerProjectRulesDao> dbProjectDetailList = null;
        List<ConformityLevelResponseTO> conformityLevelList = null;
        String projectImgBaseUrl = this.commonUtils.readUserDefinedMessages("smp.cloud.project_logo.read_image_base_url");
        if (projectRulesInputTO.isInitialRuleStatus()) {
            dbProjectDetailList = this.projectDao.getReviewerProjectRules(projectRulesInputTO.getProjectId(),
                projectRulesInputTO.getRuleId());
            conformityLevelList = this.projectDao.getReviewerConformityLevel();
        } else {
            dbProjectDetailList = this.projectDao.getReviewerProjectRules(projectRulesInputTO.getProjectId(),
                projectRulesInputTO.getRuleId());
        }

        ReviewerProjectDetailResponseTO projectRuleDetail = new ReviewerProjectDetailResponseTO();

        for (ReviewerProjectRulesDao dbRecord : dbProjectDetailList) {
            if (projectRuleDetail.getProjectId() == null) {
                projectRuleDetail.setProjectId(dbRecord.getProjectId());
                projectRuleDetail.setProjectName(dbRecord.getProjectName());
                projectRuleDetail.setProjectStatus(dbRecord.getProjectStatus());
                projectRuleDetail.setProjectLogoUrl(projectImgBaseUrl + dbRecord.getProjectLogoName());
                if (conformityLevelList != null) {
                    projectRuleDetail.setConformityLevelList(conformityLevelList);
                }
            }
            ReviewerRuleDetailResponseTO ruleObject = new ReviewerRuleDetailResponseTO();
            ruleObject.setRuleId(dbRecord.getRuleId());
            ruleObject.setRelationId(dbRecord.getRelationId());
            ruleObject.setEmployeeCommentId(dbRecord.getEmployeeCommentId());
            ruleObject.setRuleTitle(dbRecord.getRuleTitle());
            ruleObject.setRuleSubclauseNum(dbRecord.getRuleSubclauseNum());
            ruleObject.setRuleDescription(dbRecord.getRuleDescription());
            ruleObject.setRuleResponsibilty(dbRecord.getRuleResponsibilty());
            ruleObject.setRuleRelevantCircular(dbRecord.getRuleRelevantCircular());
            ruleObject.setReviewerRemarkNAstatus(dbRecord.isReviewerRemarkNAstatus());
            ruleObject.setManagementComment(dbRecord.getManagementComment());
            ruleObject.setManagementCommentedDate(dbRecord.getManagementCommentedDate());
            ruleObject.setReviewerConformaityStatus(dbRecord.getReviewerConformaityStatus());
            ruleObject.setReviewerConformaityLevel(String.valueOf(dbRecord.getReviewerConformaityLevel()));
            ruleObject.setReviewerRemark(dbRecord.getReviewerRemark());
            ruleObject.setReviewerRemarkedDate(dbRecord.getReviewerRemarkedDate());
            ruleObject.setLeadReviewerRemark(dbRecord.getLeadReviewerRemark());
            ruleObject.setLeadReviewerRemarkedDate(dbRecord.getLeadReviewerRemarkedDate());
            ruleObject.setRelevantCircularList(this.projectDao.getRulesRelevantCircularList(ruleObject.getRuleId()));

            List<String> reviewerImageList = this.imageUpload.getReviewerImageList(ruleObject.getEmployeeCommentId());
            ruleObject.setReviewerImageList(reviewerImageList);

            if (dbRecord.getCircularAttachmentName() != null && !dbRecord.getCircularAttachmentName().isEmpty()) {
                ruleObject.setCircularAttachmentName(
                    this.commonUtils.readUserDefinedMessages("smp.cloud.circular_attachment.read_image_base_url")
                        + dbRecord.getRuleId() + "_" + dbRecord.getCircularAttachmentName());
            }
            projectRuleDetail.getRuleDetailList().add(ruleObject);
        }
        projectRuleDetail.setRuleIdsList(ruleIdsList);
        projectRuleDetail.setReviewerSavedRecordList(
            this.projectDao.savedReviewerLeadReviewerRecord(projectRuleDetail.getProjectId(), true));
        projectRuleDetail.setLeadReviewerSavedRecordList(
            this.projectDao.savedReviewerLeadReviewerRecord(projectRuleDetail.getProjectId(), false));
        return projectRuleDetail;
    }

    @Override
    public SaveReviewerResponseTO saveReviewerRemark(List<MultipartFile> attachmentList, String reviewerRemarkDetails) {
        SaveReviewerResponseTO basicResponseTO = new SaveReviewerResponseTO();
        List<String> reviewerImgList = new ArrayList<String>();
        try {
            JsonObject remarkDetailJsonObj = new JsonParser().parse(reviewerRemarkDetails).getAsJsonObject();
            UserReviewComments reviewComment = this.empReviewCommentDao.read(
                remarkDetailJsonObj.get("employeeCommentId").getAsInt());
            reviewComment.setReviewerConformityStatus(remarkDetailJsonObj.get("reviewerConformityStatus").getAsInt());
            reviewComment.setReviewerConformityLevel(remarkDetailJsonObj.get("reviewerConformityLevel").getAsInt());
            reviewComment.setReviewerRemark(remarkDetailJsonObj.get("reviewerRemark").getAsString());
            reviewComment.setReviewerNAStatus(remarkDetailJsonObj.get("remarkNAstatus").getAsBoolean());
            reviewComment.setReviewerRemarkedDate(new Date());

            this.empReviewCommentDao.saveOrUpdate(reviewComment);
            if (attachmentList != null && attachmentList.size() > 0) {
                for (MultipartFile fileObj : attachmentList) {
                    String orgFileName = fileObj.getOriginalFilename();
                    String fileUploadFileName = orgFileName.replaceAll("\\s", "");
                    imageUpload.uploadReviewerImage(fileObj, fileUploadFileName, reviewComment.getReviewCommentId());
                    reviewerImgList.add(
                        commonUtils.readUserDefinedMessages("smp.cloud.project_reviewer_images.read_image_base_url").trim() + reviewComment.getReviewCommentId() + "/" + fileUploadFileName);
                    basicResponseTO.setReviewerImageList(reviewerImgList);
                }
            }
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_REVIEWER_REMARK_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_REVIEWER_REMARK_FAILURE_MSG));
            LOGGER.error(e.toString());
        }
        return basicResponseTO;
    }

    @Override
    public ProjectRemarkStatusResponseTO checkReviewerProjectRemarkStatus(Integer projectId) {
        ProjectRemarkStatusResponseTO remarkStatusResponseTO = new ProjectRemarkStatusResponseTO();
        Boolean projectRemarkStatus = this.projectDao.checkReviewerProjectRemarkStatus(projectId);
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
    public BasicResponseTO saveLeadReviewerRemark(saveLeadReviewerRemarkInputTO remarkInput) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        try {
            UserReviewComments reviewComment = this.empReviewCommentDao.read(remarkInput.getUserReviewCommentId());
            reviewComment.setLeadReviewerRemark(remarkInput.getLeadReviewerRemark());
            reviewComment.setLeadReviewerRemarkedDate(new Date());
            this.empReviewCommentDao.saveOrUpdate(reviewComment);
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_REVIEWER_REMARK_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_REVIEWER_REMARK_FAILURE_MSG));
            LOGGER.error(e.toString());
        }
        return basicResponseTO;
    }

    @Override
    public ProjectRemarkStatusResponseTO checkLeadReviewerProjectStatus(Integer projectId) {
        ProjectRemarkStatusResponseTO remarkStatusResponseTO = new ProjectRemarkStatusResponseTO();
        Boolean projectRemarkStatus = this.projectDao.checkLeadReviewerProjectStatus(projectId);
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
    public PreviewResponseListTO reviewerPreviewerDetail(Integer projectId) {
        List<ReviewerPreviewDetailsResponseTO> resultList = this.projectDao.reviewerPreviewerDetail(projectId);
        Collections.sort(resultList, new ReviewerPreviewSortByRuleTitle());

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

        resultList.get(0).setCircularAttacmentBaseURL(
            this.commonUtils.readUserDefinedMessages("smp.cloud.circular_attachment.read_image_base_url"));

        PreviewResponseListTO previewResponse = new PreviewResponseListTO();
        previewResponse.setResultList(resultList);
        previewResponse.setCircularNameMapObject(circularNameMapObject);

        return previewResponse;
    }

    @Override
    public PreviewResponseListTO pdfReportReviewerPreviewerDetail(Integer projectId) {

        Map<String, List<ReviewerPreviewDetailsResponseTO>> resultMapObject = new TreeMap<String,
            List<ReviewerPreviewDetailsResponseTO>>();

        List<ReviewerPreviewDetailsResponseTO> resultList = this.projectDao.pdfReportReviewerPreviewerDetail(projectId);
        for (ReviewerPreviewDetailsResponseTO previewDetail : resultList) {
            if (resultMapObject.containsKey(previewDetail.getBookName())) {
                resultMapObject.get(previewDetail.getBookName()).add(previewDetail);
            } else {
                List<ReviewerPreviewDetailsResponseTO> targetList = new LinkedList<ReviewerPreviewDetailsResponseTO>();
                targetList.add(previewDetail);
                resultMapObject.put(previewDetail.getBookName(), targetList);
            }
        }
        //Collections.sort(resultList, new ReviewerPreviewSortByRuleTitle());
        for (String bookTitle : resultMapObject.keySet()) {
            List<ReviewerPreviewDetailsResponseTO> targetRuleList = resultMapObject.get(bookTitle);
            Collections.sort(targetRuleList, new PdfReportRuleSorting());
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

        resultList.get(0).setCircularAttacmentBaseURL(
            this.commonUtils.readUserDefinedMessages("smp.cloud.circular_attachment.read_image_base_url"));

        PreviewResponseListTO previewResponse = new PreviewResponseListTO();
        //previewResponse.setResultList(resultList);
        previewResponse.setPdfResultReviewerMapObj(resultMapObject);
        previewResponse.setCircularNameMapObject(circularNameMapObject);

        return previewResponse;
    }


    @Override
    public BasicResponseTO previewReviewerRemarkUpdate(PreviewReviewerRemarkInputTO remarkInput) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        try {
            UserReviewComments userReviewComments = this.empReviewCommentDao.read(remarkInput.getReviewerCommentId());
            userReviewComments.setReviewerConformityStatus(remarkInput.getConformityStatus());
            userReviewComments.setReviewerConformityLevel(remarkInput.getConformityLevel());
            userReviewComments.setReviewerRemark(remarkInput.getReviewerRemark());
            userReviewComments.setReviewerNAStatus(remarkInput.isRemarkNAstatus());
            userReviewComments.setReviewerRemarkedDate(new Date());
            this.empReviewCommentDao.saveOrUpdate(userReviewComments);
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_REVIEWER_REMARK_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_REVIEWER_REMARK_FAILURE_MSG));
            LOGGER.error(e.toString());
        }
        return basicResponseTO;
    }

    @Override
    public List<ReviewerProjectListResponseTO> getManagementUserProjectList(ReviewerProjectListInputTO userInput) {
        String projectImgBaseUrl = this.commonUtils.readUserDefinedMessages("smp.cloud.project_logo.read_image_base_url");
        return this.projectDao.getManagementUserProjectList(userInput, projectImgBaseUrl);
    }

    @Override
    public BasicResponseTO saveRuleRelevantCircular(String circularJsonStr) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        Map<Integer, ProjectRuleDetail> ruleDetailMap = new HashMap<Integer, ProjectRuleDetail>();
        try {
            JsonObject circularJsonSObj = new JsonParser().parse(circularJsonStr).getAsJsonObject();
            JsonArray ruleIdJsonArr = circularJsonSObj.get("selectedRuleIds").getAsJsonArray();
            JsonArray circularJsonArr = circularJsonSObj.get("ruleRelevantCircularList").getAsJsonArray();

            for (int i = 0; i < ruleIdJsonArr.size(); i++) {
                Integer ruleId = ruleIdJsonArr.get(i).getAsInt();
                ProjectRuleDetail ruleDetail = null;
                if (!ruleDetailMap.containsKey(ruleId)) {
                    ruleDetailMap.put(ruleId, this.projectRuleDao.read(ruleId));
                }
                ruleDetail = ruleDetailMap.get(ruleId);

                for (int j = 0; j < circularJsonArr.size(); j++) {
                    JsonObject circularJsonObj = circularJsonArr.get(j).getAsJsonObject();
                    RuleRelevantCircular relevantCircular = new RuleRelevantCircular();
                    relevantCircular.setCircularName(circularJsonObj.get("ruleRelevantCircularName").getAsString());
                    relevantCircular.setCircularDescription(circularJsonObj.get("ruleRelevantCircularDescr").getAsString());
                    relevantCircular.setProjectRuleDetail(ruleDetail);
                    this.relevantCircularDao.saveOrUpdate(relevantCircular);
                }
            }
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_RELEVANT_CIRCULAR_SUCCESS_MSG));
        } catch (Exception e) {

            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_RELEVANT_CIRCULAR_FAILURE_MSG));
            LOGGER.error(e.toString());
        }

        return basicResponseTO;
    }

}
