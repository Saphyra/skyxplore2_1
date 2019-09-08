package com.github.saphyra.skyxplore.platform.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.authservice.auth.domain.AuthContext;
import com.github.saphyra.authservice.auth.domain.RestErrorResponse;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseResolverImplTest {
    @Mock
    private ErrorResponseFactory errorResponseFactory;

    @InjectMocks
    private ErrorResponseResolverImpl underTest;

    @Mock
    private AuthContext authContext;

    @Mock
    private ErrorResponse errorResponse;

    @Mock
    private HttpServletRequest request;

    @Test
    public void getRestErrorResponse() {
        //GIVEN
        given(authContext.getRequest()).willReturn(request);
        given(authContext.getAccessStatus()).willReturn(AccessStatus.LOGIN_FAILED);
        given(errorResponseFactory.createErrorResponse(request, AccessStatus.LOGIN_FAILED)).willReturn(errorResponse);
        //WHEN
        RestErrorResponse result = underTest.getRestErrorResponse(authContext);
        //THEN
        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(result.getResponseBody()).isEqualTo(errorResponse);
    }
}