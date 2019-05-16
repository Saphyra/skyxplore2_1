package com.github.saphyra.skyxplore.community.mail.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MailView {
    @NonNull
    private final String mailId;

    @NonNull
    private final String from;

    @NonNull
    private final String to;

    @NonNull
    private final String fromName;

    @NonNull
    private final String toName;

    @NonNull
    private final String subject;

    @NonNull
    private final String message;

    @NonNull
    private final Boolean read;

    @NonNull
    private final Long sendTime;
}
