package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.fromlobby.FillFromLobbyService;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai.FillWithAiService;

@RunWith(MockitoJUnitRunner.class)
public class FillGroupingServiceTest {
    @Mock
    private FillFromLobbyService fillFromLobbyService;

    @Mock
    private FillWithAiService fillWithAiService;

    @InjectMocks
    private FillGroupingService underTest;

    @Test
    public void fillGroupingsWithLobbies() {
        //WHEN
        underTest.fillGroupingsWithLobbies();
        //THEN
        verify(fillFromLobbyService).fillGroupingsWithLobbies();
    }

    @Test
    public void fillGroupingsWithAis() {
        //WHEN
        underTest.fillGroupingsWithAis();
        //THEN
        verify(fillWithAiService).fillWithAi();
    }
}