package org.github.saphyra.skyxplore.community.mail;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.common.exception.CharacterBlockedException;
import org.github.saphyra.skyxplore.community.blockedcharacter.BlockedCharacterQueryService;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;
import org.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.springframework.stereotype.Service;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
class MailSenderService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final MailDao mailDao;

    void sendMail(SendMailRequest request, String characterId) {
        characterQueryService.findByCharacterId(request.getAddresseeId());
        checkBlockStatus(characterId, request.getAddresseeId());
        Mail mail = createMail(request, characterId);
        mailDao.save(mail);
    }

    private void checkBlockStatus(String characterId, String addresseeId) {
        if (!blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(characterId, addresseeId).isEmpty()) {
            throw new CharacterBlockedException("You blocked each other. Mail cannot be sent.");
        }
    }

    private Mail createMail(SendMailRequest request, String characterId) {
        return Mail.builder()
            .mailId(idGenerator.generateRandomId())
            .from(characterId)
            .to(request.getAddresseeId())
            .subject(request.getSubject())
            .message(request.getMessage())
            .sendTime(dateTimeUtil.now())
            .build();
    }
}
