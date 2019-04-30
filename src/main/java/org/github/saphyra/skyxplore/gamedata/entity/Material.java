package org.github.saphyra.skyxplore.gamedata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.FactoryData;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Material extends FactoryData {
    private boolean buildable;
}
