package com.github.saphyra.skyxplore.community.mail.repository;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class MailConverter extends ConverterBase<MailEntity, Mail> {
    private final DateTimeUtil dateTimeUtil;
    private final StringEncryptor stringEncryptor;

    @Override
    public Mail processEntityConversion(MailEntity entity) {
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
    public MailEntity processDomainConversion(Mail domain) {
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
