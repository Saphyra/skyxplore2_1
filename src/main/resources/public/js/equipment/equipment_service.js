(function EquipmentService(){
    window.equipmentService = new function(){
        scriptLoader.loadScript("js/common/dao/ship_dao.js");
        
        this.dragEnd = dragEnd;
        this.dragStart = dragStart;
        this.unequip = unequip;
    }
    
    function dragEnd(e){
        try{
            $(".emptyslot")
                .css("border-color", "white")
                .off("dragover, drop", null);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function dragStart(event){
        try{
            const itemId = event.dataTransfer.getData("item");
            const itemData = cache.get(itemId);
            
            $(".emptyslot." + itemData.slot)
                .css("border-color", "red")
                .on("dragover", function(e){e.preventDefault()})
                .on("drop", function(e){
                    e.preventDefault();
                    const itemId = e.originalEvent.dataTransfer.getData("item");
                    const equipTo = this.getAttribute("inslot");
                    if(shipDao.equip(itemId, equipTo, sessionStorage.characterId)){
                        notificationService.showSuccess("Felszerelve.");
                    }else{
                        notificationService.showError("Felszerelés sikertelen.");
                    }
                    pageController.refresh(true);
                });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Unequips the selected equipment from the ship.
    Arguments:
        - slot: the slot of the ship the equipment is unequipped from.
        - itemId: the id of the item to unequip.
    */
    function unequip(slot, itemId){
        try{
            if(shipDao.unequip(slot, itemId, sessionStorage.characterId)){
                notificationService.showSuccess("Leszerelés sikeres.");
            }else{
                notificationService.showError("Sikertelen leszerelés.");
            }
            
            pageController.refresh(true);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();