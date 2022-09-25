package com.smp.app.controller;

import com.smp.app.pojo.SaveManagementRemarkInputTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.pojo.LoginResponseTO;
import com.smp.app.pojo.ManagementRuleListResponseTO;
import com.smp.app.pojo.MangtWelComeResponseTO;
import com.smp.app.pojo.PreviewResponseListTO;
import com.smp.app.pojo.ProjectRemarkStatusResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.pojo.SaveManagementRemarkResponseTO;
import com.smp.app.pojo.UserDetailResponseTO;
import com.smp.app.service.ManagementService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/managementAction")
public class ManagementController {

    @Autowired
    private ManagementService managementService;


    @RequestMapping(value = "/getBookListBasedOnManagementId/{managementId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<RuleListResponseTO>> getBookListBasedOnManagementId(@PathVariable Integer managementId) {
        return this.managementService.getBookListBasedOnManagementId(managementId);
    }

    @RequestMapping(value = "/getBookListBasedOnProjectId/{projectId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<RuleListResponseTO>> getBookListBasedOnProjectId(@PathVariable Integer projectId) {
        return this.managementService.getBookListBasedOnProjectId(projectId);
    }

    @RequestMapping(value = "/getProjectRules/{managementId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ManagementRuleListResponseTO getProjectRules(@PathVariable Integer managementId) {
        return this.managementService.getProjectRules(managementId);
    }

    @RequestMapping(value = "/saveManagementRemark", method = RequestMethod.POST, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SaveManagementRemarkResponseTO saveManagementRemark(@RequestBody SaveManagementRemarkInputTO remarkInputTo) {
        return this.managementService.saveManagementRemark(remarkInputTo);
    }


    @RequestMapping(value = "/checkProjectRemarkStatus/{projectId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProjectRemarkStatusResponseTO checkProjectRemarkStatus(@PathVariable Integer projectId) {
        return this.managementService.checkProjectRemarkStatus(projectId);
    }

    @RequestMapping(value = "/managementPreviewDetails/{projectId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PreviewResponseListTO managementPreviewDetails(@PathVariable Integer projectId) {
        return this.managementService.managementPreviewDetails(projectId);
    }

    @RequestMapping(value = "/getMangtWelComeContent/{managementId}", method = RequestMethod.GET, produces =
		MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MangtWelComeResponseTO getMangtWelComeContent(@PathVariable Integer managementId) {
        return this.managementService.getMangtWelComeContent(managementId);
    }
}
