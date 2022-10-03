package com.smp.app.dao;

import com.smp.app.pojo.UserDetailResponseTO;
import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.pojo.UserDetailReviewerRelation;
import com.smp.app.entity.UserDetail;
import java.util.List;

public interface UserDetailDao extends GenericDao<UserDetail, Integer> {

    UserDetail getUserDetailLoginInput(UserLoginInputTO loginDetail);

    UserDetail getUserBasedEmail(String userEmailId);

    List<UserDetailReviewerRelation> getUserListReviewerRelation();

    UserDetail getUserOnBasisOfRole(Integer userRuleId, Integer userId);

    List<UserDetail> getUserOnBasisOfRole(Integer userRuleId);

    UserDetail findByRefreshToken(String refreshToken);

    List<UserDetailResponseTO> getUsersWithAssignedProjectId(Integer ruleId, Integer projectId);
}
