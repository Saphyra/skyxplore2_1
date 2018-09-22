package skyxplore.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import skyxplore.controller.PageController;
import skyxplore.service.AccessTokenFacade;
import skyxplore.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthFilter extends OncePerRequestFilter {
    public static final String COOKIE_USER_ID = "userid";
    public static final String COOKIE_ACCESS_TOKEN = "accesstoken";
    static final String REQUEST_TYPE_HEADER = "Request-Type";
    static final String REST_TYPE_REQUEST = "rest";

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> allowedUris = Arrays.asList(
            "/",
            "/**/favicon.ico",
            "/login",
            "/registration",
            "/isusernameexists",
            "/isemailexists",
            "/css/**",
            "/images/**",
            "/js/**"
    );

    private final AccessTokenFacade accessTokenFacade;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("AuthFilter");
        String path = request.getRequestURI();
        log.debug("Request arrived: {}", path);
        if (pathMatcher.match("/logout", path)) {
            logout(request, response);
        } else if (isAllowedPath(path)) {
            log.debug("Allowed path: {}", path);
            filterChain.doFilter(request, response);
        } else if (isAuthenticated(request)) {
            log.debug("Needs authentication: {}", path);
            filterChain.doFilter(request, response);
        } else {
            if (REST_TYPE_REQUEST.equals(request.getHeader(REQUEST_TYPE_HEADER))) {
                log.info("Sending error. Cause: Unauthorized access.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
            } else {
                log.info("Redirect to login page. Cause: Unauthorized access.");
                response.sendRedirect(PageController.INDEX_MAPPING);
            }
        }
    }

    private boolean isAllowedPath(String path){
        return allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path));
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Logging out...");
        String accessTokenId = cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = cookieUtil.getCookie(request, COOKIE_USER_ID);
        accessTokenFacade.logout(userIdValue, accessTokenId);
        log.info("Successfully logged out.");
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        log.debug("Authenticating...");
        String accessTokenId = cookieUtil.getCookie(request, COOKIE_ACCESS_TOKEN);
        String userIdValue = cookieUtil.getCookie(request, COOKIE_USER_ID);

        if(accessTokenId == null || userIdValue == null){
            log.warn("Cookies not found.");
            return false;
        }

        return accessTokenFacade.isAuthenticated(userIdValue, accessTokenId);
    }


}
