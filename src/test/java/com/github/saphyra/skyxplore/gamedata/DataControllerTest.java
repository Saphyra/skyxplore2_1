package com.github.saphyra.skyxplore.gamedata;

import com.github.saphyra.skyxplore.gamedata.subservice.CommonDataQueryService;
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
public class DataControllerTest {
    private static final String DATA_ID = "data_id";
    private static final String CATEGORY_ID = "category_id";

    @Mock
    private CommonDataQueryService commonDataQueryService;

    @InjectMocks
    private DataController underTest;

    @Test
    public void testGetItemsOfCategory() {
        //GIVEN
        List<String> items = Arrays.asList(DATA_ID);
        when(commonDataQueryService.getItemsOfCategory(CATEGORY_ID)).thenReturn(items);
        //WHEN
        List<String> result = underTest.getItemsOfCategory(CATEGORY_ID);
        //THEN
        assertThat(result).isEqualTo(items);
    }
}