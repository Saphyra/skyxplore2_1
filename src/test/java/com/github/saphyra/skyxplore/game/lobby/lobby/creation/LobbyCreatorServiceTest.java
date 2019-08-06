package com.github.saphyra.skyxplore.game.lobby.lobby.creation;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.NotImplementedException;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyStorage;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.CreateLobbyRequest;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LobbyCreatorServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String DATA = "data";
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private LobbyQueryService lobbyQueryService;

    @Mock
    private LobbyStorage lobbyStorage;

    @Mock
    private LobbyFactory lobbyFactory;

    private LobbyCreatorService underTest;

    @Mock
    private Lobby lobby;

    private CreateLobbyRequest createLobbyRequest;

    @Before
    public void setUp() {
        underTest = LobbyCreatorService.builder()
            .lobbyQueryService(lobbyQueryService)
            .lobbyStorage(lobbyStorage)
            .lobbyFactories(Arrays.asList(lobbyFactory))
            .build();

        createLobbyRequest = new CreateLobbyRequest(GameMode.TEAMFIGHT, DATA);
    }

    @Test(expected = BadRequestException.class)
    public void createLobby_characterAlreadyInLobby() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        //WHEN
        underTest.createLobby(createLobbyRequest, CHARACTER_ID);
    }

    @Test(expected = NotImplementedException.class)
    public void createLobby_unsupportedGameMode() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        given(lobbyFactory.canCreate(GameMode.TEAMFIGHT)).willReturn(false);
        //WHEN
        underTest.createLobby(createLobbyRequest, CHARACTER_ID);
    }

    @Test
    public void createLobby() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());
        given(lobbyFactory.canCreate(GameMode.TEAMFIGHT)).willReturn(true);
        given(lobbyFactory.create(GameMode.TEAMFIGHT, CHARACTER_ID, DATA)).willReturn(lobby);
        given(lobby.getLobbyId()).willReturn(LOBBY_ID);
        //WHEN
        underTest.createLobby(createLobbyRequest, CHARACTER_ID);
        //THEN
        verify(lobbyStorage).put(LOBBY_ID, lobby);
    }
}