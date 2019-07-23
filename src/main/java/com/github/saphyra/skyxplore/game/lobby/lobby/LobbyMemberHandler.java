package com.github.saphyra.skyxplore.game.lobby.lobby;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.event.UserLoggedOutEvent;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMemberView;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class LobbyMemberHandler {
    private final CharacterQueryService characterQueryService;
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
            .characterName(characterQueryService.findByCharacterId(lobbyMember.getCharacterId()).getCharacterName())
            .isReady(lobbyMember.isReady())
            .build();
    }

    @EventListener
    void userLoggedOutEventListener(UserLoggedOutEvent event) {
        characterQueryService.getCharactersByUserId(event.getUserId())
            .forEach(skyXpCharacter -> exitFromLobby(skyXpCharacter.getCharacterId()));
    }

    void exitFromLobby(String characterId) {
        lobbyQueryService.findByCharacterId(characterId)
            .ifPresent(lobby -> lobby.removeMember(characterId));
    }
}
