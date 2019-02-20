(function EquipService(){
    events.UNEQUIP_ITEM = "unequip_item";
    events.ITEM_UNEQUIPPED = "item_unequipped";
    
    events.EQUIP_SHIP = "equip_ship";
    events.SHIP_EQUIPPED = "ship_equipped";
    events.EQUIP_ITEM = "equip_item";
    events.ITEM_EQUIPPED = "item_equipped";

    window.equipService = new function(){
        this.dragStart = dragStart;
        this.dragEnd = dragEnd;
    }
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.UNEQUIP_ITEM},
        unequip
    ));
    
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.EQUIP_SHIP},
        equipShip
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.EQUIP_ITEM},
        equipItem
    ));
    
    function unequip(event){
        const payload = event.getPayload();
        eventProcessor.processEvent(new Event(events.ITEM_UNEQUIPPED, payload));
    }
    
    function equipShip(event){
        const payload = event.getPayload();
        eventProcessor.processEvent(new Event(events.SHIP_EQUIPPED, payload));
    }

    function equipItem(event){
        const payload = event.getPayload();
        eventProcessor.processEvent(new Event(events.ITEM_EQUIPPED, payload));
    }

    function dragEnd(){
        $(".empty-slot")
            .css("border-color", "white")
            .off("dragover, drop", null);
    }

    function dragStart(event){
        const itemId = event.dataTransfer.getData("item");
        const itemData = itemCache.get(itemId);

        $("." + itemData.slot + "-slot .empty-slot")
            .filter(function(){
                return isEquipmentAllowed(itemData);
            })
            .css("border-color", "green")
            .on("dragover", function(e){e.preventDefault()})
            .on("drop", function(e){
                const itemId = e.originalEvent.dataTransfer.getData("item");
                const equipTo = this.getAttribute("parent-id");

                eventProcessor.processEvent(new Event(events.EQUIP_ITEM, {itemId: itemId, containerId: equipTo}));
            });

        function isEquipmentAllowed(itemData){
            return itemData.type === "extender" ? !shipService.isExtenderOfTypeEquipped(itemData.extendedslot) : true;
        }
    }
})();