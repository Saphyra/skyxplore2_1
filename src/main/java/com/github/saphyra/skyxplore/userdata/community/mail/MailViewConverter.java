package com.github.saphyra.skyxplore.userdata.community.mail;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.MailView;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class MailViewConverter extends AbstractViewConverter<Mail, MailView> {
    private final CharacterQueryService characterQueryService;
    private final DateTimeUtil dateTimeUtil;

    @Override
    public MailView convertDomain(Mail domain) {
        return MailView.builder()
            .mailId(domain.getMailId())
            .from(domain.getFrom())
            .to(domain.getTo())
            .fromName(characterQueryService.findByCharacterId(domain.getFrom()).getCharacterName())
            .toName(characterQueryService.findByCharacterId(domain.getTo()).getCharacterName())
            .subject(domain.getSubject())
            .message(domain.getMessage())
            .read(domain.getRead())
            .sendTime(dateTimeUtil.convertDomain(domain.getSendTime()))
            .build();
    }
}
