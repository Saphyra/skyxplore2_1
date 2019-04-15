(function PageController(){
    scriptLoader.loadScript("js/community/notification_controller.js");
    scriptLoader.loadScript("js/community/mail_operations.js");
    scriptLoader.loadScript("js/community/write_mail_controller.js");
    scriptLoader.loadScript("js/community/sent_mails_controller.js");
    scriptLoader.loadScript("js/community/incoming_mails_controller.js");
    scriptLoader.loadScript("js/community/archived_mails_controller.js");
    scriptLoader.loadScript("js/community/bulk_operations/with_incoming_mails.js");

    events.OPEN_WRITE_MAIL_WINDOW = "open_write_mail_window";
    events.OPEN_MAIN_LISTS = "open_mail_lists";
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