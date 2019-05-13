package com.github.saphyra.skyxplore.lobby.message;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.lobby.message.domain.Message;

@Component
//TODO place to Lobby -> performance
public class MessageStorage extends ConcurrentHashMap<UUID, Message> {
}
