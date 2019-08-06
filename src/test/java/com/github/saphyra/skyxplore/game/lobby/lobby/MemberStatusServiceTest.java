package com.github.saphyra.skyxplore.game.lobby.lobby;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class MemberStatusServiceTest {
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private MemberStatusService underTest;

    @Mock
    private Lobby lobby;

    @Before
    public void setUp() {
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
    }

    @Test
    public void setReady() {
        //WHEN
        underTest.setReady(CHARACTER_ID);
        //THEN
        verify(lobby).setMemberReady(CHARACTER_ID);
    }

    @Test
    public void setUnready() {
        //WHEN
        underTest.setUnready(CHARACTER_ID);
        //THEN
        verify(lobby).setMemberUnready(CHARACTER_ID);
    }
}