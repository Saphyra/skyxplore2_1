package com.github.saphyra.skyxplore.lobby.message;

import com.github.saphyra.skyxplore.lobby.message.domain.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageStorage extends ConcurrentHashMap<UUID, Message> {
}
