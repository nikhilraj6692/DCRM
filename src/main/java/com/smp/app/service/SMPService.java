package com.smp.app.service;

import com.google.gson.JsonObject;
import com.smp.app.pojo.BaseResponse;
import com.smp.app.pojo.DeleteAttachmentInputTO;
import com.smp.app.pojo.FileToReturnSaveInputTO;
import com.smp.app.pojo.LoginResponseTO;
import com.smp.app.pojo.ProjectReviewerRelationInputTO;
import com.smp.app.pojo.ProjectStatusInputTO;
import com.smp.app.pojo.TokenDetailInputTO;
import com.smp.app.pojo.UpdateProjectRuleInputTO;
import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.pojo.BookListResponseTO;
import com.smp.app.pojo.ChechUserExistenceResponseTO;
import com.smp.app.pojo.CompletedProjectListResponseTo;
import com.smp.app.pojo.DeleteImgResponseTO;
import com.smp.app.pojo.FileReturnListResponseTO;
import com.smp.app.pojo.FileReturnNotificationResponseTO;
import com.smp.app.pojo.NotificationDetailResponseTO;
import com.smp.app.pojo.ProjectListResponseTO;
import com.smp.app.pojo.ProjectReviewerResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.entity.ProjectDetail;
import com.smp.app.pojo.UserLoginInputTO;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface SMPService {

    BasicResponseTO saveRuleDetail(String ruleDetailStr);

    //public BasicResponseTO saveRuleDetail(String ruleDetailStr, MultipartFile file);
    BasicResponseTO saveCompanyDetail(List<MultipartFile> attachmentList, JsonObject companyDetailsJsonObj);

    BasicResponseTO saveProjectDetail(MultipartFile file, String projectDetail);

    BasicResponseTO updateProjectStatus(ProjectStatusInputTO projectInputTO);

    Map<String, List<RuleListResponseTO>> getRuleList();

    ChechUserExistenceResponseTO chechUserExistence(String userEmailId);

    List<BookListResponseTO> getBookList();

    ProjectReviewerResponseTO getProjectAndReviewerList();

    BasicResponseTO saveProjectReviewerRelation(ProjectReviewerRelationInputTO reviewerRelationInput);

    BasicResponseTO updateReviewerTokenDetail(TokenDetailInputTO tokenInput);

    List<NotificationDetailResponseTO> getNotificationList(Integer UserId);

    Map<Integer, String> reviewerConformityLevelMapObj();

    Boolean uploadProjectReport(File file, Integer projectId, Integer projVersionNum, String companyName);

    List<CompletedProjectListResponseTo> getCompletedProjectList();

    BasicResponseTO changeCompProjectStatus(Integer projectId);

    List<CompletedProjectListResponseTo> getOpenStateProjectList();

    Map<String, List<RuleListResponseTO>> getNewBooksForExistingProject(Integer projectId);

    BasicResponseTO updateProjectRuleDetail(UpdateProjectRuleInputTO updateProjInput);

    ProjectDetail readProjectDetail(Integer projectId);

    DeleteImgResponseTO deleteUploadedAttachment(DeleteAttachmentInputTO attachmentInputTO);

    List<ProjectListResponseTO> getProjectList();

    Map<String, List<RuleListResponseTO>> getRuleListBasedOnProjectId(Integer projectId);

    BasicResponseTO saveFileToReturnRequest(FileToReturnSaveInputTO fileToRequestInputTO);

    List<FileReturnNotificationResponseTO> getNotificationBasedOnProjectId(Integer projectId);

    List<FileReturnListResponseTO> getFileReturnRequestListBasedOnProjectId(Integer projectId);

    FileReturnListResponseTO getReturnDetailBasedOnRequestId(Integer requestId);

    BasicResponseTO updateFileReturnDetail(MultipartFile file, String fileReturnDetail);

    BaseResponse login(UserLoginInputTO loginDetail);
}
