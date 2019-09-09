package com.github.saphyra.skyxplore.game.lobby.invitation;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@Slf4j
@RequiredArgsConstructor
class InvitationCleanupService {
    private final DateTimeUtil dateTimeUtil;
    private final InvitationProperties invitationProperties;
    private final InvitationStorage invitationStorage;

    @Scheduled(cron = "${lobby.invitation.cleanup-cron}")
    void deleteExpiredInvitations() {
        log.debug("Deleting expired invitations...");
        invitationStorage.values().stream()
            .filter(this::isExpired)
            .collect(Collectors.toList())
            .forEach(invitation -> {
                log.debug("Deleting invitation {}", invitation);
                invitationStorage.remove(invitation.getInvitationId());
            });
    }

    private boolean isExpired(Invitation invitation) {
        OffsetDateTime now = dateTimeUtil.now();
        OffsetDateTime expiration = getExpiration(invitation, now);
        return invitation.getCreatedAt().isBefore(expiration);
    }

    private OffsetDateTime getExpiration(Invitation invitation, OffsetDateTime now) {
        return invitation.isQueried() ?
            now.minusSeconds(invitationProperties.getExpirationSeconds())
                .minusSeconds(invitationProperties.getExtendedExpirationSeconds())
            : now.minusSeconds(invitationProperties.getExpirationSeconds());
    }
}
