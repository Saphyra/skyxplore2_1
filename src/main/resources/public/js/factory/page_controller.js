(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/factory/content_controller.js");
        scriptLoader.loadScript("js/factory/materials_controller.js");
        scriptLoader.loadScript("js/factory/menu_controller.js");
        scriptLoader.loadScript("js/factory/queue_controller.js");
        
        this.refresh = refresh;
        
        $(document).ready(function(){
            refresh(true);
        });
    }
    
    function refresh(needReload){
        try{
            if(needReload){
                materialsController.loadMaterials();
            }
            
            materialsController.displayMaterials();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();