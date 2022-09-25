package com.smp.app.controller;

import com.itextpdf.text.DocumentException;
import com.smp.app.config.AuthorizationProcessor;
import com.smp.app.config.PublicApi;
import com.smp.app.entity.ProjectDetail;
import com.smp.app.pojo.BaseResponse;
import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.pojo.BookListResponseTO;
import com.smp.app.pojo.ChechUserExistenceResponseTO;
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
import com.smp.app.pojo.TokenDetailInputTO;
import com.smp.app.pojo.UpdateProjectRuleInputTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.service.ReviewerService;
import com.smp.app.service.SMPService;
import com.smp.app.util.CommonUtils;
import com.smp.app.util.ImageUpload;
import com.smp.app.util.PDFGenerator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
    public BaseResponse login(@RequestBody UserLoginInputTO loginDetail) {
        authorizationProcessor.authorizeRequest(loginDetail.getUserEmailId());
        return this.smpService.login(loginDetail);
    }

    @RequestMapping(value = "/saveRuleDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveRuleDetail(@RequestBody String ruleDetailStr) {
        return this.smpService.saveRuleDetail(ruleDetailStr);
    }


    @RequestMapping(value = "/xyzz", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
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
    }


    @RequestMapping(value = "/saveProjectDetail", method = RequestMethod.POST, consumes =
        MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveProjectDetail(@RequestParam("upload-file") MultipartFile file,
        @RequestParam("projectDetail") String projectDetail, RedirectAttributes redirectAttributes) {
        return this.smpService.saveProjectDetail(file, projectDetail);
    }

    @RequestMapping(value = "/updateProjectStatus", method = RequestMethod.POST, produces =
        MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO updateProjectStatus(@RequestBody ProjectStatusInputTO projectInputTO) {
        return this.smpService.updateProjectStatus(projectInputTO);
    }

    @RequestMapping(value = "/getRuleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<RuleListResponseTO>> getRuleList() {
        return this.smpService.getRuleList();
    }

    @RequestMapping(value = "/userLogout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO userLogout(HttpServletRequest httpServletRequest) {
        BasicResponseTO basicResponseTO = new BasicResponseTO();
        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("userId", "");
        httpSession.setAttribute("username", "");
        httpSession.setAttribute("userEmailId", "");
        httpSession.setAttribute("userRuleType", "");
        basicResponseTO.setResponseStatus(true);
        return basicResponseTO;
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
    public PdfReportResponseTO generatePdfReport(@PathVariable("projectId") Integer projectId,
        HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping(value = "/chechUserExistence", method = RequestMethod.POST, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ChechUserExistenceResponseTO chechUserExistence(@RequestBody UserLoginInputTO loginDetail) {
        return this.smpService.chechUserExistence(loginDetail.getUserEmailId());
    }

    @RequestMapping(value = "/getBookList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookListResponseTO> getBookList() {
        return this.smpService.getBookList();
    }

    @RequestMapping(value = "/getProjectAndReviewerList", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public ProjectReviewerResponseTO getProjectAndReviewerList() {
        return this.smpService.getProjectAndReviewerList();
    }

    @RequestMapping(value = "/saveProjectReviewerRelation", method = RequestMethod.POST, produces =
        MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveProjectReviewerRelation(@RequestBody ProjectReviewerRelationInputTO reviewerRelationInput) {
        return this.smpService.saveProjectReviewerRelation(reviewerRelationInput);
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

    @RequestMapping(value = "/getCompletedProjectList", method = RequestMethod.GET, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompletedProjectListResponseTo> getCompletedProjectList() {
        return this.smpService.getCompletedProjectList();
    }

    @RequestMapping(value = "/getOpenStateProjectList", method = RequestMethod.GET, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompletedProjectListResponseTo> getOpenStateProjectList() {
        return this.smpService.getOpenStateProjectList();
    }


    @RequestMapping(value = "/changeCompProjectStatus/{projectId}", method = RequestMethod.GET, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO changeCompProjectStatus(@PathVariable Integer projectId) {
        return this.smpService.changeCompProjectStatus(projectId);
    }

    @RequestMapping(value = "/getNewBooksForExistingProject/{projectId}", method = RequestMethod.GET, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<RuleListResponseTO>> getNewBooksForExistingProject(@PathVariable Integer projectId) {
        return this.smpService.getNewBooksForExistingProject(projectId);
    }


    @RequestMapping(value = "/updateProjectRuleDetail", method = RequestMethod.POST, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO updateProjectRuleDetail(@RequestBody UpdateProjectRuleInputTO updateProjInput) {
        return this.smpService.updateProjectRuleDetail(updateProjInput);
    }

    @RequestMapping(value = "/deleteUploadedAttachment", method = RequestMethod.POST, consumes =
        MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteImgResponseTO deleteUploadedAttachment(@RequestBody DeleteAttachmentInputTO attachmentInputTO) {
        return this.smpService.deleteUploadedAttachment(attachmentInputTO);
    }


    @RequestMapping(value = "/getProjectList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectListResponseTO> getProjectList() {
        return this.smpService.getProjectList();
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
