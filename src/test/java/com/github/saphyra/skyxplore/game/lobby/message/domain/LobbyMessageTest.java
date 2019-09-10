package com.github.saphyra.skyxplore.game.lobby.message.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LobbyMessageTest {
    private static final UUID MESSAGE_ID = UUID.randomUUID();
    private static final String SENDER = "sender";
    private static final String MESSAGE = "message";
    private static final Long CREATED_AT = 7678L;
    private static final String CHARACTER_ID = "character_id";

    private LobbyMessage underTest;

    @Mock
    private List<String> queriedBy;

    @Before
    public void setUp() {
        underTest = LobbyMessage.builder()
            .messageId(MESSAGE_ID)
            .queriedBy(queriedBy)
            .sender(SENDER)
            .message(MESSAGE)
            .createdAt(CREATED_AT)
            .build();
    }

    @Test
    public void addQueriedBy() {
        //GIVEN
        given(queriedBy.contains(CHARACTER_ID)).willReturn(false);
        //WHEN
        underTest.addQueriedBy(CHARACTER_ID);
        //THEN
        verify(queriedBy).add(CHARACTER_ID);
    }

    @Test
    public void addQueriedBy_alreadyAdded() {
        //GIVEN
        given(queriedBy.contains(CHARACTER_ID)).willReturn(true);
        //WHEN
        underTest.addQueriedBy(CHARACTER_ID);
        //THEN
        verify(queriedBy).contains(CHARACTER_ID);
        verifyNoMoreInteractions(queriedBy);
    }
}