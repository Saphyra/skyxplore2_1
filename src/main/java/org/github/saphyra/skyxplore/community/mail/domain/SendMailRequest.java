package org.github.saphyra.skyxplore.community.mail.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SendMailRequest {
    @NotNull
    private String addresseeId;

    @NotNull
    @Size(min = 1, max = 100)
    private String subject;

    @NotNull
    @Size(min = 1, max = 4000)
    private String message;
}