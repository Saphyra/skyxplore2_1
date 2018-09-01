package skyxplore.controller.view.community.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.controller.view.AbstractViewConverter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.DateTimeUtil;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MailViewConverter extends AbstractViewConverter<Mail, MailView> {
    private final CharacterQueryService characterQueryService;
    private final DateTimeUtil dateTimeUtil;

    @Override
    public MailView convertDomain(Mail domain) {
        return MailView.builder()
            .mailId(domain.getMailId())
            .from(domain.getFrom())
            .fromName(characterQueryService.findByCharacterId(domain.getFrom()).getCharacterName())
            .subject(domain.getSubject())
            .message(domain.getMessage())
            .read(domain.getRead())
            .sendTime(dateTimeUtil.convertDomain(domain.getSendTime()))
            .build();
    }
}
