package com.github.saphyra.skyxplore.game.gamecreator;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GroupingCreatorConfiguration;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.util.OffsetDateTimeProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupingQueryServiceTest {
    private static final OffsetDateTime CURRENT_DATE = OffsetDateTime.now();

    @Mock
    private GroupingCreatorConfiguration configuration;

    @Mock
    private GameGroupingStorage gameGroupingStorage;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @InjectMocks
    private GameGroupingQueryService underTest;

    @Mock
    private GameGrouping gameGrouping1;

    @Mock
    private GameGrouping gameGrouping2;

    @Mock
    private GameGrouping gameGrouping3;

    @Test
    public void getAutoFillableGameGroupings() {
        //GIVEN
        given(gameGroupingStorage.values()).willReturn(Arrays.asList(gameGrouping1, gameGrouping2, gameGrouping3));

        given(configuration.getAutoFillDelaySeconds()).willReturn(1);
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(CURRENT_DATE);

        given(gameGrouping1.getCreatedAt()).willReturn(CURRENT_DATE);
        given(gameGrouping2.getCreatedAt()).willReturn(CURRENT_DATE.minusSeconds(2));
        given(gameGrouping3.getCreatedAt()).willReturn(CURRENT_DATE.minusSeconds(2));

        given(gameGrouping2.hasEnoughMembers()).willReturn(true);
        given(gameGrouping3.hasEnoughMembers()).willReturn(false);
        //WHEN
        List<GameGrouping> result = underTest.getAutoFillableGameGroupings();
        //THEN
        assertThat(result).containsExactly(gameGrouping3);
    }

    @Test
    public void getGroupingsByGameMode() {
        //GIVEN
        given(gameGroupingStorage.values()).willReturn(Arrays.asList(gameGrouping1, gameGrouping2));
        given(gameGrouping1.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(gameGrouping2.getGameMode()).willReturn(GameMode.VS);
        //WHEN
        List<GameGrouping> result = underTest.getGroupingsByGameMode(GameMode.VS);
        //THEN
        assertThat(result).containsExactly(gameGrouping2);
    }

    @Test
    public void getGroupingsWithEnoughMembers() {
        //GIVEN
        given(gameGroupingStorage.values()).willReturn(Arrays.asList(gameGrouping1, gameGrouping2));
        given(gameGrouping1.hasEnoughMembers()).willReturn(true);
        given(gameGrouping2.hasEnoughMembers()).willReturn(false);
        //WHEN
        List<GameGrouping> result = underTest.getGroupingsWithEnoughMembers();
        //THEN
        assertThat(result).containsExactly(gameGrouping1);
    }

}