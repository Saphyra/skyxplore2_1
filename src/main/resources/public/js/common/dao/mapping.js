window.Mapping = {
    ACCEPT_FRIEND_REQUEST: "/api/friend/request/accept",
    ACCEPT_INVITATION: "/api/lobby/invitation/*",
    ADD_FRIEND: "/api/friend/request",
    ADD_TO_QUEUE: "/api/factory",
    ADDRESSEES: "/api/mail/addressee",
    ALLOW_CHARACTER: "/api/blockedcharacter",
    ARCHIVE_MAILS: "/api/mail/archive",
    BLOCK_CHARACTER: "/api/blockcharacter",
    BUY_ITEMS: "/api/character/equipment",
    CANCEL_FRIEND_REQUEST: "/api/friend/request",
    CHANGE_EMAIL: "/api/user/email",
    CHANGE_PASSWORD: "/api/user/password",
    CHANGE_USERNAME: "/api/user/name",
    CHARACTER_MONEY: "/api/character/money",
    CHARACTER_NAME_EXISTS: "/api/character/name",
    CHARACTER_SELECT: "/api/characterselect",
    CREATE_CHARACTER: "/api/character",
    CREATE_LOBBY: "/api/lobby",
    DECLINE_FRIEND_REQUEST: "/api/friend/request",
    DELETE_ACCOUNT: "/api/user",
    DELETE_CHARACTER: "/api/character/*",
    DELETE_FRIEND: "/api/friend",
    DELETE_MAILS: "/api/mail",
    DESELECT_CHARACTER: "/api/character/deselect",
    EMAIL_EXISTS: "/api/user/email",
    EQUIP_ITEM: "/api/ship/equipment",
    EQUIP_SHIP: "/api/ship/equipment/*",
    EQUIPMENT_STORAGE: "/api/character/storage",
    EXIT_FROM_LOBBY: "/api/lobby",
    GET_ACTIVE_FRIENDS: "/api/friend/active",
    GET_ARCHIVED_MAILS: "/api/mail/archived",
    GET_BLOCKED_CHARACTERS: "/api/blockedcharacter",
    GET_CHARACTER_ID: "/api/character/id",
    GET_CHARACTER_STATUS: "/api/character/status",
    GET_CHARACTERS: "/api/character",
    GET_CHARACTERS_CAN_BE_BLOCKED: "/api/blockcharacter/name",
    GET_CHARACTERS_CAN_BE_FRIEND: "/api/friend/name",
    GET_FRIEND_REQUESTS: "/api/friend/request/received",
    GET_FRIENDS: "/api/friend",
    GET_INCOMING_MAILS: "/api/mail",
    GET_INVITATIONS: "/api/lobby/invitation",
    GET_LANGUAGES: "/gamedata/languages.json",
    GET_LOBBY: "/api/lobby",
    GET_LOBBY_EVENTS: "/api/lobby/event",
    GET_LOBBY_MEMBERS: "/api/lobby/member",
    GET_LOBBY_MESSAGES: "/api/lobby/message",
    GET_MATERIALS: "/api/factory/materials",
    GET_SENT_FRIEND_REQUESTS: "/api/friend/request/sent",
    GET_SENT_MAILS: "/api/mail/sent",
    GET_SHIP_DATA: "/api/ship",
    GET_QUEUE: "/api/factory/queue",
    INVITE_TO_LOBBY: "/api/lobby/invitation/*",
    ITEMS_OF_CATEGORY: "/api/categories/*",
    KICK_FROM_LOBBY: "/api/lobby/member/*",
    LOGIN: "/api/login",
    LOGOUT: "/api/logout",
    MARK_MAILS_READ: "/api/mail/mark/read",
    MARK_MAILS_UNREAD: "/api/mail/mark/unread",
    NUMBER_OF_FRIEND_REQUEST: "/api/notification/friend-request",
    NUMBER_OF_NOTIFICATIONS: "/api/notification",
    NUMBER_OF_UNREAD_MAILS: "/api/notification/unread-mail",
    READY_CHARACTER: "/api/lobby/ready",
    REGISTER: "/api/user",
    RENAME_CHARACTER: "/api/character",
    RESTORE_MAILS: "/api/mail/restore",
    SEARCH_INVITABLE_CHARACTERS: "/api/character/active/name",
    SELECT_CHARACTER: "/api/character/*",
    SEND_MAIL: "/api/mail",
    SEND_LOBBY_MESSAGE: "/api/lobby/message",
    SET_LOCALE: "/api/locale/*",
    START_QUEUEING: "/api/lobby/queue/*",
    TRANSFER_OWNERSHIP: "/api/lobby/owner/*",
    UNEQUIP_ITEM: "/api/ship/equipment",
    UNREADY_CHARACTER: "/api/lobby/unready",
    USERNAME_EXISTS: "/api/user/name",

    ACCOUNT_PAGE: "/web/account",
    CHARACTER_SELECT_PAGE: "/web/characterselect",
    COMMUNITY_PAGE: "/web/community",
    EQUIPMENT_PAGE: "/web/equipment",
    FACTORY_PAGE: "/web/factory",
    GAME_PAGE: "/web/game",
    HANGAR_PAGE: "/web/hangar",
    INDEX_PAGE: "/web",
    OVERVIEW_PAGE: "/web/overview",
    SHOP_PAGE: "/web/shop",
    LOBBY_PAGE_PAGE: "/web/lobby-page",
    LOBBY_QUEUE_PAGE: "/web/lobby-queue",

    concat: function(path, id){
        return path.replace("*", id);
    }
}