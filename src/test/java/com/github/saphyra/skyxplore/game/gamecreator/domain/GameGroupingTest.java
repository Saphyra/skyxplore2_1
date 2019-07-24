package com.github.saphyra.skyxplore.game.gamecreator.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;

@RunWith(MockitoJUnitRunner.class)
public class GameGroupingTest {
    private static final UUID GAME_GROUPING_ID = UUID.randomUUID();
    private static final Integer MINIMUM_GAME_MEMBERS_AMOUNT = 1;
    private static final GameMode GAME_MODE = GameMode.VS;
    private static final OffsetDateTime CREATED_AT = OffsetDateTime.now();
    private static final UUID LOBBY_ID = UUID.randomUUID();

    private List<GameGroup> gameGroups = new ArrayList<>();
    private List<UUID> lockedLobbyIds = new ArrayList<>();

    private GameGrouping underTest;

    @Mock
    private GameGroupSizeRange gameGroupSizeRange;

    @Mock
    private GameGroup gameGroup;

    @Mock
    private GameCharacter gameCharacter;

    @Before
    public void setUp() {
        underTest = GameGrouping.builder()
            .gameGroupingId(GAME_GROUPING_ID)
            .minimumGameMembersAmount(MINIMUM_GAME_MEMBERS_AMOUNT)
            .gameGroupSizeRange(gameGroupSizeRange)
            .gameMode(GAME_MODE)
            .createdAt(CREATED_AT)
            .gameGroups(gameGroups)
            .lockedLobbyIds(lockedLobbyIds)
            .build();
    }

    @Test
    public void addGroup() {
        //WHEN
        underTest.addGroup(gameGroup);
        //THEN
        assertThat(gameGroups).containsExactly(gameGroup);
    }

    @Test
    public void addGroups() {
        //WHEN
        GameGrouping result = underTest.addGroups(Arrays.asList(gameGroup));
        //THEN
        assertThat(gameGroups).containsExactly(gameGroup);
        assertThat(result).isEqualTo(underTest);
    }

    @Test
    public void lockLobby() {
        //WHEN
        GameGrouping result = underTest.lockLobby(LOBBY_ID);
        //THEN
        assertThat(lockedLobbyIds).containsExactly(LOBBY_ID);
        assertThat(result).isEqualTo(underTest);
    }

    @Test
    public void hasEnoughMembers() {
        //GIVEN
        FixedSizeConcurrentList<GameCharacter> gameCharacters = new FixedSizeConcurrentList<>(1);
        gameCharacters.add(gameCharacter);
        given(gameGroup.getCharacters()).willReturn(gameCharacters);
        underTest.addGroup(gameGroup);
        //WHEN
        boolean result = underTest.hasEnoughMembers();
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void getMembersAmount() {
        //GIVEN
        FixedSizeConcurrentList<GameCharacter> gameCharacters = new FixedSizeConcurrentList<>(1);
        gameCharacters.add(gameCharacter);
        given(gameGroup.getCharacters()).willReturn(gameCharacters);
        underTest.addGroup(gameGroup);
        //WHEN
        int result = underTest.getMembersAmount();
        //THEN
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void getGameGroups() {
        //GIVEN
        underTest.addGroup(gameGroup);
        //WHEN
        List<GameGroup> result = underTest.getGameGroups();
        //THEN
        assertThat(result).containsExactly(gameGroup);
        result.add(gameGroup);
        assertThat(underTest.getGameGroups()).hasSize(1);
    }

    @Test
    public void getLockedLobbyIds() {
        //GIVEN
        underTest.lockLobby(LOBBY_ID);
        //WHEN
        List<UUID> result = underTest.getLockedLobbyIds();
        //THEN
        assertThat(result).containsExactly(LOBBY_ID);
        result.add(LOBBY_ID);
        assertThat(underTest.getLockedLobbyIds()).hasSize(1);
    }
}