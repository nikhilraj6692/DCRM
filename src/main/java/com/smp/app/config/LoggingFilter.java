package com.smp.app.config;

import com.smp.app.util.Constants;
import io.micrometer.core.instrument.util.IOUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Slf4j
@Order(-1000)
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpServletRequestWrapper requestWrapper;
        if (null != request.getHeader("content-type") && request.getHeader("content-type").contains("multipart/form-data")) {
            requestWrapper = new StandardMultipartHttpServletRequest(request);
        } else {
            requestWrapper = new CachedBodyHttpServletRequest(request);
        }
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        //add correlation id to request
        String correlationId = getOrCreateCorrelationId(request);
        MDC.put(Constants.CORRELATION_ID, correlationId);

        response.setHeader(Constants.CORRELATION_ID, correlationId);

        long startTime = System.currentTimeMillis();
        Map<String, Object> headers = getHeaders(request);

        String requestBody = null;
        Object requestHeader = headers.get("content-type");
        if (null != requestHeader) {
            if (!requestHeader.toString().contains("multipart/form-data")) {
                requestBody = IOUtils.toString(requestWrapper.getInputStream(), StandardCharsets.UTF_8);
            } else {
                Map<String, String> parameterMap = new HashMap<>();

                requestWrapper.getParameterMap().entrySet().stream().forEach(entry -> {
                    parameterMap.put(entry.getKey(), entry.getValue()[0]);
                });

                Map<String, MultipartFile> multipartFileMap =
                    ((StandardMultipartHttpServletRequest) requestWrapper).getMultiFileMap()
                    .toSingleValueMap();
                multipartFileMap.entrySet().stream().forEach(entry -> {
                    parameterMap.put(entry.getKey(), entry.getValue().getOriginalFilename());
                });

                requestBody = parameterMap.toString();
            }
        }

        StringBuilder requestLogBuilder = new StringBuilder();
        requestLogBuilder.append("\nMethod :: {}, ").append("\nRequest URI :: {}, ")
            .append(null != headers.get("content-length") ? "\nRequest Payload ::  {}, " : "{}")
            .append("\nRequest Headers :: {}");
        log.info(requestLogBuilder.toString(), request.getMethod(),
            request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""), requestBody,
            headers);

        filterChain.doFilter(requestWrapper, responseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;

        String responseBody = null;
        String responseHeader = responseWrapper.getContentType();
        if (null != responseHeader) {
            if (responseHeader.equals("application/json")) {
                responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                    responseWrapper.getCharacterEncoding());
            } else {
                //if the download response is a file, then get the file name from content-disposition
                responseBody = fetchFileNameFromContentDisposition(responseWrapper);
            }
        }

        StringBuilder responseLogBuilder = new StringBuilder();
        responseLogBuilder.append("\nResponse Code :: {}, ").append("\nResponse :: {}, ").append("\nTime Taken :: {} ms");
        log.info(responseLogBuilder.toString(), response.getStatus(), responseBody, timeTaken);
        responseWrapper.copyBodyToResponse();

    }

    private String fetchFileNameFromContentDisposition(ContentCachingResponseWrapper responseWrapper) {
        String contentDispositionHeader = responseWrapper.getHeader("content-disposition");
        if (null != contentDispositionHeader) {
            int fileIndex = contentDispositionHeader.indexOf("filename=");
            if (fileIndex != -1) {
                return contentDispositionHeader.substring(fileIndex);
            }
        }

        return null;
    }

    @Override
    public void destroy() {
        MDC.remove(Constants.CORRELATION_ID);
    }

    private Map<String, Object> getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        Map<String, Object> headers = new LinkedHashMap<>();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                headers.put(header, request.getHeader(header));
            }
        }

        return headers;
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getOrCreateCorrelationId(final HttpServletRequest request) {
        final String correlationId = request.getHeader(Constants.CORRELATION_ID);
        if (null == correlationId) {
            return UUID.randomUUID().toString();
        }
        return correlationId;
    }

}