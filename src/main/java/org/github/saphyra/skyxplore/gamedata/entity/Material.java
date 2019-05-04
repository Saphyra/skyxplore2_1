package org.github.saphyra.skyxplore.gamedata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Material extends FactoryData {
    private boolean buildable;
}
