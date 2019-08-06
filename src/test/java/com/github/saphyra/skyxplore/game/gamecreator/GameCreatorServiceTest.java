package com.github.saphyra.skyxplore.game.gamecreator;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.CreateGameService;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.FillGroupingService;

@RunWith(MockitoJUnitRunner.class)
public class GameCreatorServiceTest {
    @Mock
    private FillGroupingService fillGroupingService;

    @Mock
    private CreateGameService createGameService;

    @InjectMocks
    private GameCreatorService underTest;

    @Test
    public void createGames(){
        //WHEN
        underTest.createGames();
        //THEN
        verify(fillGroupingService).fillGroupingsWithLobbies();
        verify(fillGroupingService).fillGroupingsWithAis();
        verify(createGameService).createGameFromGroupings();
    }
}