package testutil;

import static testutil.TestUtils.DATA_DESCRIPTION;
import static testutil.TestUtils.DATA_ELEMENT;
import static testutil.TestUtils.DATA_NAME;
import static testutil.TestUtils.DATA_SLOT;

import lombok.Data;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

@Data
public class TestGeneralDescription extends GeneralDescription {
    public TestGeneralDescription(){
        setId(DATA_ELEMENT);
        setName(DATA_NAME);
        setDescription(DATA_DESCRIPTION);
        setSlot(DATA_SLOT);
    }
}
