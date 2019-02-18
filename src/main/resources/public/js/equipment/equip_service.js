(function EquipService(){
    events.UNEQUIP_ITEM = "unequip_item";
    events.ITEM_UNEQUIPPED = "item_unequipped";
    
    events.EQUIP_SHIP = "equip_ship";
    events.SHIP_EQUIPPED = "ship_equipped";
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.UNEQUIP_ITEM},
        unequip
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.EQUIP_SHIP},
        equipShip
    ));
    
    function unequip(event){
        const payload = event.getPayload();
        eventProcessor.processEvent(new Event(events.ITEM_UNEQUIPPED, payload));
    }
    
    function equipShip(event){
        const payload = event.getPayload();
        eventProcessor.processEvent(new Event(events.SHIP_EQUIPPED, payload));
    }
})();