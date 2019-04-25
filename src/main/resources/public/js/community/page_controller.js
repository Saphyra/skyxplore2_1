(function PageController(){
    scriptLoader.loadScript("js/community/notification_controller.js");

    scriptLoader.loadScript("js/community/mail/mail_operations.js");
    scriptLoader.loadScript("js/community/mail/write_mail_controller.js");
    scriptLoader.loadScript("js/community/mail/sent_mails_controller.js");
    scriptLoader.loadScript("js/community/mail/incoming_mails_controller.js");
    scriptLoader.loadScript("js/community/mail/archived_mails_controller.js");
        scriptLoader.loadScript("js/community/mail/bulk_operations/with_incoming_mails.js");
        scriptLoader.loadScript("js/community/mail/bulk_operations/with_archived_mails.js");
        scriptLoader.loadScript("js/community/mail/bulk_operations/with_sent_mails.js");

    scriptLoader.loadScript("js/community/friend/add_friend_controller.js");
    scriptLoader.loadScript("js/community/friend/friend_request_controller.js");
    scriptLoader.loadScript("js/community/friend/sent_friend_request_controller.js");

    events.OPEN_MAIN_LISTS = "open_main_lists";
    events.OPEN_WRITE_MAIL_WINDOW = "open_write_mail_window";
    events.OPEN_ADD_FRIEND_WINDOW = "open_add_friend_window";

    events.OPEN_FRIENDS_TAB = "open_friends_tab";
    events.OPEN_FRIEND_REQUESTS_TAB = "open_friend_requests_tab";
    events.OPEN_SENT_FRIEND_REQUESTS_TAB = "open_sent_friend_requests_tab";

    events.OPEN_SENT_MAILS_TAB = "open_sent_mails_tab";
    events.OPEN_INCOMING_MAILS_TAB = "open_incoming_mails_tab";
    events.OPEN_ARCHIVED_MAILS_TAB = "open_archived_mails_tab";

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "community"));
    });

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){
            return eventType === events.LOAD_STATE_CHANGED
                && LoadState.localizationLoaded
        },
        function(){
            eventProcessor.processEvent(new Event(events.OPEN_INCOMING_MAILS_TAB));
        },
        true
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_ADD_FRIEND_WINDOW},
        function(){
            switchTab("main-tab", "main-add-friend");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_FRIENDS_TAB},
        function(){
            switchTab("friend-list-tab", "friend-list-container");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_FRIEND_REQUESTS_TAB},
        function(){
            switchTab("friend-list-tab", "friend-request-list-container");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_SENT_FRIEND_REQUESTS_TAB},
        function(){
            switchTab("friend-list-tab", "sent-friend-request-list-container");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_WRITE_MAIL_WINDOW},
        function(){
            switchTab("main-tab", "main-write-mail");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_MAIN_LISTS},
        function(){
            switchTab("main-tab", "main-lists");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_SENT_MAILS_TAB},
        function(){
            switchTab("mail-list-tab", "sent-mail-list-container");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_INCOMING_MAILS_TAB},
        function(){
            switchTab("mail-list-tab", "incoming-mail-list-container");
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.OPEN_ARCHIVED_MAILS_TAB},
        function(){
            switchTab("mail-list-tab", "archived-mail-list-container");
        }
    ));
})();

function getSearchResultTimeout(){
    const presetTimeout = getCookie("search-result-timeout");
    return presetTimeout == null ? 1000 : Number(presetTimeout);
}

function generateIncomingMailId(mailId){
    return "incoming-mail-" + mailId;
}

function generateArchivedMailId(mailId){
    return "archived-mail-" + mailId;
}

function generateSentMailId(mailId){
    return "sent-mail-" + mailId;
}

function generateMarkButtonId(mailId){
    return "mark-button-" + mailId;
}