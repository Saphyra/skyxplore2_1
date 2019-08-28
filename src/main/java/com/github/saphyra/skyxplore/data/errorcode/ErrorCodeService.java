package com.github.saphyra.skyxplore.data.errorcode;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;

@Component
public class ErrorCodeService extends AbstractDataService<ErrorCodeLocalization> {
    public ErrorCodeService() {
        super("public/i18n/error_code");
    }

    @Override
    @PostConstruct
    public void init() {
        super.load(ErrorCodeLocalization.class);
    }

    //TODO unit test
    public void validateLocale(String locale) {
        if(!isValidLocale(locale)){
            throw ExceptionFactory.invalidLocale(locale);
        }
    }

    private boolean isValidLocale(String locale) {
        return containsKey(locale);
    }
}
