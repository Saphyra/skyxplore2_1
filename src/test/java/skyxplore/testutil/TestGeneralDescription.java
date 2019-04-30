package skyxplore.testutil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;


@EqualsAndHashCode(callSuper = true)
@Data
public class TestGeneralDescription extends GeneralDescription {
    public TestGeneralDescription(String id, String category, String slot) {
        setId(id);
        setSlot(slot);
        setCategory(category);
    }
}
