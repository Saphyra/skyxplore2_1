package skyxplore.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.filter.CustomFilterHelper.REQUEST_TYPE_HEADER;
import static skyxplore.filter.CustomFilterHelper.REST_TYPE_REQUEST;
import static skyxplore.testutil.TestUtils.REDIRECTION_PATH;

@RunWith(MockitoJUnitRunner.class)
public class CustomFilterHelperTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CustomFilterHelper underTest;

    @Test
    public void testHandleUnauthorizedShouldSendErrorWhenRest() throws IOException {
        //GIVEN
        when(request.getHeader(REQUEST_TYPE_HEADER)).thenReturn(REST_TYPE_REQUEST);
        //WHEN
        underTest.handleUnauthorized(request, response, REDIRECTION_PATH);
        //THEN
        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void testHandleUnauthorizedShouldRedirectWhenNotRest() throws IOException {
        //GIVEN
        when(request.getHeader(REQUEST_TYPE_HEADER)).thenReturn(null);
        //WHEN
        underTest.handleUnauthorized(request, response, REDIRECTION_PATH);
        //THEN
        verify(response).sendRedirect(REDIRECTION_PATH);
    }
}