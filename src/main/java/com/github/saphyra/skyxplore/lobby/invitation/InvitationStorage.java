package com.github.saphyra.skyxplore.lobby.invitation;

import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InvitationStorage extends ConcurrentHashMap<UUID, Invitation> {
}
