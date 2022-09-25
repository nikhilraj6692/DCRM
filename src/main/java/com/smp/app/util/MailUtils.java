package com.smp.app.util;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


@SuppressWarnings("deprecation")
public class MailUtils {

    static final Logger LOGGER = LoggerFactory.getLogger(MailUtils.class);

    public void sendMail(String from, List<String> to, String subject, String msg) {
        try {
            Resource res = new ClassPathResource("mailConfiguration.xml");
            BeanFactory factory = new XmlBeanFactory(res);
            Object o = factory.getBean("id2");
            AOPUtil ml = (AOPUtil) o;
            ml.sendMail(from, to, subject, msg);
            //System.out.println("Verification Code Sent Successfully!!");
        } catch (Exception e) {
            LOGGER.info("Exception in MailUtils -> Send Mail Method -> " + e.getMessage());
            e.printStackTrace();
        }
    }

}
