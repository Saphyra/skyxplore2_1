package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.OffsetDateTimeProvider;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupingFactoryTest {
    private static final int EXPECTED_GAME_MEMBERS_AMOUNT = 7;
    private static final UUID GAME_GROUPING_ID = UUID.randomUUID();
    private static final String LOBBY_DATA = "lobby_data";
    private static final OffsetDateTime ACTUAL_DATE = OffsetDateTime.now();
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @InjectMocks
    private GameGroupingFactory underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private GameGroup gameGroup;

    @Mock
    private GameGroupSizeRange gameGroupSizeRange;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.randomUUID()).willReturn(GAME_GROUPING_ID);
        given(lobby.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(lobby.getData()).willReturn(LOBBY_DATA);
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(ACTUAL_DATE);
        given(lobby.getLobbyId()).willReturn(LOBBY_ID);
        //WHEN
        GameGrouping result = underTest.create(lobby, Arrays.asList(gameGroup), EXPECTED_GAME_MEMBERS_AMOUNT, gameGroupSizeRange);
        //THEN
        assertThat(result.getGameGroupingId()).isEqualTo(GAME_GROUPING_ID);
        assertThat(result.getMinimumGameMembersAmount()).isEqualTo(EXPECTED_GAME_MEMBERS_AMOUNT);
        assertThat(result.getGameGroupSizeRange()).isEqualTo(gameGroupSizeRange);
        assertThat(result.getGameMode()).isEqualTo(GameMode.TEAMFIGHT);
        assertThat(result.getData()).isEqualTo(LOBBY_DATA);
        assertThat(result.getCreatedAt()).isEqualTo(ACTUAL_DATE);
        assertThat(result.getLockedLobbyIds()).containsExactly(LOBBY_ID);
        assertThat(result.getGameGroups()).containsExactly(gameGroup);
    }
}