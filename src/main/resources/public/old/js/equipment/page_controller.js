(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/equipment/ship_controller.js")
        scriptLoader.loadScript("js/equipment/equipment_controller.js")
        scriptLoader.loadScript("js/equipment/equipment_service.js")
        
        this.refresh = refresh;
        
        $(document).ready(function(){
            refresh()
        });
    }

    /*
    Reloads the content of the page.
    Arguments:
        - needReload: If true, queries the actual state of the character.
    */
    function refresh(needReload){
        try{
            const characterId = sessionStorage.characterId;
            
            if(needReload == null || needReload == undefined){
                needReload = true;
            }
            
            if(needReload){
                shipController.loadShipEquipment();
                equipmentController.loadEquipment();
            }
            
            shipController.showShipEquipment();
            equipmentController.showEquipment();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();