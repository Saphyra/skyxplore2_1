window.Mapping = {
    ACCEPT_FRIEND_REQUEST: "friend/request/accept",
    ADD_FRIEND: "friend/request",
    ADD_TO_QUEUE: "factory",
    ADDRESSEES: "mail/addressee",
    ARCHIVE_MAILS: "mail/archive",
    BUY_ITEMS: "character/equipment",
    CANCEL_FRIEND_REQUEST: "friend/request",
    CHANGE_EMAIL: "user/email",
    CHANGE_PASSWORD: "user/password",
    CHANGE_USERNAME: "user/name",
    CHARACTER_MONEY: "character/money",
    CHARACTER_NAME_EXISTS: "character/name",
    CHARACTER_SELECT: "characterselect",
    CREATE_CHARACTER: "character",
    DECLINE_FRIEND_REQUEST: "friend/request",
    DELETE_ACCOUNT: "user",
    DELETE_CHARACTER: "character/*",
    DELETE_MAILS: "mail",
    EMAIL_EXISTS: "user/email",
    EQUIP_ITEM: "ship/equipment",
    EQUIP_SHIP: "ship/equipment/*",
    EQUIPMENT_STORAGE: "character/storage",
    GET_ARCHIVED_MAILS: "mail/archived",
    GET_CHARACTERS: "character",
    GET_CHARACTERS_CAN_BE_FRIEND: "friend/name",
    GET_FRIEND_REQUESTS: "friend/request/received",
    GET_FRIENDS: "friend",
    GET_INCOMING_MAILS: "mail",
    GET_MATERIALS: "factory/materials",
    GET_SENT_FRIEND_REQUESTS: "friend/request/sent",
    GET_SENT_MAILS: "mail/sent",
    GET_SHIP_DATA: "ship",
    GET_QUEUE: "factory/queue",
    ITEMS_OF_CATEGORY: "categories/*",
    LOGIN: "login",
    LOGOUT: "logout",
    MARK_MAILS_READ: "mail/mark/read",
    MARK_MAILS_UNREAD: "mail/mark/unread",
    NUMBER_OF_FRIEND_REQUEST: "notification/friend-request",
    NUMBER_OF_NOTIFICATIONS: "notification",
    NUMBER_OF_UNREAD_MAILS: "notification/unread-mail",
    REGISTER: "user",
    RENAME_CHARACTER: "character",
    RESTORE_MAILS: "mail/restore",
    SELECT_CHARACTER: "character/*",
    SEND_MAIL: "mail",
    UNEQUIP_ITEM: "ship/equipment",
    USERNAME_EXISTS: "user/name",
    
    concat: function(path, id){
        return path.replace("*", id);
    }
}