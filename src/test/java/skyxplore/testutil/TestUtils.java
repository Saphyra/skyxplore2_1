package skyxplore.testutil;

import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.character.domain.view.character.CharacterView;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.github.saphyra.skyxplore.gamedata.entity.Ship;
import org.github.saphyra.skyxplore.gamedata.entity.Slot;
import org.github.saphyra.skyxplore.ship.domain.EquippedShip;
import org.github.saphyra.skyxplore.slot.domain.EquippedSlot;
import skyxplore.controller.request.character.EquipRequest;
import skyxplore.controller.request.character.UnequipRequest;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;
import skyxplore.controller.view.community.mail.MailView;
import skyxplore.controller.view.ship.ShipView;
import skyxplore.controller.view.slot.SlotView;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static final String DATA_ABILITY = "ability";
    public static final String DATA_CONNECTOR = "connector";
    public static final String DATA_ELEMENT = "element";
    public static final String DATA_ITEM_FRONT = "item_front";
    public static final String DATA_ITEM_LEFT = "item_left";
    public static final String DATA_ITEM_RIGHT = "item_right";
    public static final String DATA_ITEM_BACK = "item_back";
    public static final String DATA_NAME = "data_name";
    public static final String DATA_SLOT = "data_slot";

    public static final Integer DATA_SHIP_CONNECTOR_SLOT = 5;
    public static final Integer DATA_SHIP_COREHULL = 1000;

    //Equip
    public static final String EQUIP_ITEM_ID = "equip_item_id";
    private static final String EQUIP_TO = "equip_to";
    private static final String UNEQUIP_FROM = "unequip_from";

    //EquippedShip
    public static final String EQUIPPED_SHIP_ID = "equipped_ship_id";
    public static final String EQUIPPED_SHIP_TYPE = "equipped_ship_type";

    //EquippedSlot
    private static final String EQUIPPED_SLOT_ID = "equipped_slot_id";
    public static final String DEFENSE_SLOT_ID = "defense_slot_id";
    public static final Integer EQUIPPED_SLOT_FRONT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_LEFT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_RIGHT_SLOT = 2;
    public static final Integer EQUIPPED_SLOT_BACK_SLOT = 2;
    public static final String WEAPON_SLOT_ID = "weapon_slot_id";

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
    public static final String MAIL_ENCRYPTED_SUBJECT = "mail_encrypted_subject";
    public static final String MAIL_MESSAGE = "mail_message";
    public static final String MAIL_ENCRYPTED_MESSAGE = "mail_encrypted_message";
    public static final String MAILS_ADDRESSEE_ID = "mail_addressee_id";
    public static final Long MAIL_SEND_TIME_EPOCH = 654612L;
    public static final OffsetDateTime MAIL_SEND_TIME = OffsetDateTime.of(LocalDateTime.ofEpochSecond(MAIL_SEND_TIME_EPOCH, 0, ZoneOffset.UTC), ZoneOffset.UTC);

    //Material
    public static final String MATERIAL_ID = "material_id";

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

    private static Slot createDefenseSlot() {
        Slot slot = new Slot();
        //Slot
        Integer SLOT_DEFENSE_FRONT = 2;
        slot.setFront(SLOT_DEFENSE_FRONT);
        Integer SLOT_DEFENSE_SIDE = 3;
        slot.setSide(SLOT_DEFENSE_SIDE);
        Integer slOT_DEFENSE_BACK = 5;
        slot.setBack(slOT_DEFENSE_BACK);
        return slot;
    }

    public static EquippedSlot createEquippedDefenseSlot() {
        return createEquippedSlot(DEFENSE_SLOT_ID);
    }

    public static EquippedSlot createEquippedWeaponSlot() {
        return createEquippedSlot(WEAPON_SLOT_ID);
    }

    public static EquippedShip createEquippedShip() {
        EquippedShip ship = EquippedShip.builder().build();
        ship.setShipId(EQUIPPED_SHIP_ID);
        ship.setCharacterId(CHARACTER_ID_1);
        ship.setShipType(EQUIPPED_SHIP_TYPE);
        ship.setCoreHull(DATA_SHIP_COREHULL);
        ship.setConnectorSlot(DATA_SHIP_CONNECTOR_SLOT);
        ship.addConnector(DATA_CONNECTOR);
        ship.setDefenseSlotId(DEFENSE_SLOT_ID);
        ship.setWeaponSlotId(WEAPON_SLOT_ID);
        return ship;
    }

    public static ShipView createShipView() {
        return ShipView.builder()
            .shipType(EQUIPPED_SHIP_TYPE)
            .coreHull(DATA_SHIP_COREHULL)
            .connectorSlot(DATA_SHIP_CONNECTOR_SLOT)
            .connectorEquipped(Arrays.asList(DATA_CONNECTOR))
            .defenseSlot(createSlotView(createEquippedDefenseSlot()))
            .defenseSlot(createSlotView(createEquippedWeaponSlot()))
            .ability(Arrays.asList(DATA_ABILITY))
            .build();
    }

    public static EquippedSlot createEquippedSlot(String slotId) {
        EquippedSlot slot = createEquippedSlot();
        slot.setSlotId(slotId);
        return slot;
    }

    public static EquippedSlot createEquippedSlot() {
        EquippedSlot slot = EquippedSlot.builder().build();
        slot.setSlotId(EQUIPPED_SLOT_ID);
        slot.setShipId(EQUIPPED_SHIP_ID);
        slot.setFrontSlot(EQUIPPED_SLOT_FRONT_SLOT);
        slot.setLeftSlot(EQUIPPED_SLOT_LEFT_SLOT);
        slot.setRightSlot(EQUIPPED_SLOT_RIGHT_SLOT);
        slot.setBackSlot(EQUIPPED_SLOT_BACK_SLOT);
        slot.addFront(DATA_ITEM_FRONT);
        slot.addLeft(DATA_ITEM_LEFT);
        slot.addRight(DATA_ITEM_RIGHT);
        slot.addBack(DATA_ITEM_BACK);
        return slot;
    }

    public static EquipRequest createEquipRequest() {
        EquipRequest request = new EquipRequest();
        request.setItemId(EQUIP_ITEM_ID);
        request.setEquipTo(EQUIP_TO);
        return request;
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

    public static MailEntity createMailEntity() {
        return MailEntity.builder()
            .mailId(MAIL_ID_1)
            .from(MAIL_FROM_ID)
            .to(MAIL_TO_ID)
            .subject(MAIL_ENCRYPTED_SUBJECT)
            .message(MAIL_ENCRYPTED_MESSAGE)
            .read(false)
            .sendTime(MAIL_SEND_TIME_EPOCH)
            .archived(false)
            .deletedByAddressee(false)
            .deletedBySender(false)
            .build();
    }

    public static List<String> createMailIdList(String... mailIds) {
        return Arrays.asList(mailIds);
    }

    public static MailView createMailView() {
        return MailView.builder()
            .mailId(MAIL_ID_1)
            .from(MAIL_FROM_ID)
            .to(MAIL_TO_ID)
            .fromName(MAIL_FROM_NAME)
            .toName(MAIL_TO_NAME)
            .subject(MAIL_SUBJECT)
            .read(false)
            .sendTime(MAIL_SEND_TIME_EPOCH)
            .build();
    }

    public static SendMailRequest createSendMailRequest() {
        SendMailRequest request = new SendMailRequest();
        request.setAddresseeId(MAILS_ADDRESSEE_ID);
        request.setSubject(MAIL_SUBJECT);
        request.setMessage(MAIL_MESSAGE);
        return request;
    }

    public static Ship createShip() {
        Ship ship = new Ship();
        ship.setCoreHull(DATA_SHIP_COREHULL);
        ship.setConnector(DATA_SHIP_CONNECTOR_SLOT);
        ship.setDefense(createDefenseSlot());
        ship.setWeapon(createWeaponSlot());
        ArrayList<String> list = new ArrayList<>();
        list.add(DATA_ABILITY);
        ship.setAbility(list);
        return ship;
    }

    public static SlotView createSlotView(EquippedSlot slot) {
        return SlotView.builder()
            .frontSlot(slot.getFrontSlot())
            .frontEquipped(slot.getFrontEquipped())
            .rightSlot(slot.getRightSlot())
            .rightEquipped(slot.getRightEquipped())
            .backSlot(slot.getBackSlot())
            .backEquipped(slot.getBackEquipped())
            .leftSlot(slot.getLeftSlot())
            .leftEquipped(slot.getLeftEquipped())
            .build();
    }

    public static UnequipRequest createUnequipRequest() {
        UnequipRequest request = new UnequipRequest();
        request.setItemId(EQUIP_ITEM_ID);
        request.setSlot(UNEQUIP_FROM);
        return request;
    }

    private static Slot createWeaponSlot() {
        Slot slot = new Slot();
        Integer SLOT_WEAPON_FRONT = 7;
        slot.setFront(SLOT_WEAPON_FRONT);
        Integer SLOT_WEAPON_SIDE = 11;
        slot.setSide(SLOT_WEAPON_SIDE);
        Integer SLOT_WEAPON_BACK = 13;
        slot.setBack(SLOT_WEAPON_BACK);
        return slot;
    }
}
