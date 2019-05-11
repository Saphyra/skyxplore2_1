package com.github.saphyra.skyxplore.lobby.storage;

import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LobbyStorage extends ConcurrentHashMap<UUID, Lobby> {
}
