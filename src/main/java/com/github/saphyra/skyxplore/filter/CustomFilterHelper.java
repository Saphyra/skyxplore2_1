package com.github.saphyra.skyxplore.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomFilterHelper {
    public static final String COOKIE_ACCESS_TOKEN = "domain";
    public static final String COOKIE_CHARACTER_ID = "characterid";
    public static final String COOKIE_USER_ID = "userid";

    public static final String REQUEST_TYPE_HEADER = "Request-Type";
    public static final String REST_TYPE_REQUEST = "rest";

    void handleUnauthorized(HttpServletRequest request, HttpServletResponse response, String redirection) throws IOException {
        if (isRestCall(request)) {
            log.info("Sending error. Cause: Unauthorized access.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
        } else {
            log.info("Redirect to {}. Cause: Unauthorized access.", redirection);
            response.sendRedirect(redirection);
        }
    }

    boolean isRestCall(HttpServletRequest request) {
        return REST_TYPE_REQUEST.equals(request.getHeader(REQUEST_TYPE_HEADER));
    }
}