package skyxplore.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import skyxplore.controller.PageController;
import skyxplore.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static skyxplore.filter.FilterHelper.COOKIE_CHARACTER_ID;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class CookieCleanupFilter extends OncePerRequestFilter {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("CookieCleanupFilter");
        String path = request.getRequestURI();

        if (pathMatcher.match(PageController.CHARACTER_SELECT_MAPPING, path)) {
            log.info("Cleaning up characterCookie...");
            cookieUtil.setCookie(response, COOKIE_CHARACTER_ID, "");
        }

        filterChain.doFilter(request, response);
    }
}
