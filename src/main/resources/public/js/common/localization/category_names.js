(function CategoryNames(){
    const categoryNames = {};
    
    window.CategoryNames = new function(){
        this.getCategoryName = function(category){
            return categoryNames[category] || throwException("IllegalArgument", "No categoryName found for category " + category);
        }
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(){
            const path = "i18n/" + getLanguage() + "/categories.json";
            const request = new Request(HttpMethod.GET, path);
                request.convertResponse = function(response){return JSON.parse(response.body)};
                request.processValidResponse = addCategoryNames;
                request.processInvalidResponse = createFallBackQuery;
            
            dao.sendRequestAsync(request);
        },
        true
    ));
    
    function addCategoryNames(codes){
        for(let eindex in codes){
            categoryNames[eindex] = codes[eindex];
        }
        
        eventProcessor.processEvent(new Event(events.CATEGORY_NAMES_LOADED));
    }
    
    function createFallBackQuery(response){
        const path = "i18n/hu/categories.json";
        const request = new Request(HttpMethod.GET, path);
            request.convertResponse = function(response){return JSON.parse(response.body)};
            request.processValidResponse = addCategoryNames;
        
        dao.sendRequestAsync(request);
    }
})();