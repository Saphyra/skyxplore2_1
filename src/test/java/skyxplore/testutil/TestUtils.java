package skyxplore.testutil;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.domain.view.character.CharacterView;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;

import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;

@Deprecated
public class TestUtils {
    //Blocked Character
    private static final Long BLOCKED_CHARACTER_ENTITY_ID = 10L;
    public static final String BLOCKED_CHARACTER_ID = "blocked_character_id";

    //Category
    public static final String CATEGORY_ID = "category_id";

    //Character
    public static final String CHARACTER_ID_1 = "character_id_1";
    public static final String CHARACTER_ID_2 = "character_id_2";
    public static final String CHARACTER_NAME = "character_name";
    private static final Integer CHARACTER_MONEY = 10;
    public static final String FRIEND_NAME = "friend_name";
    private static final String CHARACTER_EQUIPMENT = "character_equipments";

    //SkyXpCredentials
    public static final String USER_NAME = "user_name";

    //Data
    public static final String DATA_ID_1 = "data_id_1";

    //FRIENDSHIP
    public static final String FRIEND_ID = "friend_id";
    public static final String FRIENDSHIP_ID = "friendship_id";

    //Friend Request
    public static final String FRIEND_REQUEST_ID = "friend_request_id";

    //Mail
    public static final String MAIL_ID_1 = "mail_id_1";
    public static final String MAIL_FROM_ID = "mail_from_id";
    public static final String MAIL_TO_ID = "mail_to_id";
    public static final String MAIL_FROM_NAME = "mail_from_name";
    public static final String MAIL_TO_NAME = "mail_to_name";
    public static final String MAIL_SUBJECT = "mail_subject";
    public static final String MAIL_MESSAGE = "mail_message";
    public static final String MAILS_ADDRESSEE_ID = "mail_addressee_id";
    public static final Long MAIL_SEND_TIME_EPOCH = 654612L;
    public static final OffsetDateTime MAIL_SEND_TIME = OffsetDateTime.of(LocalDateTime.ofEpochSecond(MAIL_SEND_TIME_EPOCH, 0, ZoneOffset.UTC), ZoneOffset.UTC);

    //User
    public static final String USER_ID = "user_id";

    public static BlockedCharacter createBlockedCharacter() {
        return BlockedCharacter.builder()
            .blockedCharacterEntityId(BLOCKED_CHARACTER_ENTITY_ID)
            .blockedCharacterId(BLOCKED_CHARACTER_ID)
            .characterId(CHARACTER_ID_1)
            .build();
    }

    public static SkyXpCharacter createCharacter() {
        SkyXpCharacter character = SkyXpCharacter.builder().build();
        character.setCharacterId(CHARACTER_ID_1);
        character.setCharacterName(CHARACTER_NAME);
        character.setUserId(USER_ID);
        character.addMoney(CHARACTER_MONEY);
        character.addEquipment(CHARACTER_EQUIPMENT);
        return character;
    }

    public static CharacterView createCharacterView(SkyXpCharacter character) {
        CharacterView view = new CharacterView();
        view.setCharacterId(character.getCharacterId());
        view.setCharacterName(character.getCharacterName());
        return view;
    }

    public static FriendRequest createFriendRequest() {
        return FriendRequest.builder()
            .friendRequestId(FRIEND_REQUEST_ID)
            .friendId(FRIEND_ID)
            .characterId(CHARACTER_ID_1)
            .build();
    }

    public static FriendRequestView createFriendRequestView() {
        FriendRequestView view = new FriendRequestView();
        view.setCharacterId(CHARACTER_ID_1);
        view.setFriendRequestId(FRIEND_REQUEST_ID);
        view.setFriendId(FRIEND_ID);
        view.setFriendName(FRIEND_NAME);
        return view;
    }

    public static Friendship createFriendship() {
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(FRIENDSHIP_ID);
        friendship.setCharacterId(CHARACTER_ID_1);
        friendship.setFriendId(FRIEND_ID);
        return friendship;
    }

    public static FriendView createFriendView() {
        FriendView view = new FriendView();
        view.setFriendId(FRIENDSHIP_ID);
        view.setFriendId(FRIEND_ID);
        view.setFriendName(FRIEND_NAME);
        view.setActive(false);
        return view;
    }

    public static Mail createMail() {
        return Mail.builder()
            .mailId(MAIL_ID_1)
            .from(MAIL_FROM_ID)
            .to(MAIL_TO_ID)
            .subject(MAIL_SUBJECT)
            .message(MAIL_MESSAGE)
            .read(false)
            .sendTime(MAIL_SEND_TIME)
            .archived(false)
            .deletedByAddressee(false)
            .deletedBySender(false)
            .build();
    }

    public static SendMailRequest createSendMailRequest() {
        SendMailRequest request = new SendMailRequest();
        request.setAddresseeId(MAILS_ADDRESSEE_ID);
        request.setSubject(MAIL_SUBJECT);
        request.setMessage(MAIL_MESSAGE);
        return request;
    }
}
