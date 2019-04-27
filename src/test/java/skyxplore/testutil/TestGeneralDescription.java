package skyxplore.testutil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.DATA_SLOT;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestGeneralDescription extends GeneralDescription {
    public TestGeneralDescription(String category){
        this(DATA_ELEMENT, category);
    }
    public TestGeneralDescription(String id, String category) {
        setId(id);
        setSlot(DATA_SLOT);
        setCategory(category);
    }
}
