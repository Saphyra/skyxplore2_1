(function PageController(){
    scriptLoader.loadScript("/js/account/change_email/change_email_controller.js");
    scriptLoader.loadScript("/js/account/change_password/change_password_controller.js");
    scriptLoader.loadScript("/js/account/change_username/change_username_controller.js");
    scriptLoader.loadScript("/js/account/delete_account/delete_account_controller.js");
    scriptLoader.loadScript("/js/account/set_language/set_language_controller.js");

    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "account"));
    }
})();