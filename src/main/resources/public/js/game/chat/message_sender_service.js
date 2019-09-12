(function MessageSenderService(){
    $(document).ready(init);

    function sendMessage(){
        const roomId = chatQueryController.getActiveChatRoom();
        const messageField = document.getElementById("chat-input");

        const request = new Request(HttpMethod.PUT, Mapping.concat(Mapping.SEND_GAME_MESSAGE, roomId), {value: messageField.value});
            request.processValidResponse = function(){
                messageField.value = "";
            }
        dao.sendRequestAsync(request);
    }

    function init(){
        $("#chat-input").on("keyup",function(e){
            if(e.which == 13){
                sendMessage();
            }
        })
    }
})();