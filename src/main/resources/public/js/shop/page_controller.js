(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/shop/menu_controller.js");
        scriptLoader.loadScript("js/shop/content_controller.js");
        scriptLoader.loadScript("js/common/dao/character_dao.js");
        
        window.money = 0;
        window.basket = [];
        
        this.displayMoney = displayMoney;
        this.refresh = refresh;
        
        $(document).ready(function(){
            refresh();
        });
    }
    
    /*
    Displays the money of the user.
    */
    function displayMoney(){
        try{
            const moneyField = document.getElementById("money");
            window.money = characterDao.getMoney(sessionStorage.characterId);
            
            moneyField.innerHTML = window.money;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function refresh(filter){
        try{
            filter = filter || "all";
            
            contentController.displayElements(filter);
            displayMoney();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();