package com.smp.app.config;

import com.smp.app.config.RolesMappingProps.RoleToCapabilityMapping;
import com.smp.app.dao.UserDetailDao;
import com.smp.app.entity.UserDetail;
import com.smp.app.exception.ForbiddenException;
import com.smp.app.exception.SystemException;
import com.smp.app.exception.UnAuthorizedException;
import com.smp.app.pojo.UserContext;
import com.smp.app.util.SMPAppConstants;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AuthorizationProcessor {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationProcessor.class);

    @Autowired
    private UserContext userContext;

    @Autowired
    private RolesMappingProps rolesMappingProps;

    @Autowired
    private ApiToPermissionProps apiToPermissionProps;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private UserDetailDao userDetailDao;

    public void authorizeRequest(String emailId) {
        if(null == emailId){
            log.error("Invalid email id");
            throw new UnAuthorizedException();
        }

        UserDetail userDetail = userDetailDao.getUserBasedEmail(emailId);
        if(null == userDetail){
            log.error("Invalid user");
            throw new UnAuthorizedException();
        }

        //authorize permissions of user
        List<String> contextRoles = userContext.getRole().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
            .collect(Collectors.toList());
        List<String> roles = !CollectionUtils.isEmpty(contextRoles)?contextRoles:Arrays.asList(userDetail.getUserRule().getRuleName());
        if (!roles.isEmpty()) {
            String role = roles.get(0);
            RoleToCapabilityMapping roleToCapabilityMapping = rolesMappingProps.getRoleToCapabilities().stream()
                .filter(r -> r.getRoleName().equals(role)).findFirst()
                .orElseThrow(() -> new SystemException(SMPAppConstants.INVALID_CONFIG));

            apiToPermissionProps.getMetaData().entrySet().stream().filter(
                entry -> Pattern.matches(entry.getKey(), request.getServletPath()) && entry.getValue().getMethod()
                    .equals(request.getMethod()) && roleToCapabilityMapping.getAllowedCapabilities()
                    .contains(entry.getValue().getCapabilityId())).findFirst().orElseThrow(() -> new ForbiddenException());
        } else {
            //fallback
            throw new SystemException(SMPAppConstants.ROLE_NOT_MAPPED);
        }
    }


}
