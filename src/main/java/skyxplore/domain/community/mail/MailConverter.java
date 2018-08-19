package skyxplore.domain.community.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.util.DateTimeConverter;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MailConverter extends ConverterBase<MailEntity, Mail> {
    private final DateTimeConverter dateTimeConverter;

    @Override
    public Mail convertEntity(MailEntity entity) {
        if (entity == null) {
            return null;
        }

        return Mail.builder()
            .mailId(entity.getMailId())
            .from(entity.getFrom())
            .to(entity.getTo())
            .subject(entity.getSubject())
            .message(entity.getMessage())
            .read(entity.getRead())
            .sendTime(dateTimeConverter.convertEntity(entity.getSendTime()))
            .archived(entity.getArchived())
            .deletedBySender(entity.getDeletedBySender())
            .deletedByAddressee(entity.getDeletedByAddressee())
            .build();
    }

    @Override
    public MailEntity convertDomain(Mail domain) {
        if (domain == null) {
            return null;
        }

        return MailEntity.builder()
            .mailId(domain.getMailId())
            .from(domain.getFrom())
            .to(domain.getTo())
            .subject(domain.getSubject())
            .message(domain.getMessage())
            .read(domain.getRead())
            .sendTime(dateTimeConverter.convertDomain(domain.getSendTime()))
            .archived(domain.getArchived())
            .deletedBySender(domain.getDeletedBySender())
            .deletedByAddressee(domain.getDeletedByAddressee())
            .build();
    }
}
