package com.github.saphyra.skyxplore.community.mail.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mail {
    private String mailId;
    private String from;
    private String to;
    private String subject;
    private String message;
    private OffsetDateTime sendTime;

    @Builder.Default
    private Boolean read = false;

    @Builder.Default
    private Boolean archived = false;

    @Builder.Default
    private Boolean deletedBySender = false;

    @Builder.Default
    private Boolean deletedByAddressee = false;
}
