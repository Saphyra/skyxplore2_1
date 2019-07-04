package com.github.saphyra.skyxplore.lobby.lobby.domain;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.PayloadTooLargeException;
import com.github.saphyra.exceptionhandling.exception.PreconditionFailedException;
import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyContext;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyStorage;
import com.github.saphyra.skyxplore.lobby.message.domain.Message;
import com.github.saphyra.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class LobbyTest {
    private static final UUID LOBBY_ID = UUID.randomUUID();
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final int DEFAULT_LOBBY_SIZE = 3;

    @Mock
    private LobbyStorage lobbyStorage;

    @Mock
    private Random random;

    private Lobby underTest;

    private Lobby.LobbyBuilder lobbyBuilder;

    @Mock
    private Message message;

    @Mock
    private LobbyEvent lobbyEvent;

    @Before
    public void setUp() {
        lobbyBuilder = Lobby.builder()
            .lobbyId(LOBBY_ID)
            .gameMode(GameMode.CLAN_WARS)
            .lobbyContext(new LobbyContext(lobbyStorage, random));
    }

    private void buildLobby() {
        underTest = lobbyBuilder.build();
    }

    private void buildLobbyWithDefaultMember() {
        buildLobbyWithDefaultMember(DEFAULT_LOBBY_SIZE);
    }

    private void buildLobbyWithDefaultMember(int lobbySize) {
        FixedSizeConcurrentList<LobbyMember> members = new FixedSizeConcurrentList<>(lobbySize);
        members.add(LobbyMember.builder().characterId(CHARACTER_ID_1).build());

        lobbyBuilder.members(members)
            .ownerId(CHARACTER_ID_1);
        buildLobby();
    }

    private void buildLobbyWithMembers(int lobbySize, String ownerId, String... characterIds) {
        FixedSizeConcurrentList<LobbyMember> members = new FixedSizeConcurrentList<>(lobbySize);

        lobbyBuilder.ownerId(ownerId);

        if (!isNull(characterIds)) {
            Arrays.stream(characterIds)
                .map(characterId -> LobbyMember.builder().characterId(characterId).build())
                .forEach(members::add);
        }

        lobbyBuilder.members(members);
        buildLobby();
    }

    @Test(expected = BadRequestException.class)
    public void removeMember_notPresent() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        underTest.removeMember(CHARACTER_ID_2);
    }

    @Test
    public void removeMember_noMoreMembers() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        underTest.removeMember(CHARACTER_ID_1);
        //THEN
        verify(lobbyStorage).remove(LOBBY_ID);
        assertThat(underTest.getMembers()).isEmpty();
    }

    @Test
    public void removeMember_removeOwner() {
        //GIVEN
        buildLobbyWithMembers(2, CHARACTER_ID_1, CHARACTER_ID_1, CHARACTER_ID_2);
        given(random.randInt(0, 0)).willReturn(0);
        //WHEN
        underTest.removeMember(CHARACTER_ID_1);
        //THEN
        assertThat(underTest.getMembers()).hasSize(1);
        assertThat(underTest.getMembers().get(0).getCharacterId()).isEqualTo(CHARACTER_ID_2);
        assertThat(underTest.getOwnerId()).isEqualTo(CHARACTER_ID_2);

        assertThat(underTest.getEvents()).hasSize(2);
        assertThat(underTest.getEvents().get(0).getEventType()).isEqualTo(LobbyEventType.EXIT);
        assertThat(underTest.getEvents().get(0).getData()).isEqualTo(CHARACTER_ID_1);
        assertThat(underTest.getEvents().get(1).getEventType()).isEqualTo(LobbyEventType.OWNER_CHANGED);
        assertThat(underTest.getEvents().get(1).getData()).isEqualTo(CHARACTER_ID_2);
    }

    @Test
    public void removeMember() {
        //GIVEN
        buildLobbyWithMembers(2, CHARACTER_ID_1, CHARACTER_ID_1, CHARACTER_ID_2);
        //WHEN
        underTest.removeMember(CHARACTER_ID_2);
        //THEN
        assertThat(underTest.getMembers()).hasSize(1);
        assertThat(underTest.getMembers().get(0).getCharacterId()).isEqualTo(CHARACTER_ID_1);
        assertThat(underTest.getOwnerId()).isEqualTo(CHARACTER_ID_1);

        assertThat(underTest.getEvents()).hasSize(1);
        assertThat(underTest.getEvents().get(0).getEventType()).isEqualTo(LobbyEventType.EXIT);
        assertThat(underTest.getEvents().get(0).getData()).isEqualTo(CHARACTER_ID_2);
    }

    @Test
    public void findMemberByCharacterId_notFound() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        Optional<LobbyMember> result = underTest.findMemberByCharacterId(CHARACTER_ID_2);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void findMemberByCharacterId_found() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        Optional<LobbyMember> result = underTest.findMemberByCharacterId(CHARACTER_ID_1);
        //THEN
        assertThat(result).isNotEmpty();
        assertThat(result.get().getCharacterId()).isEqualTo(CHARACTER_ID_1);
    }

    @Test
    public void getMembers() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        List<LobbyMember> result = underTest.getMembers();
        //THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCharacterId()).isEqualTo(CHARACTER_ID_1);

        result.add(LobbyMember.builder().characterId(CHARACTER_ID_2).build());
        assertThat(underTest.getMembers()).hasSize(1);
    }

    @Test(expected = PayloadTooLargeException.class)
    public void addMember_lobbyIsFull() {
        //GIVEN
        buildLobbyWithDefaultMember(1);
        //WHEN
        underTest.addMember(CHARACTER_ID_2);
    }

    @Test
    public void addMember() {
        //GIVEN
        buildLobbyWithDefaultMember(2);
        //WHEN
        underTest.addMember(CHARACTER_ID_2);
        //THEN
        assertThat(underTest.getMembers()).hasSize(2);
        assertThat(underTest.getMembers().get(1).getCharacterId()).isEqualTo(CHARACTER_ID_2);
        assertThat(underTest.getEvents()).hasSize(1);
        assertThat(underTest.getEvents().get(0).getEventType()).isEqualTo(LobbyEventType.ENTER);
        assertThat(underTest.getEvents().get(0).getData()).isEqualTo(CHARACTER_ID_2);
    }

    @Test
    public void addMessage() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        underTest.addMessage(message);
        //THEN
        assertThat(underTest.getMessages()).hasSize(1);
        assertThat(underTest.getMessages().get(0)).isEqualTo(message);
    }

    @Test
    public void getMessages() {
        //GIVEN
        buildLobbyWithDefaultMember();
        underTest.addMessage(message);
        //WHEN
        List<Message> result = underTest.getMessages();
        //THEN
        assertThat(result).hasSize(1);
        assertThat(underTest.getMessages().get(0)).isEqualTo(message);

        result.add(message);
        assertThat(underTest.getMessages()).hasSize(1);
    }

    @Test(expected = ForbiddenException.class)
    public void transferOwnership_notTheOwner() {
        //GIVEN
        buildLobbyWithMembers(2, CHARACTER_ID_1, CHARACTER_ID_1, CHARACTER_ID_2);
        //WHEN
        underTest.transferOwnership(CHARACTER_ID_2, CHARACTER_ID_2);
    }

    @Test(expected = BadRequestException.class)
    public void transferOwnership_notInLobby() {
        //GIVEN
        buildLobbyWithMembers(2, CHARACTER_ID_1, CHARACTER_ID_1);
        //WHEN
        underTest.transferOwnership(CHARACTER_ID_1, CHARACTER_ID_2);
    }

    @Test
    public void transferOwnership() {
        //GIVEN
        buildLobbyWithMembers(2, CHARACTER_ID_1, CHARACTER_ID_1, CHARACTER_ID_2);
        //WHEN
        underTest.transferOwnership(CHARACTER_ID_1, CHARACTER_ID_2);
        //THEN
        assertThat(underTest.getOwnerId()).isEqualTo(CHARACTER_ID_2);
        assertThat(underTest.getEvents()).hasSize(1);
        assertThat(underTest.getEvents().get(0).getEventType()).isEqualTo(LobbyEventType.OWNER_CHANGED);
        assertThat(underTest.getEvents().get(0).getData()).isEqualTo(CHARACTER_ID_2);
    }

    @Test(expected = NotFoundException.class)
    public void setMemberReady_memberNotFound() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        underTest.setMemberReady(CHARACTER_ID_2);
    }

    @Test
    public void setMemberReady() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        underTest.setMemberReady(CHARACTER_ID_1);
        //THEN
        assertThat(underTest.getMembers().get(0).isReady()).isTrue();
    }

    @Test(expected = NotFoundException.class)
    public void setMemberUnready_memberNotFound() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        underTest.setMemberUnready(CHARACTER_ID_2);
    }

    @Test
    public void setMemberUnready() {
        //GIVEN
        buildLobbyWithDefaultMember();
        underTest.setMemberReady(CHARACTER_ID_1);
        //WHEN
        underTest.setMemberUnready(CHARACTER_ID_1);
        //THEN
        assertThat(underTest.getMembers().get(0).isReady()).isFalse();
    }

    @Test(expected = PreconditionFailedException.class)
    public void startQueueing_unreadyMember() {
        //GIVEN
        buildLobbyWithDefaultMember();
        //WHEN
        underTest.startQueueing(true);
    }

    @Test
    public void startQueueing() {
        //GIVEN
        buildLobbyWithDefaultMember();
        underTest.setMemberReady(CHARACTER_ID_1);
        //WHEN
        underTest.startQueueing(true);
        //THEN
        assertThat(underTest.isInQueue()).isTrue();
        assertThat(underTest.isAutoFill()).isTrue();
        assertThat(underTest.getEvents()).hasSize(2);
        assertThat(underTest.getEvents().get(1).getEventType()).isEqualTo(LobbyEventType.START_QUEUE);
    }

    @Test
    public void getEvents() {
        //GIVEN
        buildLobbyWithDefaultMember();
        underTest.setMemberReady(CHARACTER_ID_1);
        //WHEN
        List<LobbyEvent> result = underTest.getEvents();
        //THEN
        assertThat(result).hasSize(1);
        result.add(lobbyEvent);
        assertThat(underTest.getEvents()).hasSize(1);
    }
}