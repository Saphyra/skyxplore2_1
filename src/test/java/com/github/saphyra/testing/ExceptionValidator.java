package com.github.saphyra.testing;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.saphyra.exceptionhandling.exception.RestException;
import com.github.saphyra.skyxplore.common.ErrorCode;

public class ExceptionValidator {
    public static <T extends RestException> void verifyException(Throwable ex, Class<T> expectedClass, ErrorCode errorCode) {
        assertThat(ex).isInstanceOf(expectedClass);
        RestException exception = (RestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(errorCode.name());
    }
}
