package org.github.saphyra.skyxplore.community.mail.domain;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//TODO builder with default values
public class Mail {
    private String mailId;
    private String from;
    private String to;
    private String subject;
    private String message;
    private Boolean read;
    private OffsetDateTime sendTime;
    private Boolean archived;
    private Boolean deletedBySender;
    private Boolean deletedByAddressee;
}
