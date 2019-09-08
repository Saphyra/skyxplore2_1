package com.github.saphyra.skyxplore.platform.filter;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class UserIdCleanupFilterTest {
    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private UserIdCleanupFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void doFilter() throws ServletException, IOException {
        //WHEN
        underTest.doFilter(request, response, filterChain);
        //THEN
        verify(cookieUtil).setCookie(response, RequestConstants.COOKIE_USER_ID, "", 0);
        verify(filterChain).doFilter(request, response);
    }

}