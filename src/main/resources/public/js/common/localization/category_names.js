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
            loadLocalization("categories", addCategoryNames);
        },
        true
    ));
    
    function addCategoryNames(codes){
        for(let eindex in codes){
            categoryNames[eindex] = codes[eindex];
        }
        
        eventProcessor.processEvent(new Event(events.CATEGORY_NAMES_LOADED));
    }
})();