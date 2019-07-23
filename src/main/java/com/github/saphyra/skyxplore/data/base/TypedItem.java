package com.github.saphyra.skyxplore.data.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypedItem {
    private String type;
}
