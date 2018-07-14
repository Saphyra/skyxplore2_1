(function EquipmentController(){
    window.equipmentController = new function(){
        scriptLoader.loadScript("js/common/dao/character_dao.js");
        
        this.equipment = null;
        
        this.loadEquipment = loadEquipment;
        this.showEquipment = showEquipment;
    }
    
    function loadEquipment(){
        try{
            this.equipment = characterDao.getEquipmentOfCharacter(sessionStorage.characterId);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showEquipment(){
        try{
            if(equipmentController.equipment == null){
                throwException("IllegalState", "equipment must not be null.");
            }
            
            logService.log(equipmentController.equipment);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})()