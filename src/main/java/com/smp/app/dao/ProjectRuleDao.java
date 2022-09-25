package com.smp.app.dao;

import com.smp.app.pojo.BookListResponseTO;
import com.smp.app.pojo.RuleListResponseTO;
import com.smp.app.entity.ProjectRuleDetail;
import java.util.List;

public interface ProjectRuleDao extends GenericDao<ProjectRuleDetail, Integer> {

    List<RuleListResponseTO> getRuleList();

    List<BookListResponseTO> getBookList();

    List<Object[]> getRuleListBasedOnProjectId(Integer projectId);

    List<Object[]> getRuleListBasedOnRuleIds(List<Integer> ruleIdList);
}
