package com.github.saphyra.skyxplore.platform.errortranslation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeLocalization;

@RunWith(MockitoJUnitRunner.class)
public class ErrorLobbyMessageResolverTest {
    private static final String ERROR_CODE = "error_code";
    private static final String ERROR_MESSAGE = "error_message";

    @Mock
    private ErrorCodeLocalizationResolver errorCodeLocalizationResolver;

    @InjectMocks
    private ErrorMessageResolver underTest;

    @Mock
    private ErrorCodeLocalization errorCodeLocalization;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        given(errorCodeLocalizationResolver.getErrorCodeLocalization(request)).willReturn(Optional.of(errorCodeLocalization));
    }

    @Test
    public void getErrorMessage() {
        //GIVEN
        given(errorCodeLocalization.getOptional(ERROR_CODE)).willReturn(Optional.of(ERROR_MESSAGE));
        //WHEN
        String result = underTest.getErrorMessage(request, ERROR_CODE);
        //THEN
        assertThat(result).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    public void getErrorMessage_messageNotFound() {
        //GIVEN
        given(errorCodeLocalization.getOptional(ERROR_CODE)).willReturn(Optional.empty());
        //WHEN
        String result = underTest.getErrorMessage(request, ERROR_CODE);
        //THEN
        assertThat(result).startsWith("Could not translate errorCode").endsWith(ERROR_CODE);
    }

    @Test
    public void getErrorMessage_localizationNotFound() {
        //GIVEN
        given(errorCodeLocalizationResolver.getErrorCodeLocalization(request)).willReturn(Optional.empty());
        //WHEN
        String result = underTest.getErrorMessage(request, ERROR_CODE);
        //THEN
        assertThat(result).startsWith("Could not translate errorCode").endsWith(ERROR_CODE);
    }
}