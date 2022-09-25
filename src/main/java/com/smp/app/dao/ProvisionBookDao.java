package com.smp.app.dao;

import com.smp.app.entity.ProvisionBookDetail;
import java.util.List;
import java.util.Set;

public interface ProvisionBookDao extends GenericDao<ProvisionBookDetail, Integer> {

    List<ProvisionBookDetail> getBookList();

    List<Object[]> getBookListBasedOnManagementId(Integer managementId);

    List<Object[]> getBookListBasedOnProjectId(Integer projectId);

    Set<Integer> getBookIdsBasedOnProjectId(Integer projectId);
}
