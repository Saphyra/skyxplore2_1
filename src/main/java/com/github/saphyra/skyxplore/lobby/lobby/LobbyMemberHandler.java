package com.github.saphyra.skyxplore.lobby.lobby;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.CharacterViewQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.event.UserLoggedOutEvent;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class LobbyMemberHandler {
    private final CharacterQueryService characterQueryService;
    private final CharacterViewQueryService characterViewQueryService;
    private final LobbyQueryService lobbyQueryService;

    List<CharacterView> getMembers(String characterId) {
        return lobbyQueryService.findByCharacterIdValidated(characterId)
            .getMembers().stream()
            .map(characterViewQueryService::findByCharacterId)
            .collect(Collectors.toList());
    }

    @EventListener
    void userLoggedOutEventListener(UserLoggedOutEvent event) {
        List<SkyXpCharacter> characters = characterQueryService.getCharactersByUserId(event.getUserId());
        characters.forEach(skyXpCharacter -> exitFromLobby(skyXpCharacter.getCharacterId()));
    }

    void exitFromLobby(String characterId) {
        Optional<Lobby> lobbyOptional = lobbyQueryService.findByCharacterId(characterId);
        if (!lobbyOptional.isPresent()) {
            return;
        }

        Lobby lobby = lobbyOptional.get();
        lobby.removeMember(characterId);
    }
}
