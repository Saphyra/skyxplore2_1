package com.github.saphyra.skyxplore.platform.errortranslation;

import org.springframework.stereotype.Component;

@Component
//TODO unit test
class ParamKeyAssembler {
    String assembleKey(String key) {
        return "\\$\\{" + key + "\\}";
    }
}
