package com.smp.app.util;

import com.smp.app.pojo.RuleListResponseTO;
import java.util.Comparator;

public class SortByRuleTitleComparactor implements Comparator<RuleListResponseTO> {

    @Override
    public int compare(RuleListResponseTO o1, RuleListResponseTO o2) {
        return o1.getRuleTitle().compareTo(o2.getRuleTitle());
    }

}
