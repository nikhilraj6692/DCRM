package com.smp.app.controller;

import com.itextpdf.text.DocumentException;
import com.smp.app.config.AuthorizationProcessor;
import com.smp.app.config.PublicApi;
import com.smp.app.entity.ProjectDetail;
import com.smp.app.exception.InvalidInputException;
import com.smp.app.pojo.BaseResponse;
import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.pojo.BookListResponseTO;
import com.smp.app.pojo.CompletedProjectListResponseTo;
import com.smp.app.pojo.DeleteAttachmentInputTO;
import com.smp.app.pojo.DeleteImgResponseTO;
import com.smp.app.pojo.FileReturnListResponseTO;
import com.smp.app.pojo.FileReturnNotificationResponseTO;
import com.smp.app.pojo.FileToReturnSaveInputTO;
import com.smp.app.pojo.NotificationDetailResponseTO;
import com.smp.app.pojo.PdfReportResponseTO;
import com.smp.app.pojo.PreviewResponseListTO;
import com.smp.app.pojo.ProjectListResponseTO;
import com.smp.app.pojo.ProjectReviewerRelationInputTO;
import com.smp.app.pojo.ProjectReviewerResponseTO;
import com.smp.app.pojo.ProjectStatusInputTO;
import com.smp.app.pojo.ReviewerPreviewDetailsResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.pojo.SaveProjectInputTO;
import com.smp.app.pojo.SaveRuleInputTO;
import com.smp.app.pojo.TokenDetailInputTO;
import com.smp.app.pojo.TokenRequest;
import com.smp.app.pojo.TokenResponse;
import com.smp.app.pojo.UpdateProjectRuleInputTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.pojo.UserLoginType;
import com.smp.app.service.ReviewerService;
import com.smp.app.service.SMPService;
import com.smp.app.util.CommonUtils;
import com.smp.app.util.ImageUpload;
import com.smp.app.util.PDFGenerator;
import com.smp.app.util.ProjectStatusEnum;
import com.smp.app.util.SMPAppConstants;
import com.smp.app.util.UserRuleEnum;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/SMPMasterAction")
public class SmpMasterController {

    @Autowired
    private SMPService smpService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    private AuthorizationProcessor authorizationProcessor;


    @PublicApi
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse login(@Valid @RequestBody UserLoginInputTO loginDetail) {
        if (StringUtils.isBlank(loginDetail.getPassword())) {
            throw new InvalidInputException(SMPAppConstants.INVALID_PASSWORD);
        }
        if (StringUtils.isBlank(loginDetail.getUsername())) {
            throw new InvalidInputException(SMPAppConstants.INVALID_USER_NAME);
        }
        if (Arrays.stream(UserLoginType.values())
            .noneMatch(userLoginType -> userLoginType.getRoleId() == loginDetail.getLoginUserType())) {
            throw new InvalidInputException(SMPAppConstants.INVALID_USER_TYPE);
        }

        authorizationProcessor.authorizeRequest(loginDetail.getUserEmailId());
        return this.smpService.login(loginDetail);
    }

    @PostMapping("/userLogout")
    public BasicResponseTO logout(HttpServletRequest request, RedirectAttributes redirectAttributes,
        HttpServletResponse response) {
        this.smpService.logOut(request, response);
        return new BasicResponseTO(SMPAppConstants.LOG_OUT_SUCCESSFUL);
    }

    @PublicApi
    @PostMapping("/silent-renewal")
    public BaseResponse renewToken(@Valid @RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = smpService.renewToken(tokenRequest);
        return new BaseResponse(tokenResponse, new BasicResponseTO(SMPAppConstants.RENEWAL_SUCCESSFUL));
    }

    @RequestMapping(value = "/checkUserExistence", method = RequestMethod.POST, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean checkUserExistence(@Valid @RequestBody UserLoginInputTO loginDetail) {
        return this.smpService.checkUserExistence(loginDetail.getUserEmailId());
    }

    @RequestMapping(value = "/getBookList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getBookList() {
        List<BookListResponseTO> bookListResponseTOS = this.smpService.getBookList();
        if (null == bookListResponseTOS) {
            bookListResponseTOS = new ArrayList<>();
        }

        return new BaseResponse(bookListResponseTOS, new BasicResponseTO(SMPAppConstants.BOOKS_RETRIEVED_SUCCESSFULLY));
    }

    @RequestMapping(value = "/getRuleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getRuleList(@RequestParam(name = "bookId", required = false) Integer bookId) {
        Map<String, List<RuleListResponseTO>> rulesMap = this.smpService.getRuleList(bookId);
        return new BaseResponse(rulesMap, new BasicResponseTO(SMPAppConstants.RULES_FETCHED_SUCCESSFULLY));
    }

    @RequestMapping(value = "/saveRuleDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse saveRuleDetail(@Valid @RequestBody SaveRuleInputTO ruleDetail) {
        return new BaseResponse(this.smpService.saveRuleDetail(ruleDetail).toMap(),
            new BasicResponseTO(SMPAppConstants.SAVE_RULE_SUCCESSFUL));
    }


    /*@RequestMapping(value = "/xyzz", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveRuleDetail(@RequestParam("attachmentUploadedStatus") String attchMentStatus,
        @RequestParam("projRuleDetail") String ruleDetailStr, RedirectAttributes redirectAttributes,
        HttpServletRequest httpServletRequest) {
        MultipartFile circularAttachFile = null;
        if (Boolean.parseBoolean(attchMentStatus)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httpServletRequest;
            circularAttachFile = multipartRequest.getFile("upload-file");
        }
        //	return this.smpService.saveRuleDetail(ruleDetailStr, circularAttachFile);
        return null;
    }*/


    @RequestMapping(value = "/saveProjectDetail", method = RequestMethod.POST, consumes =
        MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse saveProjectDetail(@RequestPart("file") MultipartFile file,
        @RequestPart("projectInfo") SaveProjectInputTO projectInfo, RedirectAttributes redirectAttributes) {
        return new BaseResponse(this.smpService.saveProjectDetail(file, projectInfo).toMap(),
            new BasicResponseTO(SMPAppConstants.PROJECT_SAVED_SUCCESSFUL));
    }

    @RequestMapping(value = "/getProjectList", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getProjectsList(@RequestParam(name = "state", required = false) ProjectStatusEnum projectStatus) {
        return new BaseResponse(this.smpService.getProjectList(projectStatus), new BasicResponseTO(SMPAppConstants.PROJECT_RETRIEVAL_SUCCESSFUL));
    }

    @RequestMapping(value = "/getNewBooksForExistingProject/{projectId}", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getNewBooksForExistingProject(@PathVariable Integer projectId) {
        return new BaseResponse(this.smpService.getNewBooksForExistingProject(projectId), new BasicResponseTO(SMPAppConstants.BOOKS_RETRIEVED_SUCCESSFULLY));
    }

    @RequestMapping(value = "/updateProjectRuleDetail", method = RequestMethod.PUT, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO updateProjectRuleDetail(@RequestBody UpdateProjectRuleInputTO updateProjInput) {
        return this.smpService.updateProjectRuleDetail(updateProjInput);
    }

    @RequestMapping(value = "/getUsersList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getUsersList(@RequestParam(name = "ruleId", required = false) UserRuleEnum userRuleEnum){
        return new BaseResponse(this.smpService.getUsersList(userRuleEnum), new BasicResponseTO(SMPAppConstants.USERS_RETRIEVED_SUCCESSFULLY));
    }

    @RequestMapping(value = "/getProjectAndReviewerList/{projectId}", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getProjectAndReviewerList(@PathVariable Integer projectId) {
        return new BaseResponse(this.smpService.getProjectAndReviewerList(projectId), new BasicResponseTO(SMPAppConstants.REVIEWERS_RETRIEVED_SUCCESSFULLY));
    }

    @RequestMapping(value = "/saveProjectReviewerRelation", method = RequestMethod.PUT, produces =
        MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveProjectReviewerRelation(@RequestBody ProjectReviewerRelationInputTO reviewerRelationInput) {
        return this.smpService.saveProjectReviewerRelation(reviewerRelationInput);
    }


    @RequestMapping(value = "/updateProjectStatus", method = RequestMethod.POST, produces =
        MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO updateProjectStatus(@RequestBody ProjectStatusInputTO projectInputTO) {
        return this.smpService.updateProjectStatus(projectInputTO);
    }


    @RequestMapping(value = "/generateProjectReport", method = RequestMethod.POST, consumes =
        MediaType.MULTIPART_FORM_DATA_VALUE)
    public void generateProjectReport(@RequestParam("hiddenProjectId") String projectId, HttpServletRequest request,
        HttpServletResponse response) {
        request.getSession(true);
        try {
            response.setContentType("application/pdf");
            String filename = "project_report.pdf";
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            //List<ReviewerPreviewDetailsResponseTO> projectRuleList = this.reviewerService.reviewerPreviewerDetail
            // (Integer.parseInt(projectId));
            PreviewResponseListTO previewResponseListTO = this.reviewerService.reviewerPreviewerDetail(
                Integer.parseInt(projectId));
            List<ReviewerPreviewDetailsResponseTO> projectRuleList = previewResponseListTO.getResultList();
            Map<Integer, List<String>> circularNameMapObject = previewResponseListTO.getCircularNameMapObject();
            Map<Integer, String> conformityLevelMapObj = this.smpService.reviewerConformityLevelMapObj();

            PDFGenerator.projectPDFReport(request, response, projectRuleList, commonUtils, imageUpload,
                conformityLevelMapObj, circularNameMapObject);
        } catch (Exception e) {
        }
    }

    @RequestMapping(value = "/projectfReportMApp/{projectId}", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public PdfReportResponseTO generatePdfReport(@PathVariable("projectId") Integer projectId, HttpServletRequest request,
        HttpServletResponse response) {
        PdfReportResponseTO pdfReportResponseTO = new PdfReportResponseTO();
        try {

            ProjectDetail dbProjectDetail = this.smpService.readProjectDetail(projectId);
            String companyName = dbProjectDetail.getCompanyDetail().getCompanyName().replaceAll("\\s+", "_").toLowerCase();
            boolean isProjectReportExisted = this.imageUpload.getProjectReportStatus(projectId,
                dbProjectDetail.getVersionNum(), companyName);
            if (isProjectReportExisted) {
                System.out.println("Existed Report >>>");
                pdfReportResponseTO.setProjectId(projectId);
                pdfReportResponseTO.setProjectReportURL(
                    this.commonUtils.readUserDefinedMessages("smp.cloud.project_report.read_image_base_url") + projectId
                        + "_" + companyName + "_v" + dbProjectDetail.getVersionNum() + ".pdf");
            } else {
                System.out.println("New Report >>>");

                //---For Local system commented this---
                //String fileToStore = "C:\\Users\\shankar.shirahatti\\Downloads\\Magma App\\smp_app\\DDDD\\project_report
                // .pdf";
                String fileToStore = System.getProperty("catalina.base") + "/logs/" + projectId + "_project_report.pdf";

                //PreviewResponseListTO previewResponseListTO = this.reviewerService.reviewerPreviewerDetail(projectId);
                PreviewResponseListTO previewResponseListTO = this.reviewerService.pdfReportReviewerPreviewerDetail(
                    projectId);

                //List<ReviewerPreviewDetailsResponseTO> projectRuleList = previewResponseListTO.getResultList();
                Map<String, List<ReviewerPreviewDetailsResponseTO>> projectRuleMapObject =
                    previewResponseListTO.getPdfResultReviewerMapObj();
                Map<Integer, List<String>> circularNameMapObject = previewResponseListTO.getCircularNameMapObject();
                Map<Integer, String> conformityLevelMapObj = this.smpService.reviewerConformityLevelMapObj();

                PDFGenerator.projectPDFReportMApp(request, response, projectRuleMapObject, commonUtils, imageUpload,
                    conformityLevelMapObj, fileToStore, circularNameMapObject);

                //---For Local system commented this---
                File file = new File(fileToStore);
                this.smpService.uploadProjectReport(file, projectId, dbProjectDetail.getVersionNum(), companyName);
                if (file.exists()) {
                    file.delete();
                }
                pdfReportResponseTO.setProjectId(projectId);
                pdfReportResponseTO.setProjectReportURL(
                    this.commonUtils.readUserDefinedMessages("smp.cloud.project_report.read_image_base_url") + projectId
                        + "_" + companyName + "_v" + dbProjectDetail.getVersionNum() + ".pdf");

            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return pdfReportResponseTO;
    }

    @RequestMapping(value = "/updateReviewerTokenDetail", method = RequestMethod.POST, produces =
        MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO updateReviewerTokenDetail(@RequestBody TokenDetailInputTO tokenInput) {
        return this.smpService.updateReviewerTokenDetail(tokenInput);
    }

    @RequestMapping(value = "/getNotificationList/{UserId}", method = RequestMethod.GET, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NotificationDetailResponseTO> getNotificationList(@PathVariable Integer UserId) {
        return this.smpService.getNotificationList(UserId);
    }

    @RequestMapping(value = "/changeCompProjectStatus/{projectId}", method = RequestMethod.GET, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO changeCompProjectStatus(@PathVariable Integer projectId) {
        return this.smpService.changeCompProjectStatus(projectId);
    }


    @RequestMapping(value = "/deleteUploadedAttachment", method = RequestMethod.POST, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteImgResponseTO deleteUploadedAttachment(@RequestBody DeleteAttachmentInputTO attachmentInputTO) {
        return this.smpService.deleteUploadedAttachment(attachmentInputTO);
    }

    @RequestMapping(value = "/getRuleListBasedOnProjectId/{projectId}", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<RuleListResponseTO>> getRuleListBasedOnProjectId(@PathVariable Integer projectId) {
        return this.smpService.getRuleListBasedOnProjectId(projectId);
    }

    @RequestMapping(value = "/saveFileToReturnRequest", method = RequestMethod.POST, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveFileToReturnRequest(@RequestBody FileToReturnSaveInputTO fileToRequestInputTO) {
        return this.smpService.saveFileToReturnRequest(fileToRequestInputTO);
    }

    @RequestMapping(value = "/getNotificationBasedOnProjectId/{projectId}", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public List<FileReturnNotificationResponseTO> getNotificationBasedOnProjectId(@PathVariable Integer projectId) {
        return this.smpService.getNotificationBasedOnProjectId(projectId);
    }

    @RequestMapping(value = "/getFileReturnRequestListBasedOnProjectId/{projectId}", method = RequestMethod.GET, produces
        = MediaType.APPLICATION_JSON_VALUE)
    public List<FileReturnListResponseTO> getFileReturnRequestListBasedOnProjectId(@PathVariable Integer projectId) {
        return this.smpService.getFileReturnRequestListBasedOnProjectId(projectId);
    }

    @RequestMapping(value = "/getReturnDetailBasedOnRequestId/{requestId}", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public FileReturnListResponseTO getReturnDetailBasedOnRequestId(@PathVariable Integer requestId) {
        return this.smpService.getReturnDetailBasedOnRequestId(requestId);
    }

    @RequestMapping(value = "/updateFileReturnDetail", method = RequestMethod.POST, consumes =
        MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO updateFileReturnDetail(@RequestParam("fileToUpload") MultipartFile file,
        @RequestParam("fileReturnDetail") String fileReturnDetail, RedirectAttributes redirectAttributes) {
        return this.smpService.updateFileReturnDetail(file, fileReturnDetail);
    }
}
