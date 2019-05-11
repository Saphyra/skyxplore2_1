(function InvitationController(){
    scriptLoader.loadScript("js/lobby/invitation/invitation_display_service.js");

    $(document).ready(init);

    function loadActiveFriends(){
        const request = new Request(HttpMethod.GET, Mapping.GET_ACTIVE_FRIENDS);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(activeCharacters){
                eventProcessor.processEvent(new Event(events.DISPLAY_INVITATION, activeCharacters));
            }
        dao.sendRequestAsync(request);
    }

    function init(){
        loadActiveFriends();
    }
})();