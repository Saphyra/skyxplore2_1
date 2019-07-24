package com.github.saphyra.skyxplore.game.lobby.lobby;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;

@RunWith(MockitoJUnitRunner.class)
public class LobbyQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private LobbyStorage lobbyStorage;

    @InjectMocks
    private LobbyQueryService underTest;

    @Mock
    private Lobby lobby1;

    @Mock
    private Lobby lobby2;

    @Mock
    private LobbyMember lobbyMember;

    @Before
    public void setUp() {
        given(lobby1.findMemberByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobbyMember));
    }

    @Test
    public void findByCharacterIdValidated_found() {
        //GIVEN
        given(lobbyStorage.values()).willReturn(Arrays.asList(lobby1));
        //WHEN
        Lobby result = underTest.findByCharacterIdValidated(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(lobby1);
    }

    @Test(expected = NotFoundException.class)
    public void findByCharacterIdValidated_notFound() {
        //GIVEN
        given(lobbyStorage.values()).willReturn(Collections.emptyList());
        //WHEN
        underTest.findByCharacterIdValidated(CHARACTER_ID);
    }

    @Test
    public void findByCharacterId_found() {
        //GIVEN
        given(lobbyStorage.values()).willReturn(Arrays.asList(lobby1));
        //WHEN
        Optional<Lobby> result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).contains(lobby1);
    }

    @Test
    public void findByCharacterId_notFound() {
        //GIVEN
        given(lobbyStorage.values()).willReturn(Collections.emptyList());
        //WHEN
        Optional<Lobby> result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void findByLobbyIdValidated_found() {
        //GIVEN
        given(lobbyStorage.get(LOBBY_ID)).willReturn(lobby1);
        //WHEN
        Lobby result = underTest.findByLobbyIdValidated(LOBBY_ID);
        //THEN
        assertThat(result).isEqualTo(lobby1);
    }

    @Test(expected = NotFoundException.class)
    public void findByLobbyIdValidated_notFound() {
        //GIVEN
        given(lobbyStorage.get(LOBBY_ID)).willReturn(null);
        //WHEN
        underTest.findByLobbyIdValidated(LOBBY_ID);
    }

    @Test
    public void getLobbiesInQueue() {
        //GIVEN
        given(lobbyStorage.values()).willReturn(Arrays.asList(lobby1, lobby2));
        given(lobby1.isInQueue()).willReturn(true);
        given(lobby2.isInQueue()).willReturn(false);
        //WHEN
        List<Lobby> result = underTest.getLobbiesInQueue();
        //THEN
        assertThat(result).containsExactly(lobby1);
    }
}