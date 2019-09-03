package com.github.saphyra.skyxplore.platform.errortranslation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorTranslationAdapterImplTest {
    private static final String ERROR_CODE = "error_code";
    private static final String ERROR_MESSAGE = "error_message";
    private static final String INSERTED_ERROR_MESSAGE = "inserted_error_message";

    @Mock
    private ErrorMessageResolver errorMessageResolver;

    @Mock
    private ParameterInserter parameterInserter;

    @InjectMocks
    private ErrorTranslationAdapterImpl underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Map<String, String> params;

    @Test
    public void translateMessage() {
        //GIVEN
        given(errorMessageResolver.getErrorMessage(request, ERROR_CODE)).willReturn(ERROR_MESSAGE);
        given(parameterInserter.insertParams(ERROR_MESSAGE, params)).willReturn(INSERTED_ERROR_MESSAGE);
        //WHEN
        String result = underTest.translateMessage(request, ERROR_CODE, params);
        //THEN
        assertThat(result).isEqualTo(INSERTED_ERROR_MESSAGE);
    }
}