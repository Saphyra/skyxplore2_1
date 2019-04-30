package org.github.saphyra.skyxplore.gamedata;

import org.github.saphyra.skyxplore.gamedata.subservice.CommonDataQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(result).isEqualTo(items);
    }
}