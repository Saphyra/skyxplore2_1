(function Localization(){
    let additionalContent = {};

    window.Localization = new function(){
        this.getAdditionalContent = function(contentId){
            return additionalContent[contentId] || throwException("IllegalArgument", "No additionalContent found with id " + contentId);
        }
    }

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
        },
        true
    ));
    
    function fillPageWithText(content){
        document.title = content.title;
        for(let id in content.staticText){
            const element = document.getElementById(id);
            if(element){
                const localizations = content.staticText[id];
                for(let lindex in localizations){
                    element[localizations[lindex].key] = localizations[lindex].message;
                }
            }else logService.log("Element not found with id " + id, "warn");
        }
        additionalContent = content.additionalContent;
        eventProcessor.processEvent(new Event(events.LOCALIZATION_LOADED));
    }
    
    function createFallBackQuery(response, pageName){
        const path = "i18n/hu/" + pageName.getPayload() + ".json";
        const request = new Request(HttpMethod.GET, path);
            request.convertResponse = function(response){return JSON.parse(response.body)};
            request.processValidResponse = fillPageWithText;
        
        dao.sendRequestAsync(request);
    }
})();