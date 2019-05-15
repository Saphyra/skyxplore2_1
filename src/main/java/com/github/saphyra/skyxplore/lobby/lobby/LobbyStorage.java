package com.github.saphyra.skyxplore.lobby.lobby;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LobbyStorage extends ConcurrentHashMap<UUID, Lobby> {
}
