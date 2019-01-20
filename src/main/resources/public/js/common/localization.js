(function Localization(){
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(pageName){
            const path = "i18n/" + getLanguage() + "/" + pageName.getPayload() + ".json";
            const request = new Request(HttpMethod.GET, path);
                request.state = pageName;
                request.convertResponse = function(response){return JSON.parse(response.body)};
                request.processValidResponse = fillPageWithText;
                request.processInvalidResponse = createFallBackQuery;
            
            dao.sendRequestAsync(request);
        }
    ));
    
    function fillPageWithText(content){
        document.title = content.title;
        for(let id in content.payload){
            const element = document.getElementById(id);
            if(element){
                const localizations = content.payload[id];
                for(let lindex in localizations){
                    element[localizations[lindex].key] = localizations[lindex].message;
                }
                
                
            }else logService.log("Element not found with id " + id, "warn");
        }
    }
    
    function createFallBackQuery(response, pageName){
        const path = "i18n/hu-hu/" + pageName.getPayload() + ".json";
        const request = new Request(HttpMethod.GET, path);
            request.convertResponse = function(response){return JSON.parse(response.body)};
            request.processValidResponse = fillPageWithText;
        
        dao.sendRequestAsync(request);
    }
})();