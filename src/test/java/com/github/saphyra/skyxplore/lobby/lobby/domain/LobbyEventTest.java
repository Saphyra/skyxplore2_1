package com.github.saphyra.skyxplore.lobby.lobby.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LobbyEventTest {
    private static final String DATA = "data";
    private static final String CHARACTER_ID = "character_id";

    private LobbyEvent underTest;

    @Before
    public void setUp() {
        underTest = LobbyEvent.builder()
            .eventType(LobbyEventType.ENTER)
            .data(DATA)
            .build();
    }

    @Test
    public void addQueriedBy() {
        //WHEN
        underTest.addQueriedBy(CHARACTER_ID);
        //THEN
        assertThat(underTest.getQueriedBy()).containsOnly(CHARACTER_ID);
    }

    @Test
    public void getQueriedBy() {
        //GIVEN
        underTest.addQueriedBy(CHARACTER_ID);
        //WHEN
        List<String> result = underTest.getQueriedBy();
        //THEN
        assertThat(result).containsExactly(CHARACTER_ID);

        result.add(CHARACTER_ID);
        assertThat(underTest.getQueriedBy()).hasSize(1);
    }
}