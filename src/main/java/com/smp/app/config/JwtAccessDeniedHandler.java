package com.smp.app.config;

import com.smp.app.util.SMPAppConstants;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


public class JwtAccessDeniedHandler implements AccessDeniedHandler, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
        throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, SMPAppConstants.FORBIDDEN);
    }
}