(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/shop/basket_controller.js");
        scriptLoader.loadScript("js/shop/content_controller.js");
        scriptLoader.loadScript("js/shop/menu_controller.js");
        scriptLoader.loadScript("js/common/dao/character_dao.js");
        
        this.money = null;
        
        this.displayMoney = displayMoney;
        this.getMoney = getMoney;
        this.refresh = refresh;
        
        $(document).ready(function(){
            refresh();
        });
    }
    
    /*
    Displays the money of the user.
    */
    function displayMoney(needReload){
        try{
            const moneyField = document.getElementById("money");
            
            moneyField.innerHTML = getMoney(needReload);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function getMoney(needUpdate){
        try{
            if(needUpdate == null || needUpdate == undefined){
                needUpdate = false;
            }
            
            if(pageController.money == null || needUpdate == true){
                pageController.money = characterDao.getMoney(sessionStorage.characterId);
            }
            
            return pageController.money;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function refresh(filter, needReload){
        try{
            filter = filter || contentController.filter || "all";
            if(needReload == null || needReload == undefined){
                needReload = true;
            }
            
            displayMoney(needReload);
            contentController.displayElements(filter, needReload);
            basketController.displayBasket();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();