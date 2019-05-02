package skyxplore.service.community;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import org.github.saphyra.skyxplore.community.blockedcharacter.BlockedCharacterQueryService;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;
import org.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.github.saphyra.skyxplore.common.exception.CharacterBlockedException;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

@RequiredArgsConstructor
@Service
public class MailSenderService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final MailDao mailDao;

    public void sendMail(SendMailRequest request, String characterId) {
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
        Mail mail = new Mail();
        mail.setMailId(idGenerator.generateRandomId());
        mail.setFrom(characterId);
        mail.setTo(request.getAddresseeId());
        mail.setSubject(request.getSubject());
        mail.setMessage(request.getMessage());
        mail.setRead(false);
        mail.setSendTime(dateTimeUtil.now());
        mail.setArchived(false);
        mail.setDeletedBySender(false);
        mail.setDeletedByAddressee(false);
        return mail;
    }
}
