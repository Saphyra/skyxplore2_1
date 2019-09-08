package com.github.saphyra.skyxplore.game.lobby.lobby;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.event.UserLoggedOutEvent;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMemberView;

@RunWith(MockitoJUnitRunner.class)
public class LobbyMemberHandlerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String USER_ID = "user_id";

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private LobbyMemberHandler underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private LobbyMember lobbyMember;

    @Mock
    private SkyXpCharacter character;

    @Test
    public void getMembers() {
        //GIVEN
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        given(lobby.getMembers()).willReturn(Arrays.asList(lobbyMember));
        given(lobbyMember.getCharacterId()).willReturn(CHARACTER_ID);
        given(lobbyMember.isReady()).willReturn(true);
        given(characterQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(character);
        given(character.getCharacterName()).willReturn(CHARACTER_NAME);
        //WHEN
        List<LobbyMemberView> result = underTest.getMembers(CHARACTER_ID);
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.get(0).getCharacterName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.get(0).isReady()).isTrue();
    }

    @Test
    public void userLoggedOutEventListener() {
        //GIVEN
        given(characterQueryService.getCharactersByUserId(USER_ID)).willReturn(Arrays.asList(character));
        given(character.getCharacterId()).willReturn(CHARACTER_ID);
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        //WHEN
        underTest.userLoggedOutEventListener(new UserLoggedOutEvent(USER_ID));
        //THEN
        verify(lobby).removeMember(CHARACTER_ID);
    }

    @Test
    public void exitFromLobby() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        //WHEN
        underTest.exitFromLobby(CHARACTER_ID);
        //THEN
        verify(lobby).removeMember(CHARACTER_ID);
    }
}