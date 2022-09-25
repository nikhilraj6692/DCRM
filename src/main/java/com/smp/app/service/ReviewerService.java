package com.smp.app.service;

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
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewerService {

    List<ReviewerProjectListResponseTO> getReviewerProjectList(ReviewerProjectListInputTO userInput);

    ReviewerProjectDetailResponseTO getReviewerProjectRules(ReviewerProjectRulesInputTO projectRulesInputTO);

    SaveReviewerResponseTO saveReviewerRemark(List<MultipartFile> attachmentList, String reviewerRemarkDDetails);

    ProjectRemarkStatusResponseTO checkReviewerProjectRemarkStatus(Integer projectId);

    BasicResponseTO saveLeadReviewerRemark(saveLeadReviewerRemarkInputTO remarkInput);

    ProjectRemarkStatusResponseTO checkLeadReviewerProjectStatus(Integer projectId);

    PreviewResponseListTO reviewerPreviewerDetail(Integer projectId);

    BasicResponseTO previewReviewerRemarkUpdate(PreviewReviewerRemarkInputTO remarkInput);

    List<ReviewerProjectListResponseTO> getManagementUserProjectList(ReviewerProjectListInputTO userInput);

    BasicResponseTO saveRuleRelevantCircular(String circularJsonStr);

    PreviewResponseListTO pdfReportReviewerPreviewerDetail(Integer projectId);
}
