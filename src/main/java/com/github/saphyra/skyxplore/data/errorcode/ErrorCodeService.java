package com.github.saphyra.skyxplore.data.errorcode;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
}
