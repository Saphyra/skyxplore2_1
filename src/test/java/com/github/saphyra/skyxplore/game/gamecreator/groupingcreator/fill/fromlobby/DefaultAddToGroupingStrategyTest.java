package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupSizeRangeProvider;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupingFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAddToGroupingStrategyTest {
    @Mock
    private GameGroupingQueryService gameGroupingQueryService;

    @Mock
    private GameGroupingStorage gameGroupingStorage;

    @Mock
    private GameGroupFactory gameGroupFactory;

    @Mock
    private GameGroupingFactory gameGroupingFactory;

    @Mock
    private GameGroupSizeRangeProvider gameGroupSizeRangeProvider;

    @InjectMocks
    private DefaultAddToGroupingStrategy underTest;

    @Mock
    private Lobby lobby;

    @Test
    public void add(){
        //GIVEN

        //WHEN

        //THEN
    }
}