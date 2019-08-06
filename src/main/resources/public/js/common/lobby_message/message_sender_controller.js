(function MessageSenderController(){
    $(document).ready(init);

    function sendMessage(){
        const message = $("#message").val();
        const request = new Request(HttpMethod.PUT, Mapping.SEND_LOBBY_MESSAGE , {value: message});
            request.processValidResponse = function(){
                $("#message").val("");
                eventProcessor.processEvent(new Event(events.LOAD_MESSAGES));
            }
        dao.sendRequestAsync(request);
    }

    function init(){
        $("#message").on("keyup",function(e){
            if(e.which == 13){
                sendMessage();
            }
        })

        $("#send-message-button").on("click", sendMessage);
    }
})();