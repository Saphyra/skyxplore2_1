package com.github.saphyra.skyxplore.lobby.lobby.creation;

import com.github.saphyra.skyxplore.lobby.lobby.domain.GameMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public abstract class AbstractLobbyFactoryTest {

    @Test
    public void canCreate() {
        //GIVEN

        //WHEN
        Arrays.stream(GameMode.values())
            .forEach(gameMode -> {
                log.info("Testing canCreate with GameMode {}", gameMode);
                boolean canCreate = getUnderTest().canCreate(gameMode);
                assertThat(canCreate).isEqualTo(getProcessableGameModes().contains(gameMode));
            });
        //THEN
    }

    protected abstract Collection<GameMode> getProcessableGameModes();

    protected abstract LobbyFactory getUnderTest();
}
