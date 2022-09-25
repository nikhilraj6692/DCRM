package com.smp.app.util;

import com.smp.app.pojo.RuleListResponseTO;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestComp implements Comparator<RuleListResponseTO> {

    @Override
    public int compare(RuleListResponseTO object1, RuleListResponseTO object2) {
        Pattern p = Pattern.compile("^\\d+");

        Matcher m = p.matcher(object1.getRuleTitle());
        Integer number1 = null;
        if (!m.find()) {
            return object1.getRuleTitle().compareTo(object2.getRuleTitle());
        } else {
            Integer number2 = null;
            number1 = Integer.parseInt(m.group());
            m = p.matcher(object2.getRuleTitle());
            if (!m.find()) {
                return object1.getRuleTitle().compareTo(object2.getRuleTitle());
            } else {
                number2 = Integer.parseInt(m.group());
                int comparison = number1.compareTo(number2);
                if (comparison != 0) {
                    return comparison;
                } else {
                    return object1.getRuleTitle().compareTo(object2.getRuleTitle());
                }
            }
        }
    }

}
