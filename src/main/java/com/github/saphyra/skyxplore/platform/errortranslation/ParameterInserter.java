package com.github.saphyra.skyxplore.platform.errortranslation;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ParameterInserter {
    private final ParamKeyAssembler paramKeyAssembler;

    String insertParams(String errorMessage, Map<String, String> params) {
        String result = errorMessage;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = paramKeyAssembler.assembleKey(entry.getKey());
            result = result.replaceAll(key, entry.getValue());
        }
        return result;
    }
}
