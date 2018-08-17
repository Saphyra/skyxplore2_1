package skyxplore.testutil;

import static skyxplore.testutil.TestUtils.DATA_DESCRIPTION;
import static skyxplore.testutil.TestUtils.DATA_ELEMENT;
import static skyxplore.testutil.TestUtils.DATA_NAME;
import static skyxplore.testutil.TestUtils.DATA_SLOT;

import lombok.Data;
import lombok.EqualsAndHashCode;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestGeneralDescription extends GeneralDescription {
    public TestGeneralDescription(){
        setId(DATA_ELEMENT);
        setName(DATA_NAME);
        setDescription(DATA_DESCRIPTION);
        setSlot(DATA_SLOT);
    }
}
