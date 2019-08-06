package com.github.saphyra.skyxplore.platform.filter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
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

@RunWith(MockitoJUnitRunner.class)
public class HeaderSetupFilterTest {
    @InjectMocks
    private HeaderSetupFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void filter() throws ServletException, IOException {
        //WHEN
        underTest.doFilterInternal(request, response, filterChain);
        //THEN
        verify(response, times(3)).setHeader(anyString(), anyString());
        verify(filterChain).doFilter(request, response);
    }
}