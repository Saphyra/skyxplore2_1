package com.github.saphyra.selenium.logic.domain.localization;

import java.util.EnumMap;
import java.util.Map;

public class PageLocalizations extends EnumMap<Page, PageLocalization> {
    public PageLocalizations(Map<Page, PageLocalization> data) {
        super(Page.class);
        putAll(data);
    }
}
