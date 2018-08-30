package skyxplore.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.PageController;

@RequiredArgsConstructor
@Slf4j
@Component
//TODO unit test
public class CharacterAuthFilter extends OncePerRequestFilter {
    public static final String COOKIE_CHARACTER_ID = "characterid";

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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("CharacterAuthFilter");
        String path = request.getRequestURI();
        log.debug("processing path {}", path);
        if (isAllowedPath(path)) {
            log.debug("Path is allowed.");
            filterChain.doFilter(request, response);
        } else if (isAuthenticated(request)) {
            log.debug("Authentication successful");
            filterChain.doFilter(request, response);
        } else {
            if ("rest".equals(request.getHeader("Request-Type"))) {
                log.info("Sending error. Cause: Unauthorized character access.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
            } else {
                log.info("Redirect to characterSelect page. Cause: Unauthorized character access.");
                response.sendRedirect(PageController.CHARACTER_SELECT_MAPPING);
            }
        }
    }
    private boolean isAllowedPath(String path) {
        return allowedUris.stream().anyMatch(allowedPath -> pathMatcher.match(allowedPath, path));
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        //TODO implement
        return false;
    }
}
