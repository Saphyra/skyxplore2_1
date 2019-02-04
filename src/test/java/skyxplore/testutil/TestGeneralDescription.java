package skyxplore.testutil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.DATA_SLOT;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestGeneralDescription extends GeneralDescription {
    public TestGeneralDescription(){
        setId(DATA_ELEMENT);
        setSlot(DATA_SLOT);
    }
}
