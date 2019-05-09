package com.github.saphyra.skyxplore.lobby;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.Random;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Getter
public class LobbyContext {
    private final LobbyStorage lobbyStorage;
    private final Random random;
}
