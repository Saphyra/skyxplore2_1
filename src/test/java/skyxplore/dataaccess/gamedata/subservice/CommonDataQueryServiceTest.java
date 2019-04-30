package skyxplore.dataaccess.gamedata.subservice;

import static org.junit.Assert.assertEquals;
import static skyxplore.testutil.TestUtils.DATA_CATEGORY_1;
import static skyxplore.testutil.TestUtils.DATA_CATEGORY_2;
import static skyxplore.testutil.TestUtils.DATA_ID_1;
import static skyxplore.testutil.TestUtils.DATA_ID_2;
import static skyxplore.testutil.TestUtils.DATA_SLOT;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import skyxplore.dataaccess.gamedata.base.AbstractGameDataService;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;
import skyxplore.testutil.TestGeneralDescription;

public class CommonDataQueryServiceTest {
    private CommonDataQueryService underTest;

    @Before
    public void setUp() {
        underTest = new CommonDataQueryService(givenServices());
        underTest.init();
    }

    @Test
    public void testGetItemsOfCategoryShouldFilter() {
        //WHEN
        List<String> result = underTest.getItemsOfCategory(DATA_CATEGORY_1);
        //THEN
        assertEquals(1, result.size());
        assertEquals(DATA_ID_1, result.get(0));
    }

    private List<AbstractGameDataService<? extends GeneralDescription>> givenServices() {
        AbstractGameDataService<GeneralDescription> generalDescriptionService = createGeneralDescriptionService();
        generalDescriptionService.init();
        return Arrays.asList(generalDescriptionService);
    }

    private AbstractGameDataService<GeneralDescription> createGeneralDescriptionService() {
        return new AbstractGameDataService<GeneralDescription>() {
            @Override
            public void init() {
                put(DATA_ID_1, new TestGeneralDescription(DATA_ID_1, DATA_CATEGORY_1, DATA_SLOT));
                put(DATA_ID_2, new TestGeneralDescription(DATA_ID_2, DATA_CATEGORY_2, DATA_SLOT));
            }
        };
    }
}