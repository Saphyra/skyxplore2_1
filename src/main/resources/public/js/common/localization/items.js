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
            loadLocalization("items", addItems);
        },
        true
    ));
    
    function addItems(itemMap){
        for(let iindex in itemMap){
            items[iindex] = itemMap[iindex];
        }
        eventProcessor.processEvent(new Event(events.ITEMS_LOADED));
    }
})();