(function MaterialsController(){
    window.materialsController = new function(){
        scriptLoader.loadScript("js/common/dao/factory_dao.js");
        
        this.displayMaterials = displayMaterials;
    }
    
    function displayMaterials(){
        try{
            const container = document.getElementById("materials");
                container.innerHTML = "";
                
            const materials = factoryDao.getMaterials();
            
            logService.log(materials);
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();