package org.github.saphyra.skyxplore.testing;

import org.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class TestGeneralDescription extends GeneralDescription {
    public TestGeneralDescription(String id, String category, String slot) {
        setId(id);
        setSlot(slot);
        setCategory(category);
    }
}
