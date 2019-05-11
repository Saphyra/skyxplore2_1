package com.github.saphyra.skyxplore.lobby;

import com.github.saphyra.skyxplore.lobby.storage.LobbyStorage;
import com.github.saphyra.util.Random;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class LobbyContext {
    private final LobbyStorage lobbyStorage;
    private final Random random;
}
