package com.github.saphyra.skyxplore.userdata.community.mail;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.userdata.community.mail.repository.MailDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
class MailDeleteService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    @Transactional
    void deleteMails(String characterId, List<String> mailIds) {
        SkyXpCharacter character = characterQueryService.findByCharacterIdValidated(characterId);
        mailIds.forEach(mailId -> processDeletion(character, mailId));
    }

    private void processDeletion(SkyXpCharacter character, String mailId) {
        log.info("deleting mail {} by charatcer {}", mailId, character.getCharacterId());
        Mail mail = mailQueryService.findMailById(mailId);

        if (mail.getTo().equals(character.getCharacterId())) {
            mail.setDeletedByAddressee(true);
        } else if (mail.getFrom().equals(character.getCharacterId())) {
            mail.setDeletedBySender(true);
        } else {
            throw ExceptionFactory.invalidMailAccess(character.getCharacterId(), mailId);
        }

        mailDao.save(mail);
    }
}
