(function Items(){
    const items = {};
    
    window.Items = new function(){
        this.getItem = getItem;
    }
    
    function getItem(itemId){
        return items[itemId] || throwException("IllegalArgument", "No item found with itemId " + itemId);
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType === events.LOAD_LOCALIZATION},
        function(){
            const path = "i18n/" + getLanguage() + "/items.json";
            const request = new Request(HttpMethod.GET, path);
                request.convertResponse = function(response){return JSON.parse(response.body)};
                request.processValidResponse = addItems;
                request.processInvalidResponse = createFallBackQuery;
            
            dao.sendRequestAsync(request);
        }
    ));
    
    function addItems(itemMap){
        for(let iindex in itemMap){
            items[iindex] = itemMap[iindex];
        }
    }
    
    function createFallBackQuery(response){
        const path = "i18n/hu/items.json";
        const request = new Request(HttpMethod.GET, path);
            request.convertResponse = function(response){return JSON.parse(response.body)};
            request.processValidResponse = addItems;
        
        dao.sendRequestAsync(request);
    }
})();