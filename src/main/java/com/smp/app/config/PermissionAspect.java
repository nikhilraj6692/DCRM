package com.smp.app.config;

import com.smp.app.pojo.UserContext;
import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    private static final Logger log = LoggerFactory.getLogger(PermissionAspect.class);

    @Autowired
    private AuthorizationProcessor authorizationProcessor;

    @Autowired
    private UserContext userContext;

    @Before(value = "execution(* com.smp.app.controller..*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Before method:" + joinPoint.getSignature());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        /*authorize basic details of logged in user. for all public api, authorizeRequest() would be called in service
        implementation classes
         */
        if (!method.isAnnotationPresent(PublicApi.class)) {
            authorizationProcessor.authorizeRequest(userContext.getUserEmailId());
        }
    }
}
