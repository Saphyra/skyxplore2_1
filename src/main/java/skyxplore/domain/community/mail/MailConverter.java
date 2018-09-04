package skyxplore.domain.community.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;
import skyxplore.encryption.StringEncryptor;
import skyxplore.util.DateTimeUtil;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MailConverter extends ConverterBase<MailEntity, Mail> {
    private final DateTimeUtil dateTimeUtil;
    private final StringEncryptor stringEncryptor;

    @Override
    public Mail convertEntity(MailEntity entity) {
        if (entity == null) {
            return null;
        }

        return Mail.builder()
            .mailId(entity.getMailId())
            .from(entity.getFrom())
            .to(entity.getTo())
            .subject(stringEncryptor.decryptEntity(entity.getSubject(), entity.getMailId()))
            .message(stringEncryptor.decryptEntity(entity.getMessage(), entity.getMailId()))
            .read(entity.getRead())
            .sendTime(dateTimeUtil.convertEntity(entity.getSendTime()))
            .archived(entity.getArchived())
            .deletedBySender(entity.getDeletedBySender())
            .deletedByAddressee(entity.getDeletedByAddressee())
            .build();
    }

    @Override
    public MailEntity convertDomain(Mail domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }

        return MailEntity.builder()
            .mailId(domain.getMailId())
            .from(domain.getFrom())
            .to(domain.getTo())
            .subject(stringEncryptor.encryptEntity(domain.getSubject(), domain.getMailId()))
            .message(stringEncryptor.encryptEntity(domain.getMessage(), domain.getMailId()))
            .read(domain.getRead())
            .sendTime(dateTimeUtil.convertDomain(domain.getSendTime()))
            .archived(domain.getArchived())
            .deletedBySender(domain.getDeletedBySender())
            .deletedByAddressee(domain.getDeletedByAddressee())
            .build();
    }
}
