(function ErrorCode(){
    const errorCodes = {};
    
    window.ErrorCode = new function(){
        this.getMessage = getMessage;
    }
    
    function getMessage(errorCode){
        return errorCodes[errorCode] || "No errorMessage found for errorCode " + errorCode;
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(){
            const path = "i18n/" + getLanguage() + "/error_codes.json";
            const request = new Request(HttpMethod.GET, path);
                request.convertResponse = function(response){return JSON.parse(response.body)};
                request.processValidResponse = addErrorCodes;
                request.processInvalidResponse = createFallBackQuery;
            
            dao.sendRequestAsync(request);
        }
    ));
    
    function addErrorCodes(codes){
        for(let eindex in codes){
            errorCodes[eindex] = codes[eindex];
        }
    }
    
    function createFallBackQuery(response){
        const path = "i18n/hu-hu/error_codes.json";
        const request = new Request(HttpMethod.GET, path);
            request.convertResponse = function(response){return JSON.parse(response.response)};
            request.processValidResponse = addErrorCodes;
        
        dao.sendRequestAsync(request);
    }
})();