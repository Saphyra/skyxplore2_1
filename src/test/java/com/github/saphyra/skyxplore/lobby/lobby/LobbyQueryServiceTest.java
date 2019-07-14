package com.github.saphyra.skyxplore.lobby.lobby;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyMember;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LobbyQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private LobbyStorage lobbyStorage;

    @InjectMocks
    private LobbyQueryService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private LobbyMember lobbyMember;

    @Before
    public void setUp() {
        given(lobby.findMemberByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobbyMember));
    }

    @Test
    public void findByCharacterIdValidated_found() {
        //GIVEN
        given(lobbyStorage.values()).willReturn(Arrays.asList(lobby));
        //WHEN
        Lobby result = underTest.findByCharacterIdValidated(CHARACTER_ID);
        //THEN
        assertThat(result).isEqualTo(lobby);
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
        given(lobbyStorage.values()).willReturn(Arrays.asList(lobby));
        //WHEN
        Optional<Lobby> result = underTest.findByCharacterId(CHARACTER_ID);
        //THEN
        assertThat(result).contains(lobby);
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
        given(lobbyStorage.get(LOBBY_ID)).willReturn(lobby);
        //WHEN
        Lobby result = underTest.findByLobbyIdValidated(LOBBY_ID);
        //THEN
        assertThat(result).isEqualTo(lobby);
    }

    @Test(expected = NotFoundException.class)
    public void findByLobbyIdValidated_notFound() {
        //GIVEN
        given(lobbyStorage.get(LOBBY_ID)).willReturn(null);
        //WHEN
        underTest.findByLobbyIdValidated(LOBBY_ID);
    }
}