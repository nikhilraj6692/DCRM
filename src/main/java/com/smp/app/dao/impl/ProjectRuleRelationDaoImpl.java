package com.smp.app.dao.impl;

import com.smp.app.dao.ProjectRuleRelationDao;
import com.smp.app.entity.ProjectRuleRelation;
import org.springframework.stereotype.Component;

@Component
public class ProjectRuleRelationDaoImpl extends GenericDaoImpl<ProjectRuleRelation, Integer> implements
    ProjectRuleRelationDao {


    public ProjectRuleRelationDaoImpl() {
        super(ProjectRuleRelation.class);
    }
}
