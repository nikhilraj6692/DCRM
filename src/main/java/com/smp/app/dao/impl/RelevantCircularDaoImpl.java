package com.smp.app.dao.impl;

import com.smp.app.dao.RelevantCircularDao;
import com.smp.app.entity.RuleRelevantCircular;
import org.springframework.stereotype.Component;

@Component
public class RelevantCircularDaoImpl extends GenericDaoImpl<RuleRelevantCircular, Integer> implements RelevantCircularDao {

    public RelevantCircularDaoImpl() {
        super(RuleRelevantCircular.class);
    }
}
