package skyxplore.controller.view.community.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.controller.view.AbstractViewConverter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

@Component
@RequiredArgsConstructor
public class MailViewConverter extends AbstractViewConverter<Mail, MailView> {
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
