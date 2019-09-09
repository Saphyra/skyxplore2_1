package com.github.saphyra.skyxplore.game.lobby.message;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.message.MessageView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_CHARACTER_ID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    public static final String GET_MESSAGES_MAPPING = API_PREFIX + "/lobby/message";
    private static final String SEND_MESSAGE_MAPPING = API_PREFIX + "/lobby/message";

    private final MessageSenderService messageSenderService;
    private final LobbyMessageViewQueryService lobbyMessageViewQueryService;

    @GetMapping(GET_MESSAGES_MAPPING)
    ResponseEntity<List<MessageView>> getMessages(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @RequestParam(value = "all", defaultValue = "false") Boolean queryAll
    ) {
        log.info("{} wants to query his mails. QueryAll: {}", characterId, queryAll);
        try {
            return ResponseEntity.ok(lobbyMessageViewQueryService.getMessages(characterId, queryAll));
        } catch (NotFoundException e) {
            //TODO unit test
            if (ErrorCode.LOBBY_NOT_FOUND.name().equals(e.getErrorMessage().getErrorCode())) {
                log.info("Lobby not found with characterId {}. The game might has been started.", characterId);
                return new ResponseEntity<>(HttpStatus.GONE);
            }
            throw e;
        }
    }

    @PutMapping(SEND_MESSAGE_MAPPING)
    void sendMessage(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @RequestBody @Valid OneStringParamRequest message
    ) {
        log.info("{} wants to send a message.", characterId);
        messageSenderService.sendMessage(characterId, message.getValue());
    }
}
