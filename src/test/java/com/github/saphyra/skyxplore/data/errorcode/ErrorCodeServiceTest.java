package com.github.saphyra.skyxplore.data.errorcode;

import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.testing.ExceptionValidator;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCodeServiceTest {
    private static final String LOCALE = "locale";

    @InjectMocks
    private ErrorCodeService underTest;

    @Test
    public void validateLocale_invalid() {
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.validateLocale(LOCALE));
        //THEN
        ExceptionValidator.verifyException(ex, BadRequestException.class, ErrorCode.INVALID_LOCALE);
    }
}