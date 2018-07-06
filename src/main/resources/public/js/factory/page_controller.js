(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/common/dao/character_dao.js");
        scriptLoader.loadScript("js/factory/content_controller.js");
        scriptLoader.loadScript("js/factory/materials_controller.js");
        scriptLoader.loadScript("js/factory/menu_controller.js");
        scriptLoader.loadScript("js/factory/queue_controller.js");
        
        this.refresh = refresh;
        this.money = 0;
        
        $(document).ready(function(){
            refresh(true);
        });
    }

    /*
    Reloads the content of the page.
    Arguments:
        - needReload: If true, queries the actual state from the server, uses stored values otherwise.
    */
    function refresh(needReload){
        try{
            if(needReload){
                materialsController.loadMaterials();
                updateMoney();
                queueController.loadQueue();
            }
            
            materialsController.displayMaterials();
            queueController.displayQueue();
            contentController.displayElementsOfCategory();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }

    /*
    Queries the actual money of the character.
    */
    function updateMoney(){
        try{
            pageController.money = characterDao.getMoney(sessionStorage.characterId);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();