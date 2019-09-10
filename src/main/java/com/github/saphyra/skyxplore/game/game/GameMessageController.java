package com.github.saphyra.skyxplore.game.game;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.common.domain.message.MessageView;
import com.github.saphyra.skyxplore.game.game.request.CreateRoomRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class GameMessageController {
    private static final String CREATE_ROOM_MAPPING = API_PREFIX + "/game/message";
    private static final String EXIT_FROM_ROOM_MAPPING = API_PREFIX + "/game/message/{roomId}";
    private static final String GET_MESSAGES_MAPPING = API_PREFIX + "/game/messages";
    private static final String SEND_MESSAGES_MAPPING = API_PREFIX + "/game/messages/{roomId}";

    @PutMapping(CREATE_ROOM_MAPPING)
    String createRoom(
        @RequestBody CreateRoomRequest createRoomRequest,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    @DeleteMapping(EXIT_FROM_ROOM_MAPPING)
    void exitFromRoom(
        @PathVariable("roomId") UUID roomId,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    @GetMapping(GET_MESSAGES_MAPPING)
    Map<UUID, List<MessageView>> getMessages(
        @RequestParam(value = "all", defaultValue = "false") Boolean queryAll,
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
