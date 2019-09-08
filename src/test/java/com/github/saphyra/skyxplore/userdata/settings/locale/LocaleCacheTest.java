package com.github.saphyra.skyxplore.userdata.settings.locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocaleCacheTest {
    private static final String KEY = "key";
    private static final String LOCALE = "locale";

    @Mock
    private LocaleQueryService localeQueryService;

    @InjectMocks
    private LocaleCache underTest;

    @Test
    public void get() {
        //GIVEN
        given(localeQueryService.getLocale(KEY)).willReturn(Optional.of(LOCALE));
        //WHEN
        Optional<String> result = underTest.get(KEY);
        //THEN
        assertThat(result).contains(LOCALE);
    }
}