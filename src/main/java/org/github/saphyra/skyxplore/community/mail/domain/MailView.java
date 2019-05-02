package org.github.saphyra.skyxplore.community.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailView {
    private String mailId;
    private String from;
    private String to;
    private String fromName;
    private String toName;
    private String subject;
    private String message;
    private Boolean read;
    private Long sendTime;
}
