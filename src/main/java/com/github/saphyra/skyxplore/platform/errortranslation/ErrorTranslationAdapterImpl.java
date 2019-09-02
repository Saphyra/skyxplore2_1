package com.github.saphyra.skyxplore.platform.errortranslation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeLocalization;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ErrorTranslationAdapterImpl implements ErrorTranslationAdapter {
    private final ErrorCodeLocalizationResolver errorCodeLocalizationResolver;
    private final ErrorMessageResolver errorMessageResolver;
    private final ParameterInserter parameterInserter;

    public String translateMessage(HttpServletRequest request, @NonNull String errorCode, @NonNull Map<String, String> params) {
        log.info("Translating errorCode {} with params {}", errorCode, params);

        ErrorCodeLocalization errorCodeLocalization = errorCodeLocalizationResolver.getErrorCodeLocalization(request);
        String errorMessage = errorMessageResolver.getErrorMessage(errorCode, errorCodeLocalization);
        String result = parameterInserter.insertParams(errorMessage, params);
        log.info("Translated errorMessage: {}", result);
        return result;
    }

}
