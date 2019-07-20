(function EquipService(){
    scriptLoader.loadScript("/js/common/localization/message_code.js");
    scriptLoader.loadScript("/js/common/equipment/item_cache.js");

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

        const request = new Request(HttpMethod.DELETE, Mapping.UNEQUIP_ITEM, {slot: payload.getContainerId(), itemId: payload.getId()});
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("ITEM_UNEQUIPPED"));
                eventProcessor.processEvent(new Event(events.ITEM_UNEQUIPPED, payload));
            }
        dao.sendRequestAsync(request);
    }
    
    function equipShip(event){
        const shipId = event.getPayload();

        const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.EQUIP_SHIP, shipId));
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("SHIP_EQUIPPED"));
                eventProcessor.processEvent(new Event(events.SHIP_EQUIPPED, shipId));
            }
        dao.sendRequestAsync(request);
    }

    function equipItem(event){
        const payload = event.getPayload();

        const request = new Request(HttpMethod.POST, Mapping.EQUIP_ITEM, {itemId: payload.itemId, equipTo: payload.containerId});
            request.processValidResponse = function(){
                notificationService.showSuccess(MessageCode.getMessage("ITEM_EQUIPPED"));
                eventProcessor.processEvent(new Event(events.ITEM_EQUIPPED, payload));
            }
        dao.sendRequestAsync(request);
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