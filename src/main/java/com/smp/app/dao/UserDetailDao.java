package com.smp.app.dao;

import com.smp.app.pojo.UserLoginInputTO;
import com.smp.app.pojo.UserDetailReviewerRelation;
import com.smp.app.entity.UserDetail;
import java.util.List;

public interface UserDetailDao extends GenericDao<UserDetail, Integer> {

    UserDetail getUserDetailLoginInput(UserLoginInputTO loginDetail);

    UserDetail getUserBasedEmail(String userEmailId);

    List<UserDetailReviewerRelation> getUserListReviewerRelation();
}
