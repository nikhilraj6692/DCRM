package com.smp.app.controller;

import com.smp.app.pojo.BaseResponse;
import com.smp.app.pojo.PreviewReviewerRemarkInputTO;
import com.smp.app.pojo.ReviewerProjectListInputTO;
import com.smp.app.pojo.ReviewerProjectRulesInputTO;
import com.smp.app.pojo.saveLeadReviewerRemarkInputTO;
import com.smp.app.pojo.BasicResponseTO;
import com.smp.app.pojo.PreviewResponseListTO;
import com.smp.app.pojo.ProjectRemarkStatusResponseTO;
import com.smp.app.pojo.ReviewerProjectDetailResponseTO;
import com.smp.app.pojo.ReviewerProjectListResponseTO;
import com.smp.app.pojo.SaveReviewerResponseTO;
import com.smp.app.service.ReviewerService;
import com.smp.app.util.SMPAppConstants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/reviewerAction")
public class ReviewerController {

    @Autowired
    private ReviewerService reviewerService;

    @RequestMapping(value = "/getReviewerProjectList", method = RequestMethod.POST, consumes =
		MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse getReviewerProjectList(@RequestBody ReviewerProjectListInputTO userInput) {
        return new BaseResponse(this.reviewerService.getReviewerProjectList(userInput), new BasicResponseTO(SMPAppConstants.PROJECTS_RETRIEVED_SUCCESSFULLY));
    }

    @RequestMapping(value = "/getReviewerProjectRules", method = RequestMethod.POST, consumes =
		MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewerProjectDetailResponseTO getReviewerProjectRules(
        @RequestBody ReviewerProjectRulesInputTO projectRulesInputTO) {
        return this.reviewerService.getReviewerProjectRules(projectRulesInputTO);
    }

    @RequestMapping(value = "/saveReviewerRemark", method = RequestMethod.POST, consumes =
		MediaType.MULTIPART_FORM_DATA_VALUE)
    public SaveReviewerResponseTO saveReviewerRemark(@RequestParam("upload-file") List<MultipartFile> attachmentList,
        @RequestParam("reviewerRemarkInput") String reviewerRemarkInput, RedirectAttributes redirectAttributes) {
        return this.reviewerService.saveReviewerRemark(attachmentList, reviewerRemarkInput);
    }

    @RequestMapping(value = "/checkReviewerProjectRemarkStatus/{projectId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProjectRemarkStatusResponseTO checkReviewerProjectRemarkStatus(@PathVariable Integer projectId) {
        return this.reviewerService.checkReviewerProjectRemarkStatus(projectId);
    }

    @RequestMapping(value = "/saveLeadReviewerRemark", method = RequestMethod.POST, consumes =
		MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveLeadReviewerRemark(@RequestBody saveLeadReviewerRemarkInputTO remarkInput) {
        return this.reviewerService.saveLeadReviewerRemark(remarkInput);
    }

    @RequestMapping(value = "/checkLeadReviewerProjectStatus/{projectId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProjectRemarkStatusResponseTO checkLeadReviewerProjectStatus(@PathVariable Integer projectId) {
        return this.reviewerService.checkLeadReviewerProjectStatus(projectId);
    }

    @RequestMapping(value = "/reviewerPreviewerDetail/{projectId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PreviewResponseListTO reviewerPreviewerDetail(@PathVariable Integer projectId) {
        return this.reviewerService.reviewerPreviewerDetail(projectId);
    }


    @RequestMapping(value = "/previewReviewerRemarkUpdate", method = RequestMethod.POST, consumes =
		MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO previewReviewerRemarkUpdate(@RequestBody PreviewReviewerRemarkInputTO remarkInput) {
        return this.reviewerService.previewReviewerRemarkUpdate(remarkInput);
    }


    @RequestMapping(value = "/getManagementUserProjectList", method = RequestMethod.POST, consumes =
		MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReviewerProjectListResponseTO> getManagementUserProjectList(
        @RequestBody ReviewerProjectListInputTO userInput) {
        return this.reviewerService.getManagementUserProjectList(userInput);
    }


    //saveRuleRelevantCircular
    @RequestMapping(value = "/saveRuleRelevantCircular", method = RequestMethod.POST, consumes =
		MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponseTO saveRuleRelevantCircular(@RequestBody String circularJsonStr) {
        return this.reviewerService.saveRuleRelevantCircular(circularJsonStr);
    }
}
