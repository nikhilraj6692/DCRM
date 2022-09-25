package com.smp.app.dao.impl;

import com.smp.app.dao.FileToReturnRuleAssociationDao;
import com.smp.app.entity.FileReturnRuleAssociatedDetail;
import org.springframework.stereotype.Component;

@Component
public class FileToReturnRuleAssociationDaoImpl extends GenericDaoImpl<FileReturnRuleAssociatedDetail, Integer> implements
    FileToReturnRuleAssociationDao {


    public FileToReturnRuleAssociationDaoImpl() {
        super(FileReturnRuleAssociatedDetail.class);
    }
}
