package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.game.GameContext;
import com.github.saphyra.skyxplore.game.game.domain.message.ChatRoom;
import com.github.saphyra.skyxplore.game.game.domain.message.GameMessages;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameMessageFactory {
    private static final String GLOBAL_CHAT_DISPLAY_NAME = "global-chat-room";
    private static final String TEAM_CHAT_DISPLAY_NAME = "team-chat-room";

    private final GameContext gameContext;
    private final IdGenerator idGenerator;

    public GameMessages create(GameGrouping gameGrouping) {
        GameMessages gameMessages = new GameMessages(gameContext);
        createChatRooms(gameGrouping).forEach(gameMessages::addRoom);
        return gameMessages;
    }

    private Map<UUID, ChatRoom> createChatRooms(GameGrouping gameGrouping) {
        Map<UUID, ChatRoom> result = new HashMap<>();
        ChatRoom globalChatRoom = createGlobalChatRoom(gameGrouping);
        result.put(globalChatRoom.getRoomId(), globalChatRoom);

        result.putAll(createTeamRooms(gameGrouping));
        return result;
    }

    private ChatRoom createGlobalChatRoom(GameGrouping gameGrouping) {
        return ChatRoom.builder()
            .roomId(idGenerator.randomUUID())
            .displayName(GLOBAL_CHAT_DISPLAY_NAME)
            .defaultRoom(true)
            .members(fetchAllCharacterIdsOfGameGrouping(gameGrouping))
            .build();
    }

    private Vector<String> fetchAllCharacterIdsOfGameGrouping(GameGrouping gameGrouping) {
        return gameGrouping.getGameGroups().stream()
            .flatMap(gameGroup -> gameGroup.getCharacters().stream())
            .map(GameGroupCharacter::getCharacterId).collect(Collectors.toCollection(Vector::new));
    }

    private Map<UUID, ChatRoom> createTeamRooms(GameGrouping gameGrouping) {
        return gameGrouping.getGameGroups().stream()
            .map(this::createTeamRoom)
            .collect(Collectors.toMap(ChatRoom::getRoomId, chatRoom -> chatRoom));
    }

    private ChatRoom createTeamRoom(GameGroup gameGroup) {
        return ChatRoom.builder()
            .roomId(idGenerator.randomUUID())
            .displayName(TEAM_CHAT_DISPLAY_NAME)
            .defaultRoom(true)
            .members(fetchCharacterIdsFromGameGroup(gameGroup))
            .build();
    }

    private Vector<String> fetchCharacterIdsFromGameGroup(GameGroup gameGroup) {
        return gameGroup.getCharacters().stream().map(GameGroupCharacter::getCharacterId).collect(Collectors.toCollection(Vector::new));
    }
}
