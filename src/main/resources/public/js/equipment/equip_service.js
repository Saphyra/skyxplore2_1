(function EquipService(){
    events.UNEQUIP_ITEM = "unequip_item";
    events.ITEM_UNEQUIPPED = "item_unequipped";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.UNEQUIP_ITEM},
        unequip
    ));
    
    function unequip(event){
        const payload = event.getPayload();
        
        eventProcessor.processEvent(new Event(events.ITEM_UNEQUIPPED, payload));
    }
})();