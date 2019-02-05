(function Description(){
    const descriptions = {};

    window.Description = new function(){
        this.getDescription = function(descriptionId){
            return descriptions[descriptionId] || throwException("IllegalArgument", "No description found with id " + descriptionId);
        }
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(){
            const path = "i18n/" + getLanguage() + "/description.json";
            const request = new Request(HttpMethod.GET, path);
                request.convertResponse = function(response){return JSON.parse(response.body)};
                request.processValidResponse = loadDescriptions;
                request.processInvalidResponse = createFallBackQuery;

            dao.sendRequestAsync(request);
        }
    ));

    function loadDescriptions(content){
        for(let dindex in content){
            descriptions[dindex] = content[dindex];
        }
    }

    function createFallBackQuery(response, pageName){
        const path = "i18n/hu/description.json";
        const request = new Request(HttpMethod.GET, path);
            request.convertResponse = function(response){return JSON.parse(response.body)};
            request.processValidResponse = loadDescriptions;

        dao.sendRequestAsync(request);
    }
})();