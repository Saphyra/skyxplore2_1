(function NotificationService(){
    events.LOAD_NOTIFICATIONS = "load_notifications";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_NOTIFICATIONS},
        loadNotifications
    ));
    
    function loadNotifications(){
        const request = new Request(HttpMethod.GET, Mapping.NUMBER_OF_NOTIFICATIONS);
            request.convertResponse = function(response){
                return Number(response.body);
            }
            request.processValidResponse = function(numberOfNotifications){
                displayNotificationNum("notification-num", numberOfNotifications);
            }
        dao.sendRequestAsync(request);
    }
})();