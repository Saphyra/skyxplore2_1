package com.github.saphyra.skyxplore.lobby.invitation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class InvitationProperties {
    @Value("${lobby.invitation.spamming-delay-seconds}")
    private Integer spammingDelaySeconds;
}
