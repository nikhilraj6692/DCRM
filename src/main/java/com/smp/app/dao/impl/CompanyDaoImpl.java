package com.smp.app.dao.impl;

import com.smp.app.dao.CompanyDao;
import com.smp.app.entity.CompanyDetail;
import org.springframework.stereotype.Component;

@Component
public class CompanyDaoImpl extends GenericDaoImpl<CompanyDetail, Integer> implements CompanyDao {


    public CompanyDaoImpl() {
        super(CompanyDetail.class);
    }
}
