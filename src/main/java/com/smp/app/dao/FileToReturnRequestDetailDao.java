package com.smp.app.dao;

import com.smp.app.pojo.FileReturnNotificationResponseTO;
import com.smp.app.entity.FileToReturnRequestDetail;
import java.util.List;

public interface FileToReturnRequestDetailDao extends GenericDao<FileToReturnRequestDetail, Integer> {

    List<FileReturnNotificationResponseTO> getNotificationBasedOnProjectId(Integer projectId);

    List<FileToReturnRequestDetail> getReturnRequestListBasedOnProjectId(Integer projectId);

    FileToReturnRequestDetail getReturnDetailBasedOnRequestId(Integer requestId);
}
