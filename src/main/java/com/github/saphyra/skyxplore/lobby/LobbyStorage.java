package com.github.saphyra.skyxplore.lobby;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.domain.Lobby;

@Component
public class LobbyStorage extends ConcurrentHashMap<UUID, Lobby> {
}
