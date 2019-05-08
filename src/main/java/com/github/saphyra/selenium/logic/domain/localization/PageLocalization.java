package com.github.saphyra.selenium.logic.domain.localization;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
public class PageLocalization {
    private String title;
    private Map<String, List<LocalizationItem>> staticText;
    private Map<String, String> additionalContent;

    public String getAdditionalContent(String  key){
        return Optional.ofNullable(additionalContent.get(key))
            .orElseThrow(() -> new RuntimeException("No additionalContent found with key " + key));
    }
}
