package org.github.saphyra.skyxplore.filter;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.REQUEST_TYPE_HEADER;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.REST_TYPE_REQUEST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomFilterHelperTest {
    private static final String REDIRECTION_PATH = "redirection_path";

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