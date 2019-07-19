package com.github.saphyra.skyxplore.characterstatus;

import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.characterstatus.domain.CharacterStatus;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class CharacterStatusQueryService {
    private final AccessTokenDao accessTokenDao;
    private final LobbyQueryService lobbyQueryService;

    CharacterStatus getCharacterStatus(String characterId) {
        //TODO check if character is in game
        Optional<Lobby> lobbyOptional = lobbyQueryService.findByCharacterId(characterId);
        if (lobbyOptional.isPresent()) {
            Lobby lobby = lobbyOptional.get();

            if (lobby.isInQueue()) {
                return CharacterStatus.IN_LOBBY_QUEUE;
            } else {
                return CharacterStatus.IN_LOBBY;
            }
        }

        return accessTokenDao.findByCharacterId(characterId).isPresent() ? CharacterStatus.ACTIVE : CharacterStatus.INACTIVE;
    }
}
