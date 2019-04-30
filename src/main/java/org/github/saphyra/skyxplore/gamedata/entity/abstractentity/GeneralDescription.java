package org.github.saphyra.skyxplore.gamedata.entity.abstractentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GeneralDescription {
    private String id;
    private String slot;
    private String category;
}
