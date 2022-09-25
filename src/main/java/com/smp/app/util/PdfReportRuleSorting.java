package com.smp.app.util;

import com.smp.app.pojo.ReviewerPreviewDetailsResponseTO;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfReportRuleSorting implements Comparator<ReviewerPreviewDetailsResponseTO> {


    @Override
    public int compare(ReviewerPreviewDetailsResponseTO object1, ReviewerPreviewDetailsResponseTO object2) {
        Pattern p = Pattern.compile("^\\d+");

        Matcher m = p.matcher(object1.getRuleSubclauseNum());
        Integer number1 = null;
        if (!m.find()) {
            return object1.getRuleSubclauseNum().compareTo(object2.getRuleSubclauseNum());
        } else {
            Integer number2 = null;
            number1 = Integer.parseInt(m.group());
            m = p.matcher(object2.getRuleSubclauseNum());
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
