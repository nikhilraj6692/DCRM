package com.smp.app.util;

import java.util.Locale;
import org.springframework.context.MessageSource;


public class MessageResourceResolver {

    private MessageSource messageSource;

    private MessageResourceResolver() {

    }

    public static MessageResourceResolver getInstance() {
        return MessageResourceResolverInstanceHolder.INSTANCE;
    }

    public String getNormalizedMessage(String message, Locale locale, Object[] args) {
        return messageSource.getMessage(message, args, locale);
    }

    private static class MessageResourceResolverInstanceHolder {

        private static final MessageResourceResolver INSTANCE;

        static {
            INSTANCE = new MessageResourceResolver();
            INSTANCE.messageSource = ApplicationContextFactory.getObject(MessageSource.class);
        }
    }


}
