package com.github.saphyra.skyxplore.userdata.characterstatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.game.GameQueryService;
import com.github.saphyra.skyxplore.game.game.domain.Game;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.platform.auth.domain.SkyXpAccessToken;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.userdata.characterstatus.domain.CharacterStatus;

@RunWith(MockitoJUnitRunner.class)
public class CharacterStatusQueryServiceTest {
    private static final String CHARACTER_ID = "characer_id";

    @Mock
    private AccessTokenDao accessTokenDao;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @Mock
    private GameQueryService gameQueryService;

    @InjectMocks
    private CharacterStatusQueryService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private SkyXpAccessToken accessToken;

    @Mock
    private Game game;

    @Before
    public void setUp() {
        given(gameQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
    }

    @Test
    public void getCharacterStatus_inGame() {
        //GIVEN
        given(gameQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(game));
        //WHEN
        CharacterStatus result = underTest.getCharacterStatus(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(CharacterStatus.IN_GAME);
    }

    @Test
    public void getCharacterStatus_inLobbyQueue() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        given(lobby.isInQueue()).willReturn(true);
        //WHEN
        CharacterStatus result = underTest.getCharacterStatus(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(CharacterStatus.IN_LOBBY_QUEUE);
    }

    @Test
    public void getCharacterStatus_inLobby() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        given(lobby.isInQueue()).willReturn(false);
        //WHEN
        CharacterStatus result = underTest.getCharacterStatus(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(CharacterStatus.IN_LOBBY);
    }

    @Test
    public void getCharacterStatus_active() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        given(accessTokenDao.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(accessToken));
        //WHEN
        CharacterStatus result = underTest.getCharacterStatus(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(CharacterStatus.ACTIVE);
    }

    @Test
    public void getCharacterStatus_inactive() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        given(accessTokenDao.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        //WHEN
        CharacterStatus result = underTest.getCharacterStatus(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(CharacterStatus.INACTIVE);
    }
}