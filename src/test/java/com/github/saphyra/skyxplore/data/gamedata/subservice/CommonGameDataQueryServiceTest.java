package com.github.saphyra.skyxplore.data.gamedata.subservice;


import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.entity.GeneralDescription;
import com.github.saphyra.testing.TestGeneralDescription;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonGameDataQueryServiceTest {
    private static final String CATEGORY_ID_1 = "category_id_1";
    private static final String CATEGORY_ID_2 = "category_id_2";
    private static final String DATA_ID_1 = "data_id_1";
    private static final String DATA_ID_2 = "data_id_2";
    private static final String DATA_SLOT = "data_slot";

    private CommonGameDataQueryService underTest;

    @Before
    public void setUp() {
        underTest = new CommonGameDataQueryService(givenServices());
        underTest.init();
    }

    @Test
    public void testGetItemsOfCategoryShouldFilter() {
        //WHEN
        List<String> result = underTest.getItemsOfCategory(CATEGORY_ID_1);
        //THEN
        assertThat(result).containsOnly(DATA_ID_1);
    }

    private List<AbstractDataService<? extends GeneralDescription>> givenServices() {
        AbstractDataService<GeneralDescription> generalDescriptionService = createGeneralDescriptionService();
        generalDescriptionService.init();
        return Arrays.asList(generalDescriptionService);
    }

    private AbstractDataService<GeneralDescription> createGeneralDescriptionService() {
        return new AbstractDataService<GeneralDescription>() {
            @Override
            public void init() {
                put(DATA_ID_1, new TestGeneralDescription(DATA_ID_1, CATEGORY_ID_1, DATA_SLOT));
                put(DATA_ID_2, new TestGeneralDescription(DATA_ID_2, CATEGORY_ID_2, DATA_SLOT));
            }
        };
    }
}