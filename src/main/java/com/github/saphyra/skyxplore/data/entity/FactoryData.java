package com.github.saphyra.skyxplore.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class FactoryData extends GeneralDescription {
    @JsonProperty("constructiontime")
    private Integer constructionTime;

    private boolean buildable;

    @JsonProperty("buildprice")
    private Integer buildPrice = 0;

    private HashMap<String, Integer> materials;
}
