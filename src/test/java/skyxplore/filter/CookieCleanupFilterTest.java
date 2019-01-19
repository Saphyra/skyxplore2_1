package skyxplore.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.util.CookieUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static skyxplore.controller.PageController.CHARACTER_SELECT_MAPPING;
import static skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static skyxplore.testutil.TestUtils.AUTHENTICATED_PATH;

@RunWith(MockitoJUnitRunner.class)
public class CookieCleanupFilterTest {
    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private CookieCleanupFilter underTest;

    @Test
    public void testShouldCallCookieUtilWhenCharacterSelect() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn(CHARACTER_SELECT_MAPPING);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verify(cookieUtil).setCookie(response, COOKIE_CHARACTER_ID, "");
    }

    @Test
    public void testShouldNotCallCookieUtilWhenNotCharacterSelect() throws ServletException, IOException {
        //GIVEN
        when(request.getRequestURI()).thenReturn(AUTHENTICATED_PATH);
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(cookieUtil);
    }
}