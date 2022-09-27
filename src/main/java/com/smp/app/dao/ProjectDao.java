package com.smp.app.dao;

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
import com.smp.app.entity.ProjectDetail;
import java.util.List;

public interface ProjectDao extends GenericDao<ProjectDetail, Integer> {

    List<ProjectRulesDaoTO> getProjectRules(Integer managementId);

    List<ReviewerProjectListResponseTO> getReviewerProjectList(ReviewerProjectListInputTO userInput,
        String projectImgBaseUrl);

    List<ReviewerProjectRulesDao> getReviewerProjectRules(Integer projectId, Integer ruleId);

    Boolean checkProjectRemarkStatus(Integer projectId);

    List<Integer> getRuleIdBasedProjectId(Integer projectId);

    Boolean checkReviewerProjectRemarkStatus(Integer projectId);

    Boolean checkLeadReviewerProjectStatus(Integer projectId);

    List<ConformityLevelResponseTO> getReviewerConformityLevel();

    List<ProjectDetailReviewerRelation> getProjectListReviewerRelation();

    List<ManagementPreviewDetailsResponseTO> managementPreviewDetails(Integer projectId);

    List<ReviewerPreviewDetailsResponseTO> reviewerPreviewerDetail(Integer projectId);

    List<Integer> savedReviewerLeadReviewerRecord(Integer projectId, boolean isReviewer);

    List<ProjectListResponseTO> getProjectListBasedOnStatus(Integer statusId);

    MangtWelComeResponseTO getMangtWelComeContent(Integer managementId);

    List<ProjectListResponseTO> getProjectList();

    List<ReviewerProjectListResponseTO> getManagementUserProjectList(ReviewerProjectListInputTO userInput,
        String projectImgBaseUrl);

    String getManagementUserEmailBasedOnProjectId(Integer projectId);

    List<RelevantCircularResponseTO> getRulesRelevantCircularList(Integer projectRuleId);

    List<Object[]> circularNameBasedonProjectId(Integer projectId);

    List<ReviewerPreviewDetailsResponseTO> pdfReportReviewerPreviewerDetail(Integer projectId);
}
