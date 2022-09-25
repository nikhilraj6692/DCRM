package com.smp.app.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smp.app.config.JwtTokenUtil;
import com.smp.app.dao.CompanyDao;
import com.smp.app.dao.EmpReviewCommentDao;
import com.smp.app.dao.FileToReturnRequestDetailDao;
import com.smp.app.dao.NotificationDao;
import com.smp.app.dao.ProjectDao;
import com.smp.app.dao.ProjectRuleDao;
import com.smp.app.dao.ProjectRuleRelationDao;
import com.smp.app.dao.ProvisionBookDao;
import com.smp.app.dao.UserDetailDao;
import com.smp.app.entity.CompanyDetail;
import com.smp.app.entity.FileReturnNotificationDetail;
import com.smp.app.entity.FileReturnRuleAssociatedDetail;
import com.smp.app.entity.FileToReturnRequestDetail;
import com.smp.app.entity.NotificationDetail;
import com.smp.app.entity.ProjectDetail;
import com.smp.app.entity.ProjectRuleDetail;
import com.smp.app.entity.ProjectRuleRelation;
import com.smp.app.entity.ProvisionBookDetail;
import com.smp.app.entity.RuleRelevantCircular;
import com.smp.app.entity.UserDetail;
import com.smp.app.entity.UserRule;
import com.smp.app.exception.UnAuthorizedException;
import com.smp.app.pojo.BaseResponse;
import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.pojo.BookListResponseTO;
import com.smp.app.pojo.ChechUserExistenceResponseTO;
import com.smp.app.pojo.CompletedProjectListResponseTo;
import com.smp.app.pojo.ConformityLevelResponseTO;
import com.smp.app.pojo.DeleteAttachmentInputTO;
import com.smp.app.pojo.DeleteImgResponseTO;
import com.smp.app.pojo.FileReturnListResponseTO;
import com.smp.app.pojo.FileReturnNotificationResponseTO;
import com.smp.app.pojo.FileToReturnSaveInputTO;
import com.smp.app.pojo.LoginResponseTO;
import com.smp.app.pojo.NotificationDetailResponseTO;
import com.smp.app.pojo.ProjectListResponseTO;
import com.smp.app.pojo.ProjectReviewerRelationInputTO;
import com.smp.app.pojo.ProjectReviewerResponseTO;
import com.smp.app.pojo.ProjectStatusInputTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.pojo.TokenDetailInputTO;
import com.smp.app.pojo.TokenResponse;
import com.smp.app.pojo.UpdateProjectRuleInputTO;
import com.smp.app.pojo.UserDetailResponseTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.service.SMPService;
import com.smp.app.util.AttachmentUtils;
import com.smp.app.util.CommonUtils;
import com.smp.app.util.ImageUpload;
import com.smp.app.util.MailContents;
import com.smp.app.util.NotificationMessageUtil;
import com.smp.app.util.ProjectStatusEnum;
import com.smp.app.util.SMPAppConstants;
import com.smp.app.util.UserRuleEnum;
import com.smp.app.util.VerificationCodeMail;
import java.io.File;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SMPServiceImpl implements SMPService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMPServiceImpl.class);

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ProjectRuleDao projectRuleDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AttachmentUtils attachmentUtils;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectRuleRelationDao projectRuleRelationDao;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private ProvisionBookDao provisionBookDao;

    @Autowired
    private EmpReviewCommentDao empReviewCommentDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private FileToReturnRequestDetailDao fileToRetuenReqDetailDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public BaseResponse login(UserLoginInputTO loginDetail) {
        UserDetail dbUserDetail = this.userDetailDao.getUserDetailLoginInput(loginDetail);
        if (dbUserDetail != null) {
            // Create JWT token
            TokenResponse jwt = jwtTokenUtil.generateToken(authenticateUser(loginDetail, dbUserDetail));

            dbUserDetail.setToken(jwt.getToken());
            dbUserDetail.setRefreshToken(jwt.getRefreshToken());
            userDetailDao.saveOrUpdate(dbUserDetail);

            LOGGER.info("Token Generated successfully : {}", loginDetail);
            return new BaseResponse(frameSignInResponse(dbUserDetail),
                new BasicResponseTO(SMPAppConstants.SIGN_IN_SUCCESSFUL));
        } else {
            LOGGER.error("Invalid Credentials : {}", loginDetail.toString());
            throw new UnAuthorizedException();
        }
    }

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public BasicResponseTO saveRuleDetail(String ruleDetailStr) {
        BasicResponseTO responseTO = new BasicResponseTO();

        try {
			
			/*ProjectRuleDetail ruleDetail = new ProjectRuleDetail();
			ruleDetail.setRuleSubclauseNum(ruleInputTO.getRuleSubclauseNum());
			ruleDetail.setRuleTitle(ruleInputTO.getRuleTitle());
			ruleDetail.setRuleResponsibilty(ruleInputTO.getRuleResponsibilty());
			ruleDetail.setRuleDescription(ruleInputTO.getRuleDescription());
			ruleDetail.setRuleRelevantCircular(ruleInputTO.getRuleRelevantCircular());
			ruleDetail.setCreatedDate(new Date());
			ruleDetail.setProvisionBookDetail(this.provisionBookDao.read(ruleInputTO.getProvisionBookId()));
			this.projectRuleDao.saveOrUpdate(ruleDetail);*/

            JsonObject ruleDetailJsonObj = new JsonParser().parse(ruleDetailStr).getAsJsonObject();

            ProjectRuleDetail ruleDetail = new ProjectRuleDetail();
            ruleDetail.setRuleSubclauseNum(ruleDetailJsonObj.get("ruleSubclauseNum").getAsString());
            ruleDetail.setRuleTitle(ruleDetailJsonObj.get("ruleTitle").getAsString());
            ruleDetail.setRuleResponsibilty(ruleDetailJsonObj.get("ruleResponsibilty").getAsString());
            ruleDetail.setRuleDescription(ruleDetailJsonObj.get("ruleDescription").getAsString());
            ruleDetail.setCreatedDate(new Date());
            ruleDetail.setProvisionBookDetail(
                this.provisionBookDao.read(ruleDetailJsonObj.get("provisionBookId").getAsInt()));

            List<RuleRelevantCircular> relevantCircularList = new ArrayList<RuleRelevantCircular>();
            JsonArray circularJsonArr = ruleDetailJsonObj.get("ruleRelevantCircularList").getAsJsonArray();
            for (int i = 0; i < circularJsonArr.size(); i++) {
                JsonObject circularJsonObj = circularJsonArr.get(i).getAsJsonObject();
                RuleRelevantCircular relevantCircular = new RuleRelevantCircular();
                relevantCircular.setCircularName(circularJsonObj.get("ruleRelevantCircularName").getAsString());
                relevantCircular.setCircularDescription(circularJsonObj.get("ruleRelevantCircularDescr").getAsString());
                relevantCircular.setProjectRuleDetail(ruleDetail);
                relevantCircularList.add(relevantCircular);
            }
            ruleDetail.setRelevantCircularList(relevantCircularList);
			/*if(file != null) {
				String orgFileName = file.getOriginalFilename();
				String fileUploadFileName = orgFileName.replaceAll("\\s", "");
				ruleDetail.setCircularAttachmentName(fileUploadFileName);
			}*/
            this.projectRuleDao.saveOrUpdate(ruleDetail);
			
			
			/*if(file != null) {// For local commented this code
				String orgFileName = file.getOriginalFilename();
				String fileUploadFileName = ruleDetail.getRuleId()+"_"+orgFileName.replaceAll("\\s", "");
				awsImageUpload.uploadCircularAttachment(file, fileUploadFileName);
			}*/

            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_RULE_SUCCESS_MSG));
        } catch (Exception e) {
            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_RULE_FAILURE_MSG));
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
        return responseTO;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public BasicResponseTO saveCompanyDetail(List<MultipartFile> attachmentList, JsonObject companyDetailsJsonObj) {
        BasicResponseTO responseTO = new BasicResponseTO();

        try {
            MultipartFile fileObj = null;
            String logoFileName = null;
            CompanyDetail companyDetail = new CompanyDetail();
            companyDetail.setCompanyName(companyDetailsJsonObj.get("companyName").getAsString());
            companyDetail.setCreatedDate(new Date());
            if (attachmentList != null && attachmentList.size() > 0) {
                fileObj = attachmentList.get(0);
                logoFileName = fileObj.getOriginalFilename();
            }
            this.companyDao.saveOrUpdate(companyDetail);
            if (fileObj != null) {
                this.attachmentUtils.uploadCompanyLogo(fileObj,
                    this.commonUtils.readUserDefinedMessages(SMPAppConstants.COMPANY_LOGO_PATH_MSG),
                    companyDetail.getCompanyId());
            }
            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_COMPANY_DETAIL_SUCCESS_MSG));

        } catch (Exception e) {
            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_COMPANY_DETAIL_FAILURE_MSG));
            LOGGER.error(e.toString());
        }
        return responseTO;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public BasicResponseTO saveProjectDetail(MultipartFile file, String projectInfo) {
        BasicResponseTO responseTO = new BasicResponseTO();
        JsonObject projectDetailJson = new JsonParser().parse(projectInfo).getAsJsonObject();

        try {
            CompanyDetail companyDetail = new CompanyDetail();
            companyDetail.setCompanyName(projectDetailJson.get("companyName").getAsString());
            companyDetail.setCreatedDate(new Date());

            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setProjectName(projectDetailJson.get("projectName").getAsString());
            projectDetail.setProjectUniqueId(projectDetailJson.get("projectIdInfo").getAsString());
            projectDetail.setProjectDescription(projectDetailJson.get("projectDescription").getAsString());
            projectDetail.setProjectStatus(ProjectStatusEnum.OPEN.getId());
            projectDetail.setCreatedDate(new Date());
            projectDetail.setVersionNum(1);// For new proect setting version num as 1.

            projectDetail.setCompanyDetail(companyDetail);
            companyDetail.getProjectList().add(projectDetail);

            UserDetail userDetail = new UserDetail();
            userDetail.setUserName(projectDetailJson.get("employeeName").getAsString());
            userDetail.setUserEmailId(projectDetailJson.get("empEmailId").getAsString());
            userDetail.setPassword(projectDetailJson.get("empPassword").getAsString());
            userDetail.getProjectList().add(projectDetail);

            UserRule userRule = new UserRule();
            userRule.setRuleId(UserRuleEnum.MANAGEMENT.getId());
            userDetail.setUserRule(userRule);
            this.userDetailDao.saveOrUpdate(userDetail);

            projectDetail.setManagementDetail(userDetail);
            this.companyDao.saveOrUpdate(companyDetail);

            JsonArray ruleIdArray = projectDetailJson.get("ruleListDropDown").getAsJsonArray();
            for (int i = 0; i < ruleIdArray.size(); i++) {
                ProjectRuleRelation projectRuleRelation = new ProjectRuleRelation();
                projectRuleRelation.setProjectDetail(projectDetail);
                projectRuleRelation.setProjectRuleDetail(
                    this.projectRuleDao.read(Integer.parseInt(ruleIdArray.get(i).getAsString())));
                projectRuleRelation.setCreatedDate(new Date());
                this.projectRuleRelationDao.saveOrUpdate(projectRuleRelation);
            }

            if (file != null) {// For local commented this code
                Integer projectId = companyDetail.getProjectList().get(0).getProjectId();
                String orgFileName = file.getOriginalFilename();
                String fileUploadFileName = projectId + "_" + orgFileName.replaceAll("\\s", "");
                imageUpload.uploadProjectImage(file, fileUploadFileName);
                ProjectDetail dbProject = this.projectDao.read(projectId);
                dbProject.setProjectLogo(fileUploadFileName);
            }
            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_DETAIL_SUCCESS_MSG));
        } catch (Exception e) {
            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_DETAIL_FAILURE_MSG));
            LOGGER.error(e.toString());
        }
        return responseTO;
    }


    @Override
    public BasicResponseTO updateProjectStatus(ProjectStatusInputTO projectInputTO) {
        BasicResponseTO responseTO = new BasicResponseTO();
        try {
            ProjectDetail projectDetail = this.projectDao.read(projectInputTO.getProjectId());
            projectDetail.setProjectStatus(projectInputTO.getProjectStatusId());
            if (projectInputTO.getProjectStatusId() == ProjectStatusEnum.LEAD_REVIEWER_COMPLETED.getId()) {
                projectDetail.setProjectSubmittedDate(new Date());
            }
            this.projectDao.saveOrUpdate(projectDetail);
            this.sendNotificationMessage(projectDetail);
            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.UPDATE_PROJECT_STATUS_SUCCESS_MSG));
        } catch (Exception e) {
            responseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            responseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.UPDATE_PROJECT_STATUS_FAILURE_MSG));
            LOGGER.error(e.toString());
        }
        return responseTO;
    }

    private void sendNotificationMessage(ProjectDetail projectDetail) {
        Map<Integer, String> deviceTokenMapObject = null;
        String notoficationMessage = "";

        if (projectDetail.getProjectStatus()
            == ProjectStatusEnum.MANAGEMENT_COMPLETED.getId()) {// Management remark Completed
            deviceTokenMapObject = this.empReviewCommentDao.getDeviceTokenList(UserRuleEnum.MANAGEMENT.getId(),
                projectDetail);
            notoficationMessage = this.commonUtils.readUserDefinedMessages(
                SMPAppConstants.MANAGEMENT_COMPLETION_NOTIFICATION_MSG);
        } else if (projectDetail.getProjectStatus()
            == ProjectStatusEnum.REVIEWER_COMPLETED.getId()) {// Reviewer remark  Completed
            deviceTokenMapObject = this.empReviewCommentDao.getDeviceTokenList(UserRuleEnum.REVIEWER.getId(), projectDetail);
            notoficationMessage = this.commonUtils.readUserDefinedMessages(
                SMPAppConstants.REVIEWER_COMPLETION_NOTIFICATION_MSG);
        } else {//Lead Reviewer remark  Completed
            deviceTokenMapObject = this.empReviewCommentDao.getDeviceTokenList(UserRuleEnum.LEAD_REVIEWER.getId(),
                projectDetail);
            notoficationMessage = this.commonUtils.readUserDefinedMessages(
                SMPAppConstants.LEAD_REVIEWER_COMPLETION_NOTIFICATION_MSG);
        }
        if (deviceTokenMapObject != null && deviceTokenMapObject.size() > 0) {
            NotificationMessageUtil.send_FCM_NotificationMulti(new ArrayList<String>(deviceTokenMapObject.values()),
                notoficationMessage);

            if (projectDetail.getProjectStatus() == ProjectStatusEnum.REVIEWER_COMPLETED.getId()
                || projectDetail.getProjectStatus() == ProjectStatusEnum.LEAD_REVIEWER_COMPLETED.getId()) {

                NotificationDetail notificationDetail = new NotificationDetail();
                notificationDetail.setNotifyMessage(notoficationMessage);
                notificationDetail.setProjectDetail(projectDetail);
                notificationDetail.setNotifiedDate(new Date());
                notificationDetail.setNotificationSendToDetail(
                    this.formatNotificationSendToUser(deviceTokenMapObject.keySet()));
                this.notificationDao.saveOrUpdate(notificationDetail);
            }
        }
    }

    private String formatNotificationSendToUser(Set<Integer> userIdSet) {
        Set<Integer> keySet = userIdSet;
        String keyStr = keySet.toString();
        keyStr = keyStr.substring(1, keyStr.length() - 1).replaceAll("\\s", "");
        keyStr = "," + keyStr + ",";
        return keyStr;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Map<String, List<RuleListResponseTO>> getRuleList() {
        //return this.projectRuleDao.getRuleList();
        List<ProvisionBookDetail> bookList = provisionBookDao.getBookList();
        Map<String, List<RuleListResponseTO>> ruleListDetail = new LinkedHashMap<String, List<RuleListResponseTO>>();
        for (ProvisionBookDetail bookDetail : bookList) {
            List<RuleListResponseTO> ruleResponseList = new ArrayList<RuleListResponseTO>();
            for (ProjectRuleDetail ruleDetail : bookDetail.getProjectRuleList()) {
                RuleListResponseTO ruleResponseObj = new RuleListResponseTO();
                ruleResponseObj.setRuleId(ruleDetail.getRuleId());
                ruleResponseObj.setRuleTitle(ruleDetail.getRuleSubclauseNum());
                ruleResponseList.add(ruleResponseObj);
            }
            if (ruleResponseList.size() > 0) {
                ruleListDetail.put(bookDetail.getBookName(), ruleResponseList);
            }
        }
        return ruleListDetail;
    }


    @Override
    public ChechUserExistenceResponseTO chechUserExistence(String userEmailId) {
        ChechUserExistenceResponseTO responseTO = new ChechUserExistenceResponseTO();
        UserDetail userDetail = this.userDetailDao.getUserBasedEmail(userEmailId);
        if (userDetail == null) {
            responseTO.setIsUserExisted(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            responseTO.setServiceMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.CHECK_USER_EXISTENCE_SUCCESS_MSG));
        } else {
            responseTO.setIsUserExisted(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            responseTO.setServiceMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.CHECK_USER_EXISTENCE_FAILURE_MSG));
        }
        return responseTO;
    }


    @Override
    public List<BookListResponseTO> getBookList() {
        return this.projectRuleDao.getBookList();
    }


    @Override
    public ProjectReviewerResponseTO getProjectAndReviewerList() {
        ProjectReviewerResponseTO responseTO = new ProjectReviewerResponseTO();
        responseTO.setProjectList(this.projectDao.getProjectListReviewerRelation());
        if (responseTO.getProjectList().size() != 0) {
            responseTO.setReviewerList(this.userDetailDao.getUserListReviewerRelation());
        }
        return responseTO;
    }


    @Override
    public BasicResponseTO saveProjectReviewerRelation(ProjectReviewerRelationInputTO reviewerRelationInput) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        try {
            ProjectDetail projectDetail = projectDao.read(reviewerRelationInput.getProjectId());
            projectDetail.setReviewerId(reviewerRelationInput.getReviewerId());
            this.projectDao.saveOrUpdate(projectDetail);
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_REVIEWER_RELATION_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_REVIEWER_RELATION_FAILURE_MSG));
        }
        return basicResponseTO;
    }


    @Override
    public BasicResponseTO updateReviewerTokenDetail(TokenDetailInputTO tokenInput) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();

        try {
            UserDetail userDetail = this.userDetailDao.read(tokenInput.getUserId());
            userDetail.setDeviceTokenValue(tokenInput.getDeviceTokenValue());
            this.userDetailDao.saveOrUpdate(userDetail);
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.UPDATE_TOKEN_SERVICE_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.UPDATE_TOKEN_SERVICE_FAILURE_MSG));
        }
        return basicResponseTO;
    }


    @Override
    public List<NotificationDetailResponseTO> getNotificationList(Integer UserId) {
        List<NotificationDetailResponseTO> resultList = this.notificationDao.getNotificationRecords(UserId);
        if (resultList != null && resultList.size() == 10) {
            String lastRecordDate = resultList.get(9).getNotifiedDate();
            this.notificationDao.deleteNotificationRecord(lastRecordDate);
        }
        return resultList;
    }


    @Override
    public Map<Integer, String> reviewerConformityLevelMapObj() {

        Map<Integer, String> resultMapObject = new LinkedHashMap<Integer, String>();
        List<ConformityLevelResponseTO> conformityLevelList = this.projectDao.getReviewerConformityLevel();
        for (ConformityLevelResponseTO conformityLevelObj : conformityLevelList) {
            resultMapObject.put(conformityLevelObj.getLevelId(), conformityLevelObj.getConformityLevel());
        }
        return resultMapObject;
    }


    public Boolean uploadProjectReport(File file, Integer projectId, Integer projVersionNum, String companyName) {
        try {
            //this.deleteProjectReportFromAWS(projectId+"_project_report.pdf");
            String folderName = this.commonUtils.readUserDefinedMessages("smp.project_report.folder_name").trim();
            attachmentUtils.uploadFile(file,
                folderName + "/"/*+projectId+"/"*/ + projectId + "_" + companyName + "_v" + projVersionNum + ".pdf", null);
            LOGGER.error("Project Report  Uploaded Successfully !!!!!!!!!!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<CompletedProjectListResponseTo> getCompletedProjectList() {
        return this.projectDao.getProjectListBasedOnStatus(ProjectStatusEnum.LEAD_REVIEWER_COMPLETED.getId());
    }


    @Override
    public BasicResponseTO changeCompProjectStatus(Integer projectId) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        try {
            ProjectDetail projectObj = this.projectDao.read(projectId);
            projectObj.setProjectStatus(ProjectStatusEnum.OPEN.getId());
            projectObj.setVersionNum(projectObj.getVersionNum() + 1);
            this.projectDao.saveOrUpdate(projectObj);
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.UPDATE_COMPL_PROJECT_STATUS_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.UPDATE_COMPL_PROJECT_STATUS_FAILURE_MSG));
        }
        return basicResponseTO;
    }


    @Override
    public List<CompletedProjectListResponseTo> getOpenStateProjectList() {
        return this.projectDao.getProjectListBasedOnStatus(ProjectStatusEnum.OPEN.getId());
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public Map<String, List<RuleListResponseTO>> getNewBooksForExistingProject(Integer projectId) {

        //Set<Integer> bookIdsForExistingProject = this.provisionBookDao.getBookIdsBasedOnProjectId(projectId);
        Set<Integer> ruleIdAssociatedProjId = new HashSet<Integer>(this.projectDao.getRuleIdBasedProjectId(projectId));

        List<ProvisionBookDetail> bookList = provisionBookDao.getBookList();
        Map<String, List<RuleListResponseTO>> ruleListDetail = new LinkedHashMap<String, List<RuleListResponseTO>>();
        for (ProvisionBookDetail bookDetail : bookList) {
            /*if(!bookIdsForExistingProject.contains(bookDetail.getBookId())) {*/
            List<RuleListResponseTO> ruleResponseList = new ArrayList<RuleListResponseTO>();
            for (ProjectRuleDetail ruleDetail : bookDetail.getProjectRuleList()) {
                if (!ruleIdAssociatedProjId.contains(ruleDetail.getRuleId())) {
                    RuleListResponseTO ruleResponseObj = new RuleListResponseTO();
                    ruleResponseObj.setRuleId(ruleDetail.getRuleId());
                    ruleResponseObj.setRuleTitle(ruleDetail.getRuleSubclauseNum());
                    ruleResponseList.add(ruleResponseObj);
                }
            }
            if (ruleResponseList.size() > 0) {
                ruleListDetail.put(bookDetail.getBookName(), ruleResponseList);
            }
            /*}*/
        }
        return ruleListDetail;
    }


    @Override
    public BasicResponseTO updateProjectRuleDetail(UpdateProjectRuleInputTO updateProjInput) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();

        try {
            ProjectDetail projectDetail = this.projectDao.read(updateProjInput.getProjectId());
            for (Integer ruleId : updateProjInput.getRuleIdList()) {
                ProjectRuleRelation projectRuleRelation = new ProjectRuleRelation();
                projectRuleRelation.setProjectDetail(projectDetail);
                projectRuleRelation.setProjectRuleDetail(this.projectRuleDao.read(ruleId));
                projectRuleRelation.setCreatedDate(new Date());
                this.projectRuleRelationDao.saveOrUpdate(projectRuleRelation);
            }
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_DETAIL_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.SAVE_PROJECT_DETAIL_FAILURE_MSG));
        }
        return basicResponseTO;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public ProjectDetail readProjectDetail(Integer projectId) {
        ProjectDetail dbProjetDetail = this.projectDao.read(projectId);
        Hibernate.initialize(dbProjetDetail.getCompanyDetail());
        return dbProjetDetail;
    }


    @Override
    public DeleteImgResponseTO deleteUploadedAttachment(DeleteAttachmentInputTO attachmentInputTO) {
        DeleteImgResponseTO basicResponseTO = new DeleteImgResponseTO();
        try {
            imageUpload.deleteReviewerImage(attachmentInputTO.getRuleRelationId(), attachmentInputTO.getImageName());
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage("Attachment deleted successfully.");
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage("Failed to delete attachment");
        }
        basicResponseTO.setImgFullURL(attachmentInputTO.getFullImageURL());
        return basicResponseTO;
    }


    @Override
    public List<ProjectListResponseTO> getProjectList() {
        return this.projectDao.getProjectList();
    }


    @Override
    public Map<String, List<RuleListResponseTO>> getRuleListBasedOnProjectId(Integer projectId) {
        List<Object[]> dbResultList = this.projectRuleDao.getRuleListBasedOnProjectId(projectId);

        Map<String, List<RuleListResponseTO>> ruleListDetail = new LinkedHashMap<String, List<RuleListResponseTO>>();
        List<RuleListResponseTO> ruleResponseList = null;

        for (Object[] record : dbResultList) {
            RuleListResponseTO ruleResponseObj = new RuleListResponseTO();
            ruleResponseObj.setRuleId(Integer.parseInt(record[1].toString()));
            ruleResponseObj.setRuleTitle(record[2].toString());
            //ruleResponseList.add(ruleResponseObj);

            if (ruleListDetail.containsKey(record[0].toString())) {
                ruleResponseList = ruleListDetail.get(record[0].toString());
                ruleResponseList.add(ruleResponseObj);
            } else {
                ruleResponseList = new ArrayList<RuleListResponseTO>();
                ruleResponseList.add(ruleResponseObj);
            }
            ruleListDetail.put(record[0].toString(), ruleResponseList);
        }

        return ruleListDetail;
    }


    @Override
    public BasicResponseTO saveFileToReturnRequest(FileToReturnSaveInputTO fileToRequestInputTO) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        try {
            FileToReturnRequestDetail fileToReturnRequestDetail = new FileToReturnRequestDetail();
            fileToReturnRequestDetail.setStartDate(fileToRequestInputTO.getStartDate());
            fileToReturnRequestDetail.setEndDate(fileToRequestInputTO.getEndDate());
            fileToReturnRequestDetail.setCreatedDate(new Date());
            fileToReturnRequestDetail.setFileReturnStatus(1);// Here 1 means Open status
            fileToReturnRequestDetail.setProjectDetail(this.projectDao.read(fileToRequestInputTO.getSelectedProjectId()));

            List<FileReturnRuleAssociatedDetail> ruleAssociationList = new ArrayList<FileReturnRuleAssociatedDetail>();
            for (String ruleId : fileToRequestInputTO.getSelectedRuleIdList()) {
                FileReturnRuleAssociatedDetail associatedDetail = new FileReturnRuleAssociatedDetail();
                associatedDetail.setProjectRuleDetail(this.projectRuleDao.read(Integer.parseInt(ruleId)));
                associatedDetail.setFileToReturnRequestDetail(fileToReturnRequestDetail);
                ruleAssociationList.add(associatedDetail);
            }
            fileToReturnRequestDetail.setFileReturnRuleAssociationList(ruleAssociationList);

            List<FileReturnNotificationDetail> notificationList = new ArrayList<FileReturnNotificationDetail>();
            FileReturnNotificationDetail notificationDetail = new FileReturnNotificationDetail();
            notificationDetail.setNotificationMessage(
                MailContents.notificationMessageText(fileToReturnRequestDetail.getEndDate()));
            notificationDetail.setNotificationStatus(true);
            notificationDetail.setCreatedDate(new Date());
            notificationDetail.setFileToReturnRequestDetail(fileToReturnRequestDetail);
            notificationList.add(notificationDetail);
            fileToReturnRequestDetail.setFileReturnNotificationList(notificationList);

            this.fileToRetuenReqDetailDao.saveOrUpdate(fileToReturnRequestDetail);
            this.sendMailToManagementUser(fileToReturnRequestDetail.getEndDate(), fileToRequestInputTO);

            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.FILE_RETURN_REQUEST_DETAIL_STATUS_SUCCESS_MSG));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
            basicResponseTO.setResponseMessage(
                this.commonUtils.readUserDefinedMessages(SMPAppConstants.FILE_RETURN_REQUEST_DETAIL_STATUS_FAILURE_MSG));
        }
        return basicResponseTO;
    }


    private void sendMailToManagementUser(String endDate, FileToReturnSaveInputTO fileToRequestInputTO) {

        List<Integer> formatedRuleIdsList = fileToRequestInputTO.getSelectedRuleIdList().stream()
            .map(s -> Integer.parseInt(s)).collect(Collectors.toList());

        List<Object[]> ruleDetailArr = this.projectRuleDao.getRuleListBasedOnRuleIds(formatedRuleIdsList);
        List<String> formatedRuleList = new ArrayList<String>();
        for (Object[] targetObj : ruleDetailArr) {
            formatedRuleList.add(targetObj[0] + " / " + targetObj[1]);
        }

        List<String> toEmailList = new ArrayList<String>();
        toEmailList.add(this.projectDao.getManagementUserEmailBasedOnProjectId(fileToRequestInputTO.getSelectedProjectId()));

        Thread thread2 = new Thread(new VerificationCodeMail(toEmailList, "awstesting25112918@gmail.com",
            MailContents.notificationMailBodyDetail(endDate, formatedRuleList), MailContents.notificationMailSubjectLine()));
        thread2.start();
    }


    @Override
    public List<FileReturnNotificationResponseTO> getNotificationBasedOnProjectId(Integer projectId) {
        return this.fileToRetuenReqDetailDao.getNotificationBasedOnProjectId(projectId);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public List<FileReturnListResponseTO> getFileReturnRequestListBasedOnProjectId(Integer projectId) {
        List<FileReturnListResponseTO> responseListObj = new ArrayList<FileReturnListResponseTO>();

        List<FileToReturnRequestDetail> returnResultList =
            this.fileToRetuenReqDetailDao.getReturnRequestListBasedOnProjectId(
            projectId);
        for (FileToReturnRequestDetail returnRequestDetail : returnResultList) {
            FileReturnListResponseTO responseTO = new FileReturnListResponseTO();
            responseTO.setFileToReturnRequestId(returnRequestDetail.getId());
            responseTO.setProjectName(returnRequestDetail.getProjectDetail().getProjectName());
            responseTO.setReturnStatus(returnRequestDetail.getFileReturnStatus().toString());
            responseTO.setCreatedDate(returnRequestDetail.getCreatedDate().toString());
            responseTO.setEndDate(this.formatDateStr(returnRequestDetail.getEndDate()));
            List<String> ruleList = new ArrayList<String>();
            for (FileReturnRuleAssociatedDetail ruleAssociatedDetail :
                returnRequestDetail.getFileReturnRuleAssociationList()) {
                ProjectRuleDetail ruleDetail = ruleAssociatedDetail.getProjectRuleDetail();
                ruleList.add(ruleDetail.getProvisionBookDetail().getBookName() + " / " + ruleDetail.getRuleSubclauseNum());
            }
            responseTO.setReturnRequestRuleList(ruleList);
            responseListObj.add(responseTO);
        }

        return responseListObj;
    }


    private String formatDateStr(String originalDateStr) {
        String convertedDate = "";
        try {
            Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(originalDateStr);
            SimpleDateFormat formatYearVal = new SimpleDateFormat("yyyy");
            String[] months = new DateFormatSymbols().getMonths();
            convertedDate = months[date1.getMonth()] + " " + date1.getDate() + ", " + formatYearVal.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    public FileReturnListResponseTO getReturnDetailBasedOnRequestId(Integer requestId) {

        FileToReturnRequestDetail returnRequestDetail = this.fileToRetuenReqDetailDao.getReturnDetailBasedOnRequestId(
            requestId);

        FileReturnListResponseTO responseTO = new FileReturnListResponseTO();
        responseTO.setFileToReturnRequestId(returnRequestDetail.getId());
        responseTO.setProjectId(returnRequestDetail.getProjectDetail().getProjectId());
        responseTO.setProjectName(returnRequestDetail.getProjectDetail().getProjectName());
        responseTO.setReturnStatus(returnRequestDetail.getFileReturnStatus().toString());
        responseTO.setCreatedDate(returnRequestDetail.getCreatedDate().toString());
        responseTO.setStartDate(returnRequestDetail.getStartDate());
        responseTO.setEndDate(returnRequestDetail.getEndDate());

        List<String> ruleList = new ArrayList<String>();
        for (FileReturnRuleAssociatedDetail ruleAssociatedDetail : returnRequestDetail.getFileReturnRuleAssociationList()) {
            ProjectRuleDetail ruleDetail = ruleAssociatedDetail.getProjectRuleDetail();
            ruleList.add(ruleDetail.getProvisionBookDetail().getBookName() + " / " + ruleDetail.getRuleSubclauseNum());
        }
        responseTO.setReturnRequestRuleList(ruleList);
        return responseTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true)
    private String getProjectName(Integer returnReqestId) {
        FileToReturnRequestDetail returnRequestDetail = this.fileToRetuenReqDetailDao.read(returnReqestId);
        return returnRequestDetail.getProjectDetail().getProjectName();
    }

    @Override
    public BasicResponseTO updateFileReturnDetail(MultipartFile file, String fileReturnDetail) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        try {
            JsonObject fileReturnJsonObj = new JsonParser().parse(fileReturnDetail).getAsJsonObject();
            FileToReturnRequestDetail returnRequestDetail = this.fileToRetuenReqDetailDao.read(
                fileReturnJsonObj.get("returnRequestId").getAsInt());
            String projectName = this.getProjectName(fileReturnJsonObj.get("returnRequestId").getAsInt());

            if (fileReturnJsonObj.get("returnStatus").getAsBoolean()) {
                returnRequestDetail.setFileReturnStatus(2);

                // -- Send Notification to Admin--
                List<String> toEmailList = new ArrayList<String>();
                toEmailList.add("contact@minemagma.com");
                Thread thread2 = new Thread(new VerificationCodeMail(toEmailList, "awstesting25112918@gmail.com",
                    MailContents.fileReturnComlpetionMailBodyDetail(projectName),
                    MailContents.fileReturnComlpetionSubjectLine()));
                thread2.start();


            } else {
                returnRequestDetail.setFileReturnStatus(1);
            }
            this.imageUpload.uploadFileReturnAttachments(file, file.getOriginalFilename(), returnRequestDetail.getId());
            this.fileToRetuenReqDetailDao.saveOrUpdate(returnRequestDetail);
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_SUCCESS_STATUS)));
        } catch (Exception e) {
            basicResponseTO.setResponseStatus(
                Boolean.parseBoolean(this.commonUtils.readUserDefinedMessages(SMPAppConstants.SERVICE_FAILURE_STATUS)));
        }

        return basicResponseTO;
    }

    private Authentication authenticateUser(UserLoginInputTO loginRequest, UserDetail user) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUserEmailId(), loginRequest.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUserRule().getRuleName())));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private Object frameSignInResponse(UserDetail dbUserDetail) {
        UserDetailResponseTO userDetailResponse = new UserDetailResponseTO();
        userDetailResponse.setUserId(dbUserDetail.getUserId());
        userDetailResponse.setUsername(dbUserDetail.getUserName());
        userDetailResponse.setUserEmailId(dbUserDetail.getUserEmailId());
        userDetailResponse.setUserRuleType(dbUserDetail.getUserRule().getRuleId());
        userDetailResponse.setToken(dbUserDetail.getToken());
        userDetailResponse.setRefreshToken(dbUserDetail.getRefreshToken());
        return userDetailResponse;
    }
}