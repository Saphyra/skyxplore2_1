package com.github.saphyra.skyxplore.data.gamedata;

import com.github.saphyra.skyxplore.data.gamedata.subservice.CommonGameDataQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameDataControllerTest {
    private static final String DATA_ID = "data_id";
    private static final String CATEGORY_ID = "category_id";

    @Mock
    private CommonGameDataQueryService commonGameDataQueryService;

    @InjectMocks
    private GameDataController underTest;

    @Test
    public void testGetItemsOfCategory() {
        //GIVEN
        List<String> items = Arrays.asList(DATA_ID);
        when(commonGameDataQueryService.getItemsOfCategory(CATEGORY_ID)).thenReturn(items);
        //WHEN
        List<String> result = underTest.getItemsOfCategory(CATEGORY_ID);
        //THEN
        assertThat(result).isEqualTo(items);
    }
}