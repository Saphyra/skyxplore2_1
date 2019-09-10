package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessages;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.GameFactory;
import com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.GameMessageFactory;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;

@RunWith(MockitoJUnitRunner.class)
public class VsGameGroupingToGameConverterStrategyTest {
    @Mock
    private GameFactory gameFactory;

    @Mock
    private GameMessageFactory gameMessageFactory;

    @InjectMocks
    private VsGameGroupingToGameConverterStrategy underTest;

    @Mock
    private GameGrouping gameGrouping;

    @Mock
    private Game game;

    @Mock
    private GameMessages gameMessages;

    @Test
    public void canConvert_false() {
        //WHEN
        boolean result = underTest.canConvert(GameMode.ARCADE);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void canConvert_true() {
        //WHEN
        boolean result = underTest.canConvert(GameMode.VS);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void convert() {
        //GIVEN
        given(gameMessageFactory.create(gameGrouping)).willReturn(gameMessages);
        given(gameFactory.create(gameGrouping, gameMessages)).willReturn(game);
        //WHEN
        Game result = underTest.convert(gameGrouping);
        //THEN
        assertThat(result).isEqualTo(game);
    }
}