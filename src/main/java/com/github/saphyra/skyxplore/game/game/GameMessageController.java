package com.github.saphyra.skyxplore.game.game;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessageView;
import com.github.saphyra.skyxplore.game.game.request.CreateRoomRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class GameMessageController {
    private static final String CREATE_ROOM_MAPPING = API_PREFIX + "/game/message/room";
    private static final String EXIT_FROM_ROOM_MAPPING = API_PREFIX + "/game/message/room/{roomId}";
    private static final String GET_MESSAGES_MAPPING = API_PREFIX + "/game/message";
    private static final String INVITE_TO_ROOM_MAPPING = API_PREFIX + "/game/message/room/{roomId}/{characterId}";
    private static final String SEND_MESSAGES_MAPPING = API_PREFIX + "/game/message/{roomId}";

    private final GameQueryService gameQueryService;

    @PutMapping(CREATE_ROOM_MAPPING)
    void createRoom(
        @RequestBody CreateRoomRequest createRoomRequest,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to create a room: {}", characterId, createRoomRequest);
        gameQueryService.findByCharacterIdValidated(characterId)
            .getMessages()
            .createRoom(characterId, createRoomRequest);
    }

    @DeleteMapping(EXIT_FROM_ROOM_MAPPING)
    void exitFromRoom(
        @PathVariable("roomId") UUID roomId,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to exit from room {}", characterId, roomId);
        gameQueryService.findByCharacterIdValidated(characterId)
            .getMessages()
            .exitFromRoom(characterId, roomId);
    }

    @GetMapping(GET_MESSAGES_MAPPING)
    Map<UUID, List<LobbyMessageView>> getMessages(
        @RequestParam(value = "all", defaultValue = "false") Boolean queryAll,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    @PostMapping(INVITE_TO_ROOM_MAPPING)
    void inviteToRoom(
        @PathVariable("roomId") UUID roomId,
        @PathVariable("characterId") String invitedCharacterId,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    @PutMapping(SEND_MESSAGES_MAPPING)
    void sendMessage(
        @PathVariable("roomId") UUID roomId,
        @RequestBody OneStringParamRequest message,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        //TODO implement
        throw new UnsupportedOperationException();
    }
}
