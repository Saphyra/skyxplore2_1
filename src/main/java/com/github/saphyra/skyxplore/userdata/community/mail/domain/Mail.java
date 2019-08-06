package com.github.saphyra.skyxplore.userdata.community.mail.domain;

import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mail {
    @NonNull
    private final String mailId;

    @NonNull
    private final String from;

    @NonNull
    private final String to;

    @NonNull
    private final String subject;

    @NonNull
    private final String message;

    @NonNull
    private final OffsetDateTime sendTime;

    @Builder.Default
    @NonNull
    private Boolean read = false;

    @Builder.Default
    @NonNull
    private Boolean archived = false;

    @Builder.Default
    @NonNull
    private Boolean deletedBySender = false;

    @Builder.Default
    @NonNull
    private Boolean deletedByAddressee = false;
}
