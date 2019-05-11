(function InvitationController(){
    scriptLoader.loadScript("js/lobby/invitation/invitation_display_service.js");
    scriptLoader.loadScript("js/lobby/invitation/send_invitation_controller.js");

    events.LOAD_ACTIVE_FRIENDS = "load_active_friends";
    events.SEARCH_INVITABLE_FRIENDS = "search_invitable_friends";

    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOAD_ACTIVE_FRIENDS},
        loadActiveFriends
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SEARCH_INVITABLE_FRIENDS},
        searchInvitableCharacters
    ));

    function loadActiveFriends(){
        const request = new Request(HttpMethod.GET, Mapping.GET_ACTIVE_FRIENDS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(activeCharacters){
                eventProcessor.processEvent(new Event(events.DISPLAY_INVITATION, {characters: activeCharacters, containerId: "active-friends"}));
            }
        dao.sendRequestAsync(request);
    }

    function searchInvitableCharacters(){
        const request = new Request(HttpMethod.POST, Mapping.SEARCH_INVITABLE_CHARACTERS, {value: $("#character-name").val()});
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(activeCharacters){
                $("#character-name").val("");
                setSearchButtonStatus();
                eventProcessor.processEvent(new Event(events.DISPLAY_INVITATION, {characters: activeCharacters, containerId: "invitation-search-result"}));
            }
        dao.sendRequestAsync(request);
    }

    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_ACTIVE_FRIENDS));
        $("#character-name").on("keyup", setSearchButtonStatus);
    }

    function setSearchButtonStatus(){
        document.getElementById("search-characters-button").disabled = $("#character-name").val().length < 3;
    }
})();