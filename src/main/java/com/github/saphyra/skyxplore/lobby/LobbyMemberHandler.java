package com.github.saphyra.skyxplore.lobby;

import java.util.List;
import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.event.UserLoggedOutEvent;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LobbyMemberHandler {
    private final CharacterQueryService characterQueryService;
    private final LobbyQueryService lobbyQueryService;
    private final LobbyStorage lobbyStorage;

    @EventListener
    void userLoggedOutEventListener(UserLoggedOutEvent event) {
        List<SkyXpCharacter> characters = characterQueryService.getCharactersByUserId(event.getUserId());
        characters.forEach(skyXpCharacter -> leaveFromLobby(skyXpCharacter.getCharacterId()));
    }

    void leaveFromLobby(String characterId) {
        Optional<Lobby> lobbyOptional = lobbyQueryService.findByCharacterId(characterId);
        if(!lobbyOptional.isPresent()){
            return;
        }

        Lobby lobby = lobbyOptional.get();
        lobby.removeMember(characterId);
    }
}
