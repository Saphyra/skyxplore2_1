(function MessageCode(){
    const messageCodes = {};
    
    window.MessageCode = new function(){
        this.getMessage = getMessage;
    }
    
    function getMessage(messageCode){
        return messageCodes[messageCode] || "No message found for messageCode " + messageCode;
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(){
            const path = "i18n/" + getLanguage() + "/message_codes.json";
            const request = new Request(HttpMethod.GET, path);
                request.convertResponse = function(response){return JSON.parse(response.body)};
                request.processValidResponse = addMessageCodes;
                request.processInvalidResponse = createFallBackQuery;
            
            dao.sendRequestAsync(request);
        },
        true
    ));
    
    function addMessageCodes(codes){
        for(let eindex in codes){
            messageCodes[eindex] = codes[eindex];
        }
        
        eventProcessor.processEvent(new Event(events.MESSAGE_CODES_LOADED));
    }
    
    function createFallBackQuery(response){
        const path = "i18n/hu/message_codes.json";
        const request = new Request(HttpMethod.GET, path);
            request.convertResponse = function(response){return JSON.parse(response.body)};
            request.processValidResponse = addMessageCodes;
        
        dao.sendRequestAsync(request);
    }
})();