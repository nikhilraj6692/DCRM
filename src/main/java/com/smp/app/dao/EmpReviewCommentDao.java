package com.smp.app.dao;

import com.smp.app.entity.ProjectDetail;
import com.smp.app.entity.UserReviewComments;
import java.util.Map;

public interface EmpReviewCommentDao extends GenericDao<UserReviewComments, Integer> {

    Map<Integer, String> getDeviceTokenList(Integer notificationTypeId, ProjectDetail projectDetail);
}
