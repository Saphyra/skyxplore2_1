package com.github.saphyra.skyxplore.lobby.message;

import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.lobby.message.domain.MessageView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private static final String GET_MESSAGES_MAPPING = "lobby/message";
    private static final String SEND_MESSAGE_MAPPING = "lobby/message";

    private final MessageSenderService messageSenderService;
    private final MessageViewQueryService messageViewQueryService;

    @GetMapping(GET_MESSAGES_MAPPING)
    List<MessageView> getMessages(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @RequestParam(value = "all", defaultValue = "false") Boolean queryAll
    ){
        log.info("{} wants to query his mails. QueryAll: {}", characterId, queryAll);
        return messageViewQueryService.getMessages(characterId, queryAll);
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
