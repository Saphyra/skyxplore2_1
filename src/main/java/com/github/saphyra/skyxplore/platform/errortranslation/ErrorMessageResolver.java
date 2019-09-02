package com.github.saphyra.skyxplore.platform.errortranslation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.errorcode.ErrorCodeLocalization;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//TODO unit test
class ErrorMessageResolver {
    private static final String DEFAULT_ERROR_TRANSLATION_PREFIX = "Could not translate errorCode %s";

    String getErrorMessage(String errorCode, ErrorCodeLocalization errorCodeLocalization) {
        String errorMessage = Optional.ofNullable(errorCodeLocalization)
            .flatMap(errorCodes -> Optional.ofNullable(errorCodes.get(errorCode)))
            .orElseGet(() -> String.format(DEFAULT_ERROR_TRANSLATION_PREFIX, errorCode));
        log.debug("ErrorMessage found: {}", errorMessage);
        return errorMessage;
    }
}
