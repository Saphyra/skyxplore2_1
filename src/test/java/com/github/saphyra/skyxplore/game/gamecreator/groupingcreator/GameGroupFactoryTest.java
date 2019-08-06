package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.util.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupFactoryTest {
    private static final int MAX_GROUP_SIZE = 1;
    private static final String CHARACTER_ID = "character_id";
    private static final UUID GAME_GROUP_ID = UUID.randomUUID();

    @Mock
    private GameGroupCharacterFactory gameGroupCharacterFactory;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private GameGroupFactory underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private LobbyMember lobbyMember;

    @Mock
    private GameGroupCharacter gameGroupCharacter;

    @Before
    public void setUp() {
        given(idGenerator.randomUUID()).willReturn(GAME_GROUP_ID);
    }

    @Test
    public void createGroups_fromLobby() {
        //GIVEN
        given(lobby.getMembers()).willReturn(Arrays.asList(lobbyMember, lobbyMember));
        given(lobbyMember.getCharacterId()).willReturn(CHARACTER_ID);
        given(gameGroupCharacterFactory.createGameGroupCharacter(CHARACTER_ID, false)).willReturn(gameGroupCharacter);
        given(lobby.isAutoFill()).willReturn(true);
        //WHEN
        List<GameGroup> result = underTest.createGroups(lobby, MAX_GROUP_SIZE);
        //THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getGameGroupId()).isEqualTo(GAME_GROUP_ID);
        assertThat(result.get(0).getCharacters()).containsExactly(gameGroupCharacter);
        assertThat(result.get(0).getCharacters().getMaxSize()).isEqualTo(MAX_GROUP_SIZE);
        assertThat(result.get(0).isAutoFill()).isTrue();

        assertThat(result.get(1).getGameGroupId()).isEqualTo(GAME_GROUP_ID);
        assertThat(result.get(1).getCharacters()).containsExactly(gameGroupCharacter);
        assertThat(result.get(1).getCharacters().getMaxSize()).isEqualTo(MAX_GROUP_SIZE);
        assertThat(result.get(1).isAutoFill()).isTrue();
    }

    @Test
    public void createGroups_fromGameGroupCharacters() {
        //WHEN
        List<GameGroup> result = underTest.createGroups(Arrays.asList(gameGroupCharacter, gameGroupCharacter), true, MAX_GROUP_SIZE);
        //THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getGameGroupId()).isEqualTo(GAME_GROUP_ID);
        assertThat(result.get(0).getCharacters()).containsExactly(gameGroupCharacter);
        assertThat(result.get(0).isAutoFill()).isTrue();

        assertThat(result.get(1).getGameGroupId()).isEqualTo(GAME_GROUP_ID);
        assertThat(result.get(1).getCharacters()).containsExactly(gameGroupCharacter);
        assertThat(result.get(1).isAutoFill()).isTrue();
    }

    @Test
    public void createGroup() {
        //WHEN
        GameGroup result = underTest.createGroup(Arrays.asList(gameGroupCharacter), true, MAX_GROUP_SIZE);
        //THEN
        assertThat(result.getGameGroupId()).isEqualTo(GAME_GROUP_ID);
        assertThat(result.getCharacters()).containsExactly(gameGroupCharacter);
        assertThat(result.getCharacters().getMaxSize()).isEqualTo(MAX_GROUP_SIZE);
        assertThat(result.isAutoFill()).isTrue();
    }
}