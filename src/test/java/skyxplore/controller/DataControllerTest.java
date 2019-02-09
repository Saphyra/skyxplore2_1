package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.gamedata.subservice.CommonDataQueryService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CATEGORY_ID;
import static skyxplore.testutil.TestUtils.DATA_ID_1;

@RunWith(MockitoJUnitRunner.class)
public class DataControllerTest {
    @Mock
    private CommonDataQueryService commonDataQueryService;

    @InjectMocks
    private DataController underTest;

    @Test
    public void testGetItemsOfCategory() {
        //GIVEN
        List<String> items = Arrays.asList(DATA_ID_1);
        when(commonDataQueryService.getItemsOfCategory(CATEGORY_ID)).thenReturn(items);
        //WHEN
        List<String> result = underTest.getItemsOfCategory(CATEGORY_ID);
        //THEN
        assertEquals(items, result);
    }
}