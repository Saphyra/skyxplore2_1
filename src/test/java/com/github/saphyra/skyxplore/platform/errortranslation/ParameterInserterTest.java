package com.github.saphyra.skyxplore.platform.errortranslation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParameterInserterTest {
    private static final String KEY = "key";
    private static final String ASSEMBLED_KEY = "assembled_key";
    private static final String VALUE = "value";
    private static final String ERROR_MESSAGE = "error assembled_key message assembled_key hello";

    @Mock
    private ParamKeyAssembler paramKeyAssembler;

    @InjectMocks
    private ParameterInserter underTest;

    @Test
    public void insertParams() {
        //GIVEN
        given(paramKeyAssembler.assembleKey(KEY)).willReturn(ASSEMBLED_KEY);
        Map<String, String> params = new HashMap<>();
        params.put(KEY, VALUE);
        //WHEN
        String result = underTest.insertParams(ERROR_MESSAGE, params);
        //THEN
        assertThat(result).isEqualTo("error value message value hello");
    }
}