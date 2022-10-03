package com.smp.app.service;

import com.google.gson.JsonObject;
import com.smp.app.pojo.BaseResponse;
import com.smp.app.pojo.DeleteAttachmentInputTO;
import com.smp.app.pojo.FileToReturnSaveInputTO;
import com.smp.app.pojo.ProjectReviewerRelationInputTO;
import com.smp.app.pojo.ProjectStatusInputTO;
import com.smp.app.pojo.SaveProjectInputTO;
import com.smp.app.pojo.SaveRuleInputTO;
import com.smp.app.pojo.TokenDetailInputTO;
import com.smp.app.pojo.TokenRequest;
import com.smp.app.pojo.TokenResponse;
import com.smp.app.pojo.UpdateProjectRuleInputTO;
import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.pojo.BookListResponseTO;
import com.smp.app.pojo.DeleteImgResponseTO;
import com.smp.app.pojo.FileReturnListResponseTO;
import com.smp.app.pojo.FileReturnNotificationResponseTO;
import com.smp.app.pojo.NotificationDetailResponseTO;
import com.smp.app.pojo.ProjectListResponseTO;
import com.smp.app.pojo.ProjectReviewerResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.entity.ProjectDetail;
import com.smp.app.pojo.UserDetailResponseTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.util.ProjectStatusEnum;
import com.smp.app.util.UserRuleEnum;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public interface SMPService {

    JSONObject saveRuleDetail(SaveRuleInputTO ruleDetail);

    //public BasicResponseTO saveRuleDetail(String ruleDetailStr, MultipartFile file);
    BasicResponseTO saveCompanyDetail(List<MultipartFile> attachmentList, JsonObject companyDetailsJsonObj);

    JSONObject saveProjectDetail(MultipartFile file, SaveProjectInputTO projectInfo);

    BasicResponseTO updateProjectStatus(ProjectStatusInputTO projectInputTO);

    Map<String, List<RuleListResponseTO>> getRuleList(Integer bookId);

    boolean checkUserExistence(String userEmailId);

    List<BookListResponseTO> getBookList();

    ProjectReviewerResponseTO getProjectAndReviewerList(Integer projectId);

    BasicResponseTO saveProjectReviewerRelation(ProjectReviewerRelationInputTO reviewerRelationInput);

    BasicResponseTO updateReviewerTokenDetail(TokenDetailInputTO tokenInput);

    List<NotificationDetailResponseTO> getNotificationList(Integer UserId);

    Map<Integer, String> reviewerConformityLevelMapObj();

    Boolean uploadProjectReport(File file, Integer projectId, Integer projVersionNum, String companyName);


    BasicResponseTO changeCompProjectStatus(Integer projectId);

    List<ProjectListResponseTO> getProjectList(ProjectStatusEnum status);

    Map<String, List<RuleListResponseTO>> getNewBooksForExistingProject(Integer projectId);

    BasicResponseTO updateProjectRuleDetail(UpdateProjectRuleInputTO updateProjInput);

    ProjectDetail readProjectDetail(Integer projectId);

    DeleteImgResponseTO deleteUploadedAttachment(DeleteAttachmentInputTO attachmentInputTO);

    Map<String, List<RuleListResponseTO>> getRuleListBasedOnProjectId(Integer projectId);

    BasicResponseTO saveFileToReturnRequest(FileToReturnSaveInputTO fileToRequestInputTO);

    List<FileReturnNotificationResponseTO> getNotificationBasedOnProjectId(Integer projectId);

    List<FileReturnListResponseTO> getFileReturnRequestListBasedOnProjectId(Integer projectId);

    FileReturnListResponseTO getReturnDetailBasedOnRequestId(Integer requestId);

    BasicResponseTO updateFileReturnDetail(MultipartFile file, String fileReturnDetail);

    BaseResponse login(UserLoginInputTO loginDetail);

    void logOut(HttpServletRequest request, HttpServletResponse response);

    TokenResponse renewToken(TokenRequest tokenRequest);

    List<UserDetailResponseTO> getUsersList(UserRuleEnum userRuleEnum);
}
