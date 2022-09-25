package com.smp.app.util;

import com.smp.app.pojo.ReviewerPreviewDetailsResponseTO;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReviewerPreviewSortByRuleTitle implements Comparator<ReviewerPreviewDetailsResponseTO> {

	/*@Override
	public int compare(ReviewerPreviewDetailsResponseTO o1, ReviewerPreviewDetailsResponseTO o2) {
        int res =  o1.getBookName().compareToIgnoreCase(o2.getBookName());
        if (res != 0)
            return res;
        return o1.getRuleSubclauseNum().compareToIgnoreCase(o2.getRuleSubclauseNum());
	}*/


    @Override
    public int compare(ReviewerPreviewDetailsResponseTO object1, ReviewerPreviewDetailsResponseTO object2) {
        Pattern p = Pattern.compile("^\\d+");

        int res = object1.getBookName().compareToIgnoreCase(object2.getRuleSubclauseNum());
        if (res != 0) {
            return res;
        }

        Matcher m = p.matcher(object1.getRuleSubclauseNum());
        Integer number1 = null;
        if (!m.find()) {
            return object1.getRuleSubclauseNum().compareTo(object2.getRuleSubclauseNum());
        } else {
            Integer number2 = null;
            number1 = Integer.parseInt(m.group());
            m = p.matcher(object2.getRuleTitle());
            if (!m.find()) {
                return object1.getRuleSubclauseNum().compareTo(object2.getRuleSubclauseNum());
            } else {
                number2 = Integer.parseInt(m.group());
                int comparison = number1.compareTo(number2);
                if (comparison != 0) {
                    return comparison;
                } else {
                    return object1.getRuleSubclauseNum().compareTo(object2.getRuleSubclauseNum());
                }
            }
        }
    }
}
