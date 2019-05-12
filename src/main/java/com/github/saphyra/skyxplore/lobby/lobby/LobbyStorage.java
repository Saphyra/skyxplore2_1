package com.github.saphyra.skyxplore.lobby.lobby;

import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.message.MessageFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class LobbyStorage extends ConcurrentHashMap<UUID, Lobby> {
    private final MessageFacade messageFacade;

    @Override
    public Lobby remove(Object key){
        messageFacade.deleteByLobbyId((UUID) key);
        return super.remove(key);
    }
}
