package com.github.saphyra.skyxplore.lobby.invitation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.invitation.domain.InvitationView;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class InvitationViewConverterTest {
    private static final UUID LOBBY_ID = UUID.randomUUID();
    private static final String LOBBY_DATA = "lobby_data";
    private static final String CHARACTER_ID = "character_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final UUID INVITATION_ID = UUID.randomUUID();

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private InvitationViewConverter underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private SkyXpCharacter character;

    @Mock
    private Invitation invitation;

    @Test
    public void convertDomain() {
        //GIVEN
        given(invitation.getLobbyId()).willReturn(LOBBY_ID);
        given(invitation.getCharacterId()).willReturn(CHARACTER_ID);
        given(invitation.getInvitationId()).willReturn(INVITATION_ID);

        given(lobbyQueryService.findByLobbyIdValidated(LOBBY_ID)).willReturn(lobby);
        given(lobby.getGameMode()).willReturn(GameMode.TEAMFIGHT);
        given(lobby.getData()).willReturn(LOBBY_DATA);

        given(characterQueryService.findByCharacterId(CHARACTER_ID)).willReturn(character);
        given(character.getCharacterName()).willReturn(CHARACTER_NAME);

        //WHEN
        InvitationView result = underTest.convertDomain(invitation);
        //THEN
        assertThat(result.getGameMode()).isEqualTo(GameMode.TEAMFIGHT);
        assertThat(result.getData()).isEqualTo(LOBBY_DATA);
        assertThat(result.getCharacterName()).isEqualTo(CHARACTER_NAME);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getInvitationId()).isEqualTo(INVITATION_ID);
    }
}