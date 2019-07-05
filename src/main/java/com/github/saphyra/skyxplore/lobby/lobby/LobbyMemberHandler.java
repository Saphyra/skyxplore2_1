package com.github.saphyra.skyxplore.lobby.lobby;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.CharacterViewQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.event.UserLoggedOutEvent;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyMemberView;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class LobbyMemberHandler {
    private final CharacterQueryService characterQueryService;
    private final CharacterViewQueryService characterViewQueryService;
    private final LobbyQueryService lobbyQueryService;

    List<LobbyMemberView> getMembers(String characterId) {
        return lobbyQueryService.findByCharacterIdValidated(characterId)
            .getMembers().stream()
            .map(this::convertToView)
            .collect(Collectors.toList());
    }

    private LobbyMemberView convertToView(LobbyMember lobbyMember) {
        return LobbyMemberView.builder()
            .characterId(lobbyMember.getCharacterId())
            .characterName(characterViewQueryService.findByCharacterId(lobbyMember.getCharacterId()).getCharacterName())
            .isReady(lobbyMember.isReady())
            .build();
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
