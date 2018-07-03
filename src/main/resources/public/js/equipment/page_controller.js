(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/equipment/ship_controller.js")
        scriptLoader.loadScript("js/equipment/equipment_controller.js")
        
        this.refresh = refresh;
        
        $(document).ready(function(){
            refresh()
        });
    }
    
    function refresh(needReload){
        try{
            if(needReload == null || needReload == undefined){
                needReload = true;
            }
            
            if(needReload){
                
            }
            
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();