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
            loadLocalization("description", loadDescriptions);
        },
        true
    ));

    function loadDescriptions(content){
        for(let dindex in content){
            descriptions[dindex] = content[dindex];
        }
        
        eventProcessor.processEvent(new Event(events.DESCRIPTION_LOADED));
    }
})();