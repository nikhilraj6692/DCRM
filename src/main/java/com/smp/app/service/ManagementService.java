package com.smp.app.service;

import com.smp.app.pojo.SaveManagementRemarkInputTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.pojo.LoginResponseTO;
import com.smp.app.pojo.ManagementRuleListResponseTO;
import com.smp.app.pojo.MangtWelComeResponseTO;
import com.smp.app.pojo.PreviewResponseListTO;
import com.smp.app.pojo.ProjectRemarkStatusResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.pojo.SaveManagementRemarkResponseTO;
import java.util.List;
import java.util.Map;

public interface ManagementService {


    ManagementRuleListResponseTO getProjectRules(Integer managementId);

    SaveManagementRemarkResponseTO saveManagementRemark(SaveManagementRemarkInputTO remarkInputTo);

    ProjectRemarkStatusResponseTO checkProjectRemarkStatus(Integer projectId);

    Map<String, List<RuleListResponseTO>> getBookListBasedOnManagementId(Integer managementId);

    Map<String, List<RuleListResponseTO>> getBookListBasedOnProjectId(Integer projectId);

    PreviewResponseListTO managementPreviewDetails(Integer projectId);

    MangtWelComeResponseTO getMangtWelComeContent(Integer managementId);
}
