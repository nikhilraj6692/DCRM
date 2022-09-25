package com.smp.app.util;

import com.smp.app.pojo.ManagementPreviewDetailsResponseTO;
import java.util.Comparator;

public class ManagementPreviewSortByRuleTitle implements Comparator<ManagementPreviewDetailsResponseTO> {

    @Override
    public int compare(ManagementPreviewDetailsResponseTO o1, ManagementPreviewDetailsResponseTO o2) {
        int res = o1.getBookName().compareToIgnoreCase(o2.getBookName());
        if (res != 0) {
            return res;
        }
        return o1.getRuleSubclauseNum().compareToIgnoreCase(o2.getRuleSubclauseNum());
    }

}
