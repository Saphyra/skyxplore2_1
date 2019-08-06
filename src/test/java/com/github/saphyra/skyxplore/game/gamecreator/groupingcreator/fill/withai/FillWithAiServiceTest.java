package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FillWithAiServiceTest {
    private static final Integer MISSING_MEMBERS = 23454;
    @Mock
    private ExistingGroupFiller existingGroupFiller;

    @Mock
    private FillWithAiGroupsService fillWithAiGroupsService;

    @Mock
    private GameGroupingQueryService gameGroupingQueryService;

    @InjectMocks
    private FillWithAiService underTest;

    @Mock
    private GameGrouping gameGrouping;

    @Test
    public void fillWithAi() {
        //GIVEN
        given(gameGroupingQueryService.getAutoFillableGameGroupings()).willReturn(Arrays.asList(gameGrouping));
        given(existingGroupFiller.fillEmptyPlacesInExistingGroups(gameGrouping)).willReturn(MISSING_MEMBERS);
        //WHEN
        underTest.fillWithAi();
        //THEN
        verify(fillWithAiGroupsService).fillGameWithAiGroups(gameGrouping, MISSING_MEMBERS);
    }
}