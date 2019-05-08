package com.github.saphyra.skyxplore.gamedata.subservice;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.gamedata.base.AbstractGameDataService;
import com.github.saphyra.skyxplore.gamedata.entity.GeneralDescription;
import org.junit.Before;
import org.junit.Test;

import com.github.saphyra.skyxplore.testing.TestGeneralDescription;

public class CommonDataQueryServiceTest {
    private static final String CATEGORY_ID_1 = "category_id_1";
    private static final String CATEGORY_ID_2 = "category_id_2";
    private static final String DATA_ID_1 = "data_id_1";
    private static final String DATA_ID_2 = "data_id_2";
    private static final String DATA_SLOT = "data_slot";

    private CommonDataQueryService underTest;

    @Before
    public void setUp() {
        underTest = new CommonDataQueryService(givenServices());
        underTest.init();
    }

    @Test
    public void testGetItemsOfCategoryShouldFilter() {
        //WHEN
        List<String> result = underTest.getItemsOfCategory(CATEGORY_ID_1);
        //THEN
        assertThat(result).containsOnly(DATA_ID_1);
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
                put(DATA_ID_1, new TestGeneralDescription(DATA_ID_1, CATEGORY_ID_1, DATA_SLOT));
                put(DATA_ID_2, new TestGeneralDescription(DATA_ID_2, CATEGORY_ID_2, DATA_SLOT));
            }
        };
    }
}