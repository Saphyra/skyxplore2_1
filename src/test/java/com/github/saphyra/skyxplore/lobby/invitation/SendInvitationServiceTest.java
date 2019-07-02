package com.github.saphyra.skyxplore.lobby.invitation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.TooManyRequestsException;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class SendInvitationServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String INVITED_CHARACTER_ID = "invited_character_id";
    private static final UUID LOBBY_ID = UUID.randomUUID();
    private static final OffsetDateTime NOW = OffsetDateTime.now();
    private static final Integer SPAMMING_DELAY_SECONDS = 10;
    private static final OffsetDateTime SPAMMING_CREATED_AT = NOW.minusSeconds(SPAMMING_DELAY_SECONDS - 1);
    private static final OffsetDateTime CREATED_AT = NOW.minusSeconds(SPAMMING_DELAY_SECONDS + 1);
    private static final UUID INVITATION_ID = UUID.randomUUID();
    private static final UUID NEW_INVITATION_ID = UUID.randomUUID();

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private InvitationFactory invitationFactory;

    @Mock
    private InvitationProperties invitationProperties;

    @Mock
    private InvitationStorage invitationStorage;

    @Mock
    private InvitationQueryService invitationQueryService;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private SendInvitationService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private Invitation invitation;

    @Mock
    private Invitation newInvitation;

    @Before
    public void setUp() {
        given(dateTimeUtil.now()).willReturn(NOW);
        given(invitationProperties.getSpammingDelaySeconds()).willReturn(SPAMMING_DELAY_SECONDS);
    }

    @Test
    public void sendInvitation_alreadyInLobby() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(INVITED_CHARACTER_ID)).willReturn(Optional.of(lobby));
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.sendInvitation(CHARACTER_ID, INVITED_CHARACTER_ID));
        //THEN
        assertThat(ex).isInstanceOf(ConflictException.class);
    }

    @Test
    public void sendInvitation_spamming() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(INVITED_CHARACTER_ID)).willReturn(Optional.empty());
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);

        given(lobby.getLobbyId()).willReturn(LOBBY_ID);

        given(invitationQueryService.findByCharacterIdAndInvitedCharacterIdAndLobbyId(CHARACTER_ID, INVITED_CHARACTER_ID, LOBBY_ID))
            .willReturn(Optional.of(invitation));
        given(invitation.getCreatedAt()).willReturn(SPAMMING_CREATED_AT);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.sendInvitation(CHARACTER_ID, INVITED_CHARACTER_ID));
        //THEN
        assertThat(ex).isInstanceOf(TooManyRequestsException.class);
    }

    @Test
    public void sendInvitation() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(INVITED_CHARACTER_ID)).willReturn(Optional.empty());
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);

        given(lobby.getLobbyId()).willReturn(LOBBY_ID);

        given(invitationQueryService.findByCharacterIdAndInvitedCharacterIdAndLobbyId(CHARACTER_ID, INVITED_CHARACTER_ID, LOBBY_ID))
            .willReturn(Optional.of(invitation));
        given(invitation.getCreatedAt()).willReturn(CREATED_AT);
        given(invitation.getInvitationId()).willReturn(INVITATION_ID);

        given(invitationFactory.create(CHARACTER_ID, INVITED_CHARACTER_ID, LOBBY_ID)).willReturn(newInvitation);
        given(newInvitation.getInvitationId()).willReturn(NEW_INVITATION_ID);
        //WHEN
        underTest.sendInvitation(CHARACTER_ID, INVITED_CHARACTER_ID);
        //THEN
        verify(invitationStorage).remove(INVITATION_ID);
        verify(invitationStorage).put(NEW_INVITATION_ID, newInvitation);
    }
}