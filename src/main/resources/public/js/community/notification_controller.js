(function NotificationController(){
    $(document).ready(init);


    function init(){
        setIntervalImmediate(displayNumberOfFriendRequests, 20000);
        setIntervalImmediate(displayNumberOfReceivedMails, 20000);
    }

    function displayNumberOfFriendRequests(){
        const request = new Request(HttpMethod.GET, Mapping.NUMBER_OF_FRIEND_REQUEST);
            request.processValidResponse = function(response){
                displayNotificationNum("friend-request-num", response.body);
            }
        dao.sendRequestAsync(request);
    };

    function displayNumberOfReceivedMails(){
        const request = new Request(HttpMethod.GET, Mapping.NUMBER_OF_UNREAD_MAILS);
            request.processValidResponse = function(response){
                displayNotificationNum("number-of-unread-mails", response.body);
            }
        dao.sendRequestAsync(request);
    }
})();