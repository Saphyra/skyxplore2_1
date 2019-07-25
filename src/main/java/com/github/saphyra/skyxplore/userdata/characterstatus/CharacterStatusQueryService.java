package com.github.saphyra.skyxplore.userdata.characterstatus;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.game.GameQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.userdata.characterstatus.domain.CharacterStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterStatusQueryService {
    private final AccessTokenDao accessTokenDao;
    private final LobbyQueryService lobbyQueryService;
    private final GameQueryService gameQueryService;

    public CharacterStatus getCharacterStatus(String characterId) {
        //TODO unit test
        if (gameQueryService.findByCharacterId(characterId).isPresent()) {
            return CharacterStatus.IN_GAME;
        }
        //end

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
