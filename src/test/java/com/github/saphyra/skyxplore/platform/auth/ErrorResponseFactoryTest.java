package com.github.saphyra.skyxplore.platform.auth;

import static com.github.saphyra.skyxplore.platform.auth.ErrorResponseFactory.GENERAL_ERROR_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.common.ErrorCode;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseFactoryTest {
    private static final String TRANSLATED_MESSAGE = "translated_message";

    @Mock
    private ErrorCodeResolver errorCodeResolver;

    @Mock
    private ErrorTranslationAdapter errorTranslationAdapter;

    @InjectMocks
    private ErrorResponseFactory underTest;

    @Mock
    private HttpServletRequest request;

    @Test
    public void createErrorResponse() {
        //GIVEN
        given(errorCodeResolver.getErrorCode(AccessStatus.LOGIN_FAILED)).willReturn(ErrorCode.WRONG_PASSWORD);
        given(errorTranslationAdapter.translateMessage(eq(request), eq(ErrorCode.WRONG_PASSWORD.name()), any())).willReturn(TRANSLATED_MESSAGE);
        //WHEN
        ErrorResponse result = underTest.createErrorResponse(request, AccessStatus.LOGIN_FAILED);
        //THEN
        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(result.getErrorCode()).isEqualTo(ErrorCode.WRONG_PASSWORD.name());
        assertThat(result.getLocalizedMessage()).isEqualTo(TRANSLATED_MESSAGE);
        assertThat(result.getParams().get(GENERAL_ERROR_KEY)).startsWith("Unknown accessStatus");
    }
}